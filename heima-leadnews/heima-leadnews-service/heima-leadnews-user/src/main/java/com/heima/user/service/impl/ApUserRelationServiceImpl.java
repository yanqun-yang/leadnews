package com.heima.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.article.IArticleClient;
import com.heima.common.constants.UserRelationConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.UserRelationDTO;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.service.ApUserRelationService;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ApUserRelationServiceImpl implements ApUserRelationService {

    @Resource
    private IArticleClient articleClient;
    @Autowired
    private CacheService cacheService;

    /**
     * 关注行为
     * @param dto
     * @return
     */
    @Override
    public ResponseResult follow(UserRelationDTO dto) {
        //1.校验参数  authorId、   必须登录、    operation 0 1
        Short operation = dto.getOperation();
        if(dto.getAuthorId() == null || (operation != 0 && operation != 1)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ResponseResult result = articleClient.getByWmUserId(dto.getAuthorId());
        if(result == null | !result.getCode().equals(AppHttpCodeEnum.SUCCESS.getCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }
        ApAuthor apAuthor = JSON.parseObject(result.getData().toString(), ApAuthor.class);
        if(apAuthor.getUserId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "作者对应的userId不存在");
        }
        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Integer loginId = user.getId();
        Integer followId = apAuthor.getUserId();
        // 判断 自己不可以关注自己
        if(loginId.equals(followId)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "不可以自己关注自己~");
        }
        // 校验之前有没有关注过 zscore
        // 参数1: key   参数2: 要查询集合元素
        Double score = cacheService.zScore(UserRelationConstants.FOLLOW_LIST + loginId, String.valueOf(followId));

        if(operation == 0){
            if(score != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "您已关注，请勿重复关注");
            }
            // 没有关注过  zadd follow：loginId  followId
            // 参数1: key   参数2: 集合元素  参数3: score
            cacheService.zAdd(UserRelationConstants.FOLLOW_LIST + loginId, String.valueOf(followId), System.currentTimeMillis());
            //           zadd fans: followId  loginId
            cacheService.zAdd(UserRelationConstants.FANS_LIST + followId, String.valueOf(loginId), System.currentTimeMillis());
        }else {
            // 是1 取关
            if(score == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "您暂未关注作者，无法取关");
            }
            // zrem follow: loginId  followId
            cacheService.zRemove(UserRelationConstants.FOLLOW_LIST + loginId, String.valueOf(followId));
            // zrem fans:  followId  loginId
            cacheService.zRemove(UserRelationConstants.FANS_LIST + followId, String.valueOf(loginId));
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
