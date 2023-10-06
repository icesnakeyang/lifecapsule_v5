package cc.cdtime.lifecapsule.middle.trigger;


import cc.cdtime.lifecapsule.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule.meta.trigger.entity.TriggerView;

import java.util.ArrayList;
import java.util.Map;

public interface ITriggerMiddle {
    /**
     * 创建一个触发器
     *
     * @param trigger
     */
    void createTrigger(NoteTrigger trigger) throws Exception;

    /**
     * 查询一个触发器
     *
     * @param qIn triggerId
     *            userId
     *            noteId
     *            toEmail
     * @return
     */
    TriggerView getTrigger(Map qIn, Boolean returnNull, String userId) throws Exception;

    /**
     * 查询触发器列表
     *
     * @param qIn userId
     *            status
     *            noteId
     *            triggerType
     *            size
     *            offset
     * @return
     */
    ArrayList<TriggerView> listTrigger(Map qIn) throws Exception;

    Integer totalTrigger(Map qIn) throws Exception;

    /**
     * 修改一个笔记触发条件
     *
     * @param qIn remark
     *            triggerTime
     *            triggerType
     *            status
     *            title
     *            toEmail
     *            toName
     *            fromName
     *            actTimes
     *            triggerId
     */
    void updateNoteTrigger(Map qIn) throws Exception;

    void updateNoteTrigger2(TriggerView triggerView) throws Exception;

    /**
     * 物理删除触发器
     *
     * @param triggerId
     */
    void deleteTrigger(String triggerId) throws Exception;
}
