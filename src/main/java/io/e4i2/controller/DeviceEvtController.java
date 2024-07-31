package io.e4i2.controller;

import io.e4i2.dto.DeviceEvtDTO;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.service.DeviceEvtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/device")
public class DeviceEvtController {

    private final DeviceEvtService deviceEvtService;

    @PostMapping("/evt")
    public ResponseDTO evt(@Validated @RequestBody DeviceEvtDTO deviceEvtDTO) {
        return deviceEvtService.insertDevice(deviceEvtDTO);
    }
}
