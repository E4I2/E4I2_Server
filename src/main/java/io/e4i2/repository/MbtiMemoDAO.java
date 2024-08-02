package io.e4i2.repository;

import io.e4i2.dto.MbtiMemoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MbtiMemoDAO {

    void mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO);

    MbtiMemoDTO duplicationCheck(MbtiMemoDTO mbtiMemoDTO);

}
