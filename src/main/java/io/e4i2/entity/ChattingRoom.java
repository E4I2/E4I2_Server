package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "chatting_room")
@Getter
@NoArgsConstructor
public class ChattingRoom extends BasicDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chattingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_PK")
    private Device device;
    
    @OneToMany(mappedBy = "chattingRoom")
    @ToString.Exclude
    private List<ChattingMessage> chattingMessages;
    
    
    public ChattingRoom(Device device) {
        this.device = device;
    }
    
    /**
     * 연관관계 편의 메서드
     *
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
