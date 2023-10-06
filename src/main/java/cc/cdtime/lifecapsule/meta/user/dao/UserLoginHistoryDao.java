package cc.cdtime.lifecapsule.meta.user.dao;

import cc.cdtime.lifecapsule.meta.user.entity.UserLoginHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginHistoryDao {
    void createUserLoginHistory(UserLoginHistory userLoginHistory);
}
