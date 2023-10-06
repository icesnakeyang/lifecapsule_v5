package cc.cdtime.lifecapsule.web.antiDelay;

import cc.cdtime.lifecapsule.business.antiDelay.IAntiDelayBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebAntiDelayBService implements IWebAntiDelayBService {
    private final IAntiDelayBService iAntiDelayBService;

    public WebAntiDelayBService(IAntiDelayBService iAntiDelayBService) {
        this.iAntiDelayBService = iAntiDelayBService;
    }

    @Override
    public Map listMyAntiDelayNote(Map in) throws Exception {
        Map out = iAntiDelayBService.listMyAntiDelayNote(in);
        return out;
    }

    @Override
    public Map getMyAntiDelayNote(Map in) throws Exception {
        Map out = iAntiDelayBService.getMyAntiDelayNote(in);
        return out;
    }

    @Override
    public Map loadLastMyAntiDelayNote(Map in) throws Exception {
        Map out = iAntiDelayBService.loadLastMyAntiDelayNote(in);
        return out;
    }

    @Override
    public void createMyAntiDelayNote(Map in) throws Exception {
        iAntiDelayBService.createAntiDelayNote(in);
    }

    @Override
    public void updateMyAntiDelayNote(Map in) throws Exception {
        iAntiDelayBService.updateAntiDelayNote(in);
    }
}
