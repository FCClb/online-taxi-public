package com.fc.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 车辆 实体类
 */
@Data
public class Car {

    /**
     * 车辆id
     */
    private Long id;
    /**
     * 车辆所在城市
     */
    private String address;
    /**
     * 车辆号牌
     */
    private String vehicleNo;
    /**
     * 车牌颜色
     */
    private String plateColor;
    /**
     * 核定载客位
     */
    private Integer seats;
    /**
     * 车辆厂牌
     */
    private String brand;
    /**
     * 车辆型号
     */
    private String model;
    /**
     * 车辆类型
     */
    private String vehicleType;
    /**
     * 车辆所有人
     */
    private String ownerName;
    /**
     * 车辆颜色（1白色，2黑色）
     */
    private String vehicleColor;
    /**
     * 发动机号
     */
    private String engineId;
    /**
     * 车辆VIN码
     */
    private String vin;
    /**
     * 车辆注册日期
     */
    private Date certifyDateA;
    /**
     * 车辆燃料类型（1汽油，2柴油，3天然气，4液化气，5电动，9其他）
     */
    private String fuelType;
    /**
     * 发动机排量（毫升）
     */
    private String engineDisplace;
    /**
     * 车辆运输证发证机构
     */
    private String transAgency;
    /**
     * 车辆经营区域
     */
    private String transArea;
    /**
     * 车辆运输证有效期起
     */
    private Date transDateStart;
    /**
     * 车辆运输证有效期止
     */
    private Date transDateEnd;
    /**
     * 车辆初次登记日期
     */
    private Date certifyDateB;
    /**
     * 车辆检修状态（0未检修，1已检修，2未知）
     */
    private String fixState;
    /**
     * 车辆下次年检时间
     */
    private Date nextFixDate;
    /**
     * 车辆年度审验状态(0未年审，1年审合格，2年审不合格)
     */
    private String checkState;
    /**
     * 发票打印设备序列号
     */
    private String feePrintId;
    /**
     * 卫星定位装置品牌
     */
    private String gpsBrand;
    /**
     * 卫星定位装置型号
     */
    private String gpsModel;
    /**
     * 卫星定位设备安装日期
     */
    private Date gpsInstallDate;
    /**
     * 报备日期
     */
    private Date registerDate;
    /**
     * 服务类型（1网络预约出租汽车，2巡游出租汽车，3私人小客车合乘）
     */
    private Integer commercialType;
    /**
     * 运价类型编码
     */
    private String fareType;
    /**
     * 状态（0有效，1失效）
     */
    private Integer state;
    /**
     * 高德猎鹰服务终端tid
     */
    private String tid;
    /**
     * 高德猎鹰服务轨迹trid
     */
    private String trid;
    /**
     * 高德猎鹰服务轨迹名称
     */
    private String trname;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}

