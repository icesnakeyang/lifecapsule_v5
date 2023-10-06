package cc.cdtime.lifecapsule.app.lastWords;

import java.util.Map;

public interface IAppLastWordsBService {
    Map listLastWords(Map in) throws Exception;

    void saveLastWords(Map in) throws Exception;

    Map getMyLastWords(Map in) throws Exception;

    void deleteMyLastWords(Map in) throws Exception;
}
