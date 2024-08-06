package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MbtiMemoResponseDTO {

    private List<Banner> banners;
    private List<Memo> memos;

    @Getter
    @Setter
    public static class Banner {
        private String imageUrl;
        private Integer contentId;
    }

    @Getter
    @Setter
    public static class Memo {
        private int memoId;
        private String memoName;
        private String memoAge;
        private String memoSex;
        private String memoRelation;
        private String interest;
        private String memoSubmitDate;
        private String mbti;
        private String profileImageUrl;
        private int devicePk;
        private String deviceId;
    }
}
