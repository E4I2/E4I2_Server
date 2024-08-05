package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatting_message")
@Getter
public class ChattingMessage {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MESSAGE_ID")
    private Integer messageId;
    @Column(name = "CHATTING_ID")
    private Integer chattingId;
    @Column(name = "MESSAGE_CONTENT")
    private String messageContent;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingMessages")
    private ChattingRoom chattingRoom;
    
    public void setChattingRoom(ChattingRoom chattingRoom) {
        if(this.chattingRoom != null) {
            this.chattingRoom.getChattingMessages().remove(this);
        }
        this.chattingRoom = chattingRoom;
        chattingRoom.getChattingMessages().add(this);
    }
}
