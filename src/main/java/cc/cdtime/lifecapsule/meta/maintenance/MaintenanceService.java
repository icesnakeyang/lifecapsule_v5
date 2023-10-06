package cc.cdtime.lifecapsule.meta.maintenance;

import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class MaintenanceService implements IMaintenanceService {
    private final MaintenanceDao maintenanceDao;

    public MaintenanceService(MaintenanceDao maintenanceDao) {
        this.maintenanceDao = maintenanceDao;
    }

    @Override
    public ArrayList<NoteView> listNoteOld() throws Exception {
        ArrayList<NoteView> maintenances = maintenanceDao.listNoteOld();
        return maintenances;
    }

    @Override
    public void updateNoteOld(Map qIn) throws Exception {
        maintenanceDao.updateNoteOld(qIn);
    }

    @Override
    public ArrayList<UserView> listUserOld() throws Exception {
        ArrayList<UserView> userViews = maintenanceDao.listUserOld();
        return userViews;
    }

    @Override
    public ArrayList<NoteView> listAllNote() throws Exception {
        ArrayList<NoteView> noteViews = maintenanceDao.listAllNote();
        return noteViews;
    }
}
