-- 新增菜单
INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`,
                                 `top_sort`)
VALUES ('接口文档', 28, '/api-doc', 'layui-icon-face-smile-fine', 1, '_blank', 'c535b4f4cecbad34d556dd0a0c8c4738', 0, 0, 0);

INSERT INTO `oms`.`system_menu` (`title`, `pid`, `href`, `icon`, `level`, `target`, `permission_code`, `sort`, `top`,
                                 `top_sort`)
VALUES ('卡片笔记', 26, '/user/card-note/page', 'layui-icon-face-smile-fine', 1, '_self',
        '2f39c7aece86b2fa27498adf13060491', 0, 0, 0);

INSERT INTO oms.system_menu (title, pid, href, icon, level, target, permission_code, sort, top, top_sort)
VALUES ('视频去水印', 28, '/tools/video-watermark/page', 'layui-icon-face-smile-fine', 1, '_self', '', 0, 0, 0);

-- 新增版本记录
INSERT INTO `oms`.`system_version_history` (`version`, `version_number`)
VALUES ('0.5.0版本更新', 500);
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '后台界面框架改为Pear Admin');
INSERT INTO `oms`.`system_version_history_content` (`version_id`, `type`, `content`)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '卡片笔记功能');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '视频去水印小工具');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 2, '健康统计数据展示');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 2, '交易统计数据展示');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '星座数据爬虫');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '汇率数据定时任务');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '交易记录支持多币种，接入汇率数据');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 2, '交易统计资金统计调整为历史累计');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 2, '交易统计资金统计调整为累计');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 1, '交易记录窗口默认数值，减少填写过于繁琐');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '交易统计新增收益率统计数据');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 0, '交易记录窗口新增计算操作，方便计算资金');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 4, '资源服务由七牛云调整为腾讯云cos');
INSERT INTO oms.system_version_history_content (version_id, type, content)
VALUES ((select id from system_version_history where version_number = 500 limit 1), 1, '交易统计数据处理性能');

-- 修改交易数据数据类型
update data_trade_record
set currency = 'USD'
where type = 2;
-- 根据货币类型修改货币资金
update data_trade_record
set start_money = round(start_money / 7.2),
    end_money   = round(end_money / 7.2)
where type = 2;
-- 剔除小数位毛刺偏差
update  data_trade_record
set start_money = CEILING(start_money/ 100),
    end_money = CEILING(end_money /100)
where type = 2;
-- 恢复到百分位
update  data_trade_record
set start_money = (start_money * 100),
    end_money = (end_money * 100)
where type = 2;