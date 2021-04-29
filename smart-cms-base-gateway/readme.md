路由网关(zuul)：主要用于集群，不必关心端口是多少，只要serviceId相同就可以通过路由表匹配

```
因为gateway和zuul不一样，gateway用的是长连接，netty-webflux，zuul1.0用的就是同步webmvc。所以你的非gateway子项目启动用的是webmvc，你的gateway启动用的是webflux. spring-boot-start-web下面的 spring-webmvc和spring-boot-start-webflux相见分外眼红。不能配置在同一pom.xml，或者不能在同一项目中出现。
```

原理：
通过路由表
gateway:
  routes:
        - id: public-auth
          # lb代表从注册中心获取服务，且已负载均衡方式转发
          uri: lb://learn-shop-public-auth
          predicates:
           - Path=/public-auth/**
          # 加上StripPrefix=1，否则转发到后端服务时会带上consumer前缀
          filters:
           - StripPrefix=1
          

在order工程中：
spring:
  application:
    name: learn-shop-core-order #客户端名称