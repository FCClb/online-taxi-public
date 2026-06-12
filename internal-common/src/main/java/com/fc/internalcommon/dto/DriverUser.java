package com.fc.internalcommon.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DriverUser {

    /**
     * 司机id
     */
    private Integer id;

    /**
     * 注册地行政区划代码
     */
    private String address;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 司机手机号
     */
    private String driverPhone;

    /**
     * 司机性别（1男，2女）
     */
    private Integer driverGender;

    /**
     * 司机出生年月日
     */
    private LocalDate driverBirthday;

    /**
     * 司机民族
     */
    private String driverNation;

    /**
     * 司机通信地址
     */
    private String driverContactAddress;

    /**
     * 司机驾驶证id
     */
    private String licenseId;

    /**
     * 初次领取驾驶证日期
     */
    private LocalDate getDriverLicenseLocalDate;

    /**
     * 驾驶证有效期起
     */
    private LocalDate driverLicenseOn;

    /**
     * 驾驶证有效期止
     */
    private LocalDate driverLicenseOff;

    /**
     * 是否巡游出租汽车（1是，2否）
     */
    private Integer taxiDriver;

    /**
     * 网络预约出租汽车驾驶员资格证号
     */
    private String certificateNo;

    /**
     * 网络预约出租汽车驾驶证发证机构
     */
    private String networkCarIssueOrganization;

    /**
     * 资格证发证日期
     */
    private LocalDate networkCarIssueLocalDate;

    /**
     * 初次领取资格证日期
     */
    private LocalDate getNetworkCarProofLocalDate;

    /**
     * 资格证有效起始日期
     */
    private LocalDate networkCarProofOn;

    /**
     * 资格证有效截止日期
     */
    private LocalDate networkCarProofOff;

    /**
     * 报备日期
     */
    private LocalDate registerLocalDate;

    /**
     * 服务类型（1网络预约出租汽车，2巡游出租汽车，3私人小客车合乘）
     */
    private Integer commercialType;

    /**
     * 驾驶员合同（协议）签署公司
     */
    private String contractCompany;

    /**
     * 合同有效期起
     */
    private LocalDate contractOn;

    /**
     * 合同有效期止
     */
    private LocalDate contractOff;

    /**
     * 司机状态（0有效，1失效）
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

}
