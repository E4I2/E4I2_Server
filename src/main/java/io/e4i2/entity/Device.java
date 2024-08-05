package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "device")
public class Device {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DEVICE_PK")
    private Integer devicePk;
    
    @Column(name = "DEVICE_ID")
    private String deviceId;
    
    @OneToMany(mappedBy = "device",fetch = FetchType.LAZY)
    private List<ChattingRoom> chattingRooms = new ArrayList<>();
    
    /**
     * 연관관계 편의 메서드
     * @param chattingRoom
     */
    public void addChattingRoom(ChattingRoom chattingRoom) {
        chattingRooms.add(chattingRoom);
        chattingRoom.setDevice(this);
    }
    
    /**
     * 연관관계 편의메서드
     * @param chattingRoom
     */
    public void removeChattingRoom(ChattingRoom chattingRoom) {
        chattingRooms.remove(chattingRoom);
        chattingRoom.setDevice(null);
    }
    
    
}
