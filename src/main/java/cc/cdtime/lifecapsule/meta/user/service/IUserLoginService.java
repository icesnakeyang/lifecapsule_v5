package cc.cdtime.lifecapsule.meta.user.service;


import cc.cdtime.lifecapsule.meta.user.entity.UserLogin;
import cc.cdtime.lifecapsule.meta.user.entity.UserLoginLog;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;

import java.util.ArrayList;
import java.util.Map;

public interface IUserLoginService {
    /**
     * 记录用户登录日志
     *
     * @param userLoginLog
     */
    void createUserLoginLog(UserLoginLog userLoginLog) throws Exception;

    /**
     * 查询用户登录日志列表
     *
     * @param qIn deviceCode
     *            userId
     *            offset
     *            size
     * @return
     */
    ArrayList<UserView> listUserLoginLog(Map qIn) throws Exception;
    Integer totalUserLoginLog(Map qIn) throws Exception;

    /**
     * 创建一个用户的登录信息
     *
     * @param userLogin
     */
    void createUserLogin(UserLogin userLogin) throws Exception;

    /**
     * 修改用户登录信息
     *
     * @param qIn token
     *            tokenTime
     *            openPassword
     *            userId
     */
    void updateUserLogin(Map qIn) throws Exception;

    /**
     * 查询一个用户的登录信息
     *
     * @param qIn userId
     *            token
     * @return
     */
    UserView getUserLogin(Map qIn) throws Exception;
}
