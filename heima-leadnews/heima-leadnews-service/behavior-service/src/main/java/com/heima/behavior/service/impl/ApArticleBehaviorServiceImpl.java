package com.heima.behavior.service.impl;

import com.heima.behavior.service.ApArticleBehaviorService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.ArticleBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ApArticleBehaviorServiceImpl implements ApArticleBehaviorService {

    @Autowired
    private CacheService cacheService;

    /**
     * 加载文章详情 数据回显
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleBehaviorDto dto) {
        Map<String, Boolean> behaviorMap= new HashMap<>();
        behaviorMap.put("isfollow", false);
        behaviorMap.put("islike", false);
        behaviorMap.put("isunlike", false);
        behaviorMap.put("iscollection", false);
        ApUser user = AppThreadLocalUtil.getUser();
        // 是游客，直接返回所有false
        if(user.getId() == 0){
            return ResponseResult.okResult(behaviorMap);
        }
        Object obj = cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        if(obj != null){
            behaviorMap.replace("islike", true);
        }
        return ResponseResult.okResult(behaviorMap);
    }
}
