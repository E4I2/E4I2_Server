package io.e4i2.repository;

import io.e4i2.dto.DeviceEvtDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceEvtDAO {

    void insertDeviceEvt(DeviceEvtDTO deviceEvtDTO);

    int insertDevice(DeviceEvtDTO deviceEvtDTO);

}
