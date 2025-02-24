package com.heima.model.behavior.dtos;
 
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class LikesBehaviorDto {

    // 设备ID
    Integer equipmentId;


    // 文章、动态、评论等ID
    @NotNull(message = "文章id不能为空")
    Long articleId;

    /**
     * 喜欢内容类型
     * 0文章
     * 1动态
     * 2评论
     */
    Short type;

    /**
     * 喜欢操作方式
     * 0 点赞
     * 1 取消点赞
     */
    @Range(min = 0, max = 1, message = "点赞方式只能是0或1")
    Short operation;
}