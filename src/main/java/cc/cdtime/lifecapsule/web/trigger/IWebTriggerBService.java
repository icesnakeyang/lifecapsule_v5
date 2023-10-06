package cc.cdtime.lifecapsule.web.trigger;

import java.util.Map;

public interface IWebTriggerBService {

    void createTriggerInstant(Map in) throws Exception;

    Map listMyTriggerQue(Map in) throws Exception;

    Map getMyTriggerDetail(Map in) throws Exception;

    void createTriggerDatetime(Map in) throws Exception;

    void createTriggerPrimary(Map in) throws Exception;

    void deleteMyNoteTrigger(Map in) throws Exception;
}
