# 牙科诊所信息管理系统

### 编辑器记得安装lombok插件
mayanze-dcims是根据"2019软件设计师"真题第3道大题所做的一套信息管理系统， dcims是Dental Clinic Information Management system（牙科诊所信息管理系统）
缩写；使用springboot，freemarker,miniui构件项目。
看了[FreeMarker、Thymeleaf、Enjoy 模板引擎性能测试](https://www.ktanx.com/blog/p/4965)
,这篇文章，决定了选择FreeMarker

### 演示地址
[http://mayanze.com/dcims](http://mayanze.com/dcims)


###技术选型

后端
* [SpringBoot SpringBootSecurity 2.3.1](https://spring.io/projects/spring-boot/)
* [MyBatis-Plus](https://baomidou.com/guide/)
* [mysql 8.0.18](https://dev.mysql.com/downloads/mysql/)

前端

* [MiniUI 3.9.4](http://www.miniui.com/docs/quickstart/)
* FreeMarker
* Jquery

### 系统模块
系统功能模块组成如下所示：
```
├─
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─菜单管理
│  └─部门管理
├─系统监控
│  ├─在线用户
│  ├─系统日志
│  ├─登录日志
│  ├─请求追踪
│  ├─系统信息
│  │  ├─JVM信息
│  │  ├─TOMCAT信息
│  │  └─服务器信息
├─
|

```

