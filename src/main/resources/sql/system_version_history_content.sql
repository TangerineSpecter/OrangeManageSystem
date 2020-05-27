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

 Date: 27/05/2020 13:45:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_version_history_content
-- ----------------------------
DROP TABLE IF EXISTS `system_version_history_content`;
CREATE TABLE `system_version_history_content` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(13) DEFAULT NULL COMMENT '版本ID',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统版本更新内容';

-- ----------------------------
-- Records of system_version_history_content
-- ----------------------------
BEGIN;
INSERT INTO `system_version_history_content` VALUES (1, 1, '新增：角色管理模块');
INSERT INTO `system_version_history_content` VALUES (2, 1, '新增：必应每日壁纸模块');
INSERT INTO `system_version_history_content` VALUES (3, 2, '修复：菜单管理编辑菜单地址回显bug');
INSERT INTO `system_version_history_content` VALUES (4, 2, '调整：权限匹配为全匹配规则');
INSERT INTO `system_version_history_content` VALUES (5, 2, '新增：系统权限管理模块');
INSERT INTO `system_version_history_content` VALUES (6, 2, '调整：角色权限关系');
INSERT INTO `system_version_history_content` VALUES (7, 2, '修复：权限验证');
INSERT INTO `system_version_history_content` VALUES (8, 2, '调整：限制同一账号登录在线');
INSERT INTO `system_version_history_content` VALUES (9, 2, '调整：健康记录更新弹窗提示方式');
INSERT INTO `system_version_history_content` VALUES (10, 2, '调整：调整健康记录数据单位');
INSERT INTO `system_version_history_content` VALUES (11, 2, '调整：首页展示');
INSERT INTO `system_version_history_content` VALUES (12, 2, '修复：修复密码修改不生效bug');
INSERT INTO `system_version_history_content` VALUES (13, 2, '修复：添加菜单权限初始化、删除菜单权限清理bug');
INSERT INTO `system_version_history_content` VALUES (14, 1, '调整：后台全局异常日志打印');
INSERT INTO `system_version_history_content` VALUES (15, 3, '调整：交易记录为私人所有');
INSERT INTO `system_version_history_content` VALUES (16, 3, '新增：七牛云图床SDK支持');
INSERT INTO `system_version_history_content` VALUES (17, 3, '新增：账号密码修改功能');
INSERT INTO `system_version_history_content` VALUES (18, 3, '修复：首页快捷入口图表展示bug');
INSERT INTO `system_version_history_content` VALUES (19, 3, '新增：健康管理新增步数统计');
INSERT INTO `system_version_history_content` VALUES (20, 3, '修复：账号资料更新异常bug');
INSERT INTO `system_version_history_content` VALUES (21, 4, '调整：列表清空操作清空查询数据');
INSERT INTO `system_version_history_content` VALUES (22, 4, '调整：进行layui版本升级为2.5.6');
INSERT INTO `system_version_history_content` VALUES (23, 4, '修复：系统日志搜索功能');
INSERT INTO `system_version_history_content` VALUES (24, 4, '新增：健康管理数据统计模块');
INSERT INTO `system_version_history_content` VALUES (25, 5, '新增：系统公告管理模块');
INSERT INTO `system_version_history_content` VALUES (26, 5, '新增：个人健康管理模块');
INSERT INTO `system_version_history_content` VALUES (27, 5, '修复：交易记录数据为0的bug');
INSERT INTO `system_version_history_content` VALUES (28, 6, '新增：首页新增资金管理图表');
INSERT INTO `system_version_history_content` VALUES (29, 6, '新增：session监听器');
INSERT INTO `system_version_history_content` VALUES (30, 6, '新增：交易数据统计模块');
INSERT INTO `system_version_history_content` VALUES (31, 6, '调整：编辑页数据初始化方式');
INSERT INTO `system_version_history_content` VALUES (32, 6, '调整：优化添加编辑页传参方式');
INSERT INTO `system_version_history_content` VALUES (33, 7, '调整：编辑收藏内容页面');
INSERT INTO `system_version_history_content` VALUES (34, 7, '修复：股票池搜索功能');
INSERT INTO `system_version_history_content` VALUES (35, 8, '新增：问题管理模块');
INSERT INTO `system_version_history_content` VALUES (36, 8, '新增：首页版本号展示');
INSERT INTO `system_version_history_content` VALUES (37, 8, '新增：菜单管理首页置顶按钮');
INSERT INTO `system_version_history_content` VALUES (38, 9, '新增：首页系统公告栏展示');
INSERT INTO `system_version_history_content` VALUES (39, 9, '新增：个人星座运势图');
INSERT INTO `system_version_history_content` VALUES (40, 9, '新增：apexCharts图表组件');
INSERT INTO `system_version_history_content` VALUES (41, 10, '新增：菜单权限控制');
INSERT INTO `system_version_history_content` VALUES (42, 11, '新增：超级管理员权限初始化');
INSERT INTO `system_version_history_content` VALUES (43, 12, '新增：角色管理模块');
INSERT INTO `system_version_history_content` VALUES (44, 12, '新增：首页数据展示');
INSERT INTO `system_version_history_content` VALUES (45, 13, '新增：股票池模块');
INSERT INTO `system_version_history_content` VALUES (46, 15, '新增：权限管理功能');
INSERT INTO `system_version_history_content` VALUES (47, 14, '新增：交易数据管理模块');
INSERT INTO `system_version_history_content` VALUES (48, 16, '新增：首页天气组件');
INSERT INTO `system_version_history_content` VALUES (49, 15, '修复：登录信息更新失败bug');
INSERT INTO `system_version_history_content` VALUES (50, 16, '新增：收藏管理模块');
INSERT INTO `system_version_history_content` VALUES (51, 16, '调整：shiro授权方式');
INSERT INTO `system_version_history_content` VALUES (52, 17, '调整：列表页面进行redis缓存处理');
INSERT INTO `system_version_history_content` VALUES (53, 17, '新增：二维码生成模块');
INSERT INTO `system_version_history_content` VALUES (54, 17, '新增：同一用户频繁请求进行次数限流');
INSERT INTO `system_version_history_content` VALUES (55, 18, '新增：系统日志管理模块');
INSERT INTO `system_version_history_content` VALUES (56, 18, '新增：管理员管理模块');
INSERT INTO `system_version_history_content` VALUES (57, 18, '新增：账号设置功能');
INSERT INTO `system_version_history_content` VALUES (58, 18, '新增：聚合数据API支持');
INSERT INTO `system_version_history_content` VALUES (59, 18, '新增：星座列表模块');
INSERT INTO `system_version_history_content` VALUES (60, 18, '新增：系统菜单管理模块');
INSERT INTO `system_version_history_content` VALUES (61, 19, '系统初始化...');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
