/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : oms

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-07 23:39:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_constellation
-- ----------------------------
DROP TABLE IF EXISTS `data_constellation`;
CREATE TABLE `data_constellation` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(8) DEFAULT '' COMMENT '星座名称',
  `datetime` varchar(255) DEFAULT '' COMMENT '日期（文字）',
  `date` int(15) DEFAULT NULL COMMENT '日期（数字）',
  `all` varchar(8) DEFAULT NULL COMMENT '综合指数',
  `color` varchar(8) DEFAULT NULL COMMENT '幸运色',
  `health` varchar(8) DEFAULT NULL COMMENT '健康指数',
  `love` varchar(8) DEFAULT NULL COMMENT '爱情指数',
  `money` varchar(8) DEFAULT NULL COMMENT '财运指数',
  `number` int(5) DEFAULT NULL COMMENT '幸运数字',
  `QFriend` varchar(8) DEFAULT NULL COMMENT '速配星座',
  `summary` varchar(255) DEFAULT NULL COMMENT '今日概述',
  `work` varchar(8) DEFAULT '' COMMENT '工作指数',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='星座信息表';

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `phone_number` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `admin` tinyint(2) DEFAULT '0' COMMENT '是否超级管理员（0：不是；1：是）',
  `is_del` tinyint(2) DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
