/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : oms

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 13/09/2022 16:58:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_constellation
-- ----------------------------
DROP TABLE IF EXISTS `data_constellation`;
CREATE TABLE `data_constellation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '星座名称',
  `datetime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '日期（文字）',
  `date` int DEFAULT NULL COMMENT '日期（数字）',
  `all_luck` int DEFAULT NULL COMMENT '综合指数',
  `color` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '幸运色',
  `health` int DEFAULT NULL COMMENT '健康指数',
  `love` int DEFAULT NULL COMMENT '爱情指数',
  `money` int DEFAULT NULL COMMENT '财运指数',
  `number` int DEFAULT NULL COMMENT '幸运数字',
  `QFriend` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '速配星座',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '今日概述',
  `work_luck` int DEFAULT '0' COMMENT '工作指数',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3020 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='星座信息表';

-- ----------------------------
-- Table structure for data_exchange
-- ----------------------------
DROP TABLE IF EXISTS `data_exchange`;
CREATE TABLE `data_exchange` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '货币名称',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '货币代码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='货币数据';

-- ----------------------------
-- Table structure for data_exchange_rate
-- ----------------------------
DROP TABLE IF EXISTS `data_exchange_rate`;
CREATE TABLE `data_exchange_rate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '源货币名称',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '源货币代码',
  `price` decimal(5,2) NOT NULL COMMENT '每100源货币的RMB兑换值',
  `record_time` date NOT NULL COMMENT '记录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='货币汇率数据';

-- ----------------------------
-- Table structure for data_question
-- ----------------------------
DROP TABLE IF EXISTS `data_question`;
CREATE TABLE `data_question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '问题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '回答内容',
  `is_del` tinyint DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='问题记录表';

