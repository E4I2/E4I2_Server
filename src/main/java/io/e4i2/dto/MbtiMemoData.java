package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

@Getter
@Setter
public class MbtiMemoData {
    private List<Banner> banners;
    private List<Memo> memos;



    @Getter
    @Setter
    public static class Banner {
        private String imageUrl;
    }

    @Getter
    @Setter
    public static class Memo {
        private int memoId;
        private String name;
        private String age;
        private String sex;
        private String relation;
        //private String interest;
        private String memoSubmitDate;
        private String mbti;
        private String profileImageUrl;
        private int devicePk;
        private String deviceId;
        private List<String> Interest;
        @Getter
        @Setter
        public static class MbtiInterest {
            private int memoId;
            private String interest;
            private int interestId;
        }
    }
}