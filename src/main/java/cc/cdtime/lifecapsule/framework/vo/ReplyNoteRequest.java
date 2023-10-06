package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

@Data
public class ReplyNoteRequest extends Request{
    private String pid;
    private String content;
    private String title;
    private String type;
}
