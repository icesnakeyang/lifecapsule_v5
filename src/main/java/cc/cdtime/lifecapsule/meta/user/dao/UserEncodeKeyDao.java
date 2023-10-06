package cc.cdtime.lifecapsule.meta.user.dao;

import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKeyView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserEncodeKeyDao {
    void createUserEncodeKey(UserEncodeKey userEncodeKey);

    /**
     * 读取一个用户秘钥记录
     *
     * @param indexId
     * @return
     */
    UserEncodeKeyView getUserEncodeKey(String indexId);

    /**
     * @param qIn encodeKey
     *            indexId
     */
    void updateUserEncodeKey(Map qIn);

    void deleteUserEncodeKey(String indexId);
}
