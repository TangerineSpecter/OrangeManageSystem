/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : oms

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 06/01/2022 02:04:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_wall_page
-- ----------------------------
DROP TABLE IF EXISTS `data_wall_page`;
CREATE TABLE `data_wall_page`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `start_date` bigint NULL DEFAULT NULL COMMENT '开始时间（yyyyMMdd）',
  `end_date` bigint NULL DEFAULT NULL COMMENT '结束时间（yyyyMMdd）',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '壁纸url',
  `full_start_date` bigint NULL DEFAULT NULL COMMENT '完整开始时间',
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '版权信息',
  `copyright_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转搜索链接',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '哈希值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- 新增菜单
INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`, `top_sort`)
VALUES ('接口文档', 28, '/api-doc', 'layui-icon-face-smile-fine', 1, '_blank', 'c535b4f4cecbad34d556dd0a0c8c4738', 0, 0, 0);