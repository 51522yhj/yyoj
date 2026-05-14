   package com.yyu.backendgateway.filter;
   
   import cn.hutool.jwt.JWTUtil;
   import com.yupi.yuojbackendcommon.utils.JwtUtil;
   import com.yupi.yuojbackendcommon.utils.ThreadLocalUtil;
   import lombok.extern.slf4j.Slf4j;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.core.Ordered;
   import org.springframework.core.annotation.Order;
   import org.springframework.http.HttpMethod;
   import org.springframework.stereotype.Component;
   import org.springframework.web.server.ServerWebExchange;
   import org.springframework.web.server.WebFilter;
   import org.springframework.web.server.WebFilterChain;
   import reactor.core.publisher.Mono;

   import java.util.Map;

   @Component
   @Order(Ordered.HIGHEST_PRECEDENCE) // 可选：设置执行顺序
   public class CustomWebFilter implements WebFilter {
   
       private static final Logger log = LoggerFactory.getLogger(CustomWebFilter.class);
   
       @Override
       public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
           // 请求预处理逻辑
           log.info("CustomWebFilter pre-filter, processing URL: {}", exchange.getRequest().getURI());
           String path = exchange.getRequest().getURI().getPath();
           HttpMethod method = exchange.getRequest().getMethod();

           // 只对 GET /gateway/userId 进行拦截
           if ("/gateway/userId".equals(path) && HttpMethod.GET.equals(method)) {
               log.info("SpecificEndpointFilter 拦截到了 GET 请求: {}", path);
               // 这里可以进行前置处理逻辑，比如权限校验、日志记录等
               //获取token
               String token = exchange.getRequest().getHeaders().getFirst("Authorization");
               log.info("token: {}", token);
               //解析token
               Map<String, Object> claims = JwtUtil.parseToken(token);
               Object o = claims.get("id");
               Long userId = Long.parseLong(o.toString());
               ThreadLocalUtil.set(userId);
           }
           return chain.filter(exchange)
                   // 请求后处理逻辑（在响应返回前或响应发送后）
                   .doOnSuccess(aVoid -> log.info("CustomWebFilter post-filter"))
                   .doOnError(throwable -> log.error("CustomWebFilter encountered error", throwable));
       }
   }