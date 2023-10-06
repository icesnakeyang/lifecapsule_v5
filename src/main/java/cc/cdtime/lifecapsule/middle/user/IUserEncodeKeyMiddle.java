package cc.cdtime.lifecapsule.middle.user;

import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKeyView;

import java.util.Map;

public interface IUserEncodeKeyMiddle {
    void createUserEncodeKey(UserEncodeKey userEncodeKey) throws Exception;

    /**
     * 读取一个用户秘钥记录
     */
    UserEncodeKeyView getUserEncodeKey(String indexId) throws Exception;

    void updateUserEncodeKey(Map qIn) throws Exception;
}
