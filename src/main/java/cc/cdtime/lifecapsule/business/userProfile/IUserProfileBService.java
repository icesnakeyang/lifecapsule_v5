package cc.cdtime.lifecapsule.business.userProfile;

import java.util.Map;

public interface IUserProfileBService {

    void saveNickname(Map in) throws Exception;

    void saveUserLanguage(Map in) throws Exception;
}
