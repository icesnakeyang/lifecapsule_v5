package cc.cdtime.lifecapsule.web.recipient;

import cc.cdtime.lifecapsule.business.recipient.IRecipientBService;
import cc.cdtime.lifecapsule.framework.constant.ESTags;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebRecipientBService implements IWebRecipientBService {
    private final IRecipientBService iRecipientBService;

    public WebRecipientBService(IRecipientBService iRecipientBService) {
        this.iRecipientBService = iRecipientBService;
    }

    @Override
    public void addContactToRecipient(Map in) throws Exception {
        iRecipientBService.addContactToRecipient(in);
    }

    @Override
    public Map listMyNoteRecipient(Map in) throws Exception {
        Map out = iRecipientBService.listRecipient(in);
        return out;
    }

    @Override
    public Map getRecipient(Map in) throws Exception {
        Map out = iRecipientBService.getRecipient(in);
        return out;
    }

    @Override
    public void saveRecipient(Map in) throws Exception {
        iRecipientBService.saveRecipient(in);
    }

    @Override
    public void addEmailToRecipient(Map in) throws Exception {
        in.put("frontEnd", ESTags.WEB_CLIENT.toString());
        iRecipientBService.createNoteRecipient(in);
    }
}
