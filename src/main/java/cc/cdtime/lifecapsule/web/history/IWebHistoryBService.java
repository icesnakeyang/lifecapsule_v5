package cc.cdtime.lifecapsule.web.history;

import java.util.Map;

public interface IWebHistoryBService {
    Map loadHistoryHome(Map in) throws Exception;

    void replyMyNote(Map in) throws Exception;

    Map listSubNoteList(Map in) throws Exception;

    Map getMyPNote(Map in) throws Exception;
}
