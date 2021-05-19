package com.smart.cms.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

/**
 * 请求信息过滤器
 *
 * @author Lacus
 * @create 20210427
 */
@Component
@AllArgsConstructor
public class RequestInfoFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;

    private static Logger log = LoggerFactory.getLogger(RequestInfoFilter.class);

    protected String getRemoteHost(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
            log.debug("Proxy-Client-IP ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
            log.debug("WL-Proxy-Client-IP ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
            log.debug("HTTP_CLIENT_IP ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
            log.debug("HTTP_X_FORWARDED_FOR ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
            log.debug("X-Real-IP ip:{}", ip);
        }
        log.debug("当前访问IP为：{}", ip);
        return ip;
    }

    protected String getClientType(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String clientType = headers.getFirst("User-Agent");
        if (clientType == null || clientType.length() == 0 || "unknown".equalsIgnoreCase(clientType)) {
            clientType = "";
        }
        log.debug("当前访问客户端类型为：{}", clientType);
        return clientType;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 校验token全局返回信息
//        ServerHttpResponse resp = exchange.getResponse();
//        String headerToken = exchange.getRequest().getHeaders().getFirst("headerToken");
//        String paramToken = exchange.getRequest().getQueryParams().getFirst("param-token");
//        if (StringUtils.isBlank(headerToken) && StringUtils.isBlank(paramToken)) {
//            return unAuth(resp, "缺失令牌,鉴权失败");
//        }
//        String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
//        String token = JwtUtil.getToken(auth);
//        Claims claims = JwtUtil.parseJWT(token);
//        if (claims == null) {
//            return unAuth(resp, "请求未授权");
//        }
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    ServerHttpRequest request = exchange.getRequest();
                    this.getRemoteHost(request);
                    this.getClientType(request);
                    Set<URI> uris = exchange.getAttributeOrDefault(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
                    String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
                    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                    URI routeUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
                    log.info("\n 路由ID：{}，执行方法：{}", route.getId(), request.getMethod());
                    log.info("\r 请求URL--> {}", originalUri);
                    log.info("\r 路由到 --> {}", routeUri);
                })
        );
    }

    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}