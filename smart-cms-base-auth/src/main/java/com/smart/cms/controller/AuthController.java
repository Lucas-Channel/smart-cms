package com.smart.cms.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.smart.cms.constant.SecurityConstants;
import com.smart.cms.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private TokenEndpoint tokenEndpoint;
    private RedisTemplate redisTemplate;
    private KeyPair keyPair;

    @ApiOperation(value = "OAuth2认证", notes = "登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "用户密码")
    })
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {

        /**
         * 获取登录认证的客户端ID
         *
         * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
         * 方式一：client_id、client_secret放在请求路径中(注：当前版本已废弃)
         * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientId = request.getParameter(SecurityConstants.GRANT_TYPE_KEY);
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(parameters));
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return R.ok(accessToken);
    }

    @ApiOperation(value = "注销")
    @DeleteMapping("/logout")
    public R logout() {
        JSONObject payload = JwtUtils.getJwtPayload();
        String jti = payload.getStr(SecurityConstants.JWT_JTI); // JWT唯一标识
        Long expireTime = payload.getLong(SecurityConstants.JWT_EXP); // JWT过期时间戳(单位：秒)
        if (expireTime != null) {
            long currentTime = System.currentTimeMillis() / 1000;// 当前时间（单位：秒）
            if (expireTime > currentTime) { // token未过期，添加至缓存作为黑名单限制访问，缓存时间为token过期剩余时间
                redisTemplate.opsForValue().set(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (expireTime - currentTime), TimeUnit.SECONDS);
            }
        } else { // token 永不过期则永久加入黑名单
            redisTemplate.opsForValue().set(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti, null);
        }
        return R.ok("注销成功");
    }

    @ApiOperation(value = "获取公钥")
    @GetMapping("/public-key")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
