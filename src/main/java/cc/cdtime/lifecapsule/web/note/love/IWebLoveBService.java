package cc.cdtime.lifecapsule.web.note.love;

import java.util.Map;

public interface IWebLoveBService {
    Map listLoveLetter(Map in) throws Exception;

    Map getLoveLetter(Map in) throws Exception;

    void saveLoveLetter(Map in) throws Exception;

    void deleteMyLoveLetter(Map in) throws Exception;

    Map getLoveLetterTrigger(Map in) throws Exception;
}
