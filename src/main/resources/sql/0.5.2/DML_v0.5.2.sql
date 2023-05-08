# 令牌记录表
DROP TABLE
    IF
        EXISTS `system_token`;
CREATE TABLE `system_token`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(64) NOT NULL COMMENT '名称描述',
    `token`       VARCHAR(128)         DEFAULT NULL COMMENT '令牌',
    `type`        tinyint(2)  NOT NULL COMMENT '类型，1：通知机器人',
    `is_del`      TINYINT(2)  NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除；1：已删除）',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '令牌记录表';