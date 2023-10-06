package cc.cdtime.lifecapsule.business.inspiration;

import java.util.Map;

public interface IInspirationBService {
    Map listInspiration(Map in) throws Exception;

    Map getInspiration(Map in) throws Exception;

    void saveInspiration(Map in) throws Exception;

    void deleteInspiration(Map in) throws Exception;
}
