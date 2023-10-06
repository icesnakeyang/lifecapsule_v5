package cc.cdtime.lifecapsule.middle.admin;

import cc.cdtime.lifecapsule.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule.meta.email.entity.UserEmailView;

import java.util.ArrayList;
import java.util.Map;

public interface IAdminUserMiddle {
    /**
     * 创建一个系统管理员
     *
     * @param adminUser
     */
    void createAdminUser(AdminUser adminUser) throws Exception;

    /**
     * 读取一个系统管理员账号
     *
     * @param qIn adminId
     *            loginName
     *            token
     * @return
     */
    AdminUserView getAdminUser(Map qIn,Boolean returnNull) throws Exception;

    ArrayList<UserEmailView> listUserEmail(Map qIn) throws Exception;
}
