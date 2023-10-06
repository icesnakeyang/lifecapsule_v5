package cc.cdtime.lifecapsule.app.contact;

import cc.cdtime.lifecapsule.business.contact.IContactBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppContactBService implements IAppContactBService {
    private final IContactBService iContactBService;

    public AppContactBService(IContactBService iContactBService) {
        this.iContactBService = iContactBService;
    }

    @Override
    public Map listMyContact(Map in) throws Exception {
        Map out = iContactBService.listContact(in);
        return out;
    }
}
