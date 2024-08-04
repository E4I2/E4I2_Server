package io.e4i2.service.impl;

import io.e4i2.dto.MbtiMemo;
import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.MbtiMemoData;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.repository.MbtiMemoDAO;
import io.e4i2.service.MbtiMemoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MbtiMemoServiceImpl implements MbtiMemoService {

    private final MbtiMemoDAO mbtiMemoDAO;

    // 메모 저장
    @Override
    public int mbtiMemoInsert(MbtiMemoDTO mbtiMemoDTO) {

        MbtiMemoDTO mbtiMemo = new MbtiMemoDTO();

        //String deviceId = mbtiMemoDTO.getDeviceId();
        //mbtiMemo = mbtiMemoDAO.duplicationCheck(deviceId);

        //mbtiMemoDTO.setDevicePk(mbtiMemo.getDevicePk());


        return mbtiMemoDAO.mbtiMemoInsert(mbtiMemoDTO);
    }

    @Override
    public void insertMemoInterest(Map<String, Object> paramMap) {
        mbtiMemoDAO.insertMemoInterest(paramMap);
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
            List<MbtiMemoData.Memo> mbtiMemos = mbtiMemoDAO.selectmbtiMemoList(deviceId);
            
            if (mbtiMemos == null || mbtiMemos.isEmpty()) {
                
                MbtiMemoData.Memo defaultMemo = new MbtiMemoData.Memo();
                defaultMemo.setDeviceId(deviceId);
                
                MbtiMemoData.Memo.MbtiInterest defaultInterest = new MbtiMemoData.Memo.MbtiInterest();
                defaultInterest.setMemoId(0);
                defaultInterest.setInterestId(0);
                
                List<MbtiMemoData.Memo.MbtiInterest> defaultInterests = new ArrayList<>();
                defaultInterests.add(defaultInterest);
                defaultMemo.setMbtiInterests(defaultInterests);
                
                mbtiMemos = new ArrayList<>();
                mbtiMemos.add(defaultMemo);
            } else {
                for (MbtiMemoData.Memo memo : mbtiMemos) {
                    List<MbtiMemoData.Memo.MbtiInterest> interests = mbtiMemoDAO.selectMbtiInterests(memo.getMemoId());
                    if (interests == null || interests.isEmpty()) {
                        MbtiMemoData.Memo.MbtiInterest defaultInterest = new MbtiMemoData.Memo.MbtiInterest();
                        interests = new ArrayList<>();
                        interests.add(defaultInterest);
                    }
                    memo.setMbtiInterests(interests);
                }
            }
            
            MbtiMemoDTO.Result result = new MbtiMemoDTO.Result();
            result.setStatus(200);
            result.setMessage("SUCCESS");
            result.setCode("success");
            
            MbtiMemoDTO finalResponseDTO = new MbtiMemoDTO();
            MbtiMemoData responseData = new MbtiMemoData();
            responseData.setMemos(mbtiMemos);
            
            List<MbtiMemoData.Banner> banners = fetchBanners();
            responseData.setBanners(banners);
            
            finalResponseDTO.setData(responseData);
            finalResponseDTO.setResult(result);
            
            return finalResponseDTO;
            
        }catch (Exception e){
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
    
    private List<MbtiMemoData.Banner> fetchBanners() {
        // Dummy implementation
        List<MbtiMemoData.Banner> banners = new ArrayList<>();
        MbtiMemoData.Banner banner = new MbtiMemoData.Banner();
        banner.setImageUrl("배너 이미지 추가해야함");
        banners.add(banner);
        return banners;
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
