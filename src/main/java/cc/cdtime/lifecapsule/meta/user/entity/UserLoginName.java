package cc.cdtime.lifecapsule.meta.user.entity;

import lombok.Data;

/**
 * 用户登录名和密码
 */
@Data
public class UserLoginName {
    private Integer ids;
    private String userId;
    private String loginName;
    private String password;
}
