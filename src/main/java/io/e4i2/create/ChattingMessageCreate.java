package io.e4i2.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChattingMessageCreate {
    
    @NotNull(message = "채팅 ID가 null 입니다.")
    private Integer chattingId;
    @NotBlank(message = "메세지 내용을 입력해주세요")
    private String messageContent;
    @Pattern(regexp = "AI|ME", message = "AI 또는 ME로 입력해주세요")
    private String sender;
}
