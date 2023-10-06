package cc.cdtime.lifecapsule.meta.user.service;

import cc.cdtime.lifecapsule.meta.user.entity.UserLoginHistory;

public interface IUserLoginHistoryService {
    void createUserLoginHistory(UserLoginHistory userLoginHistory) throws Exception;
}
