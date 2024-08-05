package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatting_message")
@Getter
@NoArgsConstructor
public class ChattingMessage extends BasicDate{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Integer messageId;
    
    @Column(name = "MESSAGE_CONTENT")
    private String messageContent;
    
    @Column(name = "SENDER")
    private String sender;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATTING_ID")
    private ChattingRoom chattingRoom;
    
    public ChattingMessage( String messageContent, String sender) {
        this.messageContent = messageContent;
        this.sender = sender;
    }
    
    public void setChattingRoom(ChattingRoom chattingRoom) {
        if(this.chattingRoom != null) {
            this.chattingRoom.getChattingMessages().remove(this);
        }
        this.chattingRoom = chattingRoom;
        chattingRoom.getChattingMessages().add(this);
    }
}
