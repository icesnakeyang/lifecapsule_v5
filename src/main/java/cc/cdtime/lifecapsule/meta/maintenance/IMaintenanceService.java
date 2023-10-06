package cc.cdtime.lifecapsule.meta.maintenance;

import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;

import java.util.ArrayList;
import java.util.Map;

public interface IMaintenanceService {
    ArrayList<NoteView> listNoteOld() throws Exception;

    void updateNoteOld(Map qIn) throws Exception;

    ArrayList<UserView> listUserOld() throws Exception;

    ArrayList<NoteView> listAllNote() throws Exception;
}
