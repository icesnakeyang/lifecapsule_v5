package cc.cdtime.lifecapsule.business.motto;

import java.util.Map;

public interface IMottoBService {
    void publishMotto(Map in) throws Exception;

    Map getMottoRandom(Map in) throws Exception;

    void likeMotto(Map in) throws Exception;
}
