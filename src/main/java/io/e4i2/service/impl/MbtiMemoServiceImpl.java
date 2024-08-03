package io.e4i2.service.impl;

import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.repository.MbtiMemoDAO;
import io.e4i2.service.MbtiMemoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MbtiMemoServiceImpl implements MbtiMemoService {

    private final MbtiMemoDAO mbtiMemoDAO;

    // 메모 저장
    @Override
    public ResponseDTO mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO) {

        mbtiMemoDAO.mbtiMemoInsert(mbtiMemoDTO);
        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }

    // 메모 상세조회
    @Override
    public MbtiMemoDTO selectmbtiMemo(MbtiMemoDTO mbtiMemoDTO) {
        return mbtiMemoDAO.selectmbtiMemo(mbtiMemoDTO);
    }


    // 메모 목록 조회
    @Override
    public MbtiMemoDTO selectmbtiMemoList(String deviceId) {

        try{

            List<MbtiMemoDTO> list = mbtiMemoDAO.selectmbtiMemoList(deviceId);

            MbtiMemoDTO.Result result = new MbtiMemoDTO.Result();
            result.setStatus(200);
            result.setMessage("SUCCESS");
            result.setCode("success");

            MbtiMemoDTO finalResponseDTO = new MbtiMemoDTO();
            finalResponseDTO.setData(list);
            finalResponseDTO.setResult(result);

            return finalResponseDTO;

        }catch (Exception e){
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseDTO deletembtiMemo(MbtiMemoDTO mbtiMemoDTO) {
        mbtiMemoDAO.deletembtiMemo(mbtiMemoDTO);
        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }

    @Override
    public ResponseDTO updateMbtiMemo(MbtiMemoDTO mbtiMemoDTO) {
        mbtiMemoDAO.updateMbtiMemo(mbtiMemoDTO);
        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }


}
