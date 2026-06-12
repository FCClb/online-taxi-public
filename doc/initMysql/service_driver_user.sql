-- 司机用户服务 数据库脚本

-- 创建数据库 service_driver_user
CREATE DATABASE IF NOT EXISTS `service_driver_user` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用该数据库
USE `service_driver_user`;

DROP TABLE IF EXISTS `driver_user`;
CREATE TABLE `driver_user`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '司机id',
                                `address` char(6) NULL DEFAULT NULL COMMENT '注册地行政区划代码',
                                `driver_name` varchar(16) NULL DEFAULT NULL COMMENT '司机姓名',
                                `driver_phone` varchar(16) NULL DEFAULT NULL COMMENT '司机手机号',
                                `driver_gender` tinyint NULL DEFAULT NULL COMMENT '司机性别（1男，2女）',
                                `driver_birthday` date NULL DEFAULT NULL COMMENT '司机出生年月日',
                                `driver_nation` char(2) NULL DEFAULT NULL COMMENT '司机民族',
                                `driver_contact_address` varchar(255) NULL DEFAULT NULL COMMENT '司机通信地址',
                                `license_id` varchar(128) NULL DEFAULT NULL COMMENT '司机驾驶证id',
                                `get_driver_license_date` date NULL DEFAULT NULL COMMENT '初次领取驾驶证日期',
                                `driver_license_on` date NULL DEFAULT NULL COMMENT '驾驶证有效期起',
                                `driver_license_off` date NULL DEFAULT NULL COMMENT '驾驶证有效期止',
                                `taxi_driver` tinyint NULL DEFAULT NULL COMMENT '是否巡游出租汽车（1是，2否）',
                                `certificate_no` varchar(255) NULL DEFAULT NULL COMMENT '网络预约出租汽车驾驶员资格证号',
                                `network_car_issue_organization` varchar(255) NULL DEFAULT NULL COMMENT '网络预约出租汽车驾驶证发证机构',
                                `network_car_issue_date` date NULL DEFAULT NULL COMMENT '资格证发证日期',
                                `get_network_car_proof_date` date NULL DEFAULT NULL COMMENT '初次领取资格证日期',
                                `network_car_proof_on` date NULL DEFAULT NULL COMMENT '资格证有效起始日期',
                                `network_car_proof_off` date NULL DEFAULT NULL COMMENT '资格证有效截止日期',
                                `register_date` date NULL DEFAULT NULL COMMENT '报备日期',
                                `commercial_type` tinyint NULL DEFAULT NULL COMMENT '服务类型（1网络预约出租汽车，2巡游出租汽车，3私人小客车合乘）',
                                `contract_company` varchar(255) NULL DEFAULT NULL COMMENT '驾驶员合同（协议）签署公司',
                                `contract_on` date NULL DEFAULT NULL COMMENT '合同有效期起',
                                `contract_off` date NULL DEFAULT NULL COMMENT '合同有效期止',
                                `state` tinyint NULL DEFAULT NULL COMMENT '司机状态（0有效，1失效）',
                                `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 插入测试数据
INSERT INTO `driver_user` (
    `address`,
    `driver_name`,
    `driver_phone`,
    `driver_gender`,
    `driver_birthday`,
    `driver_nation`,
    `driver_contact_address`,
    `license_id`,
    `get_driver_license_date`,
    `driver_license_on`,
    `driver_license_off`,
    `taxi_driver`,
    `certificate_no`,
    `network_car_issue_organization`,
    `network_car_issue_date`,
    `get_network_car_proof_date`,
    `network_car_proof_on`,
    `network_car_proof_off`,
    `register_date`,
    `commercial_type`,
    `contract_company`,
    `contract_on`,
    `contract_off`,
    `state`,
    `gmt_create`,
    `gmt_modified`
) VALUES (
             '110101',
             '张三',
             '13800138000',
             1,
             '1990-05-20',
             '汉',
             '北京市东城区建国门街道XX小区3号楼2单元101室',
             '110101199005201234',
             '2015-03-10',
             '2015-03-10',
             '2035-03-10',
             1,
             'W1234567890123',
             '北京市交通运输管理局',
             '2020-01-15',
             '2020-01-15',
             '2020-01-15',
             '2026-01-15',
             '2026-06-12',
             1,
             '北京XX网约车运营有限公司',
             '2025-01-01',
             '2028-12-31',
             0,
             NOW(),
             NOW()
         );