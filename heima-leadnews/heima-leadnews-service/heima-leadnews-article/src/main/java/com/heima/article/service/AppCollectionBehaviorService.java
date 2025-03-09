package com.heima.article.service;

import com.heima.model.article.dtos.CollectionBehaviorDTO;
import com.heima.model.common.dtos.ResponseResult;

public interface AppCollectionBehaviorService {

    /**
     * 收藏功能
     * @param dto
     * @return
     */
    ResponseResult collect(CollectionBehaviorDTO dto);
}
