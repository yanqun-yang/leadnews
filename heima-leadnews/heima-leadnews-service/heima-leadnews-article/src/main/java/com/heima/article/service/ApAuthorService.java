package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.pojos.ApAuthor;

public interface ApAuthorService extends IService<ApAuthor> {

    /**
     * 根据wm_user_id获取ap_author
     * @param wmUserId
     * @return
     */
    ApAuthor getByWmUserId(Integer wmUserId);
}
