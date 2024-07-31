package io.e4i2.service;

import io.e4i2.dto.DeviceEvtDTO;
import io.e4i2.dto.ResponseDTO;

public interface DeviceEvtService {

    ResponseDTO insertDevice(DeviceEvtDTO deviceEvtDTO);

}