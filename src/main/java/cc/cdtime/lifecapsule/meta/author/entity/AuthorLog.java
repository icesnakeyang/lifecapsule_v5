package cc.cdtime.lifecapsule.meta.author.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息的日志
 */
@Data
public class AuthorLog {
    private Integer ids;
    private String userId;
    private String authorName;
    private Date createTime;
    private String status;
    private String authorLogId;
}
