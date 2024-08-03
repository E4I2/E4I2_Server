package io.e4i2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MbtiMemoDTO {

    private Result result;
    private List<MbtiMemoDTO> data;

    private int memoId;

    @NotBlank(message = "이름값이 올바르지 않습니다.")
    private String memoName;

    @NotBlank(message = "나이값이 올바르지 않습니다.")
    private String memoAge;

    @NotBlank(message = "성별값이 올바르지 않습니다.")
    private String memoSex;

    @NotBlank(message = "관계값이 올바르지 않습니다.")
    private String memoRelation;

    @NotBlank(message = "관심사값이 올바르지 않습니다.")
    private String memoInterest;

    private String memoSubmitDate;

    @NotBlank(message = "mbti값이 올바르지 않습니다.")
    private String mbti;

    @NotNull(message = "디바이스키값이 올바르지 않습니다.")
    private int devicePk;

    private String deviceId;


    @Getter
    @Setter
    public static class Result {
        private int status;
        private String message;
        private String code;

        public Result() {
        }

        public Result(int status, String message, String code) {
            this.status = status;
            this.message = message;
            this.code = code;
        }

    }


}
