package cc.cdtime.lifecapsule.app.inspiration;

import cc.cdtime.lifecapsule.framework.vo.Request;
import lombok.Data;

@Data
public class InspirationRequest extends Request {
    private String noteId;
    private String content;
    private String encryptKey;
    private String keyToken;
    private String title;
}
