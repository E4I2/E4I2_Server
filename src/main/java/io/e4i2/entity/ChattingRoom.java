package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CHATTING_ROOM")
@Getter
public class ChattingRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer chattingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_PK")
    private Device device;
    
    @OneToMany(mappedBy = "chattingRoom")
    private List<ChattingMessage> chattingMessages;
    
    private LocalDateTime createdAt;
    
    
    
    /**
     * 연관관계 편의 메서드
     * @param device
     */
    public void setDevice(Device device) {
        if (this.device != null) {
            this.device.getChattingRooms().remove(this);
        }
        this.device = device;
        if (device != null) {
            device.getChattingRooms().add(this);
        }
    }
    
}
