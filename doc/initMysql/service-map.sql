-- 地图服务 数据库脚本

-- 创建数据库 service-map
CREATE DATABASE IF NOT EXISTS `service-map` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用该数据库
USE `service-map`;

DROP TABLE IF EXISTS `dic_district`;
CREATE TABLE `dic_district`  (
                                 `address_code` char(6) NOT NULL COMMENT '地区编码',
                                 `address_name` varchar(128) NULL DEFAULT NULL COMMENT '地区名称',
                                 `parent_address_code` char(6) NULL DEFAULT NULL COMMENT '父地区编码',
                                 `level` tinyint NULL DEFAULT NULL COMMENT '地区级别 1省 2市 3区',
                                 PRIMARY KEY (`address_code`) USING BTREE
) ENGINE = InnoDB;