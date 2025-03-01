package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDTO;

public interface ApUserRelationService {

    /**
     * 关注行为
     * @param dto
     * @return
     */
    ResponseResult follow(UserRelationDTO dto);
}
