package com.heima.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.AppUnlikesBehaviorService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.UnLikesBehaviorDTO;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AppUnlikesBehaviorServiceImpl implements AppUnlikesBehaviorService {

    @Autowired
    private CacheService cacheService;

    /**
     * 不喜欢功能
     * @param dto
     * @return
     */
    @Override
    public ResponseResult unlike(UnLikesBehaviorDTO dto) {

        // 1. 校验参数
        if(dto == null || dto.getArticleId() == null || !checkParam(dto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 2. 不喜欢需要登录
        ApUser user = AppThreadLocalUtil.getUser();
        // 游客，需要登录才能不喜欢
        if(user.getId() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Object object = cacheService.hGet(BehaviorConstants.UNLIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        // 3. 如果是不喜欢  判断是否已经不喜欢
        if(dto.getType() == 0){
            if(object != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已不喜欢");
            }
            // 保存当前key
            log.info("当前保存key：{}， {}， {}", dto.getArticleId(),user.getId(), dto);
            cacheService.hPut(BehaviorConstants.UNLIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        }else { //取消不喜欢  判断是否本来就没有不喜欢
            if(object == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "未不喜欢无法取消");
            }
            // 删除当前key
            log.info("删除当前key：{}， {}", BehaviorConstants.UNLIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            cacheService.hDelete(BehaviorConstants.UNLIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 检查参数
     * @param dto
     * @return true参数合法，false不参数合法
     */
    private boolean checkParam(UnLikesBehaviorDTO dto){
        /**
         * 不喜欢操作方式
         * 0 不喜欢
         * 1 取消不喜欢
         */
        if(dto.getType() == null || dto.getType() > 2 || dto.getType() < 0){
            return false;
        }
        return true;
    }
}
