package cc.cdtime.lifecapsule.business.noteTrigger;

import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteTriggerBService implements INoteTriggerBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ITriggerMiddle iTriggerMiddle;

    public NoteTriggerBService(IUserMiddle iUserMiddle,
                               INoteMiddle iNoteMiddle,
                               ITriggerMiddle iTriggerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
    }

    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn=new HashMap();
        Integer offset=(pageIndex-1)*pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("userId", userView.getUserId());
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);

        Map out = new HashMap();
        out.put("triggerList", triggerViews);

        return out;
    }
}
