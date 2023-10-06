package cc.cdtime.lifecapsule.web.publicWeb;

import cc.cdtime.lifecapsule.framework.vo.Request;
import lombok.Data;

@Data
public class PublicWebRequest extends Request {
    private String title;
    private String content;
    private String noteId;
    private String authorName;
}
