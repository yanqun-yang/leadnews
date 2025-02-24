package com.heima.behavior.controller.v1;

import com.heima.behavior.service.ApArticleBehaviorService;
import com.heima.model.behavior.dtos.ArticleBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/article")
public class ApArticleBehaviorController {

    @Autowired
    private ApArticleBehaviorService apArticleBehaviorService;

    @PostMapping("/load_article_behavior")
    public ResponseResult loadArticleBehavior(@RequestBody @Valid ArticleBehaviorDto dto){
        return apArticleBehaviorService.loadArticleBehavior(dto);
    }

}
