# OrangeManageSystem
**橘子管理系统**

主要用于个人信息管理。

> 搭建本项目的起初用意：

因为平时用Excel
去整理个人数据比较麻烦，随着各个App和应用的信息数据的分散化，导致不方便个人进行信息数据的维护，以及个别应用的功能不能够满足个人平时使用，所以开发了这个个人信息管理系统。

本系统是一个权限管理系统，支持多人使用，进行权限功能的分配使用，私人信息数据独立。更多功能后续不断迭代更新中...

> 敲黑板

```
本项目主要是个人兴趣使然，本着学习和使用的目的由个人维护开发，希望感兴趣的朋友们能反馈系统bug以及提出更有趣的功能建议^_^
```

## 当前系统版本

```
v0.4.1
```

## 相关技术

**使用JDK版本：** 1.8

**搭建框架：** 
- 基础框架：Spring Boot 2.5.3
- 持久层框架：MyBatis-Plus 3.4.3 
- 模版引擎：Thymeleaf 3.0.9
- 依赖管理：Maven 3.3.9
- 缓存中间件：Redis 5.0.3
- 安全框架：Shiro 1.4.0
- 分页插件：PageHelper 1.3.0
- 消息中间件：RabbitMQ 2.3.0
- Netty框架：Netty 4.1.48

**前端框架：**
- 基础框架：Bootstrap 4.1.3
- 后台框架：Layui 2.5.6
- JavaScript框架：Jquery 3.3.1
- 分页插件：Bootstrap-Paginator 1.0

**开发环境：**
- 数据库：Mysql 5.7
- 版本管理：Git 2.9.2
- 依赖管理：Maven 3.6.1

## 技术特点

```
1.对thymeleaf模板页进行页面缓存处理，提高响应速度和Qps能力。
2.支持动态配置系统菜单。
3.通过AOP切面对后台操作进行日志管理以及接口性能监控。
4.通过角色权限进行管理员权限管理
5.通过RabbitMQ消息中间件进行系统消息推送
6.集成ApexEcharts图表组件方便进行数据分析
7.通过Redis缓存中间件进行数据缓存
```

## 帮助

### 1.集成lombok插件
```
1.Eclipse:
    找到本地仓库中的lombok.jar，执行java -jar lombok.jar对IDE做集成
2.IDEA:
    设置->Plugins搜索lambok插件安装
```

### 2.配置Maven setting
```
<mirror>
	<id>alimaven</id>
	<mirrorOf>central</mirrorOf>
	<name>aliyun maven</name>
	<url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
</mirror>
<mirror>        
	<id>nexus-aliyun</id>      
     <name>nexus-aliyun</name>    
     <url>http://maven.aliyun.com/nexus/content/groups/public</url>      
     <mirrorOf>central</mirrorOf>        
</mirror>
```

### 3.数据库以及数据初始化初始化
```
1.执行resources目录下的oms.sql初始化系统表格
2.执行system_menu.sql初始化系统菜单
3.执行data_constellation.sql初始化一部分数据查看
4.执行system_version_history初始化系统版本信息
5.执行system_version_history_content初始化更新内容信息
```

### 4.代码生成器的使用
```
1.配置generatorConfig.xml生成实体的表名
2.右键项目-->Maven build
3.配置指令：mybatis-generator:generate
4.Run运行
```
### 5.启动RabbitMQ
```
1.安装RabbitMQ
2.rabbitmq-server 启动
```
## 系统菜单功能说明

```text
个人管理
    | -- 健康管理（进行个人健康信息管理）
    | -- 问题管理（进行搜集的问题记录）
交易管理
    | -- 交易记录（进行投资收益管理）
    | -- 交易逻辑（进行交易操作的逻辑管理）
    | -- 股票池（进行股票管理）
乐趣印记
    | -- 内容收藏（对感兴趣的内容进行收藏）
小工具
    | -- 二维码生成（生成二维码方便分享）
    | -- 每日壁纸（必应每日推荐壁纸）
数据列表
    | -- 星座列表（每日星座数据记录）
数据分析
    | -- 交易统计（交易数据图表分析）
    | -- 健康统计（健康信息图表分析）
系统设置
    | -- 后台管理员（进行后台管理员管理）
    | -- 系统公告（对系统公告进行配置）
    | -- 版本记录（系统版本更新的信息记录）
    | -- 系统配置（系统参数的配置）
    | -- 系统日志（系统的使用行为记录）
    | -- 角色管理（管理员角色管理和权限分配）
    | -- 菜单管理（系统菜单的管理）
    | -- 权限管理（角色权限的管理）

功能持续更新中...
```

## 第三方API集成

- 七牛云
- 聚合API
- 必应壁纸API

## 关于
```
主要用于对技术的探讨和学习。
```

## 相关参考资源

- [Pear Admin Layui](https://gitee.com/pear-admin/Pear-Admin-Layui#https://github.com/PearAdmin/pear-admin-layui)