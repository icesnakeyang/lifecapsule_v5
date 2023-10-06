package cc.cdtime.lifecapsule.business.noteSend;

import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.noteSendLog.entity.NoteSendLog;
import cc.cdtime.lifecapsule.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule.meta.recipient.entity.RecipientView;
import cc.cdtime.lifecapsule.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule.middle.recipient.IRecipientMiddle;
import cc.cdtime.lifecapsule.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteSendBService implements INoteSendBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final INoteSendMiddle iNoteSendMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final IRecipientMiddle iRecipientMiddle;
    private final ITriggerMiddle iTriggerMiddle;

    public NoteSendBService(IUserMiddle iUserMiddle,
                            INoteMiddle iNoteMiddle,
                            INoteSendMiddle iNoteSendMiddle,
                            ISecurityMiddle iSecurityMiddle,
                            IRecipientMiddle iRecipientMiddle,
                            ITriggerMiddle iTriggerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iRecipientMiddle = iRecipientMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
    }

    @Override
    public void sendNoteInstant(Map in) throws Exception {
        String token = in.get("token").toString();
        String email = in.get("email").toString();
        String noteContent = (String) in.get("noteContent");
        String title = (String) in.get("title");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * todo
         * 直接发送email
         */

        /**
         * 如果email用户已经注册，则读取该用户
         */
        qIn = new HashMap();
        qIn.put("email", email);
        UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);

        NoteSendLog noteSendLog = new NoteSendLog();
        noteSendLog.setSendLogId(GogoTools.UUID32());
        noteSendLog.setSendTime(new Date());
        noteSendLog.setSendUserId(userView.getUserId());
        if (userEmailView != null) {
            noteSendLog.setReceiveUserId(userEmailView.getUserId());
        }
        noteSendLog.setToEmail(email);
        noteSendLog.setNoteContent(noteContent);
        noteSendLog.setTitle(title);
        noteSendLog.setTriggerType(ESTags.INSTANT_MESSAGE.toString());
        iNoteSendMiddle.createNoteSendLog(noteSendLog);
    }


    @Override
    public Map searchUser(Map in) throws Exception {
        String token = in.get("token").toString();
        String searchKey = in.get("searchKey").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
//        qIn.put("phone", searchKey);
//        qIn.put("email", searchKey);
        qIn.put("loginName", searchKey);
        ArrayList<UserView> userViews = iUserMiddle.listUser(qIn);

        Map out = new HashMap();
        out.put("users", userViews);

        return out;
    }

    @Override
    public Map loadMyNoteSendStatistic(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我收到的笔记总数
        qIn = new HashMap();
        qIn.put("receiveUserId", userView.getUserId());
        Integer totalReceive = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceive", totalReceive);
        //收到未读总数
        qIn.put("unread", true);
        Integer totalReceiveUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceiveUnread", totalReceiveUnread);

        //我发送的笔记总数
        qIn = new HashMap();
        qIn.put("sendUserId", userView.getUserId());
        qIn.put("noteId", noteId);
        Integer totalSend = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSend", totalSend);
        //我发送对方未读
        qIn.put("unread", true);
        Integer totalSendUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSendUnread", totalSendUnread);

        return out;
    }

    @Override
    public Map listNoteReceiveLog(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我收到的笔记列表
        qIn = new HashMap();
        qIn.put("receiveUserId", userView.getUserId());
        qIn.put("noteId", noteId);
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<NoteSendLogView> views = iNoteSendMiddle.listNoteSendLog(qIn);
        Integer total = iNoteSendMiddle.totalNoteSendLog(qIn);
        ArrayList logs = new ArrayList();
        for (int i = 0; i < views.size(); i++) {
            Map log = new HashMap();
            log.put("title", views.get(i).getTitle());
            log.put("sendUserId", views.get(i).getSendUserId());
            UserView sender = iUserMiddle.getUserTiny(views.get(i).getSendUserId(), false, false);
            log.put("sendUserId", sender.getUserId());
            log.put("sendUserNickname", sender.getNickname());
            log.put("sendTime", views.get(i).getSendTime());
            log.put("readTime", views.get(i).getReadTime());
            log.put("sendLogId", views.get(i).getSendLogId());
            log.put("ids", views.get(i).getIds());
            log.put("triggerType", views.get(i).getTriggerType());
            if (views.get(i).getTriggerType() != null) {
                if (views.get(i).getTriggerType().equals(ESTags.REPLY_SEND_LOG.toString())) {
                    //读取上级笔记信息
                    if (views.get(i).getRefPid() != null) {
                        Map qIn2 = new HashMap();
                        qIn2.put("sendLogId", views.get(i).getRefPid());
                        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn2, false, null);
                        log.put("reTitle", noteSendLogView.getTitle());
                    }
                }
            }
            logs.add(log);
        }
        out.put("receiveNoteList", logs);
        out.put("totalReceiveNote", total);
        //收到未读总数
        qIn.put("unread", true);
        qIn.put("receiveUserId", userView.getUserId());
        Integer totalReceiveUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalReceiveNoteUnread", totalReceiveUnread);

        return out;
    }

    /**
     * App端用户读取自己发送的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map listNoteSendLogSend(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        //我发送的笔记列表
        qIn = new HashMap();
        qIn.put("sendUserId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        ArrayList<NoteSendLogView> views = iNoteSendMiddle.listNoteSendLog(qIn);
        Integer total = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("sendNoteList", views);
        out.put("totalSendNote", total);
        //未读总数
        qIn.put("unread", true);
        Integer totalSendNoteUnread = iNoteSendMiddle.totalNoteSendLog(qIn);
        out.put("totalSendNoteUnread", totalSendNoteUnread);

        return out;
    }

    @Override
    public Map getNoteSendLog(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = (String) in.get("sendLogId");

        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map noteMap = new HashMap();

        Map qIn1 = new HashMap();
        qIn1.put("sendLogId", sendLogId);
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn1, false, userView.getUserId());

        noteMap.put("sendLogId", noteSendLogView.getSendLogId());
        noteMap.put("sendUserId", noteSendLogView.getSendUserId());
        noteMap.put("sendTime", noteSendLogView.getSendTime());
        noteMap.put("content", noteSendLogView.getContent());
        noteMap.put("title", noteSendLogView.getTitle());
        noteMap.put("triggerType", noteSendLogView.getTriggerType());
        noteMap.put("readTime", noteSendLogView.getReadTime());
        /**
         * 把用户秘钥加密发送回前端
         */
        if (noteSendLogView.getUserEncodeKey() != null) {
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(noteSendLogView.getUserEncodeKey(), strAESKey);
            noteMap.put("userEncodeKey", outCode);
        }
        noteMap.put("fromName",noteSendLogView.getFromName());

        return noteMap;
    }

    @Override
    public Map getNoteSendLogSender(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = (String) in.get("sendLogId");

        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        Map qIn1 = new HashMap();
        qIn1.put("sendLogId", sendLogId);
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn1, false, userView.getUserId());
        if (!noteSendLogView.getSendUserId().equals(userView.getUserId())) {
            //不是发送人，不能查看发送笔记
            throw new Exception("10049");
        }

        /**
         * 把用户秘钥加密发送回前端
         */
        if (noteSendLogView.getUserEncodeKey() != null) {
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(noteSendLogView.getUserEncodeKey(), strAESKey);
            noteSendLogView.setUserEncodeKey(outCode);
        }

        if (noteSendLogView.getRecipientId() != null) {
            RecipientView recipientView = iRecipientMiddle.getRecipient(noteSendLogView.getRecipientId(), false);
            noteSendLogView.setRecipientTitle(recipientView.getTitle());
            noteSendLogView.setToName(recipientView.getToName());
            noteSendLogView.setFromName(recipientView.getFromName());
            noteSendLogView.setDescription(recipientView.getDescription());
            noteSendLogView.setRecipientName(recipientView.getRecipientName());
            noteSendLogView.setRecipientEmail(recipientView.getEmail());
            noteSendLogView.setRecipientRemark(recipientView.getRecipientRemark());
        }

        out.put("noteSendLog", noteSendLogView);

        return out;
    }

    @Override
    public void deleteSendNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = in.get("sendLogId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("sendLogId", sendLogId);
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn, false, userView.getUserId());

        iNoteSendMiddle.deleteNoteSendLog(sendLogId);
    }

    @Override
    public Map getTriggerIdFromSendLog(Map in) throws Exception {
        String token = in.get("token").toString();
        String sendLogId = (String) in.get("ref_pid");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("sendLogId", sendLogId);
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn, true, null);
        if (!noteSendLogView.getSendUserId().equals(userView.getUserId())) {
            //当前用户不是笔记的发送方
            throw new Exception("10079");
        }
        Map out = new HashMap();
        out.put("triggerId", noteSendLogView.getTriggerId());

        return out;
    }
}
