package io.e4i2.controller;

import io.e4i2.dto.MbtiMemoDTO;

import io.e4i2.dto.ResponseDTO;
import io.e4i2.service.MbtiMemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/mbtiMemo")
public class MbtiMemoController {

    private final MbtiMemoService mbtiMemoService;

    @PostMapping("/mbtiMemo/create")
    public ResponseDTO mbtiMemoCreate(@Validated @RequestBody MbtiMemoDTO mbtiMemoDTO) {
        return mbtiMemoService.mbtiMemoInsert(mbtiMemoDTO);
    }

    @GetMapping("/mbtiMemo/{deviceId}/detail/{memoId}")
    public MbtiMemoDTO mbtiMemoDetail(@PathVariable("deviceId") String deviceId,
                                      @PathVariable("memoId") int memoId ){

        MbtiMemoDTO mbtiMemoDTO = new MbtiMemoDTO();
        mbtiMemoDTO.setDeviceId(deviceId);
        mbtiMemoDTO.setMemoId(memoId);

        MbtiMemoDTO memoDto = mbtiMemoService.selectmbtiMemo(mbtiMemoDTO);

        return memoDto;

    }

    // 메모 저장 목록 조회
    @GetMapping("/home/{deviceId}")
    public MbtiMemoDTO selectmbtiMemoList(@Validated @PathVariable("deviceId") String deviceId){
        MbtiMemoDTO memoList = mbtiMemoService.selectmbtiMemoList(deviceId);

        return memoList;
    }

    @PostMapping("/mbtiMemo/{deviceId}/delete/{memoId}")
    public ResponseDTO deletembtiMemo(@Validated @RequestBody @PathVariable("deviceId") String deviceId,
                                      @PathVariable("memoId") int memoId){

        MbtiMemoDTO mbtiMemoDTO = new MbtiMemoDTO();
        mbtiMemoDTO.setDeviceId(deviceId);
        mbtiMemoDTO.setMemoId(memoId);

        return mbtiMemoService.deletembtiMemo(mbtiMemoDTO);
    }

    @PostMapping("/mbtiMemo/{deviceId}/update/{memoId}")
    public ResponseDTO updateMbtiMemo(@Validated @RequestBody MbtiMemoDTO mbtiMemoDTO,
                                      @PathVariable("memoId") int memoId,
                                      @PathVariable("deviceId") String deviceId){

        mbtiMemoDTO.setDeviceId(deviceId);
        mbtiMemoDTO.setMemoId(memoId);

        return mbtiMemoService.updateMbtiMemo(mbtiMemoDTO);
    }


}
