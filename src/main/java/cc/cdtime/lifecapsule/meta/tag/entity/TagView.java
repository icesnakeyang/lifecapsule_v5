package cc.cdtime.lifecapsule.meta.tag.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TagView {
    private Integer ids;
    private String tagId;
    private String noteId;
    private Date createTime;
    private String tagName;
    private Integer tagHot;
    private String userId;
}
