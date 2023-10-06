package cc.cdtime.lifecapsule.middle.admin;

import cc.cdtime.lifecapsule.meta.admin.entity.AdminStatisticView;
import cc.cdtime.lifecapsule.meta.admin.service.IAdminStatisticService;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.userAct.entity.UserActView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminStatisticMiddle implements IAdminStatisticMiddle {
    private final IAdminStatisticService iAdminStatisticService;

    public AdminStatisticMiddle(IAdminStatisticService iAdminStatisticService) {
        this.iAdminStatisticService = iAdminStatisticService;
    }

    @Override
    public ArrayList<AdminStatisticView> totalUserLogin(Map qIn) throws Exception {
        ArrayList<AdminStatisticView> adminStatisticViews =
                iAdminStatisticService.totalUserLogin(qIn);
        return adminStatisticViews;
    }

    @Override
    public ArrayList<NoteView> listNoteInfo(Map qIn) throws Exception {
        ArrayList<NoteView> noteViews = iAdminStatisticService.listNoteInfo(qIn);
        return noteViews;
    }

    @Override
    public Integer totalUserAct(Map qIn) throws Exception {
        Integer total = iAdminStatisticService.totalUserAct(qIn);
        return total;
    }
}
