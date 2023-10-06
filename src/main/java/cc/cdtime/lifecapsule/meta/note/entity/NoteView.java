package cc.cdtime.lifecapsule.meta.note.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class NoteView {
    private Integer ids;
    private String noteId;
    private String userId;
    private String title;
    private Date createTime;
    private String status;
    private Integer encrypt;
    private String userEncodeKey;
    private String content;
    private String categoryName;
    private String noteType;
    private String nickname;
    private String pid;
    private String email;
    private ArrayList tagList;
    /**
     * 父笔记标题
     */
    private String pTitle;
}
