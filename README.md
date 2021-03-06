<p align="center">
    <img src="https://img.shields.io/badge/SpringBoot-2.3.4-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/SpringCloud & Alibaba -2021-green.svg"/>
    <img src="https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg"/>
    <img src="https://img.shields.io/badge/Gateway-2.2.5-blue.svg"/>
    <img src="https://img.shields.io/badge/oauth2-5.3.4-critical.svg"/>
    <img src="https://img.shields.io/badge/redis-2.3.4-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/Mysql-8.0.21-ff69b4.svg"/>
</p>

## 项目介绍
### SmartCmsSystem 智能管理销售系统
* 项目背景 
 >互联网的快速发展，加上全国受疫情影响导致线下工作很难展开，同时伴随科技的发展，现在web和app的功能也越来越完善，线下的功能都几乎都可以搬到线上处理，实现数据明晰化以及加速行业的快速发展。本项目开发的目的是为中小型销售类企业节约时间和人力成本，预估行业数据走向，加快企业对信息化社会的融入。主要功能包括了:用户(会员)管理，商品管理，库存管理，订单管理，财务管理，积分管理等。

* 项目介绍
 >`SmartCms`系统是基于当前主流技术框架所搭建的一个基础服务框架，其中主要集成了：`spring boot，spring cloud，security oauth2, alibaba nacos, gateway, element-ui`。

## 技术栈
* spring cloud / spring boot
* spring security
* spring oauth2
* spring gateway
* swagger
* springMVC
* mybatisPlus
* MySQL
* redis

## 开发环境
+ jdk1.8及以上
+ maven 3.5及以上
+ idea
+ redis
+ mysql
+ nacos

## 项目模块
* doc文档是存储技术文档或者其他开发文档的地址
* database是存储脚本文件的地址，注意命名格式
### 层级
> #### entity
> >  * 为了区分数据库entity、vo、rest，为了更好的使用mybatisplus的使用，需要明确的区分开来，具体层级如下
> >  1. System
> >  2. project
>
> #### gateway
> >  * 实现系统的统一认证管理授权
> >  `+ 用户密码认证` 
> >  `+ 授权码认证` 
> >  `+ 短信认证` 
> >  `+ 扫码认证` 
>
> #### oauth
> > * 实现资源认证，获取登入令牌
>
> #### common
> > * 公共模块，工具类存放（后期还是需要拆分）
>
> #### service
> > * 业务模块
>
> #### api
> > * 开放接口，供跨服务调用

## 基本流程图
```
    A[web] -->B[gateWay]
    B -->C{授权}
    C -->|用户模块| D[接口]
    C -->|订单模块| E[接口]
    C -->|其他模块| F[接口]
```
## 获取授权码和获取token
### 授权码模式response_type=code
* /oauth/authorize?client_id=客户端id&response_type=code&scope=all&redirect_ui=www.baidu.com
* /oauth/token?client_id=客户端id&client_secret=密钥&grant_type=authorization_code&code=上面地址返回的code&redirect_ui=www.baidu.com
### 简易模式response_type=token
* /oauth/authorize?client_id=客户端id&response_type=token&scope=all&redirect_ui=www.baidu.com
### 密码模式
* /oauth/token?client_id=客户端id&client_secret=密钥&grant_type=password&username=s&password=2312
### 短信模式
* /oauth/token?grant_type=SMS&mobile=手机号&code=验证码
