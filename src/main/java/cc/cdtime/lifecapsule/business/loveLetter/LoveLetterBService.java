package cc.cdtime.lifecapsule.business.loveLetter;

import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.trigger.entity.NoteTrigger;
import cc.cdtime.lifecapsule.meta.trigger.entity.TriggerView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule.middle.trigger.ITriggerMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoveLetterBService implements ILoveLetterBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final ITriggerMiddle iTriggerMiddle;

    public LoveLetterBService(IUserMiddle iUserMiddle,
                              INoteMiddle iNoteMiddle,
                              ISecurityMiddle iSecurityMiddle,
                              ITriggerMiddle iTriggerMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iTriggerMiddle = iTriggerMiddle;
    }

    @Override
    public Map listLoveLetter(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String searchKey = (String) in.get("searchKey");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("noteType", ESTags.LOVE_LETTER);
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("searchKey", searchKey);
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);
        Integer totalNote = iNoteMiddle.totalNote(qIn);

        Map out = new HashMap();
        out.put("noteList", noteViews);
        out.put("totalNote", totalNote);

        return out;
    }

    @Override
    public Map getLoveLetter(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
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

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        Map noteMap = new HashMap();

        /**
         * 读取userEncodeKey
         */
        if (noteView.getUserEncodeKey() != null) {
            if (strAESKey == null) {
                //查询秘钥错误
                throw new Exception("10037");
            }
            String data = noteView.getUserEncodeKey();
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            noteMap.put("userEncodeKey", outCode);
//            noteView.setUserEncodeKey(outCode);
        } else {
            noteMap.put("userEncodeKey", null);
//            noteView.setUserEncodeKey(null);
        }
        noteMap.put("content", noteView.getContent());
        noteMap.put("title", noteView.getTitle());
        noteMap.put("noteId", noteView.getNoteId());
        noteMap.put("createTime", noteView.getCreateTime());

        qIn = new HashMap();
        qIn.put("noteId", noteView.getNoteId());
        qIn.put("userId", userView.getUserId());
        ArrayList<TriggerView> triggerViews = iTriggerMiddle.listTrigger(qIn);
        noteMap.put("triggerList", triggerViews);

        Map out = new HashMap();
        out.put("note", noteMap);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveLoveLetter(Map in) throws Exception {
        String token = in.get("token").toString();
        String content = in.get("content").toString();
        String toEmail = (String) in.get("toEmail");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String title = in.get("title").toString();
        String toName = (String) in.get("toName");
        String fromName = (String) in.get("fromName");
        String noteId = (String) in.get("noteId");
        Date sendDateTime = (Date) in.get("sendDateTime");
        String triggerType = (String) in.get("triggerType");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        if (toEmail != null) {
            toEmail = toEmail.toLowerCase();
        }

        /**
         * 首先创建或者修改笔记
         */
        if (noteId == null) {
            /**
             * 创建笔记
             */
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setNoteType(ESTags.LOVE_LETTER.toString());
            noteId = GogoTools.UUID32();
            noteInfo.setNoteId(noteId);
            noteInfo.setContent(content);
            noteInfo.setCreateTime(new Date());
            noteInfo.setUserId(userView.getUserId());
            noteInfo.setStatus(ESTags.ACTIVE.toString());
            noteInfo.setUserEncodeKey(strAESKey);
            noteInfo.setTitle(title);

            iNoteMiddle.createNoteInfo(noteInfo);

            /**
             * 如果有发送信息，就创建trigger
             */
            NoteTrigger trigger = new NoteTrigger();
            int cc = 0;
            if (toName != null && !toName.equals("")) {
                trigger.setToName(toName);
                cc++;
            }
            if (fromName != null && !fromName.equals("")) {
                trigger.setFromName(fromName);
                cc++;
            }
            if (triggerType.equals(ESTags.TIMER_TYPE_DATETIME.toString())) {
                trigger.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
                if (sendDateTime != null) {
                    trigger.setTriggerTime(sendDateTime);
                    trigger.setStatus(ESTags.ACTIVE.toString());
                }
                cc++;
            } else {
                if (triggerType.equals(ESTags.TIMER_TYPE_PRIMARY.toString())) {
                    trigger.setTriggerType(ESTags.TIMER_TYPE_PRIMARY.toString());
                    trigger.setStatus(ESTags.ACTIVE.toString());
                    cc++;
                }
            }
            if (toEmail != null && !toEmail.equals("")) {
                trigger.setToEmail(toEmail);
                cc++;
            }else{
                if(trigger.getStatus().equals(ESTags.ACTIVE.toString())){
                    trigger.setStatus(ESTags.WAIT.toString());
                }
            }
            if (cc > 0) {
                trigger.setTriggerId(GogoTools.UUID32());
                trigger.setNoteId(noteId);
                trigger.setCreateTime(new Date());
                trigger.setUserId(userView.getUserId());

                iTriggerMiddle.createTrigger(trigger);
            }
        } else {
            /**
             * 修改笔记
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            qIn.put("title", title);
            qIn.put("content", content);
            qIn.put("encodeKey", strAESKey);
            iNoteMiddle.updateNoteInfo(qIn);
        }

        /**
         * 读取trigger
         * 一封情书可以有多个trigger，如果没有trigger，就创建一个trigger
         * 如果trigger已经发送了，那就再创建一个
         * 显示情书时，把trigger的列表列出来，已经发送的，等待发送的都列出来，用户可以点击修改，如果没有发送，可以删除
         * 实际上，所有的note都分2部分，内容主体，发送列表，发送列表里又包括接收人和发送条件
         *
         */
        if (triggerType != null && !triggerType.equals("")) {
            if (toEmail != null && !toEmail.equals("")) {
                qIn = new HashMap();
                qIn.put("noteId", noteId);
                qIn.put("userId", userView.getUserId());
                TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, null);
                if (triggerView != null) {
                    //发送人不变
                    qIn = new HashMap();
                    qIn.put("toEmail", toEmail);
                    if (toName != null) {
                        //修改toName
                        if (triggerView.getToName() == null) {
                            qIn.put("toName", toName);
                        } else {
                            if (!toName.equals(triggerView.getToName())) {
                                qIn.put("toName", toName);
                            }
                        }
                    }
                    if (fromName != null) {
                        //修改fromName
                        if (triggerView.getFromName() == null) {
                            qIn.put("fromName", fromName);
                        } else {
                            if (!fromName.equals(triggerView.getFromName())) {
                                qIn.put("fromName", fromName);
                            }
                        }
                    }
                    if (sendDateTime != null) {
                        qIn.put("triggerTime", sendDateTime);
                    }
                    if (qIn.size() > 0) {
                        qIn.put("triggerId", triggerView.getTriggerId());
                        iTriggerMiddle.updateNoteTrigger(qIn);
                    }
                } else {
                    /**
                     * 该toEmail还没有设置trigger，添加一个
                     */
                    NoteTrigger noteTrigger = new NoteTrigger();
                    noteTrigger.setTriggerId(GogoTools.UUID32());
                    noteTrigger.setNoteId(noteId);
                    noteTrigger.setCreateTime(new Date());
                    if (sendDateTime != null) {
                        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
                    } else {
                        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_PRIMARY.toString());
                    }
                    noteTrigger.setUserEncodeKey(strAESKey);
                    noteTrigger.setToEmail(toEmail);
                    noteTrigger.setToName(toName);
                    noteTrigger.setStatus(ESTags.ACTIVE.toString());
                    noteTrigger.setFromName(fromName);
                    noteTrigger.setNoteContent(content);
                    noteTrigger.setUserId(userView.getUserId());
                    noteTrigger.setTitle(title);
                    noteTrigger.setTriggerTime(sendDateTime);
                    iTriggerMiddle.createTrigger(noteTrigger);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveLoveLetterNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String content = in.get("content").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String title = in.get("title").toString();
        String noteId = (String) in.get("noteId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        /**
         * 首先创建或者修改笔记
         */
        if (noteId == null) {
            /**
             * 创建笔记
             */
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setNoteType(ESTags.LOVE_LETTER.toString());
            noteId = GogoTools.UUID32();
            noteInfo.setNoteId(noteId);
            noteInfo.setContent(content);
            noteInfo.setCreateTime(new Date());
            noteInfo.setUserId(userView.getUserId());
            noteInfo.setStatus(ESTags.ACTIVE.toString());
            noteInfo.setUserEncodeKey(strAESKey);
            noteInfo.setTitle(title);

            iNoteMiddle.createNoteInfo(noteInfo);
        } else {
            /**
             * 修改笔记
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            qIn.put("title", title);
            qIn.put("content", content);
            qIn.put("encodeKey", strAESKey);
            iNoteMiddle.updateNoteInfo(qIn);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveLoveLetterTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String toEmail = (String) in.get("toEmail");
        String toName = (String) in.get("toName");
        String fromName = (String) in.get("fromName");
        String noteId = (String) in.get("noteId");
        Date sendDateTime = (Date) in.get("sendDateTime");
        String triggerType = (String) in.get("triggerType");
        String triggerId = (String) in.get("triggerId");
        String content = in.get("content").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String title = in.get("title").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        String privateKey = iSecurityMiddle.getRSAKey(keyToken);
        strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityMiddle.deleteRSAKey(keyToken);

        if (toEmail != null) {
            toEmail = toEmail.toLowerCase();
        }

        /**
         * 读取trigger
         * 一封情书可以有多个trigger，如果没有trigger，就创建一个trigger
         * 如果trigger已经发送了，那就再创建一个
         * 显示情书时，把trigger的列表列出来，已经发送的，等待发送的都列出来，用户可以点击修改，如果没有发送，可以删除
         * 实际上，所有的note都分2部分，内容主体，发送列表，发送列表里又包括接收人和发送条件
         *
         */

        if (triggerId == null) {
            //创建一个trigger

        } else {
            //修改一个trigger
            TriggerView triggerView = new TriggerView();
            triggerView.setTitle(title);
            triggerView.setNoteContent(content);
            triggerView.setTriggerId(triggerId);
            triggerView.setUserEncodeKey(strAESKey);
            iTriggerMiddle.updateNoteTrigger2(triggerView);
        }

        if (triggerType != null && !triggerType.equals("")) {
            if (toEmail != null && !toEmail.equals("")) {
                qIn = new HashMap();
                qIn.put("noteId", noteId);
                qIn.put("userId", userView.getUserId());
                TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, null);
                if (triggerView != null) {
                    //发送人不变
                    qIn = new HashMap();
                    qIn.put("toEmail", toEmail);
                    if (toName != null) {
                        //修改toName
                        if (triggerView.getToName() == null) {
                            qIn.put("toName", toName);
                        } else {
                            if (!toName.equals(triggerView.getToName())) {
                                qIn.put("toName", toName);
                            }
                        }
                    }
                    if (fromName != null) {
                        //修改fromName
                        if (triggerView.getFromName() == null) {
                            qIn.put("fromName", fromName);
                        } else {
                            if (!fromName.equals(triggerView.getFromName())) {
                                qIn.put("fromName", fromName);
                            }
                        }
                    }
                    if (sendDateTime != null) {
                        qIn.put("triggerTime", sendDateTime);
                    }
                    if (qIn.size() > 0) {
                        qIn.put("triggerId", triggerView.getTriggerId());
                        iTriggerMiddle.updateNoteTrigger(qIn);
                    }
                } else {
                    /**
                     * 该toEmail还没有设置trigger，添加一个
                     */
                    NoteTrigger noteTrigger = new NoteTrigger();
                    noteTrigger.setTriggerId(GogoTools.UUID32());
                    noteTrigger.setNoteId(noteId);
                    noteTrigger.setCreateTime(new Date());
                    if (sendDateTime != null) {
                        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_DATETIME.toString());
                    } else {
                        noteTrigger.setTriggerType(ESTags.TIMER_TYPE_PRIMARY.toString());
                    }
                    noteTrigger.setUserEncodeKey(strAESKey);
                    noteTrigger.setToEmail(toEmail);
                    noteTrigger.setToName(toName);
                    noteTrigger.setStatus(ESTags.ACTIVE.toString());
                    noteTrigger.setFromName(fromName);
                    noteTrigger.setNoteContent(content);
                    noteTrigger.setUserId(userView.getUserId());
                    noteTrigger.setTitle(title);
                    noteTrigger.setTriggerTime(sendDateTime);
                    iTriggerMiddle.createTrigger(noteTrigger);
                }
            }
        } else {
            /**
             * 不设置发送条件，如果已经有发送条件，就删除
             */
            TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
            if (triggerView != null) {
                /**
                 * 如果触发条件还没有发送，就删除
                 */
                int cc = 0;
                if (triggerView.getToUserStatus() != null && triggerView.getToUserStatus().equals(ESTags.SEND_COMPLETE.toString())) {
                    cc++;
                }
                if (triggerView.getToEmailStatus() != null && triggerView.getToEmailStatus().equals(ESTags.SEND_COMPLETE.toString())) {
                    cc++;
                }
                if (cc > 0) {
                    /**
                     * 已经发送了，不处理
                     */
                } else {
                    /**
                     * 删除trigger
                     */
                    iTriggerMiddle.deleteTrigger(triggerView.getTriggerId());
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteLoveLetter(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        iNoteMiddle.deleteNote(noteView.getNoteId());
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        TriggerView triggerView = iTriggerMiddle.getTrigger(qIn, true, userView.getUserId());
        if (triggerView != null) {
            iTriggerMiddle.deleteTrigger(triggerView.getTriggerId());
        }
    }

    @Override
    public Map getLoveLetterTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();

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

        if (triggerView.getUserEncodeKey() != null) {
            if (strAESKey == null) {
                //查询秘钥错误
                throw new Exception("10037");
            }
            String data = triggerView.getUserEncodeKey();
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            triggerView.setUserEncodeKey(outCode);
        }

        Map out = new HashMap();
        out.put("trigger", triggerView);
        return out;
    }
}
