package com.yyu.backendgateway.filter;

import cn.hutool.json.JSONUtil;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.common.ResultUtils;
import com.yupi.yuojbackendcommon.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: she
 * @Author: Yhj
 * @Date: 2025/2/1 19:21
 */
@Component
@Slf4j
public class ThreadFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("ThreadFilter filter method called for request: {}", exchange.getRequest().getURI());
        if (exchange.getRequest().getURI().getPath().contains("/get/login")||exchange.getRequest().getURI().getPath().contains("/login")||exchange.getRequest().getURI().getPath().contains("/sandbox")||exchange.getRequest().getURI().getPath().contains("/register")
                ||exchange.getRequest().getURI().getPath().contains("/doc.html")||exchange.getRequest().getURI().getPath().contains("/swagger-ui.html")||exchange.getRequest().getURI().getPath().contains("/webjars")||exchange.getRequest().getURI().getPath().contains("/api-docs")
        ){
            return chain.filter(exchange);
        }
        // 令牌验证
        // 前端请求拦截器中设置Authorization值为token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.info("token: {}", token);
        if (token == null || token.isEmpty()) {
            // 如果没有token，设置响应状态码为401并返回
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
            exchange.getResponse().getHeaders().set("Content-Type", "application/json");
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr(ResultUtils.error(ErrorCode.TOKEN_ERROR)).getBytes())));
        }

//        try {
            // 从Redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        Map<String, Object> claims = null ;
        try {
            claims = JwtUtil.parseToken(token);
//            Object userId =  claims.get("id");
//            Long userIdLong = Long.parseLong(userId.toString());
//            log.info("userIdLong: {}", userIdLong);
//            ThreadLocalUtil.set(userIdLong);
//            log.info("Interceptor Thread ID: {}", Thread.currentThread().getId());
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
            exchange.getResponse().getHeaders().set("Content-Type", "application/json");
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr(ResultUtils.error(ErrorCode.TOKEN_ERROR)).getBytes())));

        }
   //     log.info("claims: {}", claims);
            String redisToken = operations.get("token:"+claims.get("id"));
            if (redisToken == null) {
                // token已经失效了
                // 如果没有token，设置响应状态码为401并返回
                exchange.getResponse().setStatusCode(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
                exchange.getResponse().getHeaders().set("Content-Type", "application/json");
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr(ResultUtils.error(ErrorCode.TOKEN_ERROR)).getBytes())));

            }
            else if (!redisToken.equals(token)) {
//                throw new BusinessException(ErrorCode.USER_ALREADY_LOGIN);
                // 如果没有token，设置响应状态码为401并返回
                exchange.getResponse().setStatusCode(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));

                exchange.getResponse().getHeaders().set("Content-Type", "application/json");
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr(ResultUtils.error(ErrorCode.USER_ALREADY_LOGIN)).getBytes())));

            }

            // 把业务数据存储到ThreadLocal中
      //      ThreadLocalUtil.set(claims);

            // 放行请求
            return chain.filter(exchange).doFinally(signalType -> {
//                log.error("清理ThreadLocal数据");
//                // 清空ThreadLocal中的数据
//                ThreadLocalUtil.remove();
            });
//        } catch (Exception e) {
//            log.error("Token validation failed", e);
//            exchange.getResponse().setStatusCode(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
//            exchange.getResponse().getHeaders().set("Content-Type", "application/json");
//            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr("Invalid token").getBytes())));
//        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
