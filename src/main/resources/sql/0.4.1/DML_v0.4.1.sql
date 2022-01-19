SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_wall_page
-- ----------------------------
DROP TABLE IF EXISTS `data_wall_page`;
CREATE TABLE `data_wall_page`
(
    `id`              bigint                                                        NOT NULL AUTO_INCREMENT,
    `start_date`      bigint                                                        NULL DEFAULT NULL COMMENT '' 开始时间（yyyyMMdd）'',
    `end_date`        bigint                                                        NULL DEFAULT NULL COMMENT '' 结束时间（yyyyMMdd）'',
    `url`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '' 壁纸url '',
    `full_start_date` bigint                                                        NULL DEFAULT NULL COMMENT '' 完整开始时间 '',
    `copyright`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '' 版权信息 '',
    `copyright_link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '' 跳转搜索链接 '',
    `title`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '' 标题 '',
    `hash`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '' 哈希值 '',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic
  COLLATE = utf8mb4_unicode_ci COMMENT ='每日壁纸表';

-- ----------------------------
-- Table structure for user_card_note
-- ----------------------------
DROP TABLE IF EXISTS `user_card_note`;
CREATE TABLE `user_card_note`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT,
    `content`     text COLLATE utf8mb4_unicode_ci COMMENT '笔记内容',
    `admin_id`    bigint   NOT NULL COMMENT '管理员id',
    `is_del`      tinyint  NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime          DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户卡片笔记表';

-- ----------------------------
-- Table structure for user_card_note_tag
-- ----------------------------
DROP TABLE IF EXISTS `user_card_note_tag`;
CREATE TABLE `user_card_note_tag`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签名称',
    `admin_id`    bigint   NOT NULL COMMENT '管理员id',
    `top`      tinyint  NOT NULL DEFAULT '0' COMMENT '是否置顶（0：未置顶；1：已置顶）',
    `is_del`      tinyint  NOT NULL                       DEFAULT '0' COMMENT '删除状态（0：未删除；1：已删除）',
    `create_time` datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户笔记标签表';

-- ----------------------------
-- Table structure for user_note_tag_assoc
-- ----------------------------
DROP TABLE IF EXISTS `user_note_tag_assoc`;
CREATE TABLE `user_note_tag_assoc`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `note_id` bigint NOT NULL COMMENT '笔记id',
    `tag_id`  bigint NOT NULL COMMENT '标签id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户笔记-标签关联表';

SET FOREIGN_KEY_CHECKS = 1;