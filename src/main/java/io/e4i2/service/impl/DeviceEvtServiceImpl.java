package io.e4i2.service.impl;

import io.e4i2.dto.DeviceEvtDTO;
import io.e4i2.repository.DeviceEvtDAO;
import io.e4i2.service.DeviceEvtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceEvtServiceImpl implements DeviceEvtService {

    
    private final DeviceEvtDAO deviceEvtDAO;
    
    @Override
    public void insertDevice(DeviceEvtDTO deviceEvtDTO) {
        DeviceEvtDTO deviceEvtDTOValue = deviceEvtDAO.duplicationCheck(deviceEvtDTO);
        if (deviceEvtDTOValue == null) {
            deviceEvtDAO.insertDevice(deviceEvtDTO);
            DeviceEvtDTO dto = deviceEvtDAO.duplicationCheck(deviceEvtDTO);
            deviceEvtDTO.setDevicePk(dto.getDevicePk());
            deviceEvtDAO.insertDeviceEvt(deviceEvtDTO);
        }else {
            deviceEvtDTO.setDevicePk(deviceEvtDTOValue.getDevicePk());
            deviceEvtDAO.insertDeviceEvt(deviceEvtDTO);
        }
    }


}
