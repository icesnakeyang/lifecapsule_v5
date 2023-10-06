package cc.cdtime.lifecapsule.web.timer;

import cc.cdtime.lifecapsule.business.timer.ITimerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebTimerBService implements IWebTimerBService {
    private final ITimerBService iTimerBService;

    public WebTimerBService(ITimerBService iTimerBService) {
        this.iTimerBService = iTimerBService;
    }

    @Override
    public Map snooze(Map in) throws Exception {
        Map out = iTimerBService.snooze(in);
        return out;
    }
}
