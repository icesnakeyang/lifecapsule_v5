package cc.cdtime.lifecapsule.meta.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户的登录状态
 */
@Data
public class UserLogin {
    private Integer ids;
    private String userId;
    private Date tokenTime;
    private String token;
}
