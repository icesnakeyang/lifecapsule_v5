package cc.cdtime.lifecapsule.business.common;

import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户的公共方法类
 * 如果不知道业务要放哪个包里，就放这里吧
 */
@Service
public class UserComBService implements IUserComBService {
    private final IUserMiddle iUserMiddle;

    public UserComBService(IUserMiddle iUserMiddle) {
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public String takeUniqueUserCode() throws Exception {
        int attempts = 0;
        int digits = 6;
        String strCode = null;
        while (true) {
            long code = GogoTools.getNumber(digits);
            Map qIn2 = new HashMap();
            qIn2.put("userCode", Long.toString(code));
            UserView userView = iUserMiddle.getUserTiny(qIn2, true, false);
            if (userView == null) {
                //code有效
                strCode = Long.toString(code);
                break;
            } else {
                attempts++;
            }
            if (attempts == 10) {
                digits++;
                attempts = 0;
            }
            if (digits > 999999) {
                break;
            }
        }
        return strCode;
    }
}
