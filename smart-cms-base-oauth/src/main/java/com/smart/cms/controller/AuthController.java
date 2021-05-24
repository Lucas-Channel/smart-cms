package com.smart.cms.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.smart.cms.utils.other.DateUtils;
import com.smart.cms.vo.Oauth2Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

@Api(tags = "认证中心认证登录")
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private CheckTokenEndpoint checkTokenEndpoint;

    @ApiOperation("Oauth2获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type",paramType = "query", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", paramType = "query",value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret",paramType = "query",value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token",paramType = "query", value = "刷新token"),
            @ApiImplicitParam(name = "username",paramType = "query", value = "登录用户名"),
            @ApiImplicitParam(name = "password",paramType = "query",value = "登录密码")
    })
    @PostMapping("/token")
    public R postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2Token oauth2Token = Oauth2Token.builder()
                .accessToken(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .scope(oAuth2AccessToken.getScope())
                .tokenType(oAuth2AccessToken.getTokenType())
                .build();
        return R.ok(oauth2Token);
    }

    @GetMapping(value = "/check_token")
    public Map<String, Object> checkToken(@RequestParam("access_token") String value) {

        Map<String, Object> map = (Map<String, Object>)checkTokenEndpoint.checkToken(value);
        String expire_date = DateUtils.timeStamp2Date(map.get("exp").toString(), "yyyy-MM-dd HH:mm:ss");
        map.put("expire_date", expire_date);
        return map;
    }

    @GetMapping("/testApi")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R<String> test() {
        return R.ok("测试");
    }
}
