package com.heima.behavior.controller.v1;

import com.heima.behavior.service.AppLikesBehaviorService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.utils.thread.AppThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/likes_behavior")
public class ApLikesBehaviorController {

    @Autowired
    private AppLikesBehaviorService appLikesBehaviorService;

    @PostMapping
    public ResponseResult like(@RequestBody @Valid LikesBehaviorDto dto){
        return appLikesBehaviorService.like(dto);
    }
}
