package com.heima.search.service.impl;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.pojos.ApAssociateWords;
import com.heima.search.service.ApAssociateWordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ApAssociateWordsServiceImpl implements ApAssociateWordsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 搜索联想词
     * @param dto
     * @return
     */
    @Override
    public ResponseResult search(UserSearchDto dto) {

        //1.检查参数
        if(StringUtils.isBlank(dto.getSearchWords())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.分页检查
        if(dto.getPageSize() > 20){
            dto.setPageSize(20);
        }

        //3.执行查询，模糊查询
        //其中 .*? 表示任意长度的任意字符（非贪婪模式），而 .* 表示任意长度的任意字符（贪婪模式）
        String regex = ".*?" + Pattern.quote(dto.getSearchWords()) + ".*"; //使用 Pattern.quote() 来避免正则表达式注入
        log.info("regex = {}", regex);
        Query query = Query.query(Criteria.where("associateWords").regex(regex));
        query.limit(dto.getPageSize());
        List<ApAssociateWords> apAssociateWords = mongoTemplate.find(query, ApAssociateWords.class);

        return ResponseResult.okResult(apAssociateWords);
    }
}
