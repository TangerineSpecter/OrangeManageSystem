/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : oms

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 15/05/2020 00:05:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_constellation
-- ----------------------------
DROP TABLE IF EXISTS `data_constellation`;
CREATE TABLE `data_constellation` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '星座名称',
  `datetime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '日期（文字）',
  `date` int(15) DEFAULT NULL COMMENT '日期（数字）',
  `all_luck` int(8) DEFAULT NULL COMMENT '综合指数',
  `color` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '幸运色',
  `health` int(8) DEFAULT NULL COMMENT '健康指数',
  `love` int(8) DEFAULT NULL COMMENT '爱情指数',
  `money` int(8) DEFAULT NULL COMMENT '财运指数',
  `number` int(5) DEFAULT NULL COMMENT '幸运数字',
  `QFriend` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '速配星座',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '今日概述',
  `work_luck` int(8) DEFAULT '0' COMMENT '工作指数',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=578 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='星座信息表';

-- ----------------------------
-- Table structure for data_question
-- ----------------------------
DROP TABLE IF EXISTS `data_question`;
CREATE TABLE `data_question` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '问题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '回答内容',
  `is_del` tinyint(2) DEFAULT NULL COMMENT '删除状态（0：未删除；1：已删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问题记录表';

-- ----------------------------
-- Table structure for data_stock
-- ----------------------------
DROP TABLE IF EXISTS `data_stock`;
CREATE TABLE `data_stock` (
  `id` bigint(23) NOT NULL AUTO_INCREMENT,
  `market` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市场缩写',
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '股票代码',
  `full_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '股票完整代码',
  `listing_date` date DEFAULT NULL COMMENT '上市日期',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '股票名称',
  `curr_capital` decimal(15,0) DEFAULT NULL COMMENT '流通股本（万股）',
  `profit_four` decimal(15,2) DEFAULT NULL COMMENT '四季度净利润',
  `total_capital` decimal(15,0) DEFAULT NULL COMMENT '总股本',
  `net_per_assets` decimal(15,0) DEFAULT NULL COMMENT '每股净资产',
  `pinyin` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '股票拼音',
  `state` int(11) DEFAULT '1' COMMENT '股票状态 1： 上市， 其他停牌',
  `industry_id` bigint(20) DEFAULT NULL COMMENT '行业',
  `net_income` decimal(15,0) DEFAULT NULL COMMENT '归属母公司股东净利润',
  PRIMARY KEY (`id`),
  KEY `idx_full_code` (`full_code`)
) ENGINE=InnoDB AUTO_INCREMENT=12851 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='股票表';

