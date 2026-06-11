-- 计价规则 数据库脚本

-- 创建数据库 service_price
CREATE DATABASE IF NOT EXISTS `service_price` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用该数据库
USE `service_price`;

DROP TABLE IF EXISTS `price_rule`;

CREATE TABLE `price_rule`  (
                               `city_code` char(6) NOT NULL COMMENT '城市代码',
                               `vehicle_type` char(8) NOT NULL COMMENT '车型',
                               `start_fare` double(4, 2) NULL DEFAULT NULL COMMENT '起步价',
                               `start_mile` int NULL DEFAULT NULL COMMENT '起步里程',
                               `unit_price_per_mile` double(4, 2) NULL DEFAULT NULL COMMENT '每公里价钱（超出起步里程后）',
                               `unit_price_per_minute` double(4, 2) NULL DEFAULT NULL COMMENT '每分钟价钱',
                               PRIMARY KEY (`city_code`, `vehicle_type`) USING BTREE
) ENGINE = InnoDB;

-- 测试用例
INSERT INTO `price_rule` VALUES ('110000', '1', 10.00, 3, 1.80, 0.50);
