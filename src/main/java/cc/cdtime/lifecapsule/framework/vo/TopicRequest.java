package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

@Data
public class TopicRequest extends Request {
    private String noteId;
    private String title;
    private String content;
    private String topicId;
    private String pid;
    private String comment;
    private String authorName;
    private Boolean includeChildren;
    private String status;
}
