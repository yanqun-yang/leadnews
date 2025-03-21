package com.heima.behavior.controller.v1;

import com.heima.behavior.service.AppUnlikesBehaviorService;
import com.heima.model.behavior.dtos.UnLikesBehaviorDTO;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/un_likes_behavior")
public class ApUnlikesBehaviorController {

    @Autowired
    private AppUnlikesBehaviorService appUnlikesBehaviorService;

    @PostMapping
    public ResponseResult like(@RequestBody UnLikesBehaviorDTO dto){
        return appUnlikesBehaviorService.unlike(dto);
    }
}
