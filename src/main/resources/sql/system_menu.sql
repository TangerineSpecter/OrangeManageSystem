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

 Date: 04/09/2019 22:46:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单权限',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
BEGIN;
INSERT INTO `system_menu` VALUES (1, '常规管理', -1, NULL, 'fa fa-address-book', 0, '_self', NULL, 3);
INSERT INTO `system_menu` VALUES (2, '组件管理', -1, NULL, 'fa fa-lemon-o', 0, '_self', NULL, 2);
INSERT INTO `system_menu` VALUES (3, '其它管理', -1, NULL, 'fa fa-slideshare', 0, '_self', NULL, 1);
INSERT INTO `system_menu` VALUES (7, '系统设置', 1, NULL, 'fa fa-gears', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (8, '菜单管理', 7, '/system/menu/page', 'fa fa-window-maximize', 2, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (9, '表格示例', 2, 'table', 'fa fa-file-text', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (10, '表单示例', 2, NULL, 'fa fa-calendar', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (11, '普通表单', 10, 'form', 'fa fa-list-alt', 2, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (12, '分步表单', 10, 'form-step', 'fa fa-navicon', 2, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (13, '异常页面', 2, NULL, 'fa fa-home', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (14, '404页面', 13, 'errorPage', 'fa fa-hourglass-end', 2, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (15, '其他页面', 2, '', 'fa fa-snowflake-o', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (16, '按钮示例', 15, 'button', 'fa fa-snowflake-o', 2, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (17, '弹出层', 15, 'layer', 'fa fa-shield', 2, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (18, '图标列表', 2, 'icon', 'fa fa-dot-circle-o', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (19, '图标选择', 2, 'icon-picker', 'fa fa-adn', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (20, '颜色选择', 2, 'color-select', 'fa fa-dashboard', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (21, '下拉选择', 2, 'table-select', 'fa fa-angle-double-down', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (22, '文件上传', 2, 'upload', 'fa fa-arrow-up', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (23, '富文本编辑器', 2, 'editor', 'fa fa-edit', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (24, '失效菜单', 3, 'error', 'fa fa-superpowers', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (25, '后台管理员', 7, 'systemUser', 'fa fa-slideshare', 1, '_self', NULL, 10);
INSERT INTO `system_menu` VALUES (26, '个人管理', 1, NULL, 'fa fa-slideshare', 0, '_self', NULL, 99);
INSERT INTO `system_menu` VALUES (27, '乐趣印记', 1, NULL, 'fa fa-slideshare', 0, '_self', NULL, 88);
INSERT INTO `system_menu` VALUES (28, '小工具', 1, NULL, 'fa fa-slideshare', 1, '_self', NULL, 77);
INSERT INTO `system_menu` VALUES (29, '数据列表', 1, NULL, 'fa fa-slideshare', 1, '_self', NULL, 66);
INSERT INTO `system_menu` VALUES (30, '状况管理', 26, NULL, 'fa fa-slideshare', 1, '_self', NULL, 88);
INSERT INTO `system_menu` VALUES (31, '文章管理', 26, NULL, 'fa fa-slideshare', 1, '_self', NULL, 77);
INSERT INTO `system_menu` VALUES (32, '计划任务', 26, NULL, 'fa fa-slideshare', 1, '_self', NULL, 66);
INSERT INTO `system_menu` VALUES (33, '成就管理', 26, NULL, 'fa fa-slideshare', 1, '_self', NULL, 55);
INSERT INTO `system_menu` VALUES (34, '内容收藏', 27, NULL, 'fa fa-slideshare', 1, '_self', NULL, 88);
INSERT INTO `system_menu` VALUES (35, '二维码生成', 28, NULL, 'fa fa-slideshare', 1, '_self', NULL, 88);
INSERT INTO `system_menu` VALUES (36, '星座列表', 29, '/table/constellation/page', 'fa fa-slideshare', 1, '_self', NULL, 88);
INSERT INTO `system_menu` VALUES (37, '系统配置', 7, 'systemSetting', 'fa fa-slideshare', 1, '_self', NULL, 0);
INSERT INTO `system_menu` VALUES (39, '系统日志', 7, '/system/log/page', 'fa fa-file-text-o', 2, '_self', NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
