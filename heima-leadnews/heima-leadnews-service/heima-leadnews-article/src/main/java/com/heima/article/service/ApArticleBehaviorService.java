package com.heima.article.service;

import com.heima.model.behavior.dtos.ArticleBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ApArticleBehaviorService {

    /**
     * 加载文章详情 数据回显
     * @param dto
     * @return
     */
    public ResponseResult loadArticleBehavior(ArticleBehaviorDto dto);
}