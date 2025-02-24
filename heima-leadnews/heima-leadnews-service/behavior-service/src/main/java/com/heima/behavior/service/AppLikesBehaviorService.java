package com.heima.behavior.service;

import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface AppLikesBehaviorService {

    /**
     * 点赞功能
     * @param dto
     * @return
     */
    ResponseResult like(LikesBehaviorDto dto);
}
