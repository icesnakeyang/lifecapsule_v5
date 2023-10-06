package cc.cdtime.lifecapsule.middle.user;

import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEncodeKeyMiddle implements IUserEncodeKeyMiddle {
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public UserEncodeKeyMiddle(IUserEncodeKeyService iUserEncodeKeyService) {
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createUserEncodeKey(UserEncodeKey userEncodeKey) throws Exception {
        iUserEncodeKeyService.createUserEncodeKey(userEncodeKey);
    }

    @Override
    public UserEncodeKeyView getUserEncodeKey(String indexId) throws Exception {
        UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(indexId);
        return userEncodeKeyView;
    }

    @Override
    public void updateUserEncodeKey(Map qIn) throws Exception {
        iUserEncodeKeyService.updateUserEncodeKey(qIn);
    }
}
