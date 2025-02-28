package com.heima.behavior.controller.v1;

import com.heima.behavior.service.AppReadBehaviorService;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@RequestMapping("/api/v1/read_behavior")
@RequestMapping("/api/v1/read_behavior1")//故意修改请求路径，避免前端弹窗
public class ApReadBehaviorController {

    @Autowired
    private AppReadBehaviorService appReadBehaviorService;

    @PostMapping
    public ResponseResult read(@RequestBody @Valid ReadBehaviorDto dto){
        return appReadBehaviorService.read(dto);
    }
}
