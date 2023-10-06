package cc.cdtime.lifecapsule.meta.tag.entity;

import lombok.Data;

/**
 * 所有的标签都放这里
 */
@Data
public class TagBase {
    private Integer ids;
    private String tagId;
    private String tagName;
    private Integer tagHot;
}
