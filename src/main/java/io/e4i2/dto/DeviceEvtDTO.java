package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceEvtDTO {

    private Integer deviceEvtId;
    private Integer deviceId;
    private String deviceName;
    private String eventName;
    private String eventTime;
    private String pageTitle;


}
