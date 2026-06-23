-- 订单服务 数据库脚本

-- 创建数据库 service_order
CREATE DATABASE IF NOT EXISTS `service_order` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用该数据库
USE `service_order`;

DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单主键id',
                         `passenger_id` bigint NULL DEFAULT NULL COMMENT '乘客id',
                         `passenger_phone` varchar(16) NULL DEFAULT NULL COMMENT '乘客手机号',
                         `driver_id` bigint NULL DEFAULT NULL COMMENT '司机id',
                         `driver_phone` varchar(16) NULL DEFAULT NULL COMMENT '司机手机号',
                         `car_id` bigint NULL DEFAULT NULL COMMENT '车辆id',
                         `vehicle_type` varchar(8) NULL DEFAULT NULL COMMENT '车型',
                         `address` char(6) NULL DEFAULT NULL COMMENT '发起地行政区划编码',
                         `order_time` datetime NULL DEFAULT NULL COMMENT '下单时间',
                         `depart_time` datetime NULL DEFAULT NULL COMMENT '预计用车时间',
                         `departure` varchar(128) NULL DEFAULT NULL COMMENT '预计出发地点详细地址',
                         `dep_longitude` varchar(16) NULL DEFAULT NULL COMMENT '出发地经度',
                         `dep_latitude` varchar(16) NULL DEFAULT NULL COMMENT '出发地纬度',
                         `destination` varchar(128) NULL DEFAULT NULL COMMENT '预计目的地地址',
                         `dest_longitude` varchar(16) NULL DEFAULT NULL COMMENT '预计目的地经度',
                         `dest_latitude` varchar(16) NULL DEFAULT NULL COMMENT '预计目的地纬度',
                         `encrypt` int NULL DEFAULT NULL COMMENT '坐标加密标识(1：GCJ-02 测绘局标准；2：WGS84 GPS标准；3：JBD-09 百度标准；4：CGCS2000 北斗标准；0：其他)',
                         `fare_type` varchar(16) NULL DEFAULT NULL COMMENT '运价类型编码',
                         `fare_version` int NULL DEFAULT NULL COMMENT '运价类型版本',
                         `receive_order_car_longitude` varchar(16) NULL DEFAULT NULL COMMENT '司机接单时车辆经度',
                         `receive_order_car_latitude` varchar(16) NULL DEFAULT NULL COMMENT '司机接单时车辆纬度',
                         `receive_order_time` datetime NULL DEFAULT NULL COMMENT '司机接单时间，派单成功时间',
                         `license_id` varchar(128) NULL DEFAULT NULL COMMENT '驾驶证编号',
                         `vehicle_no` varchar(8) NULL DEFAULT NULL COMMENT '车牌号',
                         `to_pick_up_passenger_time` datetime NULL DEFAULT NULL COMMENT '司机前往接驾时间',
                         `to_pick_up_passenger_longitude` varchar(16) NULL DEFAULT NULL COMMENT '前往接驾时，司机经度',
                         `to_pick_up_passenger_latitude` varchar(16) NULL DEFAULT NULL COMMENT '前往接驾时，司机纬度',
                         `to_pick_up_passenger_address` varchar(128) NULL DEFAULT NULL COMMENT '接驾时，司机地址',
                         `driver_arrived_departure_time` datetime NULL DEFAULT NULL COMMENT '司机到达上车点时间',
                         `pick_up_passenger_time` datetime NULL DEFAULT NULL COMMENT '乘客上车时间',
                         `pick_up_passenger_longitude` varchar(16) NULL DEFAULT NULL COMMENT '乘客上车经度',
                         `pick_up_passenger_latitude` varchar(16) NULL DEFAULT NULL COMMENT '乘客上车纬度',
                         `passenger_getoff_time` datetime NULL DEFAULT NULL COMMENT '乘客下车时间',
                         `passenger_getoff_longitude` varchar(16) NULL DEFAULT NULL COMMENT '乘客下车经度',
                         `passenger_getoff_latitude` varchar(16) NULL DEFAULT NULL COMMENT '乘客下车纬度',
                         `cancel_time` datetime NULL DEFAULT NULL COMMENT '订单取消时间',
                         `cancel_operator` int NULL DEFAULT NULL COMMENT '取消操作人',
                         `cancel_type_code` int NULL DEFAULT NULL COMMENT '取消类型编码(1：乘客提前撤销 2：驾驶员提前撤销 3：平台公司撤销 4：乘客违约撤销 5：驾驶员违约撤销)',
                         `drive_mile` bigint NULL DEFAULT NULL COMMENT '载客里程(米)',
                         `drive_time` bigint NULL DEFAULT NULL COMMENT '载客时长(分)',
                         `order_status` int NULL DEFAULT NULL COMMENT '订单状态(1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9. 订单取消)',
                         `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                         `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;