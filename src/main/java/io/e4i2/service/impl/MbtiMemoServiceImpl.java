package io.e4i2.service.impl;

import io.e4i2.dto.*;
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
    public int mbtiMemoInsert(MbtiMemoData.Memo mbtiMemoData) {

        MbtiMemoData.Memo mbtiMemo = new MbtiMemoData.Memo();

        String deviceId = mbtiMemoData.getDeviceId();
        System.out.println("1. deviceId : " + deviceId);

        mbtiMemo = mbtiMemoDAO.duplicationCheck(deviceId);
        System.out.println("2. DevicePk : " + mbtiMemo.getDevicePk());

        mbtiMemoData.setDevicePk(mbtiMemo.getDevicePk());


        mbtiMemoDAO.mbtiMemoInsert(mbtiMemoData);

        return mbtiMemoData.getMemoId();
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
            List<MbtiMemoResponseDTO.Memo> mbtiMemos = mbtiMemoDAO.selectmbtiMemoList(deviceId);
//            List<MbtiMemoData.Memo> mbtiMemos = mbtiMemoDAO.selectmbtiMemoList(deviceId);
//
//            if (mbtiMemos == null || mbtiMemos.isEmpty()) {
//
//                MbtiMemoData.Memo defaultMemo = new MbtiMemoData.Memo();
//                defaultMemo.setDeviceId(deviceId);
//
//                MbtiMemoData.Memo.MbtiInterest defaultInterest = new MbtiMemoData.Memo.MbtiInterest();
//                defaultInterest.setMemoId(0);
//                defaultInterest.setInterestId(0);
//
//                List<MbtiMemoData.Memo.MbtiInterest> defaultInterests = new ArrayList<>();
//                defaultInterests.add(defaultInterest);
//                defaultMemo.setMbtiInterests(defaultInterests);
//
//                mbtiMemos = new ArrayList<>();
//                mbtiMemos.add(defaultMemo);
//            } else {
//                for (MbtiMemoData.Memo memo : mbtiMemos) {
//                    List<MbtiMemoData.Memo.MbtiInterest> interests = mbtiMemoDAO.selectMbtiInterests(memo.getMemoId());
//                    if (interests == null || interests.isEmpty()) {
//                        MbtiMemoData.Memo.MbtiInterest defaultInterest = new MbtiMemoData.Memo.MbtiInterest();
//                        interests = new ArrayList<>();
//                        interests.add(defaultInterest);
//                    }
//                    memo.setMbtiInterests(interests);
//                }
//            }
            
            MbtiMemoDTO.Result result = new MbtiMemoDTO.Result();
            result.setStatus(200);
            result.setMessage("SUCCESS");
            result.setCode("success");
            
            MbtiMemoDTO finalResponseDTO = new MbtiMemoDTO();
            //MbtiMemoData responseData = new MbtiMemoData();
            MbtiMemoResponseDTO responseData = new MbtiMemoResponseDTO();
            responseData.setMemos(mbtiMemos);
            
            List<MbtiMemoResponseDTO.Banner> banners = fetchBanners();
            responseData.setBanners(banners);



            finalResponseDTO.setData(responseData);
            finalResponseDTO.setResult(result);
            
            return finalResponseDTO;
            
        }catch (Exception e){
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
    
    private List<MbtiMemoResponseDTO.Banner> fetchBanners() {
        // Dummy implementation
        List<MbtiMemoResponseDTO.Banner> banners = new ArrayList<>();
        MbtiMemoResponseDTO.Banner banner = new MbtiMemoResponseDTO.Banner();
        banner.setImageUrl("배너 이미지 추가해야함");
        banners.add(banner);
        return banners;
    }

    @Override
    public ResponseDTO deletembtiMemo(MbtiMemoData.Memo mbtiMemoData) {
        MbtiMemoData.Memo mbtiMemo = new MbtiMemoData.Memo();

        String deviceId = mbtiMemoData.getDeviceId();
        System.out.println("deviceId : " + deviceId);

        mbtiMemo = mbtiMemoDAO.duplicationCheck(deviceId);

        mbtiMemoData.setDevicePk(mbtiMemo.getDevicePk());
        System.out.println("getDevicePk : " + mbtiMemo.getDevicePk());


        mbtiMemoDAO.deletembtiMemo(mbtiMemoData);
        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }

    @Override
    public ResponseDTO updateMbtiMemo(MbtiMemoDTO mbtiMemoDTO) {
        mbtiMemoDAO.updateMbtiMemo(mbtiMemoDTO);
        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }


}
