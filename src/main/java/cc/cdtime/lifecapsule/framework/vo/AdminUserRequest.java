package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AdminUserRequest extends Request {
    private String loginName;
    private String password;
    private String roleType;
    private Date startTime;
    private Date endTime;
    private String userId;
    private String emailKey;
}
