路由网关(zuul)：主要用于集群，不必关心端口是多少，只要serviceId相同就可以通过路由表匹配

原理：
通过路由表
zuul:
  routes:
    admin-user:// 用户模块
      path: /admin-user/**
      serviceId: learn-shop-admin-user
    core-order:// 所属模块
          path: /core-order/**
          serviceId: learn-shop-core-order
          

在learn-shop-core-order工程中：
spring:
  application:
    name: learn-shop-core-order #客户端名称