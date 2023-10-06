package cc.cdtime.lifecapsule.admin.cash;

import cc.cdtime.lifecapsule.framework.vo.Request;
import lombok.Data;

@Data
public class AdminCashRequest extends Request {
    private String userId;
}
