package io.e4i2.repository;

import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.MbtiMemoData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MbtiMemoDAO {

    //int mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO);
    int mbtiMemoInsert(MbtiMemoData.Memo mbtiMemoData);

    void insertMemoInterest(Map<String, Object> paramMap);

    MbtiMemoData.Memo duplicationCheck(String deviceId);

    // 메모 상세 조회
    MbtiMemoDTO selectmbtiMemo(MbtiMemoDTO mbtiMemoDTO);

    // 디바이스별 메모 목록 조회
    List<MbtiMemoData.Memo> selectmbtiMemoList(String devicePk);

    // 메모 삭제
    void deletembtiMemo(MbtiMemoDTO mbtiMemoDTO);

    // 메모 수정
    void updateMbtiMemo(MbtiMemoDTO mbtiMemoDTO);
    
    List<MbtiMemoData.Memo.MbtiInterest> selectMbtiInterests(int memoId);
    //
}
