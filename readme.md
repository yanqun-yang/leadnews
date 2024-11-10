- [基于微服务的头条类项目](#基于微服务的头条类项目)
  - [一、项目背景介绍](#一项目背景介绍)
    - [1. 项目概述](#1-项目概述)
    - [2. 功能架构图](#2-功能架构图)
    - [3. 技术栈说明](#3-技术栈说明)
      - [3.1 基础层](#31-基础层)
      - [3.2 服务层](#32-服务层)
    - [4. 项目主体结构](#4-项目主体结构)
  - [二、登录功能实现](#二登录功能实现)
    - [1. 需求设计](#1-需求设计)
    - [2. 表结构分析](#2-表结构分析)
    - [3. ap\_user表结构如下：](#3-ap_user表结构如下)
      - [3.1 使用mybais-plus逆向生成对应的实体类](#31-使用mybais-plus逆向生成对应的实体类)
    - [4. 手动加密（md5+随机字符串）](#4-手动加密md5随机字符串)
    - [5. 思路分析](#5-思路分析)
      - [5.1 核心代码](#51-核心代码)
    - [6.  全局过滤器实现jwt校验](#6--全局过滤器实现jwt校验)
  - [三、接口测试工具postman、swagger、knife4j](#三接口测试工具postmanswaggerknife4j)
    - [1. postman](#1-postman)
    - [2.swagger](#2swagger)
      - [2.1 Swagger常用注解](#21-swagger常用注解)
      - [2.2 访问接口文档](#22-访问接口文档)
    - [3. knife4j](#3-knife4j)
      - [3.1 核心功能](#31-核心功能)
      - [3.2 访问接口文档](#32-访问接口文档)
  - [四、Nginx](#四nginx)
  - [五、文章列表加载](#五文章列表加载)
    - [1. 需求分析](#1-需求分析)
    - [2. 表结构分析](#2-表结构分析-1)
      - [2.1 表的拆分-垂直分表](#21-表的拆分-垂直分表)
    - [3. 实现思路](#3-实现思路)
    - [4. 数据库查询](#4-数据库查询)
    - [5. 核心代码](#5-核心代码)
  - [六、freemarker](#六freemarker)
    - [1. 基础语法种类](#1-基础语法种类)
    - [2. 集合指令（List和Map）](#2-集合指令list和map)
    - [3. if指令](#3-if指令)
    - [4. 运算符](#4-运算符)
      - [4.1. 算数运算符](#41-算数运算符)
      - [4.2. 比较运算符](#42-比较运算符)
      - [4.3. 逻辑运算符](#43-逻辑运算符)
    - [5. 空值处理](#5-空值处理)
    - [6. 内建函数](#6-内建函数)
    - [7. 静态化测试](#7-静态化测试)
  - [七、对象存储服务MinIO](#七对象存储服务minio)
    - [1. MinIO简介](#1-minio简介)
    - [2. MinIO特点](#2-minio特点)
    - [3. 使用docker进行环境部署和启动](#3-使用docker进行环境部署和启动)
    - [4. 核心代码](#4-核心代码)
  - [八、文章详情](#八文章详情)
    - [1. 需求分析](#1-需求分析-1)
    - [2. 实现方案](#2-实现方案)
    - [3. 核心代码](#3-核心代码)
  - [九、自媒体文章发布](#九自媒体文章发布)
    - [1. 后台搭建](#1-后台搭建)
    - [2. 前台搭建](#2-前台搭建)
    - [3. 自媒体素材管理](#3-自媒体素材管理)
      - [3.1 素材上传](#31-素材上传)
        - [3.1.1 需求分析](#311-需求分析)
        - [3.1.2 表结构](#312-表结构)
        - [3.1.3 实现思路](#313-实现思路)
        - [3.1.4 接口定义](#314-接口定义)
        - [3.1.5 核心代码](#315-核心代码)
      - [3.2 素材列表查询](#32-素材列表查询)
        - [3.2.1 核心代码](#321-核心代码)
    - [4. 自媒体文章管理](#4-自媒体文章管理)
      - [4.1 查询所有频道](#41-查询所有频道)
        - [4.1.1 需求分析](#411-需求分析)
        - [4.1.2 表结构](#412-表结构)
        - [4.1.3 接口定义](#413-接口定义)
        - [4.1.4 核心代码](#414-核心代码)
      - [4.2 查询自媒体文章](#42-查询自媒体文章)
        - [4.2.1 需求说明](#421-需求说明)
        - [4.2.2 表结构分析](#422-表结构分析)
        - [4.2.3 枚举类](#423-枚举类)
        - [4.2.4 接口定义](#424-接口定义)
        - [4.2.5 核心代码](#425-核心代码)
      - [4.3 文章发布](#43-文章发布)
        - [4.3.1 需求分析](#431-需求分析)
        - [4.3.2 表结构分析](#432-表结构分析)
        - [4.3.3 实现思路](#433-实现思路)
        - [4.3.4 接口定义](#434-接口定义)
        - [4.3.5 核心代码](#435-核心代码)


# 基于微服务的头条类项目

## 一、项目背景介绍

### 1. 项目概述

&emsp;类似于今日头条/腾讯新闻，是一个新闻资讯类项目。随着智能手机的普及，人们更加习惯于通过手机来看新闻。由于生活节奏的加快，很多人只能利用碎片时间来获取信息，因此，对于移动资讯客户端的需求也越来越高。头条项目采用当下火热的微服务+大数据技术架构实现。本项目主要着手于获取最新最热新闻资讯，通过大数据分析用户喜好精确推送咨询新闻。

&emsp;本系统分为普通用户、作者、管理员三种角色

- 普通用户：注册登录 浏览文章 关注 点赞 喜欢 评论 回复评论 点赞评论 搜索文章 搜索历史记录
- 作者：拥有普通用户所有功能 图片素材管理 发布文章 上下架文章

- 管理员：用户列表 用户申请成为作者列表 审核 频道管理（文章类型管理）文章管理审核 敏感词管理

文章发布还接入了阿里云文字和图片识别，内容安全涉黄涉黑检测

### 2. 功能架构图

<div align="center">
    <img src="截图/项目背景介绍/功能架构图.png" alt="功能架构图" />
</div>

### 3. 技术栈说明

#### 3.1 基础层

<div align="center">
    <img src="截图/项目背景介绍/基础层.png" alt="基础层" />
</div>

#### 3.2 服务层

<div align="center">
    <img src="截图/项目背景介绍/服务层.png" alt="服务层" />
</div>

- Spring-Cloud-Gateway : 微服务之前架设的网关服务，实现服务注册中的API请求路由，以及控制流速控制和熔断处理都是常用的架构手段，而这些功能Gateway天然支持
- 运用Spring Boot快速开发框架，构建项目工程；并结合Spring Cloud全家桶技术，实现后端个人中心、自媒体、管理中心等微服务。
- 运用Spring Cloud Alibaba Nacos作为项目中的注册中心和配置中心
- 运用mybatis-plus作为持久层提升开发效率
- 运用Kafka完成内部系统消息通知；与客户端系统消息通知；以及实时数据计算
- 运用Redis缓存技术，实现热数据的计算，提升系统性能指标
- 使用Mysql存储用户数据，以保证上层数据查询的高性能
- 使用Mongo存储用户热数据，以保证用户热数据高扩展和高性能指标
- 使用FastDFS作为静态资源存储器，在其上实现热静态资源缓存、淘汰等功能
- 运用Hbase技术，存储系统中的冷数据，保证系统数据的可靠性
- 运用ES搜索技术，对冷数据、文章数据建立索引，以保证冷数据、文章查询性能
- 运用AI技术，来完成系统自动化功能，以提升效率及节省成本。比如实名认证自动化
- PMD&P3C : 静态代码扫描工具，在项目中扫描项目代码，检查异常点、优化点、代码规范等，为开发团队提供规范统一，提升项目代码质量

### 4. 项目主体结构

<div align="center">
    <img src="截图/项目背景介绍/工程主体结构.png" alt="工程主体结构" />
</div>



## 二、登录功能实现

### 1. 需求设计

- 用户点击**开始使用**

  登录后的用户权限较大，可以查看，也可以操作（点赞，关注，评论）

- 用户点击**不登录，先看看**

​       游客只有查看的权限

### 2. 表结构分析

&emsp;关于app端用户相关的内容较多，可以单独设置一个库leadnews_user

| **表名称**       | **说明**          |
| ---------------- | :---------------- |
| ap_user          | APP用户信息表     |
| ap_user_fan      | APP用户粉丝信息表 |
| ap_user_follow   | APP用户关注信息表 |
| ap_user_realname | APP实名认证信息表 |

### 3. ap_user表结构如下：

```mysql
-- auto-generated definition
create table ap_user
(
    id                         int unsigned auto_increment comment '主键'
        primary key,
    salt                       varchar(32)      null comment '密码、通信等加密盐',
    name                       varchar(20)      null comment '用户名',
    password                   varchar(32)      null comment '密码,md5加密',
    phone                      varchar(11)      null comment '手机号',
    image                      varchar(255)     null comment '头像',
    sex                        tinyint unsigned null comment '0 男
            1 女
            2 未知',
    is_certification           tinyint unsigned null comment '0 未
            1 是',
    is_identity_authentication tinyint(1)       null comment '是否身份认证',
    status                     tinyint unsigned null comment '0正常
            1锁定',
    flag                       tinyint unsigned null comment '0 普通用户
            1 自媒体人
            2 大V',
    created_time               datetime         null comment '注册时间'
)
    comment 'APP用户信息表' row_format = DYNAMIC;
```

<div align="center">
    <img src="截图/登录功能实现/tinyint.png" alt="tinyint" />
</div>


#### 3.1 使用mybais-plus逆向生成对应的实体类

&emsp;项目中的持久层使用的mybatis-plus，一般都使用mybais-plus逆向生成对应的实体类

app_user表对应的实体类如下：

```java
package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author itheima
 */
@Data
@TableName("ap_user")
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码、通信等加密盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 密码,md5加密
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 头像
     */
    @TableField("image")
    private String image;

    /**
     * 0 男
            1 女
            2 未知
     */
    @TableField("sex")
    private Boolean sex;

    /**
     * 0 未
            1 是
     */
    @TableField("is_certification")
    private Boolean certification;

    /**
     * 是否身份认证
     */
    @TableField("is_identity_authentication")
    private Boolean identityAuthentication;

    /**
     * 0正常
            1锁定
     */
    @TableField("status")
    private Boolean status;

    /**
     * 0 普通用户
            1 自媒体人
            2 大V
     */
    @TableField("flag")
    private Short flag;

    /**
     * 注册时间
     */
    @TableField("created_time")
    private Date createdTime;

}
```

### 4. 手动加密（md5+随机字符串）

&emsp;md5是不可逆加密，md5相同的密码每次加密都一样，不太安全。在md5的基础上手动加盐（salt）处理

注册->生成盐

<div align="center">
    <img src="截图/登录功能实现/注册.png" alt="注册" />
</div>


登录->使用盐来配合验证

<div align="center">
    <img src="截图/登录功能实现/登录.png" alt="登录" />
</div>


### 5. 思路分析

<div align="center">
    <img src="截图/登录功能实现/思路分析.png" alt="思路分析" />
</div>


1. 用户输入了用户名和密码进行登录，校验成功后返回jwt**(基于当前用户的id生成，数据库id必大于0)**

2. 用户游客登录，生成jwt返回**(基于默认值0生成)**

#### 5.1 核心代码

```java
package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;


@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Override
    public ResponseResult login(LoginDto dto) {

        //1.正常登录（手机号+密码登录）
        if (!StringUtils.isBlank(dto.getPhone()) && !StringUtils.isBlank(dto.getPassword())) {
            //1.1查询用户
            ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (apUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
            }

            //1.2 比对密码
            String salt = apUser.getSalt();
            String pswd = dto.getPassword();
            pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
            if (!pswd.equals(apUser.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            //1.3 返回数据  jwt
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(apUser.getId().longValue()));
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);
            return ResponseResult.okResult(map);
        } else {
            //2.游客  同样返回token  id = 0
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(0l));
            return ResponseResult.okResult(map);
        }
    }
}
```

### 6.  全局过滤器实现jwt校验

<div align="center">
    <img src="截图/登录功能实现/全局过滤器实现jwt校验.png" alt="全局过滤器实现jwt校验" />
</div>

思路分析：

1. 用户进入网关开始登陆，网关过滤器进行判断，如果是登录，则路由到后台管理微服务进行登录
2. 用户登录成功，后台管理微服务签发JWT TOKEN信息返回给用户
3. 用户再次进入网关开始访问，网关过滤器接收用户携带的TOKEN 
4. 网关过滤器解析TOKEN ，判断是否有权限，如果有，则放行，如果没有则返回未认证错误

具体实现：

1. 在认证过滤器中需要用到jwt的解析，所以需要把工具类拷贝一份到网关微服务

2. 在网关微服务中新建全局过滤器：

```java
package com.heima.app.gateway.filter;


import com.heima.app.gateway.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.判断是否是登录
        if(request.getURI().getPath().contains("/login")){
            //放行
            return chain.filter(exchange);
        }


        //3.获取token
        String token = request.getHeaders().getFirst("token");

        //4.判断token是否存在
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //5.判断token是否有效
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            //是否是过期
            int result = AppJwtUtil.verifyToken(claimsBody);
            if(result == 1 || result  == 2){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //6.放行
        return chain.filter(exchange);
    }

    /**
     * 优先级设置  值越小  优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
```



## 三、接口测试工具postman、swagger、knife4j

### 1. postman

&emsp;Postman是一款功能强大的网页调试与发送网页HTTP请求的Chrome插件。postman被500万开发者和超100,000家公司用于每月访问1.3亿个API。官方网址：https://www.postman.com/

### 2.swagger

&emsp;Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务(<https://swagger.io/>)。 它的主要作用是：

1. 使得前后端分离开发更加方便，有利于团队协作

2. 接口的文档在线自动生成，降低后端开发人员编写接口文档的负担

3. 功能测试：Spring已经将Swagger纳入自身的标准，建立了Spring-swagger项目，现在叫Springfox。通过在项目中引入Springfox ，即可非常简单快捷的使用Swagger。


#### 2.1 Swagger常用注解

在Java类中添加Swagger的注解即可生成Swagger接口文档，常用Swagger注解如下：

@Api：修饰整个类，描述Controller的作用  

@ApiOperation：描述一个类的一个方法，或者说一个接口  

@ApiParam：单个参数的描述信息  

@ApiModel：用对象来接收参数  

@ApiModelProperty：用对象接收参数时，描述对象的一个字段  

@ApiResponse：HTTP响应其中1个描述  

@ApiResponses：HTTP响应整体描述  

@ApiIgnore：使用该注解忽略这个API  

@ApiError ：发生错误返回的信息  

@ApiImplicitParam：一个请求参数  

@ApiImplicitParams：多个请求参数的描述信息

#### 2.2 访问接口文档

&emsp;启动user微服务，访问地址：http://localhost:port/swagger-ui.html

### 3. knife4j

&emsp;knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案,前身是swagger-bootstrap-ui,取名kni4j是希望它能像一把匕首一样小巧,轻量,并且功能强悍!

gitee地址：https://gitee.com/xiaoym/knife4j

官方文档：https://doc.xiaominfo.com/

效果演示：http://knife4j.xiaominfo.com/doc.html

#### 3.1 核心功能

该UI增强包主要包括两大核心功能：文档说明 和 在线调试

- 文档说明：根据Swagger的规范说明，详细列出接口文档的说明，包括接口地址、类型、请求示例、请求参数、响应示例、响应参数、响应码等信息，使用swagger-bootstrap-ui能根据该文档说明，对该接口的使用情况一目了然。
- 在线调试：提供在线接口联调的强大功能，自动解析当前接口参数,同时包含表单验证，调用参数可返回接口响应内容、headers、Curl请求命令实例、响应时间、响应状态码等信息，帮助开发者在线调试，而不必通过其他测试工具测试接口是否正确，简洁、强大。
- 个性化配置：通过个性化ui配置项，可自定义UI的相关显示信息
- 离线文档：根据标准规范，生成的在线markdown离线文档，开发者可以进行拷贝生成markdown接口文档，通过其他第三方markdown转换工具转换成html或pdf，这样也可以放弃swagger2markdown组件
- 接口排序：自1.8.5后，ui支持了接口排序功能，例如一个注册功能主要包含了多个步骤,可以根据swagger-bootstrap-ui提供的接口排序规则实现接口的排序，step化接口操作，方便其他开发者进行接口对接

#### 3.2 访问接口文档

&emsp;在浏览器输入地址：`http://host:port/doc.html`

## 四、Nginx

<div align="center">
    <img src="截图/登录功能实现/nginx.png" alt="nginx" />
</div>

通过nginx来进行配置，功能如下

- 通过nginx的**反向代理**功能访问后台的网关资源
- 通过nginx的**静态服务器**功能访问前端静态页面

## 五、文章列表加载

### 1. 需求分析

文章布局展示

<div align="center">
    <img src="截图/文章列表加载/文章布局展示.png" alt="文章布局展示" />
</div>

### 2. 表结构分析

ap_article  文章基本信息表

```mysql
-- auto-generated definition
create table ap_article
(
    id           bigint unsigned auto_increment
        primary key,
    title        varchar(50)                  null comment '标题',
    author_id    int unsigned                 null comment '文章作者的ID',
    author_name  varchar(20)                  null comment '作者昵称',
    channel_id   int unsigned                 null comment '文章所属频道ID',
    channel_name varchar(10)                  null comment '频道名称',
    layout       tinyint unsigned             null comment '文章布局
            0 无图文章
            1 单图文章
            2 多图文章',
    flag         tinyint unsigned             null comment '文章标记
            0 普通文章
            1 热点文章
            2 置顶文章
            3 精品文章
            4 大V 文章',
    images       varchar(1000)                null comment '文章图片
            多张逗号分隔',
    labels       varchar(500)                 null comment '文章标签最多3个 逗号分隔',
    likes        int unsigned                 null comment '点赞数量',
    collection   int unsigned                 null comment '收藏数量',
    comment      int unsigned                 null comment '评论数量',
    views        int unsigned                 null comment '阅读数量',
    province_id  int unsigned                 null comment '省市',
    city_id      int unsigned                 null comment '市区',
    county_id    int unsigned                 null comment '区县',
    created_time datetime                     null comment '创建时间',
    publish_time datetime                     null comment '发布时间',
    sync_status  tinyint(1)       default 0   null comment '同步状态',
    origin       tinyint unsigned default '0' null comment '来源',
    static_url   varchar(150)                 null
)
    comment '文章信息表，存储已发布的文章' row_format = DYNAMIC;
```

ap_article_config  文章配置表

```mysql
-- auto-generated definition
create table ap_article_config
(
    id         bigint unsigned auto_increment comment '主键'
        primary key,
    article_id bigint unsigned  null comment '文章ID',
    is_comment tinyint unsigned null comment '是否可评论',
    is_forward tinyint unsigned null comment '是否转发',
    is_down    tinyint unsigned null comment '是否下架',
    is_delete  tinyint unsigned null comment '是否已删除'
)
    comment 'APP已发布文章配置表' row_format = DYNAMIC;

create index idx_article_id
    on ap_article_config (article_id);
```

ap_article_content 文章内容表

```mysql
-- auto-generated definition
create table ap_article_content
(
    id         bigint unsigned auto_increment comment '主键'
        primary key,
    article_id bigint unsigned null comment '文章ID',
    content    longtext        null comment '文章内容'
)
    comment 'APP已发布文章内容表' row_format = DYNAMIC;

create index idx_article_id
    on ap_article_content (article_id);
```

三张表关系分析

<div align="center">
    <img src="截图/文章列表加载/三表关系.png" alt="三表关系" />
</div>
#### 2.1 表的拆分-垂直分表

&emsp;垂直分表：将一个表的字段分散到多个表中，每个表存储其中一部分字段。

优势：

1. 减少IO争抢，减少锁表的几率，查看文章概述与文章详情互不影响
2. 充分发挥高频数据的操作效率，对文章概述数据操作的高效率不会被操作文章详情数据的低效率所拖累

拆分原则：

1. 把不常用的字段单独放在一张表
2. 把text，blob等大字段拆分出来单独放在一张表
3. 经常组合查询的字段单独放在一张表中

### 3. 实现思路

<div align="center">
    <img src="截图/文章列表加载/实现思路.png" alt="实现思路" />
</div>

1. 在默认频道展示10条文章信息

2. 可以切换频道查看不同种类文章

3. 当用户下拉可以加载最新的文章（分页）本页文章列表中发布时间为最大的时间为依据

4. 当用户上拉可以加载更多的文章信息（按照发布时间）本页文章列表中发布时间最小的时间为依据

5. 如果是当前频道的首页，前端传递默认参数：

- maxBehotTime：0（毫秒）

- minBehotTime：20000000000000（毫秒）--->2063年

### 4. 数据库查询

Mapper接口对应的映射文件

在resources中新建mapper/ApArticleMapper.xml     如下配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.heima.article.mapper.ApArticleMapper">

    <resultMap id="resultMap" type="com.heima.model.article.pojos.ApArticle">
        <id column="id" property="id"/>
        <result column="title" property="title"/>
        <result column="author_id" property="authorId"/>
        <result column="author_name" property="authorName"/>
        <result column="channel_id" property="channelId"/>
        <result column="channel_name" property="channelName"/>
        <result column="layout" property="layout"/>
        <result column="flag" property="flag"/>
        <result column="images" property="images"/>
        <result column="labels" property="labels"/>
        <result column="likes" property="likes"/>
        <result column="collection" property="collection"/>
        <result column="comment" property="comment"/>
        <result column="views" property="views"/>
        <result column="province_id" property="provinceId"/>
        <result column="city_id" property="cityId"/>
        <result column="county_id" property="countyId"/>
        <result column="created_time" property="createdTime"/>
        <result column="publish_time" property="publishTime"/>
        <result column="sync_status" property="syncStatus"/>
        <result column="static_url" property="staticUrl"/>
    </resultMap>
    <select id="loadArticleList" resultMap="resultMap">
        SELECT
        aa.*
        FROM
        `ap_article` aa
        LEFT JOIN ap_article_config aac ON aa.id = aac.article_id
        <where>
            and aac.is_delete != 1
            and aac.is_down != 1
            <!-- loadmore -->
            <if test="type != null and type == 1">
                and aa.publish_time <![CDATA[<]]> #{dto.minBehotTime}
            </if>
            <!-- loadnew -->
            <if test="type != null and type == 2">
                and aa.publish_time <![CDATA[>]]> #{dto.maxBehotTime}
            </if>
            <if test="dto.tag != '__all__'">
                and aa.channel_id = #{dto.tag}
            </if>
        </where>
        order by aa.publish_time desc
        limit #{dto.size}
    </select>

</mapper>
```

### 5. 核心代码

```java
package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleHomeDto;

import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
@Slf4j
public class ApArticleServiceImpl  extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 根据参数加载文章列表
     * @param loadtype 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    @Override
    public ResponseResult load(Short loadtype, ArticleHomeDto dto) {
        //1.校验参数
        Integer size = dto.getSize();
        if(size == null || size == 0){
            size = 10;
        }
        size = Math.min(size,MAX_PAGE_SIZE);
        dto.setSize(size);

        //类型参数检验
        if(!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_MORE)&&!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            loadtype = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        //文章频道校验
        if(StringUtils.isEmpty(dto.getTag())){
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        //时间校验
        if(dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if(dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());
        //2.查询数据
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(dto, loadtype);

        //3.结果封装
        ResponseResult responseResult = ResponseResult.okResult(apArticles);
        return responseResult;
    }
    
}
```

定义常量类

```java
package com.heima.common.constants;

public class ArticleConstants {
    public static final Short LOADTYPE_LOAD_MORE = 1;
    public static final Short LOADTYPE_LOAD_NEW = 2;
    public static final String DEFAULT_TAG = "__all__";

}
```

## 六、freemarker

&emsp;FreeMarker 是一款**模板引擎**： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。 它不是面向最终用户的，而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件。

&emsp;模板编写为FreeMarker Template Language (FTL)。它是简单的，专用的语言， *不是* 像PHP那样成熟的编程语言。 那就意味着要准备数据在真实编程语言中来显示，比如数据库查询和业务运算， 之后模板显示已经准备好的数据。在模板中，你可以专注于如何展现数据， 而在模板之外可以专注于要展示什么数据。 

<div align="center">
    <img src="截图/文章列表加载/freemarker.png" alt="freemarker" />
</div>

常用的java模板引擎还有哪些？

Jsp、Freemarker、Thymeleaf 、Velocity 等。

1. Jsp 为 Servlet 专用，不能单独进行使用。

2. Thymeleaf 为新技术，功能较为强大，但是执行的效率比较低。

3. Velocity从2010年更新完 2.0 版本后，便没有在更新。Spring Boot 官方在 1.4 版本后对此也不在支持，虽然 Velocity 在 2017 年版本得到迭代，但为时已晚。 

### 1. 基础语法种类

  1、注释，即<#--  -->，介于其之间的内容会被freemarker忽略

```velocity
<#--我是一个freemarker注释-->
```

  2、插值（Interpolation）：即 **`${..}`** 部分,freemarker会用真实的值代替**`${..}`**

```velocity
Hello ${name}
```

  3、FTL指令：和HTML标记类似，名字前加#予以区分，Freemarker会解析标签中的表达式或逻辑。

```velocity
<# >FTL指令</#> 
```

  4、文本，仅文本信息，这些不是freemarker的注释、插值、FTL指令的内容会被freemarker忽略解析，直接输出内容。

```velocity
我是一个普通的文本
```

### 2. 集合指令（List和Map）

实例代码

```velocity
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
    
<#-- list 数据的展示 -->
<b>展示list中的stu数据:</b>
<br>
<br>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stus as stu>
        <tr>
            <td>${stu_index+1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.money}</td>
        </tr>
    </#list>

</table>
<hr>
    
<#-- Map 数据的展示 -->
<b>map数据的展示：</b>
<br/><br/>
<a href="###">方式一：通过map['keyname'].property</a><br/>
输出stu1的学生信息：<br/>
姓名：${stuMap['stu1'].name}<br/>
年龄：${stuMap['stu1'].age}<br/>
<br/>
<a href="###">方式二：通过map.keyname.property</a><br/>
输出stu2的学生信息：<br/>
姓名：${stuMap.stu2.name}<br/>
年龄：${stuMap.stu2.age}<br/>

<br/>
<a href="###">遍历map中两个学生信息：</a><br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuMap?keys as key >
        <tr>
            <td>${key_index}</td>
            <td>${stuMap[key].name}</td>
            <td>${stuMap[key].age}</td>
            <td>${stuMap[key].money}</td>
        </tr>
    </#list>
</table>
<hr>
 
</body>
</html>
```

👆上面代码解释：

${k_index}：
	index：得到循环的下标，使用方法是在stu后边加"_index"，它的值是从0开始

### 3. if指令

​	 if 指令即判断指令，是常用的FTL指令，freemarker在解析时遇到if会进行判断，条件为真则输出if中间的内容，否则跳过内容不再输出。

- 指令格式

```html
<#if ></if>
```

### 4. 运算符

#### 4.1. 算数运算符

FreeMarker表达式中完全支持算术运算,FreeMarker支持的算术运算符包括:

- 加法： `+`
- 减法： `-`
- 乘法： `*`
- 除法： `/`
- 求模 (求余)： `%`

#### 4.2. 比较运算符

- **`=`**或者**`==`**:判断两个值是否相等. 
- **`!=`**:判断两个值是否不等. 
- **`>`**或者**`gt`**:判断左边值是否大于右边值 
- **`>=`**或者**`gte`**:判断左边值是否大于等于右边值 
- **`<`**或者**`lt`**:判断左边值是否小于右边值 
- **`<=`**或者**`lte`**:判断左边值是否小于等于右边值 

**比较运算符注意**

- **`=`**和**`!=`**可以用于字符串、数值和日期来比较是否相等
- **`=`**和**`!=`**两边必须是相同类型的值,否则会产生错误
- 字符串 **`"x"`** 、**`"x "`** 、**`"X"`**比较是不等的.因为FreeMarker是精确比较
- 其它的运行符可以作用于数字和日期,但不能作用于字符串
- 使用**`gt`**等字母运算符代替**`>`**会有更好的效果,因为 FreeMarker会把**`>`**解释成FTL标签的结束字符
- 可以使用括号来避免这种情况,如:**`<#if (x>y)>`**

#### 4.3. 逻辑运算符

- 逻辑与:&& 
- 逻辑或:|| 
- 逻辑非:! 

逻辑运算符只能作用于布尔值,否则将产生错误 。

### 5. 空值处理

**1、判断某变量是否存在使用 “??”**

用法为:variable??,如果该变量存在,返回true,否则返回false 

例：为防止stus为空报错可以加上判断如下：

**2、缺失变量默认值使用 “!”**

- 使用!要以指定一个默认值，当变量为空时显示默认值

  例：  ${name!''}表示如果name为空显示空字符串。

- 如果是嵌套对象则建议使用（）括起来

  例： ${(stu.bestFriend.name)!''}表示，如果stu或bestFriend或name为空默认显示空字符串。

### 6. 内建函数

内建函数语法格式： **`变量+?+函数名称`**  

**1、和到某个集合的大小**

**`${集合名?size}`**

**2、日期格式化**

显示年月日: **`${today?date}`** 
显示时分秒：**`${today?time}`**   
显示日期+时间：**`${today?datetime}`**   
自定义格式化：  **`${today?string("yyyy年MM月")}`**

**3、内建函数`c`**

model.addAttribute("point", 102920122);

point是数字型，使用${point}会显示这个数字的值，每三位使用逗号分隔。

如果不想显示为每三位分隔的数字，可以使用c函数将数字型转成字符串输出

**`${point?c}`**

**4、将json字符串转成对象**

一个例子：

其中用到了 assign标签，assign的作用是定义一个变量。

```velocity
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank}  账号：${data.account}
```

### 7. 静态化测试

&emsp;之前的测试都是SpringMVC将Freemarker作为视图解析器（ViewReporter）来集成到项目中，工作中，有的时候需要使用Freemarker原生Api来生成静态内容，下面一起来学习下原生Api生成文本文件。

使用freemarker原生Api将页面生成html文件，本节测试html文件生成的方法：

<div align="center">
    <img src="截图/文章列表加载/静态化测试.png" alt="静态化测试" />
</div>

```java
package com.heima.freemarker.test;


import com.heima.freemarker.FreemarkerDemoApplication;
import com.heima.freemarker.entity.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest(classes = FreemarkerDemoApplication.class)
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    @Autowired
    private Configuration configuration;

    @Test
    public void test() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("02-list.ftl");
        Map params = getData();
        //合成
        //第一个参数 数据模型
        //第二个参数  输出流
        template.process(params, new FileWriter("d:/list.html"));
    }

    private Map getData() {
        Map<String, Object> map = new HashMap<>();

        //小强对象模型数据
        Student stu1 = new Student();
        stu1.setName("小强");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());

        //小红对象模型数据
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);

        //将两个对象模型数据存放到List集合中
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);

        //向map中存放List集合数据
        map.put("stus", stus);


        //创建Map数据
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", stu1);
        stuMap.put("stu2", stu2);
        //向map中存放Map数据
        map.put("stuMap", stuMap);

        //返回Map
        return map;
    }
}
```

## 七、对象存储服务MinIO 

### 1. MinIO简介   

&emsp;MinIO基于Apache License v2.0开源协议的对象存储服务，可以做为云存储的解决方案用来保存海量的图片，视频，文档。由于采用Golang实现，服务端可以工作在Windows,Linux, OS X和FreeBSD上。配置简单，基本是复制可执行程序，单行命令可以运行起来。

&emsp;MinIO兼容亚马逊S3云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。

**S3 （ Simple Storage Service简单存储服务）**

基本概念

- bucket – 类比于文件系统的目录
- Object – 类比文件系统的文件
- Keys – 类比文件名

官网文档：http://docs.minio.org.cn/docs/

### 2. MinIO特点 

- 数据保护

  Minio使用Minio Erasure Code（纠删码）来防止硬件故障。即便损坏一半以上的driver，但是仍然可以从中恢复。

- 高性能

  作为高性能对象存储，在标准硬件条件下它能达到55GB/s的读、35GB/s的写速率

- 可扩容

  不同MinIO集群可以组成联邦，并形成一个全局的命名空间，并跨越多个数据中心

- SDK支持

  基于Minio轻量的特点，它得到类似Java、Python或Go等语言的sdk支持

- 有操作页面

  面向用户友好的简单操作界面，非常方便的管理Bucket及里面的文件资源

- 功能简单

  这一设计原则让MinIO不容易出错、更快启动

- 丰富的API

  支持文件资源的分享连接及分享链接的过期策略、存储桶操作、文件列表访问及文件上传下载的基本功能等。

- 文件变化主动通知

  存储桶（Bucket）如果发生改变,比如上传对象和删除对象，可以使用存储桶事件通知机制进行监控，并通过以下方式发布出去:AMQP、MQTT、Elasticsearch、Redis、NATS、MySQL、Kafka、Webhooks等。

### 3. 使用docker进行环境部署和启动

```bash
docker run -p 9000:9000 --name minio -d --restart=always -e "MINIO_ACCESS_KEY=minio" -e "MINIO_SECRET_KEY=minio123" -v /home/data:/data -v /home/config:/root/.minio minio/minio server /data
```

### 4. 核心代码

```java
package com.heima.file.service.impl;


import com.heima.file.config.MinIOConfig;
import com.heima.file.config.MinIOConfigProperties;
import com.heima.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
public class MinIOFileStorageService implements FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    private final static String separator = "/";

    /**
     * @param dirPath
     * @param filename  yyyy/mm/dd/file.jpg
     * @return
     */
    public String builderFilePath(String dirPath,String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if(!StringUtils.isEmpty(dirPath)){
            stringBuilder.append(dirPath).append(separator);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(separator);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    /**
     *  上传图片文件
     * @param prefix  文件前缀
     * @param filename  文件名
     * @param inputStream 文件流
     * @return  文件全路径
     */
    @Override
    public String uploadImgFile(String prefix, String filename,InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        }catch (Exception ex){
            log.error("minio put file error.",ex);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     *  上传html文件
     * @param prefix  文件前缀
     * @param filename   文件名
     * @param inputStream  文件流
     * @return  文件全路径
     */
    @Override
    public String uploadHtmlFile(String prefix, String filename,InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        }catch (Exception ex){
            log.error("minio put file error.",ex);
            ex.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 删除文件
     * @param pathUrl  文件全路径
     */
    @Override
    public void delete(String pathUrl) {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0,index);
        String filePath = key.substring(index+1);
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}",pathUrl);
            e.printStackTrace();
        }
    }


    /**
     * 下载文件
     * @param pathUrl  文件全路径
     * @return  文件流
     *
     */
    @Override
    public byte[] downLoadFile(String pathUrl)  {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0,index);
        String filePath = key.substring(index+1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(minIOConfigProperties.getBucket()).object(filePath).build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}",pathUrl);
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while (true) {
            try {
                if (!((rc = inputStream.read(buff, 0, 100)) > 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
```

## 八、文章详情

### 1. 需求分析

<div align="center">
    <img src="截图/文章列表加载/文章详情.png" alt="文章详情" />
</div>

### 2. 实现方案

方案一

用户某一条文章，根据文章的id去查询文章内容表，返回渲染页面

<div align="center">
    <img src="截图/文章列表加载/id查文章.png" alt="id查文章" />
</div>

方案二

<div align="center">
    <img src="截图/文章列表加载/方案2.png" alt="方案2" />
</div>

### 3. 核心代码

```java
package com.heima.article.test;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.article.ArticleApplication;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ArticleFreemarkerTest {

    @Autowired
    private Configuration configuration;

    @Autowired
    private FileStorageService fileStorageService;


    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Test
    public void createStaticUrlTest() throws Exception {
        //1.获取文章内容
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, 1390536764510310401L));
        if(apArticleContent != null && StringUtils.isNotBlank(apArticleContent.getContent())){
            //2.文章内容通过freemarker生成html文件
            StringWriter out = new StringWriter();
            Template template = configuration.getTemplate("article.ftl");

            Map<String, Object> params = new HashMap<>();
            params.put("content", JSONArray.parseArray(apArticleContent.getContent()));

            template.process(params, out);
            InputStream is = new ByteArrayInputStream(out.toString().getBytes());

            //3.把html文件上传到minio中
            String path = fileStorageService.uploadHtmlFile("", apArticleContent.getArticleId() + ".html", is);

            //4.修改ap_article表，保存static_url字段
            ApArticle article = new ApArticle();
            article.setId(apArticleContent.getArticleId());
            article.setStaticUrl(path);
            apArticleMapper.updateById(article);

        }
    }
}
```

## 九、自媒体文章发布

### 1. 后台搭建

<div align="center">
    <img src="截图/自媒体文章发布/后台搭建.png" alt="后台搭建" />
</div>

### 2. 前台搭建

<div align="center">
    <img src="截图/自媒体文章发布/前台搭建.png" alt="前台搭建" />
</div>

### 3. 自媒体素材管理

#### 3.1 素材上传

##### 3.1.1 需求分析

<div align="center">
    <img src="截图/自媒体文章发布/素材上传-需求分析.png" alt="素材上传-需求分析" />
</div>

图片上传的页面，首先是展示素材信息，可以点击图片上传，弹窗后可以上传图片

##### 3.1.2 表结构

```mysql
-- auto-generated definition
create table wm_material
(
    id            int unsigned auto_increment comment '主键'
        primary key,
    user_id       int unsigned     null comment '自媒体用户ID',
    url           varchar(255)     null comment '图片地址',
    type          tinyint unsigned null comment '素材类型
            0 图片
            1 视频',
    is_collection tinyint(1)       null comment '是否收藏',
    created_time  datetime         null comment '创建时间'
)
    comment '自媒体图文素材信息表' collate = utf8mb4_unicode_ci
                                   row_format = DYNAMIC;
```

##### 3.1.3 实现思路

<div align="center">
    <img src="截图/自媒体文章发布/素材上传-实现思路.png" alt="素材上传-实现思路" />
</div>

①：前端发送上传图片请求，类型为MultipartFile

②：网关进行token解析后，把解析后的用户信息存储到header中

```java
//获得token解析后中的用户信息
Object userId = claimsBody.get("id");
//在header中添加新的信息
ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
    httpHeaders.add("userId", userId + "");
}).build();
//重置header
exchange.mutate().request(serverHttpRequest).build();
```

③：自媒体微服务使用拦截器获取到header中的的用户信息，并放入到threadlocal中

在heima-leadnews-utils中新增工具类

```java
package com.heima.utils.thread;

import com.heima.model.wemedia.pojos.WmUser;

public class WmThreadLocalUtil {

    private final static ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 添加用户
     * @param wmUser
     */
    public static void  setUser(WmUser wmUser){
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    /**
     * 获取用户
     */
    public static WmUser getUser(){
        return WM_USER_THREAD_LOCAL.get();
    }

    /**
     * 清理用户
     */
    public static void clear(){
        WM_USER_THREAD_LOCAL.remove();
    }
}
```

新增拦截器

```java
package com.heima.wemedia.interceptor;

import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.thread.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class WmTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //得到header中的信息
        String userId = request.getHeader("userId");
        Optional<String> optional = Optional.ofNullable(userId);
        if(optional.isPresent()){
            //把用户id存入threadloacl中
            WmUser wmUser = new WmUser();
            wmUser.setId(Integer.valueOf(userId));
            WmThreadLocalUtils.setUser(wmUser);
            log.info("wmTokenFilter设置用户信息到threadlocal中...");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("清理threadlocal...");
        WmThreadLocalUtils.clear();
    }
}
```

配置使拦截器生效，拦截所有的请求

```java
package com.heima.wemedia.config;

import com.heima.wemedia.interceptor.WmTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WmTokenInterceptor()).addPathPatterns("/**");
    }
}
```

④：先把图片上传到minIO中，获取到图片请求的路径

⑤：把用户id和图片上的路径保存到素材表中

##### 3.1.4 接口定义

|          | **说明**                        |
| -------- | ------------------------------- |
| 接口路径 | /api/v1/material/upload_picture |
| 请求方式 | POST                            |
| 参数     | MultipartFile                   |
| 响应结果 | ResponseResult                  |

MultipartFile  ：Springmvc指定的文件接收类型

ResponseResult  ：成功需要**回显图片**，返回素材对象

```json
{
  "host":null,
  "code":200,
  "errorMessage":"操作成功",
  "data":{
    "id":52,
    "userId":1102,
    "url":"http://192.168.200.130:9000/leadnews/2021/04/26/a73f5b60c0d84c32bfe175055aaaac40.jpg",
    "type":0,
    "isCollection":0,
    "createdTime":"2021-01-20T16:49:48.443+0000"
  }
}
```

失败：

- 参数失效
- 文章上传失败

##### 3.1.5 核心代码

```java
package com.heima.wemedia.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;


    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {

        //1.检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");
        }

        //3.保存到数据库中
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setType((short)0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);

        //4.返回结果

        return ResponseResult.okResult(wmMaterial);
    }

}
```

#### 3.2 素材列表查询

##### 3.2.1 核心代码

```java
/**
     * 素材列表查询
     * @param dto
     * @return
     */
@Override
public ResponseResult findList(WmMaterialDto dto) {

    //1.检查参数
    dto.checkParam();

    //2.分页查询
    IPage page = new Page(dto.getPage(),dto.getSize());
    LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    //是否收藏
    if(dto.getIsCollection() != null && dto.getIsCollection() == 1){
        lambdaQueryWrapper.eq(WmMaterial::getIsCollection,dto.getIsCollection());
    }

    //按照用户查询
    lambdaQueryWrapper.eq(WmMaterial::getUserId,WmThreadLocalUtil.getUser().getId());

    //按照时间倒序
    lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);


    page = page(page,lambdaQueryWrapper);

    //3.结果返回
    ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
    responseResult.setData(page.getRecords());
    return responseResult;
}
```

### 4. 自媒体文章管理

#### 4.1 查询所有频道

##### 4.1.1 需求分析

<div align="center">
    <img src="截图/自媒体文章发布/自媒体文章管理-需求分析.png" alt="自媒体文章管理-需求分析" />
</div>

##### 4.1.2 表结构

wm_channel 频道信息表

```mysql
-- auto-generated definition
create table wm_channel
(
    id           int unsigned auto_increment
        primary key,
    name         varchar(10)      null comment '频道名称',
    description  varchar(255)     null comment '频道描述',
    is_default   tinyint unsigned null comment '是否默认频道',
    status       tinyint unsigned null,
    ord          tinyint unsigned null comment '默认排序',
    created_time datetime         null comment '创建时间'
)
    comment '频道信息表' collate = utf8mb4_unicode_ci
                         row_format = DYNAMIC;
```

##### 4.1.3 接口定义

|          | **说明**                 |
| -------- | ------------------------ |
| 接口路径 | /api/v1/channel/channels |
| 请求方式 | POST                     |
| 参数     | 无                       |
| 响应结果 | ResponseResult           |

ResponseResult  ：

```json
{
  "host": "null",
  "code": 0,
  "errorMessage": "操作成功",
  "data": [
    {
      "id": 4,
      "name": "java",
      "description": "java",
      "isDefault": true,
      "status": false,
      "ord": 3,
      "createdTime": "2019-08-16T10:55:41.000+0000"
    },
    Object {  ... },
    Object {  ... }
  ]
}
```

##### 4.1.4 核心代码

```java
package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    /**
     * 查询所有频道
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }
}
```

#### 4.2 查询自媒体文章

##### 4.2.1 需求说明

<div align="center">
    <img src="截图/自媒体文章发布/查询自媒体文章-需求说明.png" alt="查询自媒体文章-需求说明" />
</div>

##### 4.2.2 表结构分析

wm_news 自媒体文章表

```mysql
-- auto-generated definition
create table wm_news
(
    id            int auto_increment comment '主键'
        primary key,
    user_id       int unsigned                 null comment '自媒体用户ID',
    title         varchar(36)                  null comment '标题',
    content       longtext                     null comment '图文内容',
    type          tinyint unsigned             null comment '文章布局
            0 无图文章
            1 单图文章
            3 多图文章',
    channel_id    int unsigned                 null comment '图文频道ID',
    labels        varchar(20)                  null,
    created_time  datetime                     null comment '创建时间',
    submited_time datetime                     null comment '提交时间',
    status        tinyint unsigned             null comment '当前状态
            0 草稿
            1 提交（待审核）
            2 审核失败
            3 人工审核
            4 人工审核通过
            8 审核通过（待发布）
            9 已发布',
    publish_time  datetime                     null comment '定时发布时间，不定时则为空',
    reason        varchar(50)                  null comment '拒绝理由',
    article_id    bigint unsigned              null comment '发布库文章ID',
    images        longtext                     null comment '//图片用逗号分隔',
    enable        tinyint unsigned default '1' null
)
    comment '自媒体图文内容信息表' collate = utf8mb4_unicode_ci
                                   row_format = DYNAMIC;
```

##### 4.2.3 枚举类

```java
//状态枚举类
    @Alias("WmNewsStatus")
    public enum Status{
        NORMAL((short)0),SUBMIT((short)1),FAIL((short)2),ADMIN_AUTH((short)3),ADMIN_SUCCESS((short)4),SUCCESS((short)8),PUBLISHED((short)9);
        short code;
        Status(short code){
            this.code = code;
        }
        public short getCode(){
            return this.code;
        }
    }
```

##### 4.2.4 接口定义

|          | **说明**          |
| -------- | ----------------- |
| 接口路径 | /api/v1/news/list |
| 请求方式 | POST              |
| 参数     | WmNewsPageReqDto  |
| 响应结果 | ResponseResult    |

WmNewsPageReqDto  :

```java
package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;

@Data
public class WmNewsPageReqDto extends PageRequestDto {

    /**
     * 状态
     */
    private Short status;
    /**
     * 开始时间
     */
    private Date beginPubDate;
    /**
     * 结束时间
     */
    private Date endPubDate;
    /**
     * 所属频道ID
     */
    private Integer channelId;
    /**
     * 关键字
     */
    private String keyword;
}
```

ResponseResult  :

```json
{
  "host": "null",
  "code": 0,
  "errorMessage": "操作成功",
  "data": [
    Object { ... },
    Object { ... },
    Object { ... }
    
  ],
  "currentPage":1,
  "size":10,
  "total":21
}
```

##### 4.2.5 核心代码

```java
package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WmNewsServiceImpl  extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    /**
     * 查询文章
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAll(WmNewsPageReqDto dto) {

        //1.检查参数
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();
        //获取当前登录人的信息
        WmUser user = WmThreadLocalUtil.getUser();
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.分页条件查询
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //状态精确查询
        if(dto.getStatus() != null){
            lambdaQueryWrapper.eq(WmNews::getStatus,dto.getStatus());
        }

        //频道精确查询
        if(dto.getChannelId() != null){
            lambdaQueryWrapper.eq(WmNews::getChannelId,dto.getChannelId());
        }

        //时间范围查询
        if(dto.getBeginPubDate()!=null && dto.getEndPubDate()!=null){
            lambdaQueryWrapper.between(WmNews::getPublishTime,dto.getBeginPubDate(),dto.getEndPubDate());
        }

        //关键字模糊查询
        if(StringUtils.isNotBlank(dto.getKeyword())){
            lambdaQueryWrapper.like(WmNews::getTitle,dto.getKeyword());
        }

        //查询当前登录用户的文章
        lambdaQueryWrapper.eq(WmNews::getUserId,user.getId());

        //发布时间倒序查询
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        page = page(page,lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }
   
}
```

#### 4.3 文章发布

##### 4.3.1 需求分析

<div align="center">
    <img src="截图/自媒体文章发布/文章发布-需求分析.png" alt="文章发布-需求分析" />
</div>


##### 4.3.2 表结构分析

保存文章，除了需要wm_news表以外，还需要另外两张表

wm_material 素材表

```mysql
-- auto-generated definition
create table wm_material
(
    id            int unsigned auto_increment comment '主键'
        primary key,
    user_id       int unsigned     null comment '自媒体用户ID',
    url           varchar(255)     null comment '图片地址',
    type          tinyint unsigned null comment '素材类型
            0 图片
            1 视频',
    is_collection tinyint(1)       null comment '是否收藏',
    created_time  datetime         null comment '创建时间'
)
    comment '自媒体图文素材信息表' collate = utf8mb4_unicode_ci
                                   row_format = DYNAMIC;
```

wm_news_material 文章素材关系表

```mysql
-- auto-generated definition
create table wm_news_material
(
    id          int unsigned auto_increment comment '主键'
        primary key,
    material_id int unsigned     null comment '素材ID',
    news_id     int unsigned     null comment '图文ID',
    type        tinyint unsigned null comment '引用类型
            0 内容引用
            1 主图引用',
    ord         tinyint unsigned null comment '引用排序'
)
    comment '自媒体图文引用素材信息表' collate = utf8mb4_unicode_ci
                                       row_format = DYNAMIC;
```

因为一篇文章可以引用多张素材，一张素材也可以被多篇文章引用，即wm_news表与wm_material表是**多对多**的关系，需要wm_news_material 中间表来维护两者关系

<div align="center">
    <img src="截图/自媒体文章发布/三表关系.png" alt="三表关系" />
</div>

##### 4.3.3 实现思路

<div align="center">
    <img src="截图/自媒体文章发布/文章发布-实现思路.png" alt="文章发布-实现思路" />
</div>
1.前端提交发布或保存为草稿

2.后台判断请求中是否包含了文章id

3.如果不包含id,则为新增

​	3.1 执行新增文章的操作

​	3.2 关联文章内容图片与素材的关系

​	3.3 关联文章封面图片与素材的关系

4.如果包含了id，则为修改请求

​	4.1 删除该文章与素材的所有关系

​	4.2 执行修改操作

​	4.3 关联文章内容图片与素材的关系

​	4.4 关联文章封面图片与素材的关系

##### 4.3.4 接口定义

|          | **说明**               |
| -------- | ---------------------- |
| 接口路径 | /api/v1/channel/submit |
| 请求方式 | POST                   |
| 参数     | WmNewsDto              |
| 响应结果 | ResponseResult         |

WmNewsDto  

```java
package com.heima.model.wemedia.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmNewsDto {
    
    private Integer id;
     /**
     * 标题
     */
    private String title;
     /**
     * 频道id
     */
    private Integer channelId;
     /**
     * 标签
     */
    private String labels;
     /**
     * 发布时间
     */
    private Date publishTime;
     /**
     * 文章内容
     */
    private String content;
     /**
     * 文章封面类型  0 无图 1 单图 3 多图 -1 自动
     */
    private Short type;
     /**
     * 提交时间
     */
    private Date submitedTime; 
     /**
     * 状态 提交为1  草稿为0
     */
    private Short status;
     
     /**
     * 封面图片列表 多张图以逗号隔开
     */
    private List<String> images;
}
```

前端给传递过来的json数据格式为：

```json
{
    "title":"黑马头条项目背景",
    "type":"1",//这个 0 是无图  1 是单图  3 是多图  -1 是自动
    "labels":"黑马头条",
    "publishTime":"2020-03-14T11:35:49.000Z",
    "channelId":1,
    "images":[
        "http://192.168.200.130/group1/M00/00/00/wKjIgl5swbGATaSAAAEPfZfx6Iw790.png"
    ],
    "status":1,
    "content":"[
    {
        "type":"text",
        "value":"随着智能手机的普及，人们更加习惯于通过手机来看新闻。由于生活节奏的加快，很多人只能利用碎片时间来获取信息，因此，对于移动资讯客户端的需求也越来越高。黑马头条项目正是在这样背景下开发出来。黑马头条项目采用当下火热的微服务+大数据技术架构实现。本项目主要着手于获取最新最热新闻资讯，通过大数据分析用户喜好精确推送咨询新闻"
    },
    {
        "type":"image",
        "value":"http://192.168.200.130/group1/M00/00/00/wKjIgl5swbGATaSAAAEPfZfx6Iw790.png"
    }
]"
}
```

ResponseResult:

```json
{
    “code”:501,
    “errorMessage”:“参数失效"
}

{
    “code”:200,
    “errorMessage”:“操作成功"
}

{
    “code”:501,
    “errorMessage”:“素材引用失效"
}
```

##### 4.3.5 核心代码

新增WmNewsMaterialMapper类，文章与素材的关联关系需要批量保存，索引需要定义mapper文件和对应的映射文件

```java
package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

     void saveRelations(@Param("materialIds") List<Integer> materialIds,@Param("newsId") Integer newsId, @Param("type")Short type);
}
```

WmNewsMaterialMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.heima.wemedia.mapper.WmNewsMaterialMapper">

    <insert id="saveRelations">
        insert into wm_news_material (material_id,news_id,type,ord)
        values
        <foreach collection="materialIds" index="ord" item="mid" separator=",">
            (#{mid},#{newsId},#{type},#{ord})
        </foreach>
    </insert>

</mapper>
```

实现方法：

```java
/**
     * 发布修改文章或保存为草稿
     * @param dto
     * @return
     */
@Override
public ResponseResult submitNews(WmNewsDto dto) {

    //0.条件判断
    if(dto == null || dto.getContent() == null){
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    //1.保存或修改文章

    WmNews wmNews = new WmNews();
    //属性拷贝 属性名词和类型相同才能拷贝
    BeanUtils.copyProperties(dto,wmNews);
    //封面图片  list---> string
    if(dto.getImages() != null && dto.getImages().size() > 0){
        //[1dddfsd.jpg,sdlfjldk.jpg]-->   1dddfsd.jpg,sdlfjldk.jpg
        String imageStr = StringUtils.join(dto.getImages(), ",");
        wmNews.setImages(imageStr);
    }
    //如果当前封面类型为自动 -1
    if(dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
        wmNews.setType(null);
    }

    saveOrUpdateWmNews(wmNews);

    //2.判断是否为草稿  如果为草稿结束当前方法
    if(dto.getStatus().equals(WmNews.Status.NORMAL.getCode())){
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    //3.不是草稿，保存文章内容图片与素材的关系
    //获取到文章内容中的图片信息
    List<String> materials =  ectractUrlInfo(dto.getContent());
    saveRelativeInfoForContent(materials,wmNews.getId());

    //4.不是草稿，保存文章封面图片与素材的关系，如果当前布局是自动，需要匹配封面图片
    saveRelativeInfoForCover(dto,wmNews,materials);

    return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

}

/**
     * 第一个功能：如果当前封面类型为自动，则设置封面类型的数据
     * 匹配规则：
     * 1，如果内容图片大于等于1，小于3  单图  type 1
     * 2，如果内容图片大于等于3  多图  type 3
     * 3，如果内容没有图片，无图  type 0
     *
     * 第二个功能：保存封面图片与素材的关系
     * @param dto
     * @param wmNews
     * @param materials
     */
private void saveRelativeInfoForCover(WmNewsDto dto, WmNews wmNews, List<String> materials) {

    List<String> images = dto.getImages();

    //如果当前封面类型为自动，则设置封面类型的数据
    if(dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
        //多图
        if(materials.size() >= 3){
            wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
            images = materials.stream().limit(3).collect(Collectors.toList());
        }else if(materials.size() >= 1 && materials.size() < 3){
            //单图
            wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
            images = materials.stream().limit(1).collect(Collectors.toList());
        }else {
            //无图
            wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
        }

        //修改文章
        if(images != null && images.size() > 0){
            wmNews.setImages(StringUtils.join(images,","));
        }
        updateById(wmNews);
    }
    if(images != null && images.size() > 0){
        saveRelativeInfo(images,wmNews.getId(),WemediaConstants.WM_COVER_REFERENCE);
    }

}


/**
     * 处理文章内容图片与素材的关系
     * @param materials
     * @param newsId
     */
private void saveRelativeInfoForContent(List<String> materials, Integer newsId) {
    saveRelativeInfo(materials,newsId,WemediaConstants.WM_CONTENT_REFERENCE);
}

@Autowired
private WmMaterialMapper wmMaterialMapper;

/**
     * 保存文章图片与素材的关系到数据库中
     * @param materials
     * @param newsId
     * @param type
     */
private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
    if(materials!=null && !materials.isEmpty()){
        //通过图片的url查询素材的id
        List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));

        //判断素材是否有效
        if(dbMaterials==null || dbMaterials.size() == 0){
            //手动抛出异常   第一个功能：能够提示调用者素材失效了，第二个功能，进行数据的回滚
            throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
        }

        if(materials.size() != dbMaterials.size()){
            throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
        }

        List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());

        //批量保存
        wmNewsMaterialMapper.saveRelations(idList,newsId,type);
    }

}


/**
     * 提取文章内容中的图片信息
     * @param content
     * @return
     */
private List<String> ectractUrlInfo(String content) {
    List<String> materials = new ArrayList<>();

    List<Map> maps = JSON.parseArray(content, Map.class);
    for (Map map : maps) {
        if(map.get("type").equals("image")){
            String imgUrl = (String) map.get("value");
            materials.add(imgUrl);
        }
    }

    return materials;
}

@Autowired
private WmNewsMaterialMapper wmNewsMaterialMapper;

/**
     * 保存或修改文章
     * @param wmNews
     */
private void saveOrUpdateWmNews(WmNews wmNews) {
    //补全属性
    wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
    wmNews.setCreatedTime(new Date());
    wmNews.setSubmitedTime(new Date());
    wmNews.setEnable((short)1);//默认上架

    if(wmNews.getId() == null){
        //保存
        save(wmNews);
    }else {
        //修改
        //删除文章图片与素材的关系
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId,wmNews.getId()));
        updateById(wmNews);
    }

}
```

## 十、自媒体文章-自动审核

### 1. 自媒体文章自动审核流程

<div align="center">
    <img src="截图/自动审核/自动审核流程.png" alt="后台搭建" />
</div>

1. 自媒体端发布文章后，开始审核文章

2. 审核的主要是审核文章的内容（文本内容和图片）

3. 借助第三方提供的接口审核文本

4. 借助第三方提供的接口审核图片，由于图片存储到minIO中，需要先下载才能审核

5. 如果审核失败，则需要修改自媒体文章的状态，status:2  审核失败    status:3  转到人工审核

6. 如果审核成功，则需要在文章微服务中创建app端需要的文章

### 2. 内容安全第三方接口

&emsp;内容安全是识别服务，支持对图片、视频、文本、语音等对象进行多样化场景检测，有效降低内容违规风险。目前很多平台都支持内容检测，如阿里云、腾讯云、百度AI、网易云等国内大型互联网公司都对外提供了API。

- 文本垃圾内容检测：https://help.aliyun.com/document_detail/70439.html?spm=a2c4g.11186623.6.659.35ac3db3l0wV5k 
- 文本垃圾内容Java SDK: https://help.aliyun.com/document_detail/53427.html?spm=a2c4g.11186623.6.717.466d7544QbU8Lr 
- 图片垃圾内容检测：https://help.aliyun.com/document_detail/70292.html?spm=a2c4g.11186623.6.616.5d7d1e7f9vDRz4 
- 图片垃圾内容Java SDK: https://help.aliyun.com/document_detail/53424.html?spm=a2c4g.11186623.6.715.c8f69b12ey35j4 

### 3. app端文章保存接口

#### 3.1 表结构说明

ap_article  文章基本信息表

```mysql
-- auto-generated definition
create table ap_article
(
    id           bigint unsigned auto_increment
        primary key,
    title        varchar(50)                  null comment '标题',
    author_id    int unsigned                 null comment '文章作者的ID',
    author_name  varchar(20)                  null comment '作者昵称',
    channel_id   int unsigned                 null comment '文章所属频道ID',
    channel_name varchar(10)                  null comment '频道名称',
    layout       tinyint unsigned             null comment '文章布局
            0 无图文章
            1 单图文章
            2 多图文章',
    flag         tinyint unsigned             null comment '文章标记
            0 普通文章
            1 热点文章
            2 置顶文章
            3 精品文章
            4 大V 文章',
    images       varchar(1000)                null comment '文章图片
            多张逗号分隔',
    labels       varchar(500)                 null comment '文章标签最多3个 逗号分隔',
    likes        int unsigned                 null comment '点赞数量',
    collection   int unsigned                 null comment '收藏数量',
    comment      int unsigned                 null comment '评论数量',
    views        int unsigned                 null comment '阅读数量',
    province_id  int unsigned                 null comment '省市',
    city_id      int unsigned                 null comment '市区',
    county_id    int unsigned                 null comment '区县',
    created_time datetime                     null comment '创建时间',
    publish_time datetime                     null comment '发布时间',
    sync_status  tinyint(1)       default 0   null comment '同步状态',
    origin       tinyint unsigned default '0' null comment '来源',
    static_url   varchar(150)                 null
)
    comment '文章信息表，存储已发布的文章' row_format = DYNAMIC;
```

ap_article_config  文章配置表

```mysql
-- auto-generated definition
create table ap_article_config
(
    id         bigint unsigned auto_increment comment '主键'
        primary key,
    article_id bigint unsigned  null comment '文章ID',
    is_comment tinyint unsigned null comment '是否可评论',
    is_forward tinyint unsigned null comment '是否转发',
    is_down    tinyint unsigned null comment '是否下架',
    is_delete  tinyint unsigned null comment '是否已删除'
)
    comment 'APP已发布文章配置表' row_format = DYNAMIC;

create index idx_article_id
    on ap_article_config (article_id);
```

ap_article_content 文章内容表

```mysql
-- auto-generated definition
create table ap_article_content
(
    id         bigint unsigned auto_increment comment '主键'
        primary key,
    article_id bigint unsigned null comment '文章ID',
    content    longtext        null comment '文章内容'
)
    comment 'APP已发布文章内容表' row_format = DYNAMIC;

create index idx_article_id
    on ap_article_content (article_id);
```

三张表关系分析

<div align="center">
    <img src="截图/文章列表加载/三表关系.png" alt="三表关系" />
</div>

#### 3.2 分布式id

&emsp;随着业务的增长，文章表可能要占用很大的物理存储空间，为了解决该问题，后期使用数据库分片技术。将一个数据库进行拆分，通过数据库中间件连接。如果数据库中该表选用ID自增策略，则可能产生重复的ID，此时应该使用分布式ID生成策略来生成ID。

<div align="center">
    <img src="截图/自动审核/分布式id.png" alt="分布式id" />
</div>

&emsp;snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID），12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0

<div align="center">
    <img src="截图/自动审核/雪花算法.png" alt="后台搭建" />
</div>

文章端相关的表都使用雪花算法生成id,包括ap_article、 ap_article_config、 ap_article_content

#### 3.3 思路分析

在文章审核成功以后需要在app的article库中新增文章数据

1.保存文章信息 ap_article

2.保存文章配置信息 ap_article_config

3.保存文章内容 ap_article_content

实现思路：

<div align="center">
    <img src="截图/自动审核/文章保存接口.png" alt="后台搭建" />
</div>

#### 3.4 feign接口

|          | **说明**             |
| -------- | -------------------- |
| 接口路径 | /api/v1/article/save |
| 请求方式 | POST                 |
| 参数     | ArticleDto           |
| 响应结果 | ResponseResult       |

ArticleDto

```java
package com.heima.model.article.dtos;

import com.heima.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto  extends ApArticle {
    /**
     * 文章内容
     */
    private String content;
}
```

成功：

```json
{
  "code": 200,
  "errorMessage" : "操作成功",
  "data":"1302864436297442242"
 }
```

失败：

```json
{
  "code":501,
  "errorMessage":"参数失效",
 }
```

```json
{
  "code":501,
  "errorMessage":"文章没有找到",
 }
```

#### 3.5 核心代码

```java
@Autowired
private ApArticleConfigMapper apArticleConfigMapper;

@Autowired
private ApArticleContentMapper apArticleContentMapper;

/**
     * 保存app端相关文章
     * @param dto
     * @return
     */
@Override
public ResponseResult saveArticle(ArticleDto dto) {
    //1.检查参数
    if(dto == null){
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    ApArticle apArticle = new ApArticle();
    BeanUtils.copyProperties(dto,apArticle);

    //2.判断是否存在id
    if(dto.getId() == null){
        //2.1 不存在id  保存  文章  文章配置  文章内容

        //保存文章
        save(apArticle);

        //保存配置
        ApArticleConfig apArticleConfig = new ApArticleConfig(apArticle.getId());
        apArticleConfigMapper.insert(apArticleConfig);

        //保存 文章内容
        ApArticleContent apArticleContent = new ApArticleContent();
        apArticleContent.setArticleId(apArticle.getId());
        apArticleContent.setContent(dto.getContent());
        apArticleContentMapper.insert(apArticleContent);

    }else {
        //2.2 存在id   修改  文章  文章内容

        //修改  文章
        updateById(apArticle);

        //修改文章内容
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, dto.getId()));
        apArticleContent.setContent(dto.getContent());
        apArticleContentMapper.updateById(apArticleContent);
    }


    //3.结果返回  文章的id
    return ResponseResult.okResult(apArticle.getId());
}
```

### 4. 自媒体文章自动审核功能实现

#### 4.1 表结构说明

wm_news 自媒体文章表

```mysql
-- auto-generated definition
create table wm_news
(
    id            int auto_increment comment '主键'
        primary key,
    user_id       int unsigned                 null comment '自媒体用户ID',
    title         varchar(36)                  null comment '标题',
    content       longtext                     null comment '图文内容',
    type          tinyint unsigned             null comment '文章布局
            0 无图文章
            1 单图文章
            3 多图文章',
    channel_id    int unsigned                 null comment '图文频道ID',
    labels        varchar(20)                  null,
    created_time  datetime                     null comment '创建时间',
    submited_time datetime                     null comment '提交时间',
    status        tinyint unsigned             null comment '当前状态
            0 草稿
            1 提交（待审核）
            2 审核失败
            3 人工审核
            4 人工审核通过
            8 审核通过（待发布）
            9 已发布',
    publish_time  datetime                     null comment '定时发布时间，不定时则为空',
    reason        varchar(50)                  null comment '拒绝理由',
    article_id    bigint unsigned              null comment '发布库文章ID',
    images        longtext                     null comment '//图片用逗号分隔',
    enable        tinyint unsigned default '1' null
)
    comment '自媒体图文内容信息表' collate = utf8mb4_unicode_ci
                                   row_format = DYNAMIC;
```

status字段：0 草稿  1 待审核  2 审核失败  3 人工审核  4 人工审核通过  8 审核通过（待发布） 9 已发布

#### 4.2 核心代码

```java
@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * 自媒体文章审核
     *
     * @param id 自媒体文章id
     */
    @Override
    public void autoScanWmNews(Integer id) {
        //1.查询自媒体文章
        WmNews wmNews = wmNewsMapper.selectById(id);
        if(wmNews == null){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章不存在");
        }

        if(wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            //从内容中提取纯文本内容和图片
            Map<String,Object> textAndImages = handleTextAndImages(wmNews);

            //2.审核文本内容  阿里云接口
            boolean isTextScan = handleTextScan((String) textAndImages.get("content"),wmNews);
            if(!isTextScan)return;

            //3.审核图片  阿里云接口
            boolean isImageScan =  handleImageScan((List<String>) textAndImages.get("images"),wmNews);
            if(!isImageScan)return;

            //4.审核成功，保存app端的相关的文章数据
            ResponseResult responseResult = saveAppArticle(wmNews);
            if(!responseResult.getCode().equals(200)){
                throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
            }
            //回填article_id
            wmNews.setArticleId((Long) responseResult.getData());
            updateWmNews(wmNews,(short) 9,"审核成功");

        }
    }

    @Autowired
    private IArticleClient articleClient;

    @Autowired
    private WmChannelMapper wmChannelMapper;

    @Autowired
    private WmUserMapper wmUserMapper;

    /**
     * 保存app端相关的文章数据
     * @param wmNews
     */
    private ResponseResult saveAppArticle(WmNews wmNews) {

        ArticleDto dto = new ArticleDto();
        //属性的拷贝
        BeanUtils.copyProperties(wmNews,dto);
        //文章的布局
        dto.setLayout(wmNews.getType());
        //频道
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
        if(wmChannel != null){
            dto.setChannelName(wmChannel.getName());
        }

        //作者
        dto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if(wmUser != null){
            dto.setAuthorName(wmUser.getName());
        }

        //设置文章id
        if(wmNews.getArticleId() != null){
            dto.setId(wmNews.getArticleId());
        }
        dto.setCreatedTime(new Date());

        ResponseResult responseResult = articleClient.saveArticle(dto);
        return responseResult;

    }


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private GreenImageScan greenImageScan;

    /**
     * 审核图片
     * @param images
     * @param wmNews
     * @return
     */
    private boolean handleImageScan(List<String> images, WmNews wmNews) {

        boolean flag = true;

        if(images == null || images.size() == 0){
            return flag;
        }

        //下载图片 minIO
        //图片去重
        images = images.stream().distinct().collect(Collectors.toList());

        List<byte[]> imageList = new ArrayList<>();

        for (String image : images) {
            byte[] bytes = fileStorageService.downLoadFile(image);
            imageList.add(bytes);
        }


        //审核图片
        try {
            Map map = greenImageScan.imageScan(imageList);
            if(map != null){
                //审核失败
                if(map.get("suggestion").equals("block")){
                    flag = false;
                    updateWmNews(wmNews, (short) 2, "当前文章中存在违规内容");
                }

                //不确定信息  需要人工审核
                if(map.get("suggestion").equals("review")){
                    flag = false;
                    updateWmNews(wmNews, (short) 3, "当前文章中存在不确定内容");
                }
            }

        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    @Autowired
    private GreenTextScan greenTextScan;

    /**
     * 审核纯文本内容
     * @param content
     * @param wmNews
     * @return
     */
    private boolean handleTextScan(String content, WmNews wmNews) {

        boolean flag = true;

        if((wmNews.getTitle()+"-"+content).length() == 0){
            return flag;
        }

        try {
            Map map = greenTextScan.greeTextScan((wmNews.getTitle()+"-"+content));
            if(map != null){
                //审核失败
                if(map.get("suggestion").equals("block")){
                    flag = false;
                    updateWmNews(wmNews, (short) 2, "当前文章中存在违规内容");
                }

                //不确定信息  需要人工审核
                if(map.get("suggestion").equals("review")){
                    flag = false;
                    updateWmNews(wmNews, (short) 3, "当前文章中存在不确定内容");
                }
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;

    }

    /**
     * 修改文章内容
     * @param wmNews
     * @param status
     * @param reason
     */
    private void updateWmNews(WmNews wmNews, short status, String reason) {
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }

    /**
     * 1。从自媒体文章的内容中提取文本和图片
     * 2.提取文章的封面图片
     * @param wmNews
     * @return
     */
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {

        //存储纯文本内容
        StringBuilder stringBuilder = new StringBuilder();

        List<String> images = new ArrayList<>();

        //1。从自媒体文章的内容中提取文本和图片
        if(StringUtils.isNotBlank(wmNews.getContent())){
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if (map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }

                if (map.get("type").equals("image")){
                    images.add((String) map.get("value"));
                }
            }
        }
        //2.提取文章的封面图片
        if(StringUtils.isNotBlank(wmNews.getImages())){
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("content",stringBuilder.toString());
        resultMap.put("images",images);
        return resultMap;

    }
}
```

#### 4.3 feign远程接口调用方式

<div align="center">
    <img src="截图/自动审核/Feign远程接口调用.png" alt="Feign远程接口调用" />
</div>

在heima-leadnews-wemedia服务中已经依赖了heima-leadnews-feign-apis工程，只需要在自媒体的引导类中开启feign的远程调用即可

注解为：`@EnableFeignClients(basePackages = "com.heima.apis")` 需要指向apis这个包

#### 4.4 服务降级处理

- 服务降级是服务自我保护的一种方式，或者保护下游服务的一种方式，用于确保服务不会受请求突增影响变得不可用，确保服务不会崩溃

- 服务降级虽然会导致请求失败，但是不会导致阻塞。

##### 4.4.1 降级逻辑

①：在自媒体微服务中添加类，扫描降级代码类的包	

```java
@Component
public class IArticleClientFallback implements IArticleClient {
    @Override
    public ResponseResult saveArticle(ArticleDto dto)  {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }
}
```

在自媒体微服务中添加类，扫描降级代码类的包

```java
@Configuration
@ComponentScan("com.heima.apis.article.fallback")
public class InitConfig {
}
```

②：远程接口中指向降级代码

```java
@FeignClient(value = "leadnews-article",fallback = IArticleClientFallback.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);
}
```

③：客户端开启降级heima-leadnews-wemedia

在wemedia的nacos配置中心里添加如下内容，开启服务降级，也可以指定服务响应的超时的时间

```yaml
feign:
  # 开启feign对hystrix熔断降级的支持
  hystrix:
    enabled: true
  # 修改调用超时时间
  client:
    config:
      default:
        connectTimeout: 2000
        readTimeout: 2000
        
# 增加Hystrix超时时间配置
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 20000   # 将Hystrix超时设置为20秒
```

### 5. 发布文章提交审核集成

#### 5.1 同步调用与异步调用

同步：就是在发出一个调用时，在没有得到结果之前， 该调用就不返回（实时处理）

异步：调用在发出之后，这个调用就直接返回了，没有返回结果（分时处理）

<div align="center">
    <img src="截图/自动审核/异步审核.png" alt="异步审核" />
</div>

异步线程的方式审核文章

#### 5.2 Springboot集成异步线程调用

①：在自动审核的方法上加上@Async注解（标明要异步调用）

```java
@Override
@Async  //标明当前方法是一个异步方法
public void autoScanWmNews(Integer id) {
	//代码略
}
```

②：在文章发布成功后调用审核的方法

```java
@Autowired
private WmNewsAutoScanService wmNewsAutoScanService;

/**
 * 发布修改文章或保存为草稿
 * @param dto
 * @return
 */
@Override
public ResponseResult submitNews(WmNewsDto dto) {

    //代码略

    //审核文章
    wmNewsAutoScanService.autoScanWmNews(wmNews.getId());

    return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

}
```

③：在自媒体引导类中使用@EnableAsync注解开启异步调用

```java
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.heima.wemedia.mapper")
@EnableFeignClients(basePackages = "com.heima.apis")
@EnableAsync  //开启异步调用
public class WemediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class,args);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```

### 6. 新需求-自管理敏感词

#### 6.1 需求分析

- 文章审核不能过滤一些敏感词：

  私人侦探、针孔摄象、信用卡提现、广告代理、代开发票、刻章办、出售答案、小额贷款…

需要完成的功能：

需要自己维护一套敏感词，在文章审核的时候，需要验证文章是否包含这些敏感词

#### 6.2 敏感词-过滤

技术选型

| **方案**               | **说明**                     |
| ---------------------- | ---------------------------- |
| 数据库模糊查询         | 效率太低                     |
| String.indexOf("")查找 | 数据库量大的话也是比较慢     |
| 全文检索               | 分词再匹配                   |
| DFA算法                | 确定有穷自动机(一种数据结构) |

#### 6.3 DFA实现原理

DFA全称为：Deterministic Finite Automaton,即确定有穷自动机。

存储：一次性的把所有的敏感词存储到了多个map中，就是下图表示这种结构

敏感词：冰毒、大麻、大坏蛋

<div align="center">
    <img src="截图/自动审核/DFA.png" alt="DFA" />
</div>

检索的过程

<div align="center">
    <img src="截图/自动审核/检索过程.png" alt="检索过程" />
</div>

#### 6.4 自管理敏感词集成到文章审核中

创建敏感词表，wm_sensitive

```mysql
-- auto-generated definition
create table wm_sensitive
(
    id           int unsigned auto_increment comment '主键'
        primary key,
    sensitives   varchar(10) null comment '敏感词',
    created_time datetime    null comment '创建时间'
)
    comment '敏感词信息表' collate = utf8mb4_unicode_ci
                           row_format = DYNAMIC;
```

##### 6.4.1 核心代码

```java
@Autowired
private WmSensitiveMapper wmSensitiveMapper;

/**
     * 自管理的敏感词审核
     * @param content
     * @param wmNews
     * @return
     */
private boolean handleSensitiveScan(String content, WmNews wmNews) {

    boolean flag = true;

    //获取所有的敏感词
    List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(Wrappers.<WmSensitive>lambdaQuery().select(WmSensitive::getSensitives));
    List<String> sensitiveList = wmSensitives.stream().map(WmSensitive::getSensitives).collect(Collectors.toList());

    //初始化敏感词库
    SensitiveWordUtil.initMap(sensitiveList);

    //查看文章中是否包含敏感词
    Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
    if(map.size() >0){
        updateWmNews(wmNews,(short) 2,"当前文章中存在违规内容"+map);
        flag = false;
    }

    return flag;
}
```



SensitiveWordUtil

```java
public class SensitiveWordUtil {

    public static Map<String, Object> dictionaryMap = new HashMap<>();

    /**
     * 生成关键词字典库
     * @param words
     * @return
     */
    public static void initMap(Collection<String> words) {
        if (words == null) {
            System.out.println("敏感词列表不能为空");
            return ;
        }

        // map初始长度words.size()，整个字典库的入口字数(小于words.size()，因为不同的词可能会有相同的首字)
        Map<String, Object> map = new HashMap<>(words.size());
        // 遍历过程中当前层次的数据
        Map<String, Object> curMap = null;
        Iterator<String> iterator = words.iterator();

        while (iterator.hasNext()) {
            String word = iterator.next();
            curMap = map;
            int len = word.length();
            for (int i =0; i < len; i++) {
                // 遍历每个词的字
                String key = String.valueOf(word.charAt(i));
                // 当前字在当前层是否存在, 不存在则新建, 当前层数据指向下一个节点, 继续判断是否存在数据
                Map<String, Object> wordMap = (Map<String, Object>) curMap.get(key);
                if (wordMap == null) {
                    // 每个节点存在两个数据: 下一个节点和isEnd(是否结束标志)
                    wordMap = new HashMap<>(2);
                    wordMap.put("isEnd", "0");
                    curMap.put(key, wordMap);
                }
                curMap = wordMap;
                // 如果当前字是词的最后一个字，则将isEnd标志置1
                if (i == len -1) {
                    curMap.put("isEnd", "1");
                }
            }
        }

        dictionaryMap = map;
    }

    /**
     * 搜索文本中某个文字是否匹配关键词
     * @param text
     * @param beginIndex
     * @return
     */
    private static int checkWord(String text, int beginIndex) {
        if (dictionaryMap == null) {
            throw new RuntimeException("字典不能为空");
        }
        boolean isEnd = false;
        int wordLength = 0;
        Map<String, Object> curMap = dictionaryMap;
        int len = text.length();
        // 从文本的第beginIndex开始匹配
        for (int i = beginIndex; i < len; i++) {
            String key = String.valueOf(text.charAt(i));
            // 获取当前key的下一个节点
            curMap = (Map<String, Object>) curMap.get(key);
            if (curMap == null) {
                break;
            } else {
                wordLength ++;
                if ("1".equals(curMap.get("isEnd"))) {
                    isEnd = true;
                }
            }
        }
        if (!isEnd) {
            wordLength = 0;
        }
        return wordLength;
    }

    /**
     * 获取匹配的关键词和命中次数
     * @param text
     * @return
     */
    public static Map<String, Integer> matchWords(String text) {
        Map<String, Integer> wordMap = new HashMap<>();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            int wordLength = checkWord(text, i);
            if (wordLength > 0) {
                String word = text.substring(i, i + wordLength);
                // 添加关键词匹配次数
                if (wordMap.containsKey(word)) {
                    wordMap.put(word, wordMap.get(word) + 1);
                } else {
                    wordMap.put(word, 1);
                }

                i += wordLength - 1;
            }
        }
        return wordMap;
    }
}
```

### 7. 新需求-图片识别文字审核敏感词

#### 7.1 需求分析

文章中包含的图片要识别文字，过滤掉图片文字的敏感词

<div align="center">
    <img src="截图/自动审核/图片识别文字.png" alt="图片识别文字" />
</div>

#### 7.2 图片文字识别

&emsp;OCR （Optical Character Recognition，光学字符识别）是指电子设备（例如扫描仪或数码相机）检查纸上打印的字符，通过检测暗、亮的模式确定其形状，然后用字符识别方法将形状翻译成计算机文字的过程

| **方案**      | **说明**                                            |
| ------------- | --------------------------------------------------- |
| 百度OCR       | 收费                                                |
| Tesseract-OCR | Google维护的开源OCR引擎，支持Java，Python等语言调用 |
| Tess4J        | 封装了Tesseract-OCR  ，支持Java调用                 |

#### 7.3 Tess4j案例

```java
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tess4j")
public class Tess4jClient {

    private String dataPath;
    private String language;

    public String doOCR(BufferedImage image) throws TesseractException {
        //创建Tesseract对象
        ITesseract tesseract = new Tesseract();
        //设置字体库路径
        tesseract.setDatapath(dataPath);
        //中文识别
        tesseract.setLanguage(language);
        //执行ocr识别
        String result = tesseract.doOCR(image);
        //替换回车和tal键  使结果为一行
        result = result.replaceAll("\\r|\\n", "-").replaceAll(" ", "");
        return result;
    }

}
```

配置中添加属性

```yaml
tess4j:
  data-path: D:\workspace\tessdata
  language: chi_sim	
```

在WmNewsAutoScanServiceImpl中的handleImageScan方法上添加如下代码

```java
try {
    for (String image : images) {
        byte[] bytes = fileStorageService.downLoadFile(image);

        //图片识别文字审核---begin-----

        //从byte[]转换为butteredImage
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedImage imageFile = ImageIO.read(in);
        //识别图片的文字
        String result = tess4jClient.doOCR(imageFile);

        //审核是否包含自管理的敏感词
        boolean isSensitive = handleSensitiveScan(result, wmNews);
        if(!isSensitive){
            return isSensitive;
        }

        //图片识别文字审核---end-----

        imageList.add(bytes);
    } 
}catch (Exception e){
    e.printStackTrace();
}
```

### 8. 文章详情-静态文件生成

#### 8.1 思路分析

文章端创建app相关文章时，生成文章详情静态页上传到MinIO中

<div align="center">
    <img src="截图/自动审核/静态文件生成.png" alt="静态文件生成" />
</div>

核心代码

```java
@Service
@Slf4j
@Transactional
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private Configuration configuration;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApArticleService apArticleService;

    /**
     * 生成静态文件上传到minIO中
     * @param apArticle
     * @param content
     */
    @Async
    @Override
    public void buildArticleToMinIO(ApArticle apArticle, String content) {
        //已知文章的id
        //4.1 获取文章内容
        if(StringUtils.isNotBlank(content)){
            //4.2 文章内容通过freemarker生成html文件
            Template template = null;
            StringWriter out = new StringWriter();
            try {
                template = configuration.getTemplate("article.ftl");
                //数据模型
                Map<String,Object> contentDataModel = new HashMap<>();
                contentDataModel.put("content", JSONArray.parseArray(content));
                //合成
                template.process(contentDataModel,out);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //4.3 把html文件上传到minio中
            InputStream in = new ByteArrayInputStream(out.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("", apArticle.getId() + ".html", in);

            //4.4 修改ap_article表，保存static_url字段
            apArticleService.update(Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId,apArticle.getId())
                    .set(ApArticle::getStaticUrl,path));

        }
    }
}
```

在ApArticleService的saveArticle实现方法中添加异步调用生成文件的方法

文章微服务启动类上开启异步调用`@EnableAsync`

## 十一、延迟任务精准发布文章

### 1. 概述

#### 1.1 什么是延迟任务

- 定时任务：有固定周期的，有明确的触发时间
- 延迟队列：没有固定的开始时间，它常常是由一个事件触发的，而在这个事件触发之后的一段时间内触发另一个事件，任务可以立即执行，也可以延迟

<div align="center">
    <img src="截图/延迟任务/概述.png" alt="概述" />
</div>

应用场景：

场景一：订单下单之后30分钟后，如果用户没有付钱，则系统自动取消订单；如果期间下单成功，任务取消

场景二：接口对接出现网络问题，1分钟后重试，如果失败，2分钟重试，直到出现阈值终止

#### 2.2 技术对比

##### 2.2.1 DelayQueue

JDK自带DelayQueue 是一个支持延时获取元素的阻塞队列， 内部采用优先队列 PriorityQueue 存储元素，同时元素必须实现 Delayed 接口；在创建元素时可以指定多久才可以从队列中获取当前元素，只有在延迟期满时才能从队列中提取元素

<div align="center">
    <img src="截图/延迟任务/DelayQueue.png" alt="DelayQueue" />
</div>

DelayQueue属于排序队列，它的特殊之处在于队列的元素必须实现Delayed接口，该接口需要实现compareTo和getDelay方法

getDelay方法：获取元素在队列中的剩余时间，只有当剩余时间为0时元素才可以出队列。

compareTo方法：用于排序，确定元素出队列的顺序。

###### DelayQueue问题-未持久化

使用线程池或者原生DelayQueue程序挂掉之后，**任务都是放在内存**，需要考虑未处理消息的丢失带来的影响，如何保证数据不丢失，需要**持久化（磁盘）**。

##### 2.2.2 RabbitMQ实现延迟任务

- TTL：Time To Live (消息存活时间)

- 死信队列：Dead Letter Exchange(死信交换机)，当消息成为Dead message后，可以重新发送另一个交换机（死信交换机）

<div align="center">
    <img src="截图/延迟任务/RabbitMQ.png" alt="RabbitMQ" />
</div>

##### 2.2.3 redis实现

zset数据类型的去重有序（分数排序）特点进行延迟。例如：时间戳作为score进行排序

<div align="center">
    <img src="截图/延迟任务/redis.png" alt="redis" />
</div>

### 2. redis实现延迟任务

<div align="center">
    <img src="截图/延迟任务/实现思路.png" alt="实现思路" />
</div>

问题思路

1.为什么任务需要存储在数据库中？

延迟任务是一个通用的服务，任何需要延迟得任务都可以调用该服务，需要考虑数据持久化的问题，存储数据库中是一种数据安全的考虑。

2.为什么redis中使用两种数据类型，list和zset？

效率问题，算法的时间复杂度

3.在添加zset数据的时候，为什么不需要预加载？

任务模块是一个通用的模块，项目中任何需要延迟队列的地方，都可以调用这个接口，要考虑到数据量的问题，如果数据量特别大，为了防止阻塞，只需要把未来几分钟要执行的数据存入缓存即可。

### 3. 延迟任务服务实现

#### 3.1 表结构

taskinfo 任务表

```mysql
-- auto-generated definition
create table taskinfo
(
    task_id      bigint      not null comment '任务id'
        primary key,
    execute_time datetime(3) not null comment '执行时间',
    parameters   longblob    null comment '参数',
    priority     int         not null comment '优先级',
    task_type    int         not null comment '任务类型'
)
    charset = utf8mb3;

create index index_taskinfo_time
    on taskinfo (task_type, priority, execute_time);
```

taskinfo_logs 任务日志表

```mysql
-- auto-generated definition
create table taskinfo_logs
(
    task_id      bigint        not null comment '任务id'
        primary key,
    execute_time datetime(3)   not null comment '执行时间',
    parameters   longblob      null comment '参数',
    priority     int           not null comment '优先级',
    task_type    int           not null comment '任务类型',
    version      int           not null comment '版本号,用乐观锁',
    status       int default 0 null comment '状态 0=初始化状态 1=EXECUTED 2=CANCELLED'
)
    charset = utf8mb3;
```

乐观锁支持：

```java
/**
     * mybatis-plus乐观锁支持
     * @return
     */
@Bean
public MybatisPlusInterceptor optimisticLockerInterceptor(){
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    return interceptor;
}
```

#### 3.2 添加任务

ScheduleConstants常量类

```java
public class ScheduleConstants {
    
    //task状态
    public static final int SCHEDULED=0;   //初始化状态

    public static final int EXECUTED=1;       //已执行状态

    public static final int CANCELLED=2;   //已取消状态

    public static String FUTURE="future_";   //未来数据key前缀

    public static String TOPIC="topic_";     //当前数据key前缀
}
```

##### 3.2.1 核心代码

```java
@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {
    /**
     * 添加延迟任务
     *
     * @param task
     * @return
     */
    @Override
    public long addTask(Task task) {
        //1.添加任务到数据库中

        boolean success = addTaskToDb(task);

        if (success) {
            //2.添加任务到redis
            addTaskToCache(task);
        }

        return task.getTaskId();
    }

    @Autowired
    private CacheService cacheService;

    /**
     * 把任务添加到redis中
     *
     * @param task
     */
    private void addTaskToCache(Task task) {

        String key = task.getTaskType() + "_" + task.getPriority();

        //获取5分钟之后的时间  毫秒值
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        long nextScheduleTime = calendar.getTimeInMillis();

        //2.1 如果任务的执行时间小于等于当前时间，存入list
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lLeftPush(ScheduleConstants.TOPIC + key, JSON.toJSONString(task));
        } else if (task.getExecuteTime() <= nextScheduleTime) {
            //2.2 如果任务的执行时间大于当前时间 && 小于等于预设时间（未来5分钟） 存入zset中
            cacheService.zAdd(ScheduleConstants.FUTURE + key, JSON.toJSONString(task), task.getExecuteTime());
        }

    }

    @Autowired
    private TaskinfoMapper taskinfoMapper;

    @Autowired
    private TaskinfoLogsMapper taskinfoLogsMapper;

    /**
     * 添加任务到数据库中
     *
     * @param task
     * @return
     */
    private boolean addTaskToDb(Task task) {

        boolean flag = false;

        try {
            //保存任务表
            Taskinfo taskinfo = new Taskinfo();
            BeanUtils.copyProperties(task, taskinfo);
            taskinfo.setExecuteTime(new Date(task.getExecuteTime()));
            taskinfoMapper.insert(taskinfo);

            //设置taskID
            task.setTaskId(taskinfo.getTaskId());

            //保存任务日志数据
            TaskinfoLogs taskinfoLogs = new TaskinfoLogs();
            BeanUtils.copyProperties(taskinfo, taskinfoLogs);
            taskinfoLogs.setVersion(1);
            taskinfoLogs.setStatus(ScheduleConstants.SCHEDULED);
            taskinfoLogsMapper.insert(taskinfoLogs);

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}
```

#### 3.3 取消任务

##### 3.3.1 核心代码

```java
/**
     * 取消任务
     * @param taskId
     * @return
     */
@Override
public boolean cancelTask(long taskId) {

    boolean flag = false;

    //删除任务，更新日志
    Task task = updateDb(taskId,ScheduleConstants.EXECUTED);

    //删除redis的数据
    if(task != null){
        removeTaskFromCache(task);
        flag = true;
    }

    return false;
}

/**
     * 删除redis中的任务数据
     * @param task
     */
private void removeTaskFromCache(Task task) {

    String key = task.getTaskType()+"_"+task.getPriority();

    if(task.getExecuteTime()<=System.currentTimeMillis()){
        cacheService.lRemove(ScheduleConstants.TOPIC+key,0,JSON.toJSONString(task));
    }else {
        cacheService.zRemove(ScheduleConstants.FUTURE+key, JSON.toJSONString(task));
    }
}

/**
     * 删除任务，更新任务日志状态
     * @param taskId
     * @param status
     * @return
     */
private Task updateDb(long taskId, int status) {
    Task task = null;
    try {
        //删除任务
        taskinfoMapper.deleteById(taskId);

        TaskinfoLogs taskinfoLogs = taskinfoLogsMapper.selectById(taskId);
        taskinfoLogs.setStatus(status);
        taskinfoLogsMapper.updateById(taskinfoLogs);

        task = new Task();
        BeanUtils.copyProperties(taskinfoLogs,task);
        task.setExecuteTime(taskinfoLogs.getExecuteTime().getTime());
    }catch (Exception e){
        log.error("task cancel exception taskid={}",taskId);
    }

    return task;

}
```

#### 3.4 消费任务

##### 3.4.1 核心代码

```java
/**
     * 按照类型和优先级拉取任务
     * @return
     */
@Override
public Task poll(int type,int priority) {
    Task task = null;
    try {
        String key = type+"_"+priority;
        String task_json = cacheService.lRightPop(ScheduleConstants.TOPIC + key);
        if(StringUtils.isNotBlank(task_json)){
            task = JSON.parseObject(task_json, Task.class);
            //更新数据库信息
            updateDb(task.getTaskId(),ScheduleConstants.EXECUTED);
        }
    }catch (Exception e){
        e.printStackTrace();
        log.error("poll task exception");
    }

    return task;
}
```

#### 3.5 未来数据定时刷新

##### 3.5.1 reids key值匹配

方案1：keys 模糊匹配

keys的模糊匹配功能很方便也很强大，但是在生产环境需要慎用！开发中使用keys的模糊匹配却发现redis的**CPU使用率极高**，所以公司的redis生产环境将**keys命令禁用**了！**redis是单线程，会被堵塞**。

<div align="center">
    <img src="截图/延迟任务/key.png" alt="key" />
</div>

方案2：scan 

SCAN 命令是一个**基于游标的迭代器**，SCAN命令每次被调用之后， 都会向用户**返回一个新的游标**， 用户在下次迭代时需要使用这个新游标作为SCAN命令的**游标参数**， 以此来延续之前的迭代过程。

<div align="center">
    <img src="截图/延迟任务/scan.png" alt="scan" />
</div>

##### 3.5.2 reids管道

普通redis客户端和服务器交互模式

<div align="center">
    <img src="截图/延迟任务/普通交互.png" alt="普通交互" />
</div>

Pipeline请求模型

<div align="center">
    <img src="截图/延迟任务/Pipeline.png" alt="Pipeline" />
</div>

官方测试结果数据对比

<div align="center">
    <img src="截图/延迟任务/官方测试结果对比.png" alt="官方测试结果对比" />
</div>

##### 3.5.3 核心代码

```java
@Scheduled(cron = "0 */1 * * * ?")
public void refresh() {
    System.out.println(System.currentTimeMillis() / 1000 + "执行了定时任务");

    // 获取所有未来数据集合的key值
    Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");// future_*
    for (String futureKey : futureKeys) { // future_250_250

        String topicKey = ScheduleConstants.TOPIC + futureKey.split(ScheduleConstants.FUTURE)[1];
        //获取该组key下当前需要消费的任务数据
        Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());
        if (!tasks.isEmpty()) {
            //将这些任务数据添加到消费者队列中
            cacheService.refreshWithPipeline(futureKey, topicKey, tasks);
            System.out.println("成功的将" + futureKey + "下的当前需要执行的任务数据刷新到" + topicKey + "下");
        }
    }
}
```

在引导类中添加开启任务调度注解：`@EnableScheduling`

#### 3.6 分布式锁解决集群下的方法抢占执行

##### 3.6.1 问题描述

启动两台heima-leadnews-schedule服务，每台服务都会去执行refresh定时任务方法

<div align="center">
    <img src="截图/延迟任务/集群抢占.png" alt="集群抢占" />
</div>

##### 3.6.2 分布式锁

分布式锁：控制分布式系统有序的去对共享资源进行操作，通过互斥来保证数据的一致性。

解决方案：

<div align="center">
    <img src="截图/延迟任务/分布式锁.png" alt="分布式锁" />
</div>

##### 3.6.3 redis分布式锁

sexnx （SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。

<div align="center">
    <img src="截图/延迟任务/redis分布式锁.png" alt="redis分布式锁" />
</div>

这种加锁的思路是，如果 key 不存在则为 key 设置 value，如果 key 已存在则 SETNX 命令不做任何操作

- 客户端A请求服务器设置key的值，如果设置成功就表示加锁成功
- 客户端B也去请求服务器设置key的值，如果返回失败，那么就代表加锁失败
- 客户端A执行代码完成，删除锁
- 客户端B在等待一段时间后再去请求设置key的值，设置成功
- 客户端B执行代码完成，删除锁

##### 3.6.4 在工具类CacheService中添加方法

```java
/**
 * 加锁
 *
 * @param name
 * @param expire
 * @return
 */
public String tryLock(String name, long expire) {
    name = name + "_lock";
    String token = UUID.randomUUID().toString();
    RedisConnectionFactory factory = stringRedisTemplate.getConnectionFactory();
    RedisConnection conn = factory.getConnection();
    try {

        //参考redis命令：
        //set key value [EX seconds] [PX milliseconds] [NX|XX]
        Boolean result = conn.set(
                name.getBytes(),
                token.getBytes(),
                Expiration.from(expire, TimeUnit.MILLISECONDS),
                RedisStringCommands.SetOption.SET_IF_ABSENT //NX
        );
        if (result != null && result)
            return token;
    } finally {
        RedisConnectionUtils.releaseConnection(conn, factory,false);
    }
    return null;
}
```

修改未来数据定时刷新的方法，如下：

```java
/**
 * 未来数据定时刷新
 */
@Scheduled(cron = "0 */1 * * * ?")
public void refresh(){

    String token = cacheService.tryLock("FUTURE_TASK_SYNC", 1000 * 30);
    if(StringUtils.isNotBlank(token)){
        log.info("未来数据定时刷新---定时任务");

        //获取所有未来数据的集合key
        Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");
        for (String futureKey : futureKeys) {//future_100_50

            //获取当前数据的key  topic
            String topicKey = ScheduleConstants.TOPIC+futureKey.split(ScheduleConstants.FUTURE)[1];

            //按照key和分值查询符合条件的数据
            Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());

            //同步数据
            if(!tasks.isEmpty()){
                cacheService.refreshWithPipeline(futureKey,topicKey,tasks);
                log.info("成功的将"+futureKey+"刷新到了"+topicKey);
            }
        }
    }
}
```

#### 3.7 数据库同步到redis

<div align="center">
    <img src="截图/延迟任务/数据库同步到redis.png" alt="数据库同步到redis" />
</div>



```java
@Scheduled(cron = "0 */5 * * * ?")
@PostConstruct //机器宕机了，希望在恢复之后第一时间做数据的同步
public void reloadData() {
    clearCache();
    log.info("数据库数据同步到缓存");
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, 5);

    //查看小于未来5分钟的所有任务
    List<Taskinfo> allTasks = taskinfoMapper.selectList(Wrappers.<Taskinfo>lambdaQuery().lt(Taskinfo::getExecuteTime,calendar.getTime()));
    if(allTasks != null && allTasks.size() > 0){
        for (Taskinfo taskinfo : allTasks) {
            Task task = new Task();
            BeanUtils.copyProperties(taskinfo,task);
            task.setExecuteTime(taskinfo.getExecuteTime().getTime());
            addTaskToCache(task);
        }
    }
}

private void clearCache(){
    // 删除缓存中未来数据集合和当前消费者队列的所有key
    Set<String> futurekeys = cacheService.scan(ScheduleConstants.FUTURE + "*");// future_
    Set<String> topickeys = cacheService.scan(ScheduleConstants.TOPIC + "*");// topic_
    cacheService.delete(futurekeys);
    cacheService.delete(topickeys);
}
```

### 4. 延迟队列解决精准时间发布文章

##### 4.1 延迟队列服务提供对外接口

提供远程的feign接口，在heima-leadnews-feign-api编写类如下：

```java
@FeignClient("leadnews-schedule")
public interface IScheduleClient {

    /**
     * 添加任务
     * @param task   任务对象
     * @return       任务id
     */
    @PostMapping("/api/v1/task/add")
    public ResponseResult  addTask(@RequestBody Task task);

    /**
     * 取消任务
     * @param taskId        任务id
     * @return              取消结果
     */
    @GetMapping("/api/v1/task/cancel/{taskId}")
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId);

    /**
     * 按照类型和优先级来拉取任务
     * @param type
     * @param priority
     * @return
     */
    @GetMapping("/api/v1/task/poll/{type}/{priority}")
    public ResponseResult poll(@PathVariable("type") int type,@PathVariable("priority")  int priority);
}
```

在heima-leadnews-schedule微服务下提供对应的实现

```java
@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;

    /**
     * 添加任务
     * @param task 任务对象
     * @return 任务id
     */
    @PostMapping("/api/v1/task/add")
    @Override
    public ResponseResult addTask(@RequestBody Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    /**
     * 取消任务
     * @param taskId 任务id
     * @return 取消结果
     */
    @GetMapping("/api/v1/task/cancel/{taskId}")
    @Override
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    /**
     * 按照类型和优先级来拉取任务
     * @param type
     * @param priority
     * @return
     */
    @GetMapping("/api/v1/task/poll/{type}/{priority}")
    @Override
    public ResponseResult poll(@PathVariable("type") int type, @PathVariable("priority") int priority) {
        return ResponseResult.okResult(taskService.poll(type,priority));
    }
}
```

##### 4.2 发布文章集成添加延迟队列接口

枚举类：

```java
@Getter
@AllArgsConstructor
public enum TaskTypeEnum {

    NEWS_SCAN_TIME(1001, 1,"文章定时审核"),
    REMOTEERROR(1002, 2,"第三方接口调用失败，重试");
    private final int taskType; //对应具体业务
    private final int priority; //业务不同级别
    private final String desc; //描述信息
}
```

###### 4.2.1 序列化工具对比

- JdkSerialize：java内置的序列化能将实现了Serilazable接口的对象进行序列化和反序列化， ObjectOutputStream的writeObject()方法可序列化对象生成字节数组
- Protostuff：google开源的protostuff采用更为紧凑的二进制数组，表现更加优异，然后使用protostuff的编译工具生成pojo类

###### 4.2.2 核心代码

```java
@Service
@Slf4j
public class WmNewsTaskServiceImpl  implements WmNewsTaskService {

    @Autowired
    private IScheduleClient scheduleClient;

    /**
     * 添加任务到延迟队列中
     * @param id          文章的id
     * @param publishTime 发布的时间  可以做为任务的执行时间
     */
    @Override
    @Async //异步方法
    public void addNewsToTask(Integer id, Date publishTime) {

        log.info("添加任务到延迟服务中----begin");

        Task task = new Task();
        task.setExecuteTime(publishTime.getTime());
        task.setTaskType(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType());
        task.setPriority(TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        WmNews wmNews = new WmNews();
        wmNews.setId(id);
        task.setParameters(ProtostuffUtil.serialize(wmNews));

        scheduleClient.addTask(task);

        log.info("添加任务到延迟服务中----end");
    }
    
}
```

修改发布文章代码：

把之前的异步调用修改为调用延迟任务

```java
    //审核文章
    //        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
    wmNewsTaskService.addNewsToTask(wmNews.getId(),wmNews.getPublishTime());
```

##### 4.3 消费任务进行审核文章

实现

```java
@Autowired
private WmNewsAutoScanServiceImpl wmNewsAutoScanService;

/**
     * 消费延迟队列数据
     */
@Scheduled(fixedRate = 1000)
@Override
@SneakyThrows
public void scanNewsByTask() {

    log.info("文章审核---消费任务执行---begin---");

    ResponseResult responseResult = scheduleClient.poll(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType(), TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
    if(responseResult.getCode().equals(200) && responseResult.getData() != null){
        String json_str = JSON.toJSONString(responseResult.getData());
        Task task = JSON.parseObject(json_str, Task.class);
        byte[] parameters = task.getParameters();
        WmNews wmNews = ProtostuffUtil.deserialize(parameters, WmNews.class);
        System.out.println(wmNews.getId()+"-----------");
        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
    }
    log.info("文章审核---消费任务执行---end---");
}
```

在WemediaApplication自媒体的引导类中添加开启任务调度注解`@EnableScheduling`

## 十二、kafka及异步通知文章上下架

### 1. 自媒体文章上下架

需求分析

<div align="center">
    <img src="截图/文章上下架/需求分析.png" alt="需求分析" />
</div>

<div align="center">
    <img src="截图/文章上下架/系统解耦.png" alt="系统解耦" />
</div>

### 2. kafka概述

消息中间件对比                              

| 特性       | ActiveMQ                               | RabbitMQ                   | RocketMQ                 | Kafka                                    |
| ---------- | -------------------------------------- | -------------------------- | ------------------------ | ---------------------------------------- |
| 开发语言   | java                                   | erlang                     | java                     | scala                                    |
| 单机吞吐量 | 万级                                   | 万级                       | 10万级                   | 100万级                                  |
| 时效性     | ms                                     | us                         | ms                       | ms级以内                                 |
| 可用性     | 高（主从）                             | 高（主从）                 | 非常高（分布式）         | 非常高（分布式）                         |
| 功能特性   | 成熟的产品、较全的文档、各种协议支持好 | 并发能力强、性能好、延迟低 | MQ功能比较完善，扩展性佳 | 只支持主要的MQ功能，主要应用于大数据领域 |

消息中间件对比-选择建议

| **消息中间件** | **建议**                                                     |
| -------------- | ------------------------------------------------------------ |
| Kafka          | 追求高吞吐量，适合产生大量数据的互联网服务的数据收集业务     |
| RocketMQ       | 可靠性要求很高的金融互联网领域,稳定性高，经历了多次阿里双11考验 |
| RabbitMQ       | 性能较好，社区活跃度高，数据量没有那么大，优先选择功能比较完备的RabbitMQ |

kafka介绍

Kafka 是一个**分布式流媒体平台**,类似于消息队列或企业消息传递系统。kafka官网：http://kafka.apache.org/  

<div align="center">
    <img src="截图/文章上下架/kafka.png" alt="kafka" />
</div>

kafka介绍-名词解释

<div align="center">
    <img src="截图/文章上下架/名词解释.png" alt="名词解释" />
</div>

- producer：发布消息的对象称之为主题生产者（Kafka topic producer）

- topic：Kafka将消息分门别类，每一类的消息称之为一个主题（Topic）

- consumer：订阅消息并处理发布的消息的对象称之为主题消费者（consumers）

- broker：已发布的消息保存在一组服务器中，称之为Kafka集群。集群中的**每一个服务器**都是一个**代理（Broker）**。 消费者可以订阅一个或多个主题（topic），并从Broker拉数据，从而消费这些已发布的消息。

### 3. kafka安装配置

Kafka对于zookeeper是强依赖，保存kafka相关的节点数据，所以安装Kafka之前必须先安装zookeeper

- Docker安装zookeeper

下载镜像：

```shell
docker pull zookeeper:3.4.14
```

创建容器

```shell
docker run -d --name zookeeper -p 2181:2181 zookeeper:3.4.14
```

- Docker安装kafka

下载镜像：

```shell
docker pull wurstmeister/kafka:2.12-2.3.1
```

创建容器

```shell
docker run -d --name kafka \
--env KAFKA_ADVERTISED_HOST_NAME=192.168.200.130 \
--env KAFKA_ZOOKEEPER_CONNECT=192.168.200.130:2181 \
--env KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.200.130:9092 \
--env KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
--env KAFKA_HEAP_OPTS="-Xmx256M -Xms256M" \
--net=host wurstmeister/kafka:2.12-2.3.1
```

### 4. kafka高可用设计

#### 4.1 集群

<div align="center">
    <img src="截图/文章上下架/集群.png" alt="集群" />
</div>

- Kafka 的服务器端由被称为 Broker 的**服务进程**构成，即一个 Kafka 集群由多个 Broker 组成

- 这样如果集群中某一台机器宕机，其他机器上的 Broker 也依然能够对外提供服务。这其实就是 Kafka 提供高可用的手段之一

#### 4.2 备份机制(Replication）

<div align="center">
    <img src="截图/文章上下架/备份机制.png" alt="备份机制" />
</div>

Kafka 中消息的备份又叫做 **副本（Replica）**

Kafka 定义了两类副本：

- 领导者副本（Leader Replica）

- 追随者副本（Follower Replica）

##### 同步方式

<div align="center">
    <img src="截图/文章上下架/同步方式.png" alt="同步方式" />
</div>

ISR（in-**sync** replica）需要**同步复制**保存的follower



如果leader失效后，需要选出新的leader，选举的原则如下：

第一：选举时**优先从ISR**中选定，因为这个列表中follower的数据是与leader**同步**的

第二：如果ISR列表中的follower**都不行了**，就**只能从其他follower中选取**



极端情况，就是所有副本都失效了，这时有两种方案

第一：**等待ISR中的一个活过来，**选为Leader，**数据可靠**，但活过来的**时间不确定**

第二：**选择第一个活**过来的Replication，不一定是ISR中的，选为leader，以**最快速度恢复可用性**，**但数据不一定完整**

### 5. 生产者详解

#### 5.1 参数详解

- ack

代码的配置方式：

```java
//ack配置  消息确认机制
prop.put(ProducerConfig.ACKS_CONFIG,"all");
```

参数的选择说明

| **确认机制**     | **说明**                                                     |
| ---------------- | ------------------------------------------------------------ |
| acks=0           | 生产者在成功写入消息之前不会等待任何来自服务器的响应,消息有丢失的风险，但是速度最快 |
| acks=1（默认值） | 只要集群**首领节点**收到消息，生产者就会收到一个来自服务器的成功响应 |
| acks=all         | 只有当所有参与赋值的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应 |

- retries

生产者从服务器收到的错误有可能是临时性错误，在这种情况下，retries参数的值决定了生产者可以重发消息的次数，如果达到这个次数，生产者会放弃重试返回错误，**默认情况**下，生产者会在每次重试之间等待**100ms**

代码中配置方式：

```java
//重试次数
prop.put(ProducerConfig.RETRIES_CONFIG,10);
```

- 消息压缩

默认情况下， 消息发送时不会被压缩。

代码中配置方式：

```java
//数据压缩
prop.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"lz4");
```

| **压缩算法** | **说明**                                                     |
| ------------ | ------------------------------------------------------------ |
| snappy       | 占用较少的  CPU，  却能提供较好的性能和相当可观的压缩比， 如果看重性能和网络带宽，建议采用 |
| lz4          | 占用较少的 CPU， 压缩和解压缩速度较快，压缩比也很客观        |
| gzip         | 占用较多的  CPU，但会提供更高的压缩比，网络带宽有限，可以使用这种算法 |

**使用压缩可以降低网络传输开销和存储开销，而这往往是向 Kafka 发送消息的瓶颈所在**。

### 6.消费者详解

#### 6.1 消费者组

<div align="center">
    <img src="截图/文章上下架/消费者.png" alt="消费者" />
</div>

- 消费者组（Consumer Group） ：指的就是由**一个或多个消费者**组成的群体

- 一个发布在Topic上消息被分发给**此消费者组中的一个消费者**

  - 所有的消费者都在一个组中，那么这就变成了queue模型

  - 所有的消费者都在不同的组中，那么就完全变成了发布-订阅模型

#### 6.2 消息有序性

应用场景：

- 即时消息中的单对单聊天和群聊，保证发送方消息发送顺序与接收方的顺序一致

- 充值转账两个渠道在同一个时间进行余额变更，短信通知必须要有顺序

<div align="center">
    <img src="截图/文章上下架/有序性.png" alt="有序性" />
</div>

**topic分区**中消息只能由**消费者组中的唯一一个消费者**处理，所以消息肯定是**按照先后顺序**进行处理的。但是它也仅仅是保证Topic的一个分区顺序处理，**不能保证跨分区的消息先后处理顺序**。 所以，如果你**想要顺序的处理**Topic的所有消息，那就**只提供一个分区**。

#### 6.3 提交和偏移量

kafka**不会**像其他JMS队列那样**需要得到消费者的确认**，消费者可以使用kafka来追踪消息在分区的位置（偏移量）

消费者会往一个叫做**_consumer_offset**的特殊主题发送消息，消息里包含了每个分区的偏移量。如果消费者发生**崩溃**或有新的消费者**加入**群组，就会**触发再均衡**

<div align="center">
    <img src="截图/文章上下架/正常情况.png" alt="正常情况" />
</div>

正常的情况

<div align="center">
    <img src="截图/文章上下架/挂掉情况.png" alt="挂掉情况" />
</div>

如果消费者2挂掉以后，会发生再均衡，消费者2**负责的分区**会被**其他消费者**进行消费

再均衡后不可避免会出现一些问题

问题一：

<div align="center">
    <img src="截图/文章上下架/问题1.png" alt="问题1" />
</div>

如果**提交偏移量**小于客户端**处理的最后一个消息的偏移量**，那么处于两个偏移量之间的消息就会被**重复处理**。



问题二：

<div align="center">
    <img src="截图/文章上下架/问题2.png" alt="问题2" />
</div>

如果提交的偏移量**大于**客户端的最后一个消息的偏移量，那么处于两个偏移量之间的消息将会**丢失**。

如果想要解决这些问题，还要知道目前kafka提交偏移量的方式：

提交偏移量的方式有两种，分别是自动提交偏移量和手动提交

- 自动提交偏移量

当enable.auto.commit被设置为true，提交方式就是让消费者自动提交偏移量，**每隔5秒**消费者会自动把从**poll()**方法接收的**最大偏移量**提交上去

- 手动提交 ，当enable.auto.commit被设置为false可以有以下三种提交方式

  - 提交当前偏移量（同步提交）

  - 异步提交

  - 同步和异步组合提交

1.提交当前偏移量（同步提交）

把`enable.auto.commit`设置为false,让应用程序决定何时提交偏移量。使用**commitSync()**提交偏移量，commitSync()将会**提交poll返回的最新的偏移量**，所以在处理完所有记录后要确保调用了commitSync()方法。否则还是会有消息丢失的风险。

只要没有发生不可恢复的错误，commitSync()方法会一直尝试直至提交成功，如果提交失败也可以记录到错误日志里。

2.异步提交

手动提交有一个缺点，那就是当发起提交调用时应用会**阻塞**。当然我们可以减少手动提交的频率，但这个会增加消息重复的概率（和自动提交一样）。另外一个解决办法是，使用异步提交的API。

3.同步和异步组合提交

**异步**提交也有个**缺点**，那就是如果服务器返回提交失败，**异步提交不会进行重试**。相比较起来，同步提交会进行重试直到成功或者最后抛出异常给应用。**异步提交没有实现重试是因为，如果同时存在多个异步提交，进行重试可能会导致位移覆盖。**

举个例子，假如我们发起了一个异步提交**commitA**，此时的提交位移为**2000**，随后又发起了一个异步提交**commitB**且位移为**3000**；**commitA提交失败但commitB提交成功**，此时commitA进行**重试并成功**的话，会将实际上将已经提交的位移从**3000回滚到2000**，导致消息**重复消费**。

### 7. 自媒体文章上下架功能完成

#### 7.1 需求分析

- 已发表且已上架的文章可以下架

- 已发表且已下架的文章可以上架

#### 7.2 流程说明

<div align="center">
    <img src="截图/文章上下架/上下架流程.png" alt="上下架流程" />
</div>

#### 7.3 接口定义

|          | **说明**                |
| -------- | ----------------------- |
| 接口路径 | /api/v1/news/down_or_up |
| 请求方式 | POST                    |
| 参数     | DTO                     |
| 响应结果 | ResponseResult          |

 DTO  

```java
@Data
public class WmNewsDto {
    
    private Integer id;
    /**
    * 是否上架  0 下架  1 上架
    */
    private Short enable;
                       
}
```

#### 7.4 核心代码

```java
/**
 * 文章的上下架
 * @param dto
 * @return
 */
@Override
public ResponseResult downOrUp(WmNewsDto dto) {
    //1.检查参数
    if(dto.getId() == null){
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    //2.查询文章
    WmNews wmNews = getById(dto.getId());
    if(wmNews == null){
        return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章不存在");
    }

    //3.判断文章是否已发布
    if(!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode())){
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"当前文章不是发布状态，不能上下架");
    }

    //4.修改文章enable
    if(dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2){
        update(Wrappers.<WmNews>lambdaUpdate().set(WmNews::getEnable,dto.getEnable())
                .eq(WmNews::getId,wmNews.getId()));
    }
    return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
}
```

#### 7.5 消息通知article端文章上下架

在自媒体端文章上下架后发送消息

```java
//发送消息，通知article端修改文章配置
if(wmNews.getArticleId() != null){
    Map<String,Object> map = new HashMap<>();
    map.put("articleId",wmNews.getArticleId());
    map.put("enable",dto.getEnable());
    kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC,JSON.toJSONString(map));
}
```

常量类：

```java
public class WmNewsMessageConstants {

    public static final String WM_NEWS_UP_OR_DOWN_TOPIC="wm.news.up.or.down.topic";
}
```

在**article端**编写监听，接收数据

```java
@Component
@Slf4j
public class ArtilceIsDownListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void onMessage(String message){
        if(StringUtils.isNotBlank(message)){
            Map map = JSON.parseObject(message, Map.class);
            apArticleConfigService.updateByMap(map);
            log.info("article端文章配置修改，articleId={}",map.get("articleId"));
        }
    }
}
```

```java
@Service
@Slf4j
@Transactional
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {
    /**
     * 修改文章配置
     * @param map
     */
    @Override
    public void updateByMap(Map map) {
        //0 下架 1 上架
        Object enable = map.get("enable");
        boolean isDown = true;
        if(enable.equals(1)){
            isDown = false;
        }
        //修改文章配置
        update(Wrappers.<ApArticleConfig>lambdaUpdate().eq(ApArticleConfig::getArticleId,map.get("articleId")).set(ApArticleConfig::getIsDown,isDown));
    }
}
```

