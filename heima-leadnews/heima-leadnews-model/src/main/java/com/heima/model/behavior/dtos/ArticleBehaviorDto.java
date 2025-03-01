package com.heima.model.behavior.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleBehaviorDto {

    // 设备ID
    Integer equipmentId;

    // 文章ID
    @NotNull(message = "文章id不能为空")
    Long articleId;

    // 作者ID
    @NotNull(message = "作者id不能为空")
    Integer authorId;

    // 作者对应的apuserid
    Integer authorApUserId;
}
