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

 Date: 27/05/2020 13:44:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_version_history
-- ----------------------------
DROP TABLE IF EXISTS `system_version_history`;
CREATE TABLE `system_version_history` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `version` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本号',
  `version_number` int(10) DEFAULT NULL COMMENT '版本号数字',
  `create_time` date DEFAULT NULL COMMENT '版本创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统版本历史记录表';

-- ----------------------------
-- Records of system_version_history
-- ----------------------------
BEGIN;
INSERT INTO `system_version_history` VALUES (1, 'v0.3.4版本更新', 340, '2020-05-26');
INSERT INTO `system_version_history` VALUES (2, 'v0.3.3版本更新', 330, '2020-05-20');
INSERT INTO `system_version_history` VALUES (3, 'v0.3.2版本更新', 320, '2020-05-14');
INSERT INTO `system_version_history` VALUES (4, 'v0.3.1版本更新', 310, '2020-05-12');
INSERT INTO `system_version_history` VALUES (5, 'v0.3.0版本更新', 300, '2020-05-09');
INSERT INTO `system_version_history` VALUES (6, 'v0.2.1版本更新', 210, '2020-05-04');
INSERT INTO `system_version_history` VALUES (7, 'v0.2.0版本更新', 200, '2020-04-28');
INSERT INTO `system_version_history` VALUES (8, 'v0.1.2版本更新', 120, '2020-04-26');
INSERT INTO `system_version_history` VALUES (9, 'v0.1.1版本更新', 110, '2020-04-21');
INSERT INTO `system_version_history` VALUES (10, 'v0.1.0版本更新', 100, '2020-04-20');
INSERT INTO `system_version_history` VALUES (11, 'v0.0.9版本更新', 90, '2020-04-19');
INSERT INTO `system_version_history` VALUES (12, 'v0.0.8版本更新', 80, '2020-04-18');
INSERT INTO `system_version_history` VALUES (13, 'v0.0.7版本更新', 70, '2020-04-15');
INSERT INTO `system_version_history` VALUES (14, 'v0.0.6版本更新', 60, '2020-04-14');
INSERT INTO `system_version_history` VALUES (15, 'v0.0.5版本更新', 50, '2020-02-15');
INSERT INTO `system_version_history` VALUES (16, 'v0.0.4版本更新', 40, '2019-09-19');
INSERT INTO `system_version_history` VALUES (17, 'v0.0.3版本更新', 30, '2019-09-10');
INSERT INTO `system_version_history` VALUES (18, 'v0.0.2版本更新', 20, '2019-09-07');
INSERT INTO `system_version_history` VALUES (19, 'v0.0.1版本更新', 10, '2019-01-03');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
