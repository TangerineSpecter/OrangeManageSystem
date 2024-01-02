# 令牌记录表
DROP TABLE
    IF
        EXISTS `system_token`;
CREATE TABLE `system_token`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(64) NOT NULL COMMENT '名称描述',
    `token`       VARCHAR(128)         DEFAULT NULL COMMENT '令牌',
    `platform`    TINYINT(2)  NOT NULL COMMENT '平台，0：飞书',
    `type`        TINYINT(2)  NOT NULL COMMENT '类型，0：机器人',
    `is_del`      TINYINT(2)  NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除；1：已删除）',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '令牌记录表';


# 系统定时任务表
DROP TABLE
    IF
        EXISTS `system_scheduled_task`;
CREATE TABLE `system_scheduled_task`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `class_path`  VARCHAR(128) NOT NULL COMMENT '任务完整类名',
    `name`        VARCHAR(64)  NOT NULL COMMENT '任务名称',
    `cron`        VARCHAR(32)  NOT NULL COMMENT '时间表达式',
    `type`        tinyint(4)   NOT NULL DEFAULT 0 COMMENT '类型（0：一般调度，1：机器人通知）',
    `msg_type`    tinyint(4)            DEFAULT null comment '0：简单消息',
    `extra_info`  VARCHAR(64)           DEFAULT NULL COMMENT '类型对应的额外信息，比如，绑定的机器人id',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '描述',
    `status`      TINYINT(2)   NOT NULL DEFAULT 1 COMMENT '状态，0：停用；1：启用',
    `is_del`      TINYINT(2)   NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除；1：已删除）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统定时任务表';
