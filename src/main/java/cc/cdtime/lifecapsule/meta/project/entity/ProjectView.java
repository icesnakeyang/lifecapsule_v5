package cc.cdtime.lifecapsule.meta.project.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectView {
    private Integer ids;
    private String projectId;
    private String projectName;
    private String userId;
    private String remark;
    private Date createTime;
}
