package cc.cdtime.lifecapsule.meta.email.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEmailView {
    private Integer ids;
    private String userId;
    private String email;
    private String emailId;
    private Date createTime;
    private String status;
    private String emailCode;
}
