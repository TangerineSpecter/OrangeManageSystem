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

 Date: 23/08/2019 01:59:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_constellation
-- ----------------------------
DROP TABLE IF EXISTS `data_constellation`;
CREATE TABLE `data_constellation` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(8) DEFAULT '' COMMENT '星座名称',
  `datetime` varchar(255) DEFAULT '' COMMENT '日期（文字）',
  `date` int(15) DEFAULT NULL COMMENT '日期（数字）',
  `all_luck` varchar(8) DEFAULT NULL COMMENT '综合指数',
  `color` varchar(8) DEFAULT NULL COMMENT '幸运色',
  `health` varchar(8) DEFAULT NULL COMMENT '健康指数',
  `love` varchar(8) DEFAULT NULL COMMENT '爱情指数',
  `money` varchar(8) DEFAULT NULL COMMENT '财运指数',
  `number` int(5) DEFAULT NULL COMMENT '幸运数字',
  `QFriend` varchar(8) DEFAULT NULL COMMENT '速配星座',
  `summary` varchar(255) DEFAULT NULL COMMENT '今日概述',
  `work_luck` varchar(8) DEFAULT '' COMMENT '工作指数',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1164580241682800642 DEFAULT CHARSET=utf8 COMMENT='星座信息表';

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `sex` tinyint(2) DEFAULT '0' COMMENT '性别（0：男；1：女）',
  `phone_number` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `brief` varchar(255) DEFAULT NULL COMMENT '简介',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `last_login_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `salt` varchar(10) DEFAULT NULL COMMENT '盐',
  `admin` tinyint(2) DEFAULT '0' COMMENT '是否超级管理员（0：不是；1：是）',
  `is_del` tinyint(2) DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1164595569015955458 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for work_collection
-- ----------------------------
DROP TABLE IF EXISTS `work_collection`;
CREATE TABLE `work_collection` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` tinyint(3) DEFAULT NULL COMMENT '类型：（1:网址）',
  `sort` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_del` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='内容收藏表';

SET FOREIGN_KEY_CHECKS = 1;
