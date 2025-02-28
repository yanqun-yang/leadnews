package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface AppReadBehaviorService {

    /**
     * 阅读功能
     * @param dto
     * @return
     */
    ResponseResult read(ReadBehaviorDto dto);
}
