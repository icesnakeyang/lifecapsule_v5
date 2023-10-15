package cc.cdtime.lifecapsule.web.note;

import cc.cdtime.lifecapsule.business.loveLetter.ILoveLetterBService;
import cc.cdtime.lifecapsule.business.note.INoteBService;
import cc.cdtime.lifecapsule.business.noteSend.INoteSendBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebNoteBService implements IWebNoteBService {
    private final INoteBService iNoteBService;
    private final INoteSendBService iNoteSendBService;
    private final ILoveLetterBService iLoveLetterBService;

    public WebNoteBService(INoteBService iNoteBService,
                           INoteSendBService iNoteSendBService,
                           ILoveLetterBService iLoveLetterBService) {
        this.iNoteBService = iNoteBService;
        this.iNoteSendBService = iNoteSendBService;
        this.iLoveLetterBService = iLoveLetterBService;
    }

    @Override
    public Map listMyNote(Map in) throws Exception {
        Map out = iNoteBService.listNote(in);
        return out;
    }

    @Override
    public Map getMyNote(Map in) throws Exception {
        Map out = iNoteBService.getMyNote(in);
        return out;
    }

    @Override
    public Map saveMyNote(Map in) throws Exception {
        Map out = iNoteBService.saveNote(in);
        return out;
    }

    @Override
    public void deleteMyNote(Map in) throws Exception {
        iNoteBService.deleteNote(in);
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        Map out = iNoteSendBService.loadMyNoteSendStatistic(in);
        return out;
    }

    @Override
    public void saveMyNoteTags(Map in) throws Exception {
        iNoteBService.saveMyNoteTags(in);
    }

    @Override
    public Map listLoveLetter(Map in) throws Exception {
        Map out = iLoveLetterBService.listLoveLetter(in);
        return out;
    }

    @Override
    public Map getLoveLetter(Map in) throws Exception {
        Map out = iLoveLetterBService.getLoveLetter(in);
        return out;
    }
}
