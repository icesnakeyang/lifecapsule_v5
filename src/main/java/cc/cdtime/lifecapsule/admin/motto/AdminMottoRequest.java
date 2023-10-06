package cc.cdtime.lifecapsule.admin.motto;

import cc.cdtime.lifecapsule.framework.vo.Request;
import lombok.Data;

@Data
public class AdminMottoRequest extends Request {
    private String mottoId;
    private String status;
}