-- ----------------------------
-- Table structure for data_stock_portfolio
-- ----------------------------
DROP TABLE IF EXISTS `data_stock_portfolio`;
CREATE TABLE `data_stock_portfolio` (
  `id` bigint(13) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='股票组合表';

-- ----------------------------
-- Table structure for data_stock_portfolio_content
-- ----------------------------
DROP TABLE IF EXISTS `data_stock_portfolio_content`;
CREATE TABLE `data_stock_portfolio_content` (
  `id` bigint(13) NOT NULL,
  `stock_id` bigint(13) NOT NULL COMMENT '股票ID',
  `portfolio_id` bigint(13) NOT NULL COMMENT '组合ID',
  `information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='股票组合内容表';

-- ----------------------------
-- Table structure for data_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `data_trade_record`;
CREATE TABLE `data_trade_record` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL COMMENT '交易时间',
  `start_money` int(20) DEFAULT NULL COMMENT '初始金额（单位：分）',
  `end_money` int(20) DEFAULT NULL COMMENT '结束金额（单位：分）',
  `income_value` int(50) NOT NULL DEFAULT '0' COMMENT '收益值(单位：分)',
  `income_rate` decimal(10,5) DEFAULT '0.00000' COMMENT '收益率（百分比）',
  `win_rate` decimal(10,5) DEFAULT '0.00000' COMMENT '赢率（百分比）',
  `sharpe_ratio` decimal(10,5) DEFAULT NULL COMMENT '夏普率',
  `type` int(4) NOT NULL COMMENT '类型（0：股票；1：期货；2：外汇）',
  `admin_id` bigint(13) NOT NULL COMMENT '管理员ID',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=460 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易记录表';

-- ----------------------------
-- Table structure for system_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `system_bulletin`;
CREATE TABLE `system_bulletin` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '公告内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '公告时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `top` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否置顶（0：否；1：是）',
  `is_del` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统公告表';

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `web_title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站标题',
  `web_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站网址',
  `cache_time` int(10) NOT NULL DEFAULT '-1' COMMENT '缓存时间(秒)',
  `file_size` bigint(13) NOT NULL DEFAULT '0' COMMENT '最大文件上传大小（单位：KB）',
  `home_title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '首页标题',
  `file_suffix` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上传文件格式限制',
  `copyright` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版权信息',
  `is_del` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept` (
  `id` bigint(13) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门层级',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `parent_id` bigint(13) NOT NULL DEFAULT '0' COMMENT '上级部门ID',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序（由大到小）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统部门表';

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作管理员',
  `event` int(11) DEFAULT NULL COMMENT '操作事件',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作方法',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '操作参数',
  `operation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作内容',
  `time` bigint(13) DEFAULT '0' COMMENT '操作耗时（毫秒）',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志记录表';

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单标题',
  `pid` bigint(13) DEFAULT NULL COMMENT '父级ID',
  `href` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '跳转链接',
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标logo地址',
  `level` int(11) DEFAULT '0' COMMENT '标签等级（0：顶级；1：一级菜单；以此类推）',
  `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面跳转方式(_self:自己；_blank:新窗口)',
  `permission_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单权限码',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `top` tinyint(2) DEFAULT '0' COMMENT '是否在首页（0：否；1：是）',
  `top_sort` int(50) DEFAULT '0' COMMENT '首页排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '消息类型（0：系统消息；）',
  `read_status` tinyint(2) DEFAULT '0' COMMENT '阅读状态（0:未读，1:已读）',
  `admin_id` bigint(13) NOT NULL COMMENT '推送管理员ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_del` tinyint(2) DEFAULT '0' COMMENT '删除状态（0:未删除；1:已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息中心表';

-- ----------------------------
-- Table structure for system_permission
-- ----------------------------
DROP TABLE IF EXISTS `system_permission`;
CREATE TABLE `system_permission` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限地址，请求的url',
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限码',
  `module_id` bigint(13) NOT NULL DEFAULT '0' COMMENT '模块id',
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '类型',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态（0：冻结；1：正常）',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sort` int(50) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- ----------------------------
-- Table structure for system_permission_module
-- ----------------------------
DROP TABLE IF EXISTS `system_permission_module`;
CREATE TABLE `system_permission_module` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块名称',
  `parent_id` bigint(13) NOT NULL DEFAULT '0' COMMENT '上级模块id',
  `level` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块层级',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态（0：冻结；1：正常）',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限模块表';

-- ----------------------------
-- Table structure for system_permission_role
-- ----------------------------
DROP TABLE IF EXISTS `system_permission_role`;
CREATE TABLE `system_permission_role` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `rid` bigint(13) NOT NULL COMMENT '角色ID',
  `module_id` bigint(13) NOT NULL COMMENT '权限模块ID',
  PRIMARY KEY (`id`),
  KEY `idx_rid` (`rid`),
  KEY `idx_pid` (`module_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限角色关系表';

-- ----------------------------
-- Table structure for system_permisson_log
-- ----------------------------
DROP TABLE IF EXISTS `system_permisson_log`;
CREATE TABLE `system_permisson_log` (
  `id` bigint(13) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '权限更新类型（0：部门；1：用户；2：权限模块；3：权限；4：角色；5：角色用户关系；6：角色权限关系）',
  `target_id` bigint(13) DEFAULT NULL COMMENT '基于type指定的对象id，比如用户、权限、角色表的主键',
  `old_value` text COLLATE utf8mb4_unicode_ci COMMENT '旧数据',
  `new_value` text COLLATE utf8mb4_unicode_ci COMMENT '新数据',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '当前是否复原过（0：没有；1：复原过）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统权限修改记录表';

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型（0:普通；1：管理员；2：其他）',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态（0：冻结；1：可用）',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `sex` tinyint(2) DEFAULT '0' COMMENT '性别（0：男；1：女）',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `brief` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `last_login_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '盐',
  `admin` tinyint(2) DEFAULT '0' COMMENT '是否超级管理员（0：不是；1：是）',
  `is_del` tinyint(2) DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1164595569015955458 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `uid` bigint(13) NOT NULL COMMENT '管理员ID',
  `rid` bigint(13) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_pid` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关系表';

-- ----------------------------
-- Table structure for user_health
-- ----------------------------
DROP TABLE IF EXISTS `user_health`;
CREATE TABLE `user_health` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重（单位：公斤）',
  `bmi` decimal(5,2) DEFAULT NULL COMMENT 'BMI',
  `body_fat_rate` decimal(8,4) DEFAULT NULL COMMENT '体脂率（百分比）',
  `viscus_level` int(8) DEFAULT NULL COMMENT '内脏等级',
  `muscle_weight` decimal(8,2) DEFAULT NULL COMMENT '肌肉量（公斤）',
  `fat_weight` decimal(8,2) DEFAULT NULL COMMENT '脂肪量（公斤）',
  `basal_metabolism_rate` int(8) DEFAULT NULL COMMENT '基础代谢率（kcal）',
  `body_moisture_rate` decimal(8,4) DEFAULT NULL COMMENT '身体水分（百分比）',
  `physical_age` int(8) DEFAULT NULL COMMENT '身体年龄',
  `lean_body_mass` decimal(8,2) DEFAULT NULL COMMENT '去脂体重（公斤）',
  `subcutaneous_fat_rate` decimal(8,4) DEFAULT NULL COMMENT '皮下脂肪（百分比）',
  `skeletal_muscle_rate` decimal(8,4) DEFAULT NULL COMMENT '骨骼肌率（百分比）',
  `protein_rate` decimal(8,4) DEFAULT NULL COMMENT '蛋白质（百分比）',
  `bone_weight` decimal(8,2) DEFAULT NULL COMMENT '骨头重量（公斤）',
  `heart_rate` int(8) DEFAULT NULL COMMENT '平均心率',
  `step_number` int(8) DEFAULT '0' COMMENT '步数',
  `calorie` int(8) DEFAULT NULL COMMENT '卡路里（千卡）',
  `pressure` int(5) DEFAULT NULL COMMENT '压力（0~100）',
  `sleep_duration` int(8) DEFAULT NULL COMMENT '睡眠时长（分）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_time` date NOT NULL COMMENT '记录时间',
  `admin_id` bigint(13) NOT NULL COMMENT '管理员ID',
  `is_del` tinyint(2) DEFAULT NULL COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for work_collection
-- ----------------------------
DROP TABLE IF EXISTS `work_collection`;
CREATE TABLE `work_collection` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `type` tinyint(3) DEFAULT NULL COMMENT '类型：（0:网站）',
  `sort` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_del` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容收藏表';

SET FOREIGN_KEY_CHECKS = 1;
