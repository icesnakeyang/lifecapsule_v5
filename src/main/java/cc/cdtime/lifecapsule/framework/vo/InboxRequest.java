package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

@Data
public class InboxRequest extends Request{
    private String sendLogId;
    private String encryptKey;
    private String keyToken;
    private String pid;
    private String title;
    private String content;
}
