package cc.cdtime.lifecapsule.app.history;

import cc.cdtime.lifecapsule.business.history.IHistoryBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppHistoryBService implements IAppHistoryBService {
    private final IHistoryBService iHistoryBService;

    public AppHistoryBService(IHistoryBService iHistoryBService) {
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
    public Map searchHistoryNote(Map in) throws Exception {
        Map out = iHistoryBService.searchHistoryNote(in);
        return out;
    }
}
