package cc.cdtime.lifecapsule.business.loveLetter;

import java.util.Map;

public interface ILoveLetterBService {
    Map listLoveLetter(Map in) throws Exception;

    Map getLoveLetter(Map in) throws Exception;

    void saveLoveLetter(Map in) throws Exception;

    void deleteLoveLetter(Map in) throws Exception;
}
