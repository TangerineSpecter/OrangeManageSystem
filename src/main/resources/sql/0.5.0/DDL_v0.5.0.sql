-- 新增菜单
INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`, `top_sort`)
VALUES ('接口文档', 28, '/api-doc', 'layui-icon-face-smile-fine', 1, '_blank', 'c535b4f4cecbad34d556dd0a0c8c4738', 0, 0, 0);

INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`, `top_sort`)
VALUES ('卡片笔记', 26, '/user/card-note/page', 'layui-icon-face-smile-fine', 1, '_self', '2f39c7aece86b2fa27498adf13060491', 0, 0, 0);

INSERT INTO oms.system_menu (title, pid, href, icon, level, target, permission_code, sort, top, top_sort)
VALUES ('视频去水印', 28, '/tools/video-watermark/page', 'layui-icon-face-smile-fine', 1, '_self', '', 0, 0, 0);

-- 新增版本记录
INSERT INTO `oms`.`system_version_history` (`version`, `version_number`) VALUES ('0.5.0版本更新', 500);
INSERT INTO oms.system_version_history_content (version_id, type, content) VALUES (22, 0, '后台界面框架改为Pear Admin');
INSERT INTO `oms`.`system_version_history_content` (`version_id`, `type`, `content`) VALUES (22, 0, '卡片笔记功能');
INSERT INTO oms.system_version_history_content (version_id, type, content) VALUES (22, 0, '视频去水印小工具');
INSERT INTO oms.system_version_history_content (version_id, type, content) VALUES (22, 2, '健康统计数据展示');