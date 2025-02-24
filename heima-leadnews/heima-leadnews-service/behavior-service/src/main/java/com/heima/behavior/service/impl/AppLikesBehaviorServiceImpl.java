package com.heima.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.AppLikesBehaviorService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AppLikesBehaviorServiceImpl implements AppLikesBehaviorService {

    @Autowired
    private CacheService cacheService;

    /**
     * 点赞或取消点赞功能
     * @param dto
     * @return
     */
    @Override
    public ResponseResult like(LikesBehaviorDto dto) {

        // 1. 校验参数
        if(dto == null || dto.getArticleId() == null || !checkParam(dto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 2. 点赞需要登录
        ApUser user = AppThreadLocalUtil.getUser();
        // 游客，需要登录才能点赞
        if(user.getId() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Object object = cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        // 3. 如果是点赞操作  判断是否已经点过赞
        if(dto.getOperation() == 0){
            if(object != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已点赞");
            }
            // 保存当前key
            log.info("当前保存key：{}， {}， {}", dto.getArticleId(),user.getId(), dto);
            cacheService.hPut(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        }else {
            if(object == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "未点赞无法取消点赞");
            }
            // 删除当前key
            log.info("删除当前key：{}， {}", BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            cacheService.hDelete(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 检查参数
     * @param dto
     * @return true参数合法，false不参数合法
     */
    private boolean checkParam(LikesBehaviorDto dto){
        if(dto.getType() > 2 || dto.getType() < 0 || dto.getOperation() > 1 || dto.getOperation() < 0){
            return false;
        }
        return true;
    }
}
