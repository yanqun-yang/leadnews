package com.heima.behavior.service.impl;

import com.heima.behavior.service.AppReadBehaviorService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppReadBehaviorServiceImpl implements AppReadBehaviorService {

    @Autowired
    private CacheService cacheService;

    /**
     * 阅读功能
     * @param dto
     * @return
     */
    @Override
    public ResponseResult read(ReadBehaviorDto dto) {

        //1.校验参数
        if(dto == null || dto.getArticleId() == null || dto.getCount() <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取登录用户
        ApUser user = AppThreadLocalUtil.getUser();

        //2.如果是游客，不用记录阅读次数
        if(user == null || user.getId() == 0){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        //3.判断阅读行为是否存在
        Object object = cacheService.hGet(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        //存在,更新count
        if(object != null){
            log.info("Redis查询到的数据: {}", object);
            int count = Integer.valueOf(String.valueOf(object))+dto.getCount();
            cacheService.hPut(BehaviorConstants.READ_BEHAVIOR+dto.getArticleId().toString(),user.getId().toString(), String.valueOf(count));
            // 更新当前key
            log.info("更新当前key: ArticleId = {}, userId = {}, 阅读次数 = {}", dto.getArticleId(),user.getId(), count);
        }
        //不存在,创建阅读行为
        else{
            cacheService.hPut(BehaviorConstants.READ_BEHAVIOR+dto.getArticleId().toString(),user.getId().toString(), String.valueOf(dto.getCount()));
            // 插入当前key
            log.info("插入当前key: ArticleId = {}, userId = {}, 阅读次数 = {}", dto.getArticleId(),user.getId(), dto.getCount());
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
