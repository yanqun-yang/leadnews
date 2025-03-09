package com.heima.article.controller.v1;

import com.heima.article.service.AppCollectionBehaviorService;
import com.heima.model.article.dtos.CollectionBehaviorDTO;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/collection_behavior")
public class CollectionBehaviorController {

    @Autowired
    private AppCollectionBehaviorService appCollectionBehaviorService;

    @PostMapping
    public ResponseResult collect(@RequestBody CollectionBehaviorDTO dto){
        return appCollectionBehaviorService.collect(dto);
    }
}
