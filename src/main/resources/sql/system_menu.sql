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

 Date: 28/06/2020 14:31:20
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
  `permission_code` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单权限code',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `top` tinyint(2) DEFAULT '0' COMMENT '是否在首页（0：否；1：是）',
  `top_sort` int(50) DEFAULT '0' COMMENT '首页排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
BEGIN;
INSERT INTO `system_menu` VALUES (1, '常规管理', -1, 'menuHeader1', 'fa fa fa-address-book', 0, '_self', 'ce23f05b9ba8f06dedc139abeae5b2e8', 3, 0, 0);
INSERT INTO `system_menu` VALUES (2, '组件管理', -1, 'menuHeader2', 'fa fa-lemon-o', 0, '_self', '60b91cd828717692a43ae2d0013c1287', 2, 0, 0);
INSERT INTO `system_menu` VALUES (3, '其它管理', -1, 'menuHeader3', 'fa fa-slideshare', 0, '_self', '026a7db6d3c3c24751c2f0fe8d0b5103', 1, 0, 0);
INSERT INTO `system_menu` VALUES (7, '系统设置', 1, '', 'fa fa fa-gears', 1, '_self', '09af50c7266259fce15bf12e7423527e', 0, 0, 0);
INSERT INTO `system_menu` VALUES (8, '菜单管理', 7, '/system/menu/page', 'fa fa-list-alt', 2, '_self', '49728da8a646661d67ff2cdfdf9470d8', 0, 0, 0);
INSERT INTO `system_menu` VALUES (9, '表格示例', 2, 'table', 'fa fa-file-text', 1, '_self', '97e66c5d2773db0a3761930b4f20e642', 0, 0, 0);
INSERT INTO `system_menu` VALUES (10, '表单示例', 2, NULL, 'fa fa-calendar', 1, '_self', 'e4ed95ff81d9d2cc6202160f6d687e34', 0, 0, 0);
INSERT INTO `system_menu` VALUES (11, '普通表单', 10, 'form', 'fa fa-list-alt', 2, '_self', 'e39d8b81fee1310d959fce8ba535daeb', 0, 0, 0);
INSERT INTO `system_menu` VALUES (12, '分步表单', 10, 'form-step', 'fa fa-navicon', 2, '_self', '528a7910a7593f239d8d40beecb44627', 0, 0, 0);
INSERT INTO `system_menu` VALUES (13, '异常页面', 2, NULL, 'fa fa-home', 1, '_self', 'c68bfca914b50863b043a32011a1a98b', 0, 0, 0);
INSERT INTO `system_menu` VALUES (14, '404页面', 13, 'errorPage', 'fa fa-hourglass-end', 2, '_self', '04a3a4ec0c8370157e1922637c765881', 0, 0, 0);
INSERT INTO `system_menu` VALUES (15, '其他页面', 2, '', 'fa fa-snowflake-o', 1, '_self', 'b4ae01a219827659699c987dee213a59', 0, 0, 0);
INSERT INTO `system_menu` VALUES (16, '按钮示例', 15, 'button', 'fa fa-snowflake-o', 2, '_self', '0b87d74cdfc6d7b68f720df30dea6e7b', 0, 0, 0);
INSERT INTO `system_menu` VALUES (17, '弹出层', 15, 'layer', 'fa fa-shield', 2, '_self', 'b2b6a883046637e108297c3d7d8890fa', 0, 0, 0);
INSERT INTO `system_menu` VALUES (18, '图标列表', 2, 'icon', 'fa fa-dot-circle-o', 1, '_self', 'fb5860508bc671b1a793e19329c16278', 0, 0, 0);
INSERT INTO `system_menu` VALUES (19, '图标选择', 2, 'icon-picker', 'fa fa-adn', 1, '_self', '957000e7712f56951b5a558fa8113b2e', 0, 0, 0);
INSERT INTO `system_menu` VALUES (20, '颜色选择', 2, 'color-select', 'fa fa-dashboard', 1, '_self', '0ca635c8480a29f6621b12de47af75ee', 0, 0, 0);
INSERT INTO `system_menu` VALUES (21, '下拉选择', 2, 'table-select', 'fa fa-angle-double-down', 1, '_self', '6971fcf9ff2900e37349557fe9fbe86f', 0, 0, 0);
INSERT INTO `system_menu` VALUES (22, '文件上传', 2, 'upload', 'fa fa-arrow-up', 1, '_self', 'c94709b22c8eddf5bf229ebb8930dfe9', 0, 0, 0);
INSERT INTO `system_menu` VALUES (23, '富文本编辑器', 2, 'editor', 'fa fa-edit', 1, '_self', 'ccb7c447a89e55af74c570b5ef4e3d8c', 0, 0, 0);
INSERT INTO `system_menu` VALUES (24, '失效菜单', 3, 'error', 'fa fa-superpowers', 1, '_self', 'f2469847bc1163c739a83cbc5b41bdf6', 0, 0, 0);
INSERT INTO `system_menu` VALUES (25, '后台管理员', 7, 'systemUser/page', 'fa fa fa fa-slideshare', 2, '_self', 'ea55064a6a4c5ff8521d1d95225bd64a', 10, 0, 0);
INSERT INTO `system_menu` VALUES (26, '个人管理', 1, '', 'fa fa fa-slideshare', 1, '_self', '149cb9ceaf8839583c33010d4180109a', 99, 0, 0);
INSERT INTO `system_menu` VALUES (27, '乐趣印记', 1, '', 'fa fa fa-slideshare', 1, '_self', '69b6be3581ef75d23d7bf1883fa80fa8', 88, 0, 0);
INSERT INTO `system_menu` VALUES (28, '小工具', 1, '', 'fa fa-dropbox', 1, '_self', '9557d9be5c3c195794c2633bbc549bea', 77, 0, 0);
INSERT INTO `system_menu` VALUES (29, '数据列表', 1, NULL, 'fa fa-slideshare', 1, '_self', 'c05a9457e5219981a072effad502e649', 66, 0, 0);
INSERT INTO `system_menu` VALUES (30, '交易记录', 66, '/data/trade-record/page', 'fa fa fa-bar-chart', 2, '_self', '6dca78f47266a576a937de0ee40f853c', 88, 1, 0);
INSERT INTO `system_menu` VALUES (31, '日记管理', 26, '/user/diary/page', 'fa fa-book', 2, '_self', '50ebee3ffea64b913fb157898fa41b62', 77, 1, 0);
INSERT INTO `system_menu` VALUES (32, '计划任务', 26, '', 'fa fa fa-slideshare', 2, '_self', '8ec9f047bb982121febbfbce8c1fcca0', 66, 0, 0);
INSERT INTO `system_menu` VALUES (33, '成就管理', 26, '', 'fa fa fa-slideshare', 2, '_self', 'e616123a3a903294dee6b4487cb63a23', 55, 0, 0);
INSERT INTO `system_menu` VALUES (34, '内容收藏', 27, '/work/collection/page', 'fa fa-bookmark', 2, '_self', 'f0a551afb26aa43926b5f8a82a6e1f09', 88, 1, 0);
INSERT INTO `system_menu` VALUES (35, '二维码生成', 28, '/tools/qr-code/page', 'fa fa-qrcode', 2, '_self', 'e9e02a9c5597941b6e33551e18842094', 88, 0, 0);
INSERT INTO `system_menu` VALUES (36, '星座列表', 29, '/table/constellation/page', 'fa fa-star', 1, '_self', '600c493c06301d1202836450c7c67140', 88, 0, 0);
INSERT INTO `system_menu` VALUES (37, '系统配置', 7, '/page/systemSetting', 'fa fa-cogs', 2, '_self', 'a98056cdcbbac449d74c64ed38179db6', 0, 0, 0);
INSERT INTO `system_menu` VALUES (39, '系统日志', 7, '/system/log/page', 'fa fa fa fa-file-text-o', 2, '_self', '393a5b35276b7842490787f7aa572fc0', 0, 1, 0);
INSERT INTO `system_menu` VALUES (65, '角色管理', 7, '/system/role/page', 'fa fa-group', 2, '_self', '19204251f17cc85fb6e2b99712b88300', 0, 0, 0);
INSERT INTO `system_menu` VALUES (66, '交易管理', 1, '', 'fa fa-balance-scale', 1, '_self', '1ad67c75a746a81560263ad421e0d328', 90, 0, 0);
INSERT INTO `system_menu` VALUES (67, '股票池', 66, '/data/stock/page', 'fa fa fa-book', 2, '_self', 'b016424e66794066ddb4b0192359b2ec', 85, 1, 0);
INSERT INTO `system_menu` VALUES (68, '相关个股', 66, '', 'fa fa-address-book', 2, '_self', '14401fdf279ed08b55d8b2b6f73e2812', 83, 0, 0);
INSERT INTO `system_menu` VALUES (69, '问题管理', 26, '/data/question/page', 'fa fa fa-book', 2, '_self', 'f2aefa425d7fc697e91b15dab3bd77f9', 0, 0, 0);
INSERT INTO `system_menu` VALUES (70, '交易统计', 71, '/statis/trade/page', 'fa fa fa-area-chart', 2, '_self', '772be3f75367bf53446636649dee7a72', 99, 0, 0);
INSERT INTO `system_menu` VALUES (71, '数据分析', 1, '', 'fa fa fa-area-chart', 1, '_self', '002a4914b90e9b7d86ebbe8166cc7005', 55, 0, 0);
INSERT INTO `system_menu` VALUES (72, '系统公告', 7, 'system/bulletin/page', 'fa fa fa fa-bookmark', 2, '_self', '3afdbc914178d0dcd9f4173b2581e53c', 9, 0, 0);
INSERT INTO `system_menu` VALUES (73, '健康管理', 26, 'user/health/page', 'fa fa-heartbeat', 2, '_self', 'f8ac1bc7595ad24cd915154f623225ec', 88, 1, 0);
INSERT INTO `system_menu` VALUES (74, '健康统计', 71, 'statis/health/page', 'fa fa fa-odnoklassniki', 2, '_self', 'fd2456d9c01dc5bc8720bd679875bc4f', 88, 0, 0);
INSERT INTO `system_menu` VALUES (75, '权限管理', 7, '/system/permission/page', 'fa fa-500px', 2, '_self', '96687a8b1f64ee64c6f8183ecc91494e', 0, 0, 0);
INSERT INTO `system_menu` VALUES (76, '每日壁纸', 28, '/tools/wall-paper/page', 'fa fa-photo', 2, '_self', 'f95bdecc9f5915a121727408c4d63812', 0, 0, 0);
INSERT INTO `system_menu` VALUES (77, '版本记录', 7, '/page/versionHistory', 'fa fa fa-info-circle', 2, '_self', '0f7b8a04e50d1b9ad261614078c2d636', 5, 1, 0);
INSERT INTO `system_menu` VALUES (79, '交易逻辑', 66, '/data/trade-logic/page', 'fa fa-bitcoin', 2, '_self', '3c979daca43162779bae457f75f58466', 10, 1, 0);
INSERT INTO `system_menu` VALUES (84, '接口文档', 28, '/api-doc', 'layui-icon-face-smile-fine', 1, '_blank', 'c535b4f4cecbad34d556dd0a0c8c4738', 0, 0, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
