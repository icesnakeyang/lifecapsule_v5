package cc.cdtime.lifecapsule.business.history;

import java.util.Map;

public interface IHistoryBService {
    Map loadHistoryHome(Map in) throws Exception;

    void replyMyNote(Map in) throws Exception;

    Map searchHistoryNote(Map in) throws Exception;

    Map listSubNoteList(Map in) throws Exception;

    Map getMyPNote(Map in) throws Exception;
}
