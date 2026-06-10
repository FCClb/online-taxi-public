-- 乘客用户 数据库脚本

-- 创建数据库 service-passenger-user
CREATE DATABASE IF NOT EXISTS `service-passenger-user` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用该数据库
USE `service-passenger-user`;

DROP TABLE IF EXISTS `passenger_user`;
CREATE TABLE `passenger_user`  (
                                   `id` bigint unsigned NOT NULL COMMENT '用户id',
                                   `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                   `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                   `passenger_phone` varchar(16) NULL DEFAULT NULL COMMENT '用户手机号',
                                   `passenger_name` varchar(16) NULL DEFAULT NULL COMMENT '用户称谓',
                                   `passenger_gender` tinyint(1) NULL DEFAULT NULL COMMENT '用户性别（0女，1男）',
                                   `state` tinyint(1) NULL DEFAULT NULL COMMENT '状态（0有效，1失效）',
                                   `profile_photo` varchar(128) NULL DEFAULT NULL COMMENT '头像图片地址的url',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 测试用例
INSERT INTO `passenger_user` VALUES (1, '2026-06-01 15:25:58', '2026-06-01 15:25:58', '15503478318', '芙芙', 0, 0, 'photoPhoto');