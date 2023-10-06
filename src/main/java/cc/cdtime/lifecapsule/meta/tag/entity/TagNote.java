package cc.cdtime.lifecapsule.meta.tag.entity;

import lombok.Data;

import java.util.Date;

/**
 * 笔记的标签
 */
@Data
public class TagNote {
    private Integer ids;
    private String tagId;
    private String noteId;
    private Date createTime;
    private String userId;
}