-- ----------------------------
-- Table structure for data_stock
-- ----------------------------
DROP TABLE IF EXISTS `data_stock`;
CREATE TABLE `data_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
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
  `state` int DEFAULT '1' COMMENT '股票状态 1： 上市， 其他停牌',
  `industry_id` bigint DEFAULT NULL COMMENT '行业',
  `net_income` decimal(15,0) DEFAULT NULL COMMENT '归属母公司股东净利润',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_full_code` (`full_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12851 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='股票表';

-- ----------------------------
-- Table structure for data_stock_portfolio
-- ----------------------------
DROP TABLE IF EXISTS `data_stock_portfolio`;
CREATE TABLE `data_stock_portfolio` (
  `id` bigint NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='股票组合表';

-- ----------------------------
-- Table structure for data_stock_portfolio_content
-- ----------------------------
DROP TABLE IF EXISTS `data_stock_portfolio_content`;
CREATE TABLE `data_stock_portfolio_content` (
  `id` bigint NOT NULL,
  `stock_id` bigint NOT NULL COMMENT '股票ID',
  `portfolio_id` bigint NOT NULL COMMENT '组合ID',
  `information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='股票组合内容表';

-- ----------------------------
-- Table structure for data_trade_logic
-- ----------------------------
DROP TABLE IF EXISTS `data_trade_logic`;
CREATE TABLE `data_trade_logic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `entry_date` date NOT NULL COMMENT '开仓时间',
  `exit_date` date DEFAULT NULL COMMENT '平仓时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `type` tinyint NOT NULL COMMENT '交易类型（0：股票；1：期货；2：外汇）',
  `status` tinyint NOT NULL DEFAULT '-1' COMMENT '盈亏状态（-1：未平仓；0：盈利；1：亏损）',
  `entry_point` decimal(10,4) DEFAULT NULL COMMENT '入场点',
  `exit_point` decimal(10,4) DEFAULT NULL COMMENT '出场点',
  `profit_point` decimal(10,4) DEFAULT NULL COMMENT '盈利点',
  `loss_point` decimal(10,4) DEFAULT NULL COMMENT '止损点',
  `closing_price` decimal(10,4) DEFAULT NULL COMMENT '收盘价',
  `trade_logic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易逻辑',
  `conclusion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当日复盘结论',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='交易逻辑明细表';

-- ----------------------------
-- Table structure for data_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `data_trade_record`;
CREATE TABLE `data_trade_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL COMMENT '交易时间',
  `start_money` int DEFAULT NULL COMMENT '初始金额（单位：分）',
  `end_money` int DEFAULT NULL COMMENT '结束金额（单位：分）',
  `income_value` int NOT NULL DEFAULT '0' COMMENT '收益值(单位：分)',
  `total_income_value` int DEFAULT '0' COMMENT '累计收益值（单位：分）',
  `income_rate` decimal(10,5) DEFAULT '0.00000' COMMENT '收益率（百分比）',
  `win_rate` decimal(10,5) DEFAULT '0.00000' COMMENT '赢率（百分比）',
  `sharpe_ratio` decimal(10,5) DEFAULT NULL COMMENT '夏普率',
  `type` int NOT NULL COMMENT '类型（0：股票；1：期货；2：外汇）',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `currency` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'CNY' COMMENT '币种',
  `deposit` int NOT NULL DEFAULT '0' COMMENT '转入金额',
  `withdrawal` int NOT NULL DEFAULT '0' COMMENT '转出金额',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=697 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='交易记录表';

-- ----------------------------
-- Table structure for data_wall_page
-- ----------------------------
DROP TABLE IF EXISTS `data_wall_page`;
CREATE TABLE `data_wall_page` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `start_date` bigint DEFAULT NULL COMMENT '开始时间（yyyyMMdd）',
  `end_date` bigint DEFAULT NULL COMMENT '结束时间（yyyyMMdd）',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '壁纸url',
  `full_start_date` bigint DEFAULT NULL COMMENT '完整开始时间',
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '版权信息',
  `copyright_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '跳转搜索链接',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题',
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '哈希值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='每日壁纸表';

-- ----------------------------
-- Table structure for system_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `system_bulletin`;
CREATE TABLE `system_bulletin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '公告内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '公告时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶（0：否；1：是）',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统公告表';

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `web_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站标题',
  `web_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站网址',
  `cache_time` int NOT NULL DEFAULT '-1' COMMENT '缓存时间(秒)',
  `file_size` bigint NOT NULL DEFAULT '0' COMMENT '最大文件上传大小（单位：KB）',
  `home_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '首页标题',
  `file_suffix` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上传文件格式限制',
  `copyright` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版权信息',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置表';

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept` (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门层级',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '上级部门ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序（由大到小）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统部门表';

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作管理员',
  `event` int DEFAULT NULL COMMENT '操作事件',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作方法',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '操作参数',
  `operation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作内容',
  `time` bigint DEFAULT '0' COMMENT '操作耗时（毫秒）',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5972 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统日志记录表';

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单标题',
  `pid` bigint DEFAULT NULL COMMENT '父级ID',
  `href` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '跳转链接',
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标logo地址',
  `level` int DEFAULT '0' COMMENT '标签等级（0：顶级；1：一级菜单；以此类推）',
  `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面跳转方式(_self:自己；_blank:新窗口)',
  `permission_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单权限code',
  `sort` int DEFAULT '0' COMMENT '排序',
  `top` tinyint DEFAULT '0' COMMENT '是否在首页（0：否；1：是）',
  `top_sort` int DEFAULT '0' COMMENT '首页排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统菜单表';

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '消息类型（0：系统消息；）',
  `push_status` tinyint NOT NULL DEFAULT '0' COMMENT '推送状态（0：未推送；1：已推送）',
  `read_status` tinyint NOT NULL DEFAULT '0' COMMENT '阅读状态（0:未读，1:已读）',
  `uid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推送管理员ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_del` tinyint DEFAULT '0' COMMENT '删除状态（0:未删除；1:已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10142 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='消息中心表';

-- ----------------------------
-- Table structure for system_permission
-- ----------------------------
DROP TABLE IF EXISTS `system_permission`;
CREATE TABLE `system_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '权限地址，请求的url',
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Table structure for system_permission_role
-- ----------------------------
DROP TABLE IF EXISTS `system_permission_role`;
CREATE TABLE `system_permission_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rid` bigint NOT NULL COMMENT '角色ID',
  `pid` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_rid` (`rid`) USING BTREE,
  KEY `idx_pid` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=335 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='权限角色关系表';

-- ----------------------------
-- Table structure for system_permisson_log
-- ----------------------------
DROP TABLE IF EXISTS `system_permisson_log`;
CREATE TABLE `system_permisson_log` (
  `id` bigint NOT NULL,
  `type` tinyint NOT NULL DEFAULT '-1' COMMENT '权限更新类型（0：部门；1：用户；2：权限模块；3：权限；4：角色；5：角色用户关系；6：角色权限关系）',
  `target_id` bigint DEFAULT NULL COMMENT '基于type指定的对象id，比如用户、权限、角色表的主键',
  `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '旧数据',
  `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '新数据',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '当前是否复原过（0：没有；1：复原过）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统权限修改记录表';

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0：冻结；1：可用）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `sex` tinyint DEFAULT '0' COMMENT '性别（0：男；1：女）',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `brief` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `dept_id` int NOT NULL DEFAULT '0' COMMENT '部门id',
  `login_count` int DEFAULT '0' COMMENT '登录次数',
  `last_login_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '盐',
  `admin` tinyint DEFAULT '0' COMMENT '是否超级管理员（0：不是；1：是）',
  `is_del` tinyint DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统用户表';

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rid` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_uid` (`uid`) USING BTREE,
  KEY `idx_pid` (`rid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色关系表';

-- ----------------------------
-- Table structure for system_version_history
-- ----------------------------
DROP TABLE IF EXISTS `system_version_history`;
CREATE TABLE `system_version_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本号',
  `version_number` int DEFAULT NULL COMMENT '版本号数字',
  `create_time` date DEFAULT NULL COMMENT '版本创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统版本历史记录表';

-- ----------------------------
-- Table structure for system_version_history_content
-- ----------------------------
DROP TABLE IF EXISTS `system_version_history_content`;
CREATE TABLE `system_version_history_content` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `version_id` bigint DEFAULT NULL COMMENT '版本ID',
  `type` tinyint DEFAULT NULL COMMENT '功能类型（0：新增；1：优化；2：改善；3：修复；4：重构）',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统版本更新内容';

-- ----------------------------
-- Table structure for user_card_note
-- ----------------------------
DROP TABLE IF EXISTS `user_card_note`;
CREATE TABLE `user_card_note` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '笔记内容',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户卡片笔记表';

-- ----------------------------
-- Table structure for user_card_note_tag
-- ----------------------------
DROP TABLE IF EXISTS `user_card_note_tag`;
CREATE TABLE `user_card_note_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签名称',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶（0：未置顶；1：已置顶）',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户笔记标签表';

-- ----------------------------
-- Table structure for user_health
-- ----------------------------
DROP TABLE IF EXISTS `user_health`;
CREATE TABLE `user_health` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重（单位：公斤）',
  `bmi` decimal(5,2) DEFAULT NULL COMMENT 'BMI',
  `body_fat_rate` decimal(8,4) DEFAULT NULL COMMENT '体脂率（百分比）',
  `viscus_level` int DEFAULT NULL COMMENT '内脏等级',
  `muscle_weight` decimal(8,2) DEFAULT NULL COMMENT '肌肉量（公斤）',
  `fat_weight` decimal(8,2) DEFAULT NULL COMMENT '脂肪量（公斤）',
  `basal_metabolism_rate` int DEFAULT NULL COMMENT '基础代谢率（kcal）',
  `body_moisture_rate` decimal(8,4) DEFAULT NULL COMMENT '身体水分（百分比）',
  `physical_age` int DEFAULT NULL COMMENT '身体年龄',
  `lean_body_mass` decimal(8,2) DEFAULT NULL COMMENT '去脂体重（公斤）',
  `subcutaneous_fat_rate` decimal(8,4) DEFAULT NULL COMMENT '皮下脂肪（百分比）',
  `skeletal_muscle_rate` decimal(8,4) DEFAULT NULL COMMENT '骨骼肌率（百分比）',
  `protein_rate` decimal(8,4) DEFAULT NULL COMMENT '蛋白质（百分比）',
  `bone_weight` decimal(8,2) DEFAULT NULL COMMENT '骨头重量（公斤）',
  `heart_rate` int DEFAULT NULL COMMENT '平均心率',
  `step_number` int DEFAULT '0' COMMENT '步数',
  `calorie` int DEFAULT NULL COMMENT '卡路里（千卡）',
  `pressure` int DEFAULT NULL COMMENT '压力（0~100）',
  `sleep_duration` int DEFAULT NULL COMMENT '睡眠时长（分）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_time` date NOT NULL COMMENT '记录时间',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_del` tinyint DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='健康记录表';

-- ----------------------------
-- Table structure for user_note_tag_assoc
-- ----------------------------
DROP TABLE IF EXISTS `user_note_tag_assoc`;
CREATE TABLE `user_note_tag_assoc` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `note_id` bigint NOT NULL COMMENT '笔记id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户笔记-标签关联表';

-- ----------------------------
-- Table structure for work_collection
-- ----------------------------
DROP TABLE IF EXISTS `work_collection`;
CREATE TABLE `work_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `type` tinyint DEFAULT NULL COMMENT '类型：（0:网站）',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='内容收藏表';

SET FOREIGN_KEY_CHECKS = 1;
