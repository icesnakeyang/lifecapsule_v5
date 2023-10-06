package cc.cdtime.lifecapsule.meta.userAct.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserActView {
    private Integer total;
    private String userId;
    private String actType;
    private Date createTime;
    private String email;
    private String nickname;
    private String result;
    private String memo;

}
