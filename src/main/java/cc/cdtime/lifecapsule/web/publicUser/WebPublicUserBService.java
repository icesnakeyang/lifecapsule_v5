package cc.cdtime.lifecapsule.web.publicUser;

import cc.cdtime.lifecapsule.business.trigger.ITriggerBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebPublicUserBService implements IWebPublicUserBService {
    private final ITriggerBService iTriggerBService;

    public WebPublicUserBService(ITriggerBService iTriggerBService) {
        this.iTriggerBService = iTriggerBService;
    }

    @Override
    public Map getNoteFromMail(Map in) throws Exception {
        Map out = iTriggerBService.getNoteFromMail(in);
        return out;
    }
}
