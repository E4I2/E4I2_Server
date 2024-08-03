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
@RequestMapping("/mbtiMemo")
public class MbtiMemoController {

    private final MbtiMemoService mbtiMemoService;

    @PostMapping("/create")
    public ResponseDTO mbtiMemoCreate(@Validated @RequestBody MbtiMemoDTO mbtiMemoDTO) {
        return mbtiMemoService.mbtiMemoInsert(mbtiMemoDTO);
    }

    @GetMapping("/{deviceId}/detail/{memoId}")
    public MbtiMemoDTO mbtiMemoDetail(@PathVariable("deviceId") String deviceId,
                                      @PathVariable("memoId") int memoId ){

        MbtiMemoDTO mbtiMemoDTO = new MbtiMemoDTO();
        mbtiMemoDTO.setDeviceId(deviceId);
        mbtiMemoDTO.setMemoId(memoId);

        MbtiMemoDTO memoDto = mbtiMemoService.selectmbtiMemo(mbtiMemoDTO);

        return memoDto;

    }

    @GetMapping("/{deviceId}")
    public List<MbtiMemoDTO> selectmbtiMemoList(@PathVariable("deviceId") String deviceId){
        List<MbtiMemoDTO> memoList = mbtiMemoService.selectmbtiMemoList(deviceId);

        return memoList;
    }

    @PostMapping("/{deviceId}/delete/{memoId}")
    public ResponseDTO deletembtiMemo(@Validated @RequestBody @PathVariable("deviceId") String deviceId,
                                      @PathVariable("memoId") int memoId){

        MbtiMemoDTO mbtiMemoDTO = new MbtiMemoDTO();
        mbtiMemoDTO.setDeviceId(deviceId);
        mbtiMemoDTO.setMemoId(memoId);

        return mbtiMemoService.deletembtiMemo(mbtiMemoDTO);
    }

    @PostMapping("/{deviceId}/update/{memoId}")
    public ResponseDTO updateMbtiMemo(@Validated @RequestBody MbtiMemoDTO mbtiMemoDTO){
        return mbtiMemoService.updateMbtiMemo(mbtiMemoDTO);
    }


}
