package cc.cdtime.lifecapsule.meta.maintenance;

import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface MaintenanceDao {
    ArrayList<NoteView> listNoteOld();

    void updateNoteOld(Map qIn);

    ArrayList<UserView> listUserOld();

    ArrayList<NoteView> listAllNote();
}
