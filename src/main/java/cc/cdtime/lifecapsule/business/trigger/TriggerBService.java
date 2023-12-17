package cc.cdtime.lifecapsule.business.trigger;

import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.contact.IContactMiddle;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TriggerBService implements ITriggerBService {
    private final IUserMiddle iUserMiddle;
    private final ITriggerMiddle iTriggerMiddle;
    private final INoteMiddle iNoteMiddle;
    private final IContactMiddle iContactMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final INoteSendMiddle iNoteSendMiddle;

    public TriggerBService(IUserMiddle iUserMiddle,
                           ITriggerMiddle iTriggerMiddle,
                           INoteMiddle iNoteMiddle,
                           IContactMiddle iContactMiddle,
                           ISecurityMiddle iSecurityMiddle,
                           INoteSendMiddle iNoteSendMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iContactMiddle = iContactMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map saveNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String remark = (String) in.get("remark");
        String triggerType = in.get("triggerType").toString();
        Date triggerTime = (Date) in.get("triggerTime");
        String triggerId = (String) in.get("triggerId");
        String fromName = (String) in.get("fromName");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        int cc = 0;
        if (triggerType.equals(ESTags.TIMER_TYPE_PRIMARY.toString())) {
            /**
             * 主倒计时
             */
            triggerTime = null;
            cc++;
        } else {
            if (triggerType.equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
                /**
                 * 用户指定时间
                 */
                if (triggerTime == null) {
                    //必须设定触发条件的触发时间
                    throw new Exception("10020");
                }
                cc++;
            } else {
                if (triggerType.equals(ESTags.NO_TRIGGER.toString())) {
                    /**
                     * 不设置触发条件
                     */
                    cc++;
                }
            }
        }
        if (cc == 0) {
            //无效的触发条件
            throw new Exception("10048");
        }

        if (triggerId != null) {
//            TriggerView triggerView = iTriggerMiddle.getTrigger(triggerId, false, userView.getUserId());
            /**
             * 保存保存触发器
             */
            qIn = new HashMap();
            qIn.put("triggerTime", triggerTime);
            qIn.put("remark", remark);
            qIn.put("triggerType", triggerType);
            qIn.put("triggerId", triggerId);
            qIn.put("fromName", fromName);
            iTriggerMiddle.updateNoteTrigger(qIn);
        } else {
            /**
             * 新增触发器
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            NoteTrigger trigger = new NoteTrigger();
            trigger.setTriggerId(GogoTools.UUID32());
            trigger.setCreateTime(new Date());
            trigger.setNoteId(noteView.getNoteId());
            trigger.setUserId(userView.getUserId());
            trigger.setTriggerType(triggerType);
            trigger.setRemark(remark);
            trigger.setTriggerTime(triggerTime);
            trigger.setStatus(ESTags.ACTIVE.toString());

            iTriggerMiddle.createTrigger(trigger);
            triggerId = trigger.getTriggerId();
        }

        Map out = new HashMap();
        out.put("triggerId", triggerId);

        return out;
    }

//    /**
//     * 用户设置指定一篇笔记的指定一个接收人的发送触发条件
//     * 说明：本方法用于用户对特定一篇笔记指定的特定一位接收人单独设置的发送触发条件，每次只能设置一个接收人的发送条件，不适合批量操作情况
//     *
//     * @param in
//     * @throws Exception
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public Map saveNoteRecipientTrigger(Map in) throws Exception {
//        String token = in.get("token").toString();
//        String remark = (String) in.get("remark");
//        String triggerType = in.get("triggerType").toString();
//        Date triggerTime = (Date) in.get("triggerTime");
//        String triggerId = (String) in.get("triggerId");
//        String fromName = (String) in.get("fromName");
//        String recipientId = in.get("recipientId").toString();
//
//        /**
//         * 读取当前用户信息
//         */
//        Map qIn = new HashMap();
//        qIn.put("token", token);
//        //检查用户是否存在，是否已经登录
//        //todo 检查登录是否有效
//        UserView userView = iUserMiddle.getUser(qIn, false, true);
//
//        /**
//         * 检查触发器是否有效
//         */
//        int cc = 0;
//        if (triggerType.equals(ESTags.TIMER_TYPE_PRIMARY.toString())) {
//            /**
//             * 主倒计时
//             */
//            triggerTime = null;
//            cc++;
//        } else {
//            if (triggerType.equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
//                /**
//                 * 用户指定时间
//                 */
//                if (triggerTime == null) {
//                    //必须设定触发条件的触发时间
//                    throw new Exception("10020");
//                }
//                cc++;
//            }
//        }
//        if (cc == 0) {
//            //无效的触发条件
//            throw new Exception("10048");
//        }
//
//        /**
//         * 读取接收人
//         */
//        RecipientView recipientView = iRecipientMiddle.getRecipient(recipientId, false, userView.getUserId());
//
//        /**
//         * 读取接收人的触发器
//         */
//        qIn = new HashMap();
//        qIn.put("recipientId", recipientId);
//        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
//
//
//        /**
//         * 如果有triggerId，就是修改，没有就增加
//         */
//        Map triggerMap = new HashMap();
//        if (triggerId != null) {
//            if (!triggerView.getTriggerId().equals(triggerId)) {
//                //触发器错误
//                throw new Exception("10032");
//            }
//            /**
//             * 保存保存触发器
//             */
//            qIn = new HashMap();
//            qIn.put("triggerTime", triggerTime);
//            qIn.put("triggerType", triggerType);
//            qIn.put("triggerId", triggerId);
//            iTriggerMiddle.updateNoteTrigger(qIn);
//            triggerMap.put("triggerId", triggerId);
//            triggerMap.put("triggerType", triggerType);
//            triggerMap.put("triggerTime", triggerTime);
//        } else {
//            /**
//             * 新增触发器
//             */
//            if (triggerView != null) {
//                //触发器已经存在，不能新增
//                throw new Exception("10033");
//            }
//            NoteTrigger trigger = new NoteTrigger();
//            trigger.setTriggerId(GogoTools.UUID32());
//            trigger.setCreateTime(new Date());
//            trigger.setNoteId(recipientView.getNoteId());
//            trigger.setUserId(userView.getUserId());
//            trigger.setTriggerType(triggerType);
//            trigger.setTriggerTime(triggerTime);
//            trigger.setRecipientId(recipientView.getRecipientId());
//            trigger.setStatus(ESTags.ACTIVE.toString());
//            iTriggerMiddle.createTrigger(trigger);
//            triggerMap.put("triggerId", trigger.getTriggerId());
//            triggerMap.put("triggerType", trigger.getTriggerType());
//            triggerMap.put("triggerTime", trigger.getTriggerTime());
//        }
//
//        Map out = new HashMap();
//        out.put("trigger", triggerMap);
//
//        return out;
//    }

//    @Override
//    public Map getNoteTrigger(Map in) throws Exception {
//        String token = in.get("token").toString();
//        String triggerId = (String) in.get("triggerId");
//
//        Map qIn = new HashMap();
//        qIn.put("token", token);
//        UserView userView = iUserMiddle.getUser(qIn, false, true);
//
//        Map out = new HashMap();
//
//        qIn = new HashMap();
//        qIn.put("triggerId", triggerId);
//        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
//        out.put("trigger", triggerView);
//        return out;
//    }

    @Override
    public Map listNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String triggerType = (String) in.get("triggerType");
        String status = (String) in.get("status");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if (status.equals(ESTags.ACTIVE.toString())) {
            qIn.put("status", status);
        } else {
            if (status.equals(ESTags.SEND_COMPLETE.toString())) {
                qIn.put("toUserStatus", ESTags.SEND_COMPLETE);
                qIn.put("toEmailStatus", ESTags.SEND_COMPLETE);
            }
        }
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);
        Integer total = iTriggerMiddle.totalTrigger(qIn);

        for (int i = 0; i < triggerViews.size(); i++) {
            TriggerView triggerView = triggerViews.get(i);
            if (triggerView.getTriggerType().equals(ESTags.TIMER_TYPE_PRIMARY.toString())) {
                /**
                 * 把触发时间设置为主倒计时时间
                 */
                triggerView.setTriggerTime(userView.getTimerPrimary());
            }
            triggerViews.set(i, triggerView);
        }

        Map out = new HashMap();
        out.put("triggerList", triggerViews);
        out.put("totalTrigger", total);

        return out;
    }

    @Override
    public Map getTriggerDetail(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

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

        qIn = new HashMap();
        qIn.put("triggerId", triggerId);
        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, false, userView.getUserId());

        /**
         * 读取userEncodeKey
         */
        if (triggerView.getUserEncodeKey() != null) {
            if (strAESKey == null) {
                //查询秘钥错误
                throw new Exception("10037");
            }
            String data = triggerView.getUserEncodeKey();
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            triggerView.setUserEncodeKey(outCode);
        }

        Map out = new HashMap();
        out.put("trigger", triggerView);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNoteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("triggerId", triggerId);
        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, false, userView.getUserId());

        if (!triggerView.getStatus().equals(ESTags.ACTIVE.toString())) {
            //只能删除未发送的触发器
            throw new Exception("10059");
        }

        iTriggerMiddle.deleteTrigger(triggerId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTriggerInstant(Map in) throws Exception {
        createTrigger1(in, ESTags.INSTANT_MESSAGE.toString());
    }

    /**
     * 用户保存一个笔记触发器，根据设置的时间来发送
     *
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNoteTriggerByDatetime(Map in) throws Exception {
        Date sendTime = (Date) in.get("sendTime");
        if (sendTime == null) {
            //必须设置发送时间
            throw new Exception("10083");
        }
        createTrigger1(in, ESTags.TIMER_TYPE_DATETIME.toString());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNoteTriggerPrimary(Map in) throws Exception {
        createTrigger1(in, ESTags.TIMER_TYPE_PRIMARY.toString());
    }

    @Override
    public Map getNoteFromMail(Map in) throws Exception {
        String triggerId = in.get("triggerId").toString();

        Map qIn = new HashMap();
        qIn.put("triggerId", triggerId);
        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, false, null);

        /**
         * 通过triggerId查询sendLog，如果sendLog发送已超过7天，就返回错误消息
         */
        NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn, false, null);
        Long date1 = noteSendLogView.getSendTime().getTime();
        Long date2 = new Date().getTime();
        Long spanDays = (date2 - date1) / 1000 / 24 / 3600;

        Map out = new HashMap();

        if (spanDays > 7) {
            //超过7天
            throw new Exception("10080");
        }

        Map note = new HashMap();
        note.put("title", triggerView.getTitle());
        note.put("fromName", triggerView.getFromName());
        note.put("toName", triggerView.getToName());
        note.put("content", triggerView.getNoteContent());
        note.put("userEncodeKey", triggerView.getUserEncodeKey());
        note.put("createTime", triggerView.getCreateTime());
        out.put("note", note);
        return out;
    }

    private void createTrigger1(Map in, String triggerType) throws Exception {
        String token = in.get("token").toString();
        String toEmail = in.get("toEmail").toString();
        String title = (String) in.get("title");
        String noteId = in.get("noteId").toString();
        String noteContent = in.get("noteContent").toString();
        String fromName = (String) in.get("fromName");
        String toName = (String) in.get("toName");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        Date sendTime = (Date) in.get("sendTime");
        String toUserCode = (String) in.get("toUserCode");

        /**
         * 首先，通过token读取当前用户信息
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 读取要发送的原笔记是否是当前用户创建的
         */
        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        int cc = 0;
        String toUserId = null;
        if (toUserCode != null && !toUserCode.equals("")) {
            /**
             * 通过userCode查询接受人Id
             */
            Map qIn2 = new HashMap();
            qIn2.put("userCode", toUserCode);
            UserView userView1 = iUserMiddle.getUserTiny(qIn2, true, false);
            if (userView1 != null) {
                toUserId = userView1.getUserId();
                cc++;

                /**
                 * 查询要发送的user code，是否在当前用户的联系人表里
                 * 没有就添加一个
                 */
                qIn = new HashMap();
                qIn.put("userCode", toUserCode);
                qIn.put("userId", userView.getUserId());
                ContactView contactView = iContactMiddle.getContact(qIn, true, null);
                if (contactView == null) {
                    /**
                     * 创建联系人
                     */
                    Contact contact = new Contact();
                    contact.setContactId(GogoTools.UUID32());
                    contact.setEmail(toEmail);
                    contact.setUserId(userView.getUserId());
                    contact.setContactName(toName);
                    contact.setUserCode(toUserCode);
                    iContactMiddle.createContact(contact);
                }
            }
        }
        if (toEmail != null && !toEmail.equals("")) {
            /**
             * 通过Email发送
             */
            /**
             * 查询要发送的email是否存在
             */
            qIn = new HashMap();
            qIn.put("email", toEmail);
            UserEmailView userEmailView = iUserMiddle.getUserEmail(qIn, true, null);

            if (userEmailView == null) {
                //发送的email还没有注册为用户
                //没有用户信息，不能发送站内信
                //检查email是否有效，如果无效就停止创建trigger
                if (!GogoTools.checkEmail(toEmail)) {
                    //无效
                    throw new Exception("10084");
                } else {
                    //email有效，可以发送
                    cc++;
                }
            } else {
                toUserId = userEmailView.getUserId();
                //查到用户信息，可以发送站内信
                cc++;
            }
            /**
             * 查询要发送的email，是否在当前用户的联系人表里
             * 没有就添加一个
             */
            qIn = new HashMap();
            qIn.put("email", toEmail);
            qIn.put("userId", userView.getUserId());
            ContactView contactView = iContactMiddle.getContact(qIn, true, null);
            if (contactView == null) {
                /**
                 * 创建联系人
                 */
                Contact contact = new Contact();
                contact.setContactId(GogoTools.UUID32());
                contact.setEmail(toEmail);
                contact.setUserId(userView.getUserId());
                contact.setContactName(toName);
                iContactMiddle.createContact(contact);
            }
        }

        if(cc==0){
            //没有有效的发送对象
            throw new Exception("10085");
        }

        /**
         * 新建触发器
         * 1、首先创建一个NoteTrigger
         * 2、生成triggerId
         * 3、创建一个content，indexId=triggerId
         * 4、创建一个UserEncodeKey，indexId=triggerId
         */
        /**
         * 如果用户没有设置密码，就保存私钥，如果设置了密码，则不保存私钥
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        if (encryptKey != null && !encryptKey.equals("")) {
            String privateKey = iSecurityMiddle.getRSAKey(keyToken);
            strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
            iSecurityMiddle.deleteRSAKey(keyToken);
        }
        NoteTrigger noteTrigger = new NoteTrigger();
        noteTrigger.setTriggerId(GogoTools.UUID32());
        noteTrigger.setTriggerType(triggerType);
        noteTrigger.setNoteContent(noteContent);
        noteTrigger.setCreateTime(new Date());
        noteTrigger.setUserId(userView.getUserId());
        noteTrigger.setStatus(ESTags.ACTIVE.toString());
        noteTrigger.setTitle(title);
        noteTrigger.setToEmail(toEmail);
        if (triggerType.equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
            noteTrigger.setTriggerTime(sendTime);
        }
        noteTrigger.setNoteId(noteView.getNoteId());
        noteTrigger.setFromName(fromName);
        noteTrigger.setToName(toName);
        noteTrigger.setUserEncodeKey(strAESKey);
        noteTrigger.setToUserId(toUserId);
        iTriggerMiddle.createTrigger(noteTrigger);
    }
}
