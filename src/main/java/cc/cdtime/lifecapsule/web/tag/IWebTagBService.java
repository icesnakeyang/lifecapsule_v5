package cc.cdtime.lifecapsule.web.tag;

import java.util.Map;

public interface IWebTagBService {
    Map listUserNoteTag(Map in) throws Exception;

    Map listHotNoteTags(Map in) throws Exception;
}
