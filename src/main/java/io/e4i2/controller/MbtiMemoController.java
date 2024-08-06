package io.e4i2.controller;

import io.e4i2.dto.*;

import io.e4i2.service.MbtiMemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/mbtiMemo")
public class MbtiMemoController {

    private final MbtiMemoService mbtiMemoService;

    @PostMapping("/mbtiMemo/create")
    public ResponseDTO mbtiMemoCreate(@Validated @RequestBody MbtiMemoData.Memo mbtiMemoData) {
        MbtiInterestDTO interestDTO;

        //mbti memo 에 insert 하고 memoId를 반환값으로 받아
        int memoId = mbtiMemoService.mbtiMemoInsert(mbtiMemoData);

        List<String> interests = mbtiMemoData.getInterest();
        System.out.println("1. interests : " + interests);

        for (String interest : interests) {
            Map<String, Object> params = new HashMap<>();
            params.put("memoId", memoId);
            params.put("interest", interest);
            System.out.println("3. memoId : " + memoId);
            System.out.println("4. interest : " + interest);

            mbtiMemoService.insertMemoInterest(params);
        }

        //for (MbtiMemoData.Memo.MbtiInterest e : mbtiMemoData.getInterest()) {
         //   Map<String, Object> params = new HashMap<>();
         //   params.put("memoId", mbtiMemoData.getMemoId());
         //   System.out.println("3. memoId : " + mbtiMemoData.getMemoId());

         //   params.put("interest", e.getInterest());
         //   System.out.println("4. interest : " + e.getInterest());
         //   mbtiMemoService.insertMemoInterest(params);
        //}

        return new ResponseDTO(new ResponseDTO.Result(200, "SUCCESS", "success"));
    }

//    @GetMapping("/mbtiMemo/{deviceId}/detail/{memoId}")
//    public MbtiMemoDTO mbtiMemoDetail(@PathVariable("deviceId") String deviceId,
//                                      @PathVariable("memoId") int memoId ){
//
//        MbtiMemoDTO mbtiMemoDTO = new MbtiMemoDTO();
//     //   mbtiMemoDTO.setDeviceId(deviceId);
//     //   mbtiMemoDTO.setMemoId(memoId);
//
//        MbtiMemoDTO memoDto = mbtiMemoService.selectmbtiMemo(mbtiMemoDTO);
//
//        return memoDto;
//
//    }

    // 메모 저장 목록 조회
    @GetMapping("/home/{deviceId}")
    public MbtiMemoDTO selectmbtiMemoList(@Validated @PathVariable("deviceId") String deviceId){
        MbtiMemoDTO memoList = mbtiMemoService.selectmbtiMemoList(deviceId);

        return memoList;
    }

    @PostMapping("/mbtiMemo/{deviceId}/delete/{memoId}")
    public ResponseDTO deletembtiMemo(@Validated @RequestBody @PathVariable("deviceId") String deviceId,
                                      @PathVariable("memoId") int memoId){

//        MbtiMemoDTO mbtiMemoDTO = new MbtiMemoDTO();
//        mbtiMemoDTO.setDeviceId(deviceId);
//        mbtiMemoDTO.setMemoId(memoId);

        MbtiMemoData.Memo mbtiMemoData = new MbtiMemoData.Memo();
        mbtiMemoData.setMemoId(memoId);
        mbtiMemoData.setDeviceId(deviceId);

        return mbtiMemoService.deletembtiMemo(mbtiMemoData);
    }

//    @PostMapping("/mbtiMemo/{deviceId}/update/{memoId}")
//    public ResponseDTO updateMbtiMemo(@Validated @RequestBody MbtiMemoDTO mbtiMemoDTO,
//                                      @PathVariable("memoId") int memoId,
//                                      @PathVariable("deviceId") String deviceId){
//
//        //mbtiMemoDTO.setDeviceId(deviceId);
//        //mbtiMemoDTO.setMemoId(memoId);
//
//        return mbtiMemoService.updateMbtiMemo(mbtiMemoDTO);
//    }


}
