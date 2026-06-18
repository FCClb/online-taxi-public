package com.fc.internalcommon.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * (OrderInfo)实体类
 */
@Data
public class OrderInfo {

    /**
     * 订单主键id
     */
    private Long id;
    /**
     * 乘客id
     */
    private Long passengerId;
    /**
     * 乘客手机号
     */
    private String passengerPhone;
    /**
     * 司机id
     */
    private Long driverId;
    /**
     * 司机手机号
     */
    private String driverPhone;
    /**
     * 车辆id
     */
    private Long carId;
    /**
     * 发起地行政区划编码
     */
    private String address;
    /**
     * 下单时间
     */
    private LocalDateTime orderTime;
    /**
     * 预计用车时间
     */
    private LocalDateTime departTime;
    /**
     * 预计出发地点详细地址
     */
    private String departure;
    /**
     * 出发地经度
     */
    private String depLongitude;
    /**
     * 出发地纬度
     */
    private String depLatitude;
    /**
     * 预计目的地地址
     */
    private String destination;
    /**
     * 预计目的地经度
     */
    private String destLongitude;
    /**
     * 预计目的地纬度
     */
    private String destLatitude;
    /**
     * 坐标加密标识(1：GCJ-02 测绘局标准；2：WGS84 GPS标准；3：JBD-09 百度标准；4：CGCS2000 北斗标准；0：其他)
     */
    private Integer encrypt;
    /**
     * 运价类型编码
     */
    private String fareType;
    /**
     * 司机接单时车辆经度
     */
    private String receiveOrderCarLongitude;
    /**
     * 司机接单时车辆纬度
     */
    private String receiveOrderCarLatitude;
    /**
     * 司机接单时间，派单成功时间
     */
    private LocalDateTime receiveOrderTime;
    /**
     * 驾驶证编号
     */
    private String licenseId;
    /**
     * 车牌号
     */
    private String vehicleNo;
    /**
     * 司机前往接驾时间
     */
    private LocalDateTime toPickUpPassengerTime;
    /**
     * 前往接驾时，司机经度
     */
    private String toPickUpPassengerLongitude;
    /**
     * 前往接驾时，司机纬度
     */
    private String toPickUpPassengerLatitude;
    /**
     * 接驾时，司机地址
     */
    private String toPickUpPassengerAddress;
    /**
     * 司机到达上车点时间
     */
    private LocalDateTime driverArrivedDepartureTime;
    /**
     * 乘客上车时间
     */
    private LocalDateTime pickUpPassengerTime;
    /**
     * 乘客上车经度
     */
    private String pickUpPassengerLongitude;
    /**
     * 乘客上车纬度
     */
    private String pickUpPassengerLatitude;
    /**
     * 乘客下车时间
     */
    private LocalDateTime passengerGetoffTime;
    /**
     * 乘客下车经度
     */
    private String passengerGetoffLongitude;
    /**
     * 乘客下车纬度
     */
    private String passengerGetoffLatitude;
    /**
     * 订单取消时间
     */
    private LocalDateTime cancelTime;
    /**
     * 取消操作人
     */
    private Integer cancelOperator;
    /**
     * 取消类型编码(1：乘客提前撤销 2：驾驶员提前撤销 3：平台公司撤销 4：乘客违约撤销 5：驾驶员违约撤销)
     */
    private Integer cancelTypeCode;
    /**
     * 载客里程(米)
     */
    private Long driveMile;
    /**
     * 载客时长(分)
     */
    private Long driveTime;
    /**
     * 订单状态(1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9. 订单取消)
     */
    private Integer orderStatus;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}

