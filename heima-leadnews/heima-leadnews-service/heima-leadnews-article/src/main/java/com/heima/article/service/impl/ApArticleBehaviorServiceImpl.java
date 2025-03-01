package com.heima.article.service.impl;

import com.heima.article.service.ApArticleBehaviorService;
import com.heima.article.service.ApAuthorService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.constants.UserRelationConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.behavior.dtos.ArticleBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
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
    @Autowired
    private ApAuthorService apAuthorService;

    /**
     * 加载文章详情 数据回显
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleBehaviorDto dto) {
        // 参数校验 articleId、 authorId
        if(dto.getArticleId() == null || dto.getAuthorId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 获取作者的userid
        ApAuthor apAuthor = apAuthorService.getByWmUserId(dto.getAuthorId());
        if(apAuthor == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "请检查authorId是否正确!");
        }
        // 初始化map
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
        // 获取点赞信息
        Object obj = cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        if(obj != null){
            behaviorMap.replace("islike", true);
        }
        // 获取关注信息
        Double score = cacheService.zScore(UserRelationConstants.FOLLOW_LIST + user.getId(), apAuthor.getUserId().toString());
        if(score != null){
            behaviorMap.replace("isfollow", true);
        }

        // 获取不喜欢信息
        obj = cacheService.hGet(BehaviorConstants.UNLIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        if(obj != null){
            behaviorMap.replace("isunlike", true);
        }

        // 获取收藏信息
        obj = cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        if(obj != null){
            behaviorMap.replace("iscollection", true);
        }

        return ResponseResult.okResult(behaviorMap);
    }
}
