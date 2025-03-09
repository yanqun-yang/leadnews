package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.article.service.AppCollectionBehaviorService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.dtos.CollectionBehaviorDTO;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AppCollectionBehaviorServiceImpl implements AppCollectionBehaviorService {

    @Autowired
    private CacheService cacheService;

    /**
     * 收藏或取消收藏功能
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collect(CollectionBehaviorDTO dto) {

        // 1. 校验参数
        if(dto == null || dto.getArticleId() == null || !checkParam(dto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 2. 收藏需要登录
        ApUser user = AppThreadLocalUtil.getUser();
        // 游客，需要登录才能收藏
        if(user.getId() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Object object = cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        // 3. 如果是收藏操作  判断是否已经收藏过
        if(dto.getOperation() == 0){
            if(object != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已收藏");
            }
            // 保存当前key
            log.info("当前保存key：{}， {}， {}", dto.getArticleId(),user.getId(), dto);
            cacheService.hPut(BehaviorConstants.COLLECTION_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        }else {
            if(object == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "未收藏无法取消收藏");
            }
            // 删除当前key
            log.info("删除当前key：{}， {}", BehaviorConstants.COLLECTION_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            cacheService.hDelete(BehaviorConstants.COLLECTION_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 检查参数
     * @param dto
     * @return true参数合法，false不参数合法
     */
    private boolean checkParam(CollectionBehaviorDTO dto){
        /**
         * type
         * 收藏内容类型
         * 0文章
         * 1动态
         */
        /**
         * operation
         * 操作类型
         * 0收藏
         * 1取消收藏
         */
        if(dto.getType() > 1 || dto.getType() < 0 || dto.getOperation() > 1 || dto.getOperation() < 0){
            return false;
        }
        return true;
    }
}
