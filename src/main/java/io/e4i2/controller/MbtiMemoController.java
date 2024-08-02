package io.e4i2.controller;

import io.e4i2.dto.MbtiMemoDTO;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.entity.MbtiMemo;
import io.e4i2.service.MbtiMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mbtiMemo")
public class MbtiMemoController {

    private final MbtiMemoService mbtiMemoService;

    @PostMapping("/create")
    public ResponseDTO mbtiMemoCreate(@Validated @RequestBody MbtiMemoDTO mbtiMemoDTO) {
        return mbtiMemoService.mbtiMemoInsert(mbtiMemoDTO);
    }

}
