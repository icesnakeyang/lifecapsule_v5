package cc.cdtime.lifecapsule.web.antiDelay;

import java.util.Map;

public interface IWebAntiDelayBService {
    Map listMyAntiDelayNote(Map in) throws Exception;

    Map getMyAntiDelayNote(Map in) throws Exception;

    Map loadLastMyAntiDelayNote(Map in) throws Exception;

    void createMyAntiDelayNote(Map in) throws Exception;

    void updateMyAntiDelayNote(Map in) throws Exception;
}
