package io.e4i2.service;

import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.MbtiMemoData;
import io.e4i2.dto.ResponseDTO;

import java.util.Map;

public interface MbtiMemoService {


    int mbtiMemoInsert(MbtiMemoData.Memo mbtiMemoData);

    void insertMemoInterest(Map<String, Object> paramMap);

    //void duplicationCheck(String deviceId);

    MbtiMemoDTO selectmbtiMemo(MbtiMemoDTO mbtiMemoDTO);

    MbtiMemoDTO selectmbtiMemoList(String deviceId);

    ResponseDTO deletembtiMemo(MbtiMemoData.Memo mbtiMemoData);

    ResponseDTO updateMbtiMemo(MbtiMemoDTO mbtiMemoDTO);

}
