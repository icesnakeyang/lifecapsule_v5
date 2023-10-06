package cc.cdtime.lifecapsule.app.author;

import cc.cdtime.lifecapsule.business.author.IAuthorBService;
import cc.cdtime.lifecapsule.meta.author.entity.AuthorLogView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AppAuthorBService implements IAppAuthorBService {
    private final IAuthorBService iAuthorBService;

    public AppAuthorBService(IAuthorBService iAuthorBService) {
        this.iAuthorBService = iAuthorBService;
    }

    @Override
    public Map getMyAuthorInfo(Map in) throws Exception {
        Map out = iAuthorBService.getAuthorInfo(in);
        return out;
    }

    @Override
    public Map listMyAuthorInfo(Map in) throws Exception {
        Map out = iAuthorBService.listMyAuthorInfo(in);
        return out;
    }
}
