package cc.cdtime.lifecapsule.web.history;

import cc.cdtime.lifecapsule.business.history.IHistoryBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebHistoryBService implements IWebHistoryBService {
    private final IHistoryBService iHistoryBService;

    public WebHistoryBService(IHistoryBService iHistoryBService) {
        this.iHistoryBService = iHistoryBService;
    }

    @Override
    public Map loadHistoryHome(Map in) throws Exception {
        Map out = iHistoryBService.loadHistoryHome(in);
        return out;
    }

    @Override
    public void replyMyNote(Map in) throws Exception {
        iHistoryBService.replyMyNote(in);
    }

    @Override
    public Map listSubNoteList(Map in) throws Exception {
        Map out = iHistoryBService.listSubNoteList(in);
        return out;
    }

    @Override
    public Map getMyPNote(Map in) throws Exception {
        Map out = iHistoryBService.getMyPNote(in);
        return out;
    }
}
