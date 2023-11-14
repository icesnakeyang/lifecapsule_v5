package cc.cdtime.lifecapsule.web.note.love;

import cc.cdtime.lifecapsule.business.loveLetter.ILoveLetterBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebLoveBService implements IWebLoveBService {
    private final ILoveLetterBService iLoveLetterBService;

    public WebLoveBService(ILoveLetterBService iLoveLetterBService) {
        this.iLoveLetterBService = iLoveLetterBService;
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

    @Override
    public void saveLoveLetter(Map in) throws Exception {
        iLoveLetterBService.saveLoveLetter(in);
    }

    @Override
    public void deleteMyLoveLetter(Map in) throws Exception {
        iLoveLetterBService.deleteLoveLetter(in);
    }

    @Override
    public Map getLoveLetterTrigger(Map in) throws Exception {
        Map out = iLoveLetterBService.getLoveLetterTrigger(in);
        return out;
    }
}
