-- 新增菜单
INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`, `top_sort`)
VALUES ('接口文档', 28, '/api-doc', 'layui-icon-face-smile-fine', 1, '_blank', 'c535b4f4cecbad34d556dd0a0c8c4738', 0, 0, 0);

INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`, `top_sort`)
VALUES ('卡片笔记', 26, '/user/card-note/page', 'layui-icon-face-smile-fine', 1, '_self', '2f39c7aece86b2fa27498adf13060491', 0, 0, 0);

-- 新增版本记录
INSERT INTO `oms`.`system_version_history_content` (`version_id`, `type`, `content`) VALUES (21, 0, '卡片笔记功能');