package com.heima.behavior.service;

import com.heima.model.behavior.dtos.UnLikesBehaviorDTO;
import com.heima.model.common.dtos.ResponseResult;

public interface AppUnlikesBehaviorService {

    /**
     * 不喜欢功能
     * @param dto
     * @return
     */
    ResponseResult unlike(UnLikesBehaviorDTO dto);
}
