package io.e4i2.repository;

import io.e4i2.dto.MbtiMemoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MbtiMemoDAO {

    void mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO);

    MbtiMemoDTO duplicationCheck(MbtiMemoDTO mbtiMemoDTO);

    // 메모 상세 조회
    MbtiMemoDTO selectmbtiMemo(MbtiMemoDTO mbtiMemoDTO);

    // 디바이스별 메모 목록 조회
    List<MbtiMemoDTO> selectmbtiMemoList(String deviceId);

    // 메모 삭제
    void deletembtiMemo(MbtiMemoDTO mbtiMemoDTO);

    // 메모 수정
    void updateMbtiMemo(MbtiMemoDTO mbtiMemoDTO);
 //
}
