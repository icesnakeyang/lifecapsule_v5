package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AntiDelayRequest extends Request{
    private String encryptKey;
    private String keyToken;
    private ArrayList tasks;
    private String happyYesterday;
    private String longGoal;
    private String todayGoal;
    private String title;
    private String myThought;
    private String noteId;
}
