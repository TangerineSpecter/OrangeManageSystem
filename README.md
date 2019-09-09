# OrangeManageSystem
**橘子管理系统**

主要用于个人信息管理。

## 相关技术

**使用JDK版本：** 1.8

**搭建框架：** 
- 基础框架：Spring Boot 2.1.1
- 持久层框架：MyBatis 
- 模版引擎：Thymeleaf 3.0.9
- 依赖管理：Maven 3.3.9
- 缓存框架：Redis 5.0.3
- 安全框架：Shiro 1.4.0
- 分页插件：PageHelper 1.2.5

**前端框架：**
- 基础框架：Bootstrap 4.1.3
- 后台框架：Layui 2.5.4
- JavaScript框架：Jquery 3.3.1
- 分页插件：Bootstrap-Paginator 1.0

**开发环境：**
- 数据库：Mysql 5.7
- 版本管理：Git 2.9.2
- 依赖管理：Maven

## 技术特点

```
1.对thymeleaf模板页进行页面缓存处理，提高响应速度和Qps能力。
2.支持动态配置系统菜单。
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

### 3.数据库初始化
```
执行resources目录下的oms.sql
```

### 4.代码生成器的使用
```
1.配置generatorConfig.xml生成实体的表名
2.右键项目-->Maven build
3.配置指令：mybatis-generator:generate
4.Run运行
```

## 关于
```
主要用于对技术的探讨和学习。
```

## 相关参考资源

- [LayuiMini管理后台](https://github.com/zhongshaofa/layuimini/)