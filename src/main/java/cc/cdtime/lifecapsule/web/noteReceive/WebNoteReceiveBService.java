package cc.cdtime.lifecapsule.web.noteReceive;

import cc.cdtime.lifecapsule.business.inbox.reply.IReplyNoteBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebNoteReceiveBService implements IWebNoteReceiveBService {
    private final IReplyNoteBService iReplyNoteBService;

    public WebNoteReceiveBService(IReplyNoteBService iReplyNoteBService) {
        this.iReplyNoteBService = iReplyNoteBService;
    }

    @Override
    public void replyReceiveNote(Map in) throws Exception {
        iReplyNoteBService.replyReceiveNote(in);
    }
}
