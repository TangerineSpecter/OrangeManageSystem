/*
Navicat MySQL Data Transfer

Source Server         : localcomein
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : oms

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2019-01-04 12:48:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `phone_number` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `admin` tinyint(2) DEFAULT '0' COMMENT '是否超级管理员（0：不是；1：是）',
  `is_del` tinyint(2) DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
