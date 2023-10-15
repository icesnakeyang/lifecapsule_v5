package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

@Data
public class HistoryRequest extends Request {
    private String content;
    private String pid;
    private String title;
    private String encryptKey;
    private String keyToken;
    private String searchKey;
    private String noteId;
}
