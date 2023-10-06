package cc.cdtime.lifecapsule.app.tag;

import cc.cdtime.lifecapsule.business.tag.ITagBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppTagBService implements IAppTagBService {
    private final ITagBService iTagBService;

    public AppTagBService(ITagBService iTagBService) {
        this.iTagBService = iTagBService;
    }

    @Override
    public void AddTagNote(Map in) throws Exception {
        iTagBService.AddTagNote(in);
    }

    @Override
    public Map listNoteTag(Map in) throws Exception {
        Map out = iTagBService.listNoteTag(in);
        return out;
    }

    @Override
    public void removeNoteTag(Map in) throws Exception {
        iTagBService.removeNoteTag(in);
    }

    @Override
    public Map listUserNoteTag(Map in) throws Exception {
        Map out = iTagBService.listUserNoteTag(in);
        return out;
    }

    @Override
    public Map listHotNoteTags(Map in) throws Exception {
        Map out = iTagBService.listHotNoteTags(in);
        return out;
    }
}
