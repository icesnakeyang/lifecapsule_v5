package cc.cdtime.lifecapsule.middle.noteSend;

import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.content.entity.Content;
import cc.cdtime.lifecapsule.meta.content.service.IContentService;
import cc.cdtime.lifecapsule.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule.meta.noteSendLog.service.INoteSendService;
import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKey;
import cc.cdtime.lifecapsule.meta.user.entity.UserEncodeKeyView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.meta.user.service.IUserBaseService;
import cc.cdtime.lifecapsule.meta.user.service.IUserEncodeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteSendMiddle implements INoteSendMiddle {
    private final INoteSendService iNoteSendService;
    private final IUserBaseService iUserBaseService;
    private final IContentService iContentService;
    private final IUserEncodeKeyService iUserEncodeKeyService;

    public NoteSendMiddle(INoteSendService iNoteSendService,
                          IUserBaseService iUserBaseService,
                          IContentService iContentService,
                          IUserEncodeKeyService iUserEncodeKeyService) {
        this.iNoteSendService = iNoteSendService;
        this.iUserBaseService = iUserBaseService;
        this.iContentService = iContentService;
        this.iUserEncodeKeyService = iUserEncodeKeyService;
    }

    @Override
    public void createNoteSendLog(NoteSendLog noteSendLog) throws Exception {
        iNoteSendService.createNoteSendLog(noteSendLog);
    }

    @Override
    public ArrayList<NoteSendLogView> listNoteSendLog(Map qIn) throws Exception {
        ArrayList<NoteSendLogView> noteSendLogViews = iNoteSendService.listNoteSendLog(qIn);
        return noteSendLogViews;
    }

    @Override
    public Integer totalNoteSendLog(Map qIn) throws Exception {
        Integer total = iNoteSendService.totalNoteSendLog(qIn);
        return total;
    }

    @Override
    public NoteSendLogView getNoteSendLog(Map qIn, Boolean returnNull, String userId) throws Exception {
        NoteSendLogView noteSendLogView = iNoteSendService.getNoteSendLog(qIn);
        if (noteSendLogView == null) {
            if (returnNull) {
                return null;
            }
            //发送的笔记不存在
            throw new Exception("10039");
        }
        if (userId != null) {
            if (!noteSendLogView.getSendUserId().equals(userId) &&
                    !noteSendLogView.getReceiveUserId().equals(userId)) {
                //当前用户既不是发送方，也不是接收方
                throw new Exception("10040");
            }
        }

        /**
         * 读取内容详情
         */
        Content content = iContentService.getContent(noteSendLogView.getTriggerId());
        if (content != null) {
            noteSendLogView.setContent(content.getContent());
        }

        /**
         * 读取解密秘钥
         */
        UserEncodeKeyView userEncodeKeyView = iUserEncodeKeyService.getUserEncodeKey(noteSendLogView.getTriggerId());
        if (userEncodeKeyView != null) {
            noteSendLogView.setUserEncodeKey(userEncodeKeyView.getEncodeKey());
        }

        return noteSendLogView;
    }

    @Override
    public void updateNoteSendLog(Map qIn) throws Exception {
        iNoteSendService.updateNoteSendLog(qIn);
    }

    @Override
    public void deleteNoteSendLog(String sendLogId) throws Exception {
        /**
         * 删除内容
         */
        iContentService.deleteContent(sendLogId);

        /**
         * 删除秘钥
         */
        iUserEncodeKeyService.deleteUserEncodeKey(sendLogId);

        /**
         * 删除发送笔记记录
         */
        iNoteSendService.deleteNoteSendLog(sendLogId);
    }
}
