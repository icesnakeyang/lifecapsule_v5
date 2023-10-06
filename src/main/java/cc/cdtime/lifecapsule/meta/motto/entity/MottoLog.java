package cc.cdtime.lifecapsule.meta.motto.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MottoLog {
    private Integer ids;
    private String mottoId;
    private String userId;
    private Date createTime;
    private String logType;
}
