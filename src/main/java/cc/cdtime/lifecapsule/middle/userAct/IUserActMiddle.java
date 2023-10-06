package cc.cdtime.lifecapsule.middle.userAct;


import cc.cdtime.lifecapsule.meta.userAct.entity.UserAct;
import cc.cdtime.lifecapsule.meta.userAct.entity.UserActView;

import java.util.ArrayList;
import java.util.Map;

public interface IUserActMiddle {
    /**
     * 记录用户行为日志
     *
     * @param userAct
     */
    void createUserAct(UserAct userAct) throws Exception;
    Integer totalUserAct(Map qIn) throws Exception;

    /**
     * 查询用户行为日志列表
     *
     * @param qIn logStartTime
     *            logEndTime
     *            size
     *            offset
     * @return
     */
    ArrayList<UserActView> listUserAct(Map qIn) throws Exception;

}
