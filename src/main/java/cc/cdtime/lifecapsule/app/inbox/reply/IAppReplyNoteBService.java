package cc.cdtime.lifecapsule.app.inbox.reply;

import java.util.Map;

public interface IAppReplyNoteBService {

    /**
     * 用户回复一条收到的笔记
     *
     * @param in
     */
    void replyReceiveNote(Map in) throws Exception;
}
