package cc.cdtime.lifecapsule.admin.note;

import cc.cdtime.lifecapsule.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminNoteBService implements IAdminNoteBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final INoteMiddle iNoteMiddle;

    public AdminNoteBService(IAdminUserMiddle iAdminUserMiddle,
                             INoteMiddle iNoteMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Override
    public Map listNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String userId=(String)in.get("userId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if(userId!=null && !userId.equals("")) {
            qIn.put("userId", userId);
        }
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);
        Integer totalNote = iNoteMiddle.totalNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);
        out.put("totalNote", totalNote);

        return out;
    }
}
