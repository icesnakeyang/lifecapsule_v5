package cc.cdtime.lifecapsule.meta.user.service;

import cc.cdtime.lifecapsule.meta.user.entity.UserLoginName;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;

import java.util.Map;

public interface IUserLoginNameService {
    /**
     * 创建一个用自定义登录名账号
     *
     * @param userLoginName
     */
    void createUserLoginName(UserLoginName userLoginName);

    /**
     * 查询一个登录名用户
     *
     * @param qIn userId
     *            loginName
     *            password
     * @return
     */
    UserView getLoginName(Map qIn);

    /**
     * 修改用户登录名密码
     *
     * @param qIn loginName
     *            password
     *            userId
     */
    void updateLoginName(Map qIn) throws Exception;
}
