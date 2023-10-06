package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

@Data
public class TagRequest extends Request {
    private String tagName;
    private String tagId;
    private String noteId;
}
