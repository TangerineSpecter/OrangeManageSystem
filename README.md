# OrangeManageSystem
**橘子管理系统**

主要用于个人信息管理。

## 相关技术

**使用JDK版本：** 1.8

**搭建框架：** 
- 基础框架：Spring Boot 2.1.1
- 持久层框架：MyBatis 
- 模版引擎：Thymeleaf
- 依赖管理：Maven 3.3.9
- 缓存框架：Redis 5.0.3
- 安全框架：Shiro

**前端框架：**
- 基础框架：AmazeUI 2.7.2
- JavaScript框架：Jquery 3.3.1

**开发环境：**
- 数据库：Mysql 10.1.37
- 版本管理：Git 2.9.2

## 帮助

### 1.集成lombok插件
```
找到本地仓库中的lombok.jar，执行java -jar lombok.jar对IDE做集成
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

## 关于
```
主要用于对技术的探讨和学习。
```