package cc.cdtime.lifecapsule.meta.user.dao;

import cc.cdtime.lifecapsule.meta.user.entity.UserBase;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserBaseDao {
    /**
     * 创建一个基本用户账户
     *
     * @param userBase
     */
    void createUserBase(UserBase userBase);

    /**
     * 查询用户基础信息
     *
     * @param userId
     * @return
     */
    UserView getUserBase(String userId);

    /**
     * @param qIn 查询用户列表
     *            userId
     *            loginName
     *            phone
     *            email
     * @return
     */
    ArrayList<UserView> listUser(Map qIn);

    Integer totalUser(Map qIn);

    /**
     * 修改用户基本信息
     *
     * @param qIn nickname
     *            language
     *            userId
     */
    void updateUserBase(Map qIn);
}
