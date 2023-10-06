package cc.cdtime.lifecapsule.meta.timer.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class TimerView {
    private Integer ids;
    private String timerId;
    private String userId;
    private Timestamp timerTime;
    private String status;
    private String remark;
    private String tag;
    /**
     * 用户每次点snooze，snooze++
     */
    private Integer snooze;
    /**
     * 旧的计时器启动时间
     */
    private Date oldTimer;
    /**
     * snooze时间
     */
    private Date snoozeTime;
    /**
     * 新的计时器启动时间
     */
    private Date newTimer;
    private String timerStatus;
    private String phone;
    private String email;
    private String userName;
}
