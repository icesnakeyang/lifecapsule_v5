package cc.cdtime.lifecapsule.app.inbox;

import java.util.Map;

public interface IAppInboxBService {
    /**
     * App端用户读取自己收到的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listMyReceiveNote(Map in) throws Exception;

    /**
     * App端用户读取自己收到的笔记详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getMyReceiveNote(Map in) throws Exception;
}
