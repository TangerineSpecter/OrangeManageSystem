DROP TABLE IF EXISTS `data_fund`;
CREATE TABLE `data_fund`
(
    `id`          BIGINT(13)   NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(128) NOT NULL COMMENT '基金名称',
    `simple_name` VARCHAR(32)           DEFAULT NULL COMMENT '基金名称（简拼）',
    `full_name`   VARCHAR(64)           DEFAULT NULL COMMENT '基金名称（全称）',
    `code`        VARCHAR(16)           DEFAULT NULL COMMENT '基金代码',
    `type`        VARCHAR(16)           DEFAULT '未知' COMMENT '基金类型',
    `is_del`      TINYINT      NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '基金数据表';

# 基金历史数据
DROP TABLE IF EXISTS `data_fund_history`;
CREATE TABLE `data_fund_history`
(
    `id`            BIGINT(13) NOT NULL AUTO_INCREMENT,
    `code`          varchar(16)   DEFAULT NULL COMMENT '基金代码',
    `date`          date          DEFAULT null COMMENT '时间',
    `earnings_rate` DECIMAL(8, 2) DEFAULT null COMMENT '收益率',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '基金历史数据';

# 基金数据索引
ALTER TABLE `data_fund`
    ADD UNIQUE (`code`);

# 唯一索引
ALTER TABLE `data_fund_history`
    ADD UNIQUE (`code`, `date`);
# 联合索引
alter table `data_fund_history`
    add index idx_code_date (`code`, `date`);