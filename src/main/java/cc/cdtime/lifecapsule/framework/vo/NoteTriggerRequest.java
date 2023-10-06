package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NoteTriggerRequest extends Request {
    private String title;
    private String noteContent;
    private String userEncodeKey;
    private String toEmail;
    private Date sendTime;
    private String triggerId;
}
