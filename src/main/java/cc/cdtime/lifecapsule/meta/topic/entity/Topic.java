package cc.cdtime.lifecapsule.meta.topic.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Topic {
    private Integer ids;
    private String topicId;
    private Date createTime;
    private String userId;
    private String title;
    private Integer views;
    private Integer comments;
    private String status;
    private String pid;
    private String content;
    private String noteId;
    private String authorName;
}
