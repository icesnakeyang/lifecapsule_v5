package cc.cdtime.lifecapsule.meta.admin.service;


import cc.cdtime.lifecapsule.meta.admin.dao.AdminUserDao;
import cc.cdtime.lifecapsule.meta.admin.entity.AdminUser;
import cc.cdtime.lifecapsule.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule.meta.email.entity.UserEmailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminUserService implements IAdminUserService {
    @Autowired
    private AdminUserDao adminUserDao;

    @Override
    public void createAdminUser(AdminUser adminUser) throws Exception {
        adminUserDao.createAdminUser(adminUser);
    }

    @Override
    public AdminUserView getAdminUser(Map qIn) throws Exception {
        AdminUserView adminUserView = adminUserDao.getAdminUser(qIn);
        return adminUserView;
    }

    @Override
    public ArrayList<UserEmailView> listUserEmail(Map qIn) throws Exception {
        ArrayList<UserEmailView> userEmailViews = adminUserDao.listUserEmail(qIn);
        return userEmailViews;
    }
}
