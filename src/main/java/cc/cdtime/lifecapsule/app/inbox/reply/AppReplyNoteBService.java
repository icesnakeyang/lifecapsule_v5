package cc.cdtime.lifecapsule.app.inbox.reply;

import cc.cdtime.lifecapsule.business.inbox.reply.IReplyNoteBService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppReplyNoteBService implements IAppReplyNoteBService {
    private final IReplyNoteBService iReplyNoteBService;

    public AppReplyNoteBService(IReplyNoteBService iReplyNoteBService) {
        this.iReplyNoteBService = iReplyNoteBService;
    }

    @Override
    public void replyReceiveNote(Map in) throws Exception {
        iReplyNoteBService.replyReceiveNote(in);
    }
}
