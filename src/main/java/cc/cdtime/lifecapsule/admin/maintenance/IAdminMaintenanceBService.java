package cc.cdtime.lifecapsule.admin.maintenance;

import java.util.Map;

public interface IAdminMaintenanceBService {
    void restoreOldDatabase(Map in) throws Exception;

    Map moveContentToIndex(Map in) throws Exception;
}
