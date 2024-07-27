package io.e4i2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DEVICE_EVT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deviceEvtId;

    @Column
    private String deviceEvtName;

    @Column
    private Integer deviceId;

    @Column
    private String eventName;

    @Column
    private String eventTime;

    @Column
    private String pageTitle;

}
