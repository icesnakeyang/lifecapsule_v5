package cc.cdtime.lifecapsule.meta.author.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorLogView {
    private Integer ids;
    private String userId;
    private String authorName;
    private Date createTime;
    private String status;
    private String authorLogId;
}
