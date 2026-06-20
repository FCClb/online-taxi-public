package com.fc.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.internalcommon.dto.DriverUser;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Resource
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    public int selectDriverUserCountByCityCode(@Param("cityCode") String username);
}
