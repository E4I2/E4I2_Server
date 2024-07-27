package io.e4i2.service.impl;

import io.e4i2.dto.DeviceEvtDTO;
import io.e4i2.repository.DeviceEvtDAO;
import io.e4i2.service.DeviceEvtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceEvtServiceImpl implements DeviceEvtService {

    @Autowired
    private DeviceEvtDAO deviceEvtDAO;

    @Override
    public void insertDevice(DeviceEvtDTO deviceEvtDTO) {
         int deviceId = deviceEvtDAO.insertDevice(deviceEvtDTO);
        deviceEvtDTO.setDeviceId(deviceId);
        deviceEvtDAO.insertDeviceEvt(deviceEvtDTO);
    }


}
