package com.heima.model.behavior.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * APP点赞行为表
 * @TableName ap_likes_behavior
 */
@Data
@TableName(value ="ap_likes_behavior")
public class ApLikesBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 实体ID
     */
    @TableField(value = "entry_id")
    private Integer entryId;

    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 点赞内容类型
            0文章
            1动态
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 0 点赞
            1 取消点赞
     */
    @TableField(value = "operation")
    private Integer operation;

    /**
     * 登录时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

}