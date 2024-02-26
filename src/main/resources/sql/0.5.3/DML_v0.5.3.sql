#移除多余表
DROP TABLE
    IF
        EXISTS `system_version_history`;
DROP TABLE
    IF
        EXISTS `system_version_history_content`;

# 收益记录表
DROP TABLE
    IF
        EXISTS `statis_trade_record`;
CREATE TABLE `statis_trade_record`
(
    `id`                 bigint(20)  NOT NULL AUTO_INCREMENT,
    `date`               date        NOT NULL COMMENT '统计时间',
    `uid`                varchar(64) NOT NULL COMMENT '用户id',
    `money`              bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '当前资金（单位分），（按照当日汇率合并）',
    `income_value`       bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '当日收益（单位分），（按照当日汇率合并）',
    `income_rate`        DECIMAL(7, 2)        DEFAULT 0 DEFAULT NULL COMMENT '当日收益率（百分比）',
    `week`               int                  DEFAULT 0 DEFAULT NULL COMMENT '周数',
    `week_income_value`  bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '本周收益（单位分），（按照当日汇率合并）',
    `week_income_rate`   DECIMAL(7, 2)        DEFAULT 0 DEFAULT NULL COMMENT '本周收益率（百分比）',
    `month`              int                  DEFAULT 0 DEFAULT NULL COMMENT '月份',
    `month_income_value` bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '本月收益（单位分），（按照当日汇率合并）',
    `month_income_rate`  DECIMAL(7, 2)        DEFAULT 0 DEFAULT NULL COMMENT '本月收益率（百分比）',
    `year`               int                  DEFAULT 0 DEFAULT NULL COMMENT '年份',
    `year_income_value`  bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '本年收益（单位分），（按照当日汇率合并）',
    `year_income_rate`   DECIMAL(7, 2)        DEFAULT 0 DEFAULT NULL COMMENT '本年收益率（百分比）',
    `total_income_value` int                  DEFAULT 0 DEFAULT NULL COMMENT '累计总收益（单位分），（按照当日汇率合并）',
    `deposit`            bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '当日转入（单位分），（按照汇率合并）',
    `withdrawal`         bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '当日转出（单位分），（按照汇率合并）',
    `total_deposit`      bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '累计转入（单位分），（按照汇率合并）',
    `total_withdrawal`   bigint(32)           DEFAULT 0 DEFAULT NULL COMMENT '累计转出（单位分），（按照汇率合并）',
    `create_time`        datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='交易记录统计表';

#交易记录表新增出入金利率
ALTER TABLE `data_trade_record`
    ADD `deposit_rate` DECIMAL(5, 4) DEFAULT 0 comment "转入金额汇率" after `deposit`;
ALTER TABLE `data_trade_record`
    ADD `withdrawal_rate` DECIMAL(5, 4) DEFAULT 0 comment "转出金额汇率" after `withdrawal`;

#交易记录表新增唯一索引
CREATE UNIQUE INDEX `uk_uid_date_type` ON data_trade_record (`uid`, `date`, `type`);

#交易统计表新增唯一索引
CREATE UNIQUE INDEX `uk_uid_date` ON statis_trade_record (`uid`, `date`);

#交易记录表新增索引
alter table `data_trade_record`
    add index idx_uid_date_type (`uid`, `date`, `type`);
