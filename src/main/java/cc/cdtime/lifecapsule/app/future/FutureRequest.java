package cc.cdtime.lifecapsule.app.future;

import cc.cdtime.lifecapsule.framework.vo.Request;
import lombok.Data;

import java.util.Date;

@Data
public class FutureRequest extends Request {
    private String noteId;
    private String keyToken;
    private String encryptKey;
    private String title;
    private String content;
    private String toEmail;
    private String toName;
    private String fromName;
    private Date sendTime;
}
