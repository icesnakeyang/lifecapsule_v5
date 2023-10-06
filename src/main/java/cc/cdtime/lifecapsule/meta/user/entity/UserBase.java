package cc.cdtime.lifecapsule.meta.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户基础类
 */
@Data
public class UserBase {
    private Integer ids;
    private String userId;
    private Date createTime;
    private String nickname;
    private String language;
}
