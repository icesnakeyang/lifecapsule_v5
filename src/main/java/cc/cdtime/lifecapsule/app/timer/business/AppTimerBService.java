package cc.cdtime.lifecapsule.app.timer.business;

import cc.cdtime.lifecapsule.business.timer.ITimerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppTimerBService implements IAppTimerBService {
    private final ITimerBService iTimerBService;

    public AppTimerBService(ITimerBService iTimerBService) {
        this.iTimerBService = iTimerBService;
    }

    @Override
    public Map snooze(Map in) throws Exception {
        Map out=iTimerBService.snooze(in);
        return out;
    }
}
