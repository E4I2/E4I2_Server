package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MbtiMemo {
    private int memoId;
    private String memoName;
    private String memoAge;
    private String memoSex;
    private String memoRelation;
    private String interest;
    private String memoSubmitDate;
    private String mbti;
    private int devicePk;
    private String deviceId;
    private MbtiInterest mbtiInterests;
    
    @Getter
    @Setter
    public static class MbtiInterest {
        private int memoId;
        private String interest;
        private int interestId;
    }
}
