package io.e4i2.service.impl;

import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.repository.MbtiMemoDAO;
import io.e4i2.service.MbtiMemoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MbtiMemoServiceImpl implements MbtiMemoService {

    private final MbtiMemoDAO mbtiMemoDAO;

    @Override
    public ResponseDTO mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO) {

        mbtiMemoDAO.mbtiMemoInsert(mbtiMemoDTO);
        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));

//        MbtiMemoDTO mbtiMemoValue = mbtiMemoDAO.duplicationCheck(mbtiMemoDTO);
//
//        if(mbtiMemoValue == null) {
//            MbtiMemoDTO dto = mbtiMemoDAO.duplicationCheck(mbtiMemoDTO);
//            mbtiMemoDTO.setDevicePk(dto.getDevicePk());
//            mbtiMemoDAO.mbtiMemoInsert(mbtiMemoDTO);
//            return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
//        }else {
//            mbtiMemoDTO.setDevicePk(mbtiMemoValue.getDevicePk());
//            mbtiMemoDAO.mbtiMemoInsert(mbtiMemoDTO);
//            return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
//        }

//        mbtiMemoDAO.mbtiMemoInsert(mbtiMemoDTO);
//        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }
}
