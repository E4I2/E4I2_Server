package io.e4i2.service;

import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.ResponseDTO;

public interface MbtiMemoService {

    ResponseDTO mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO);

    //void duplicationCheck(String deviceId);

    MbtiMemoDTO selectmbtiMemo(MbtiMemoDTO mbtiMemoDTO);

    MbtiMemoDTO selectmbtiMemoList(String deviceId);

    ResponseDTO deletembtiMemo(MbtiMemoDTO mbtiMemoDTO);

    ResponseDTO updateMbtiMemo(MbtiMemoDTO mbtiMemoDTO);

}
