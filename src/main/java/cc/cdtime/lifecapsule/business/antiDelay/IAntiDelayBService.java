package cc.cdtime.lifecapsule.business.antiDelay;

import java.util.Map;

public interface IAntiDelayBService {
    /**
     * app端用户读取自己的防拖延笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyAntiDelayNote(Map in) throws Exception;

    void createAntiDelayNote(Map in) throws Exception;

    void updateAntiDelayNote(Map in) throws Exception;

    Map getMyAntiDelayNote(Map in) throws Exception;

    void deleteAntiDelayNote(Map in) throws Exception;

    Map loadLastMyAntiDelayNote(Map in) throws Exception;
}
