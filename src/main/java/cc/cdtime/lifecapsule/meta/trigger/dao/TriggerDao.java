package cc.cdtime.lifecapsule.meta.trigger.dao;

import cc.cdtime.lifecapsule.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule.meta.trigger.entity.TriggerView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TriggerDao {
    /**
     * 创建一个触发器
     *
     * @param trigger
     */
    void createTrigger(NoteTrigger trigger);

    /**
     * 查询一个触发器
     *
     * @param qIn triggerId
     *            userId
     *            noteId
     *            toEmail
     * @return
     */
    TriggerView getTrigger(Map qIn);

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
    ArrayList<TriggerView> listTrigger(Map qIn);

    Integer totalTrigger(Map qIn);

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
    void updateNoteTrigger(Map qIn);

    void updateNoteTrigger2(TriggerView triggerView);

    /**
     * 物理删除触发器
     *
     * @param triggerId
     */
    void deleteTrigger(String triggerId);
}
