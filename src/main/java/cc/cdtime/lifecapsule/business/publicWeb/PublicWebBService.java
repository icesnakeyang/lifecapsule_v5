package cc.cdtime.lifecapsule.business.publicWeb;

import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.notePublic.NotePublic;
import cc.cdtime.lifecapsule.meta.notePublic.NotePublicView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.publicWeb.IPublicWebMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PublicWebBService implements IPublicWebBService {
    private final IUserMiddle iUserMiddle;
    private final IPublicWebMiddle iPublicWebMiddle;

    public PublicWebBService(IPublicWebMiddle iPublicWebMiddle,
                             IUserMiddle iUserMiddle) {
        this.iPublicWebMiddle = iPublicWebMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public void publishNoteToPublicWeb(Map in) throws Exception {
        String token = in.get("token").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();
        String authorName = in.get("authorName").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NotePublic notePublic = new NotePublic();
        notePublic.setNoteId(GogoTools.UUID32());
        notePublic.setContent(content);
        notePublic.setCreateTime(new Date());
        notePublic.setTitle(title);
        notePublic.setUserId(userView.getUserId());
        notePublic.setAuthorName(authorName);
        iPublicWebMiddle.createNotePublic(notePublic);
    }

    @Override
    public Map listPublicNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<NotePublicView> notePublicViews = iPublicWebMiddle.listNotePublic(qIn);

        Map out = new HashMap();
        out.put("noteList", notePublicViews);

        return out;
    }

    @Override
    public Map getMyPublicNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NotePublicView notePublicView = iPublicWebMiddle.getNotePublic(noteId);

        Map out = new HashMap();
        out.put("note", notePublicView);

        return out;
    }

    @Override
    public Map getArticle(Map in) throws Exception {
        String noteId = in.get("noteId").toString();

        NotePublicView notePublicView = iPublicWebMiddle.getNotePublic(noteId);

        Map articleMap = new HashMap();
        if (notePublicView != null) {
            articleMap.put("title", notePublicView.getTitle());
            articleMap.put("content", notePublicView.getContent());
            articleMap.put("authorName", notePublicView.getAuthorName());
            articleMap.put("createTime", notePublicView.getCreateTime());
            articleMap.put("views", notePublicView.getViews());
        }

        Map out = new HashMap();
        out.put("article", articleMap);

        return out;
    }

    @Override
    public void updateNotePublic(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("noteId", noteId);
        qIn.put("title", title);
        qIn.put("content", content);
        iPublicWebMiddle.updateNotePublic(qIn);
    }
}
