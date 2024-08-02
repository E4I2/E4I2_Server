package io.e4i2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MbtiMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memoId;

    @Column
    private String memoName;

    @Column
    private String memoAge;

    @Column
    private String memoSex;

    @Column
    private String memoRelation;

    @Column
    private String memoInterest;

    @Column
    private String memoSubmitDate;

    @Column
    private String mbti;

    @Column
    private int devicePk;

    private String deviceId;

}
