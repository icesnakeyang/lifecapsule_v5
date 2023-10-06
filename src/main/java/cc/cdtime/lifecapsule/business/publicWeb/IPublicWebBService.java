package cc.cdtime.lifecapsule.business.publicWeb;

import java.util.Map;

public interface IPublicWebBService {
    void publishNoteToPublicWeb(Map in) throws Exception;

    Map listPublicNote(Map in) throws Exception;

    Map getMyPublicNote(Map in) throws Exception;

    Map getArticle(Map in) throws Exception;

    void updateNotePublic(Map in) throws Exception;
}
