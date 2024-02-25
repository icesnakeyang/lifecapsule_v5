package cc.cdtime.lifecapsule.business.note;

import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.note.entity.NoteInfo;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.noteSendLog.entity.NoteSendLogView;
import cc.cdtime.lifecapsule.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule.middle.noteSend.INoteSendMiddle;
import cc.cdtime.lifecapsule.middle.security.ISecurityMiddle;
import cc.cdtime.lifecapsule.middle.tag.ITagMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoteBService implements INoteBService {
    private final IUserMiddle iUserMiddle;
    private final INoteMiddle iNoteMiddle;
    private final ISecurityMiddle iSecurityMiddle;
    private final ITagMiddle iTagMiddle;
    private final INoteSendMiddle iNoteSendMiddle;

    public NoteBService(IUserMiddle iUserMiddle,
                        INoteMiddle iNoteMiddle,
                        ISecurityMiddle iSecurityMiddle,
                        ITagMiddle iTagMiddle,
                        INoteSendMiddle iNoteSendMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iNoteMiddle = iNoteMiddle;
        this.iSecurityMiddle = iSecurityMiddle;
        this.iTagMiddle = iTagMiddle;
        this.iNoteSendMiddle = iNoteSendMiddle;
    }

    @Override
    public Map listNote(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        ArrayList tagList = (ArrayList) in.get("tagList");
        String searchKey = (String) in.get("searchKey");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * 读取当前登录用户，如果未登录，则返回错误
         */
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        if (tagList != null && tagList.size() > 0) {
            qIn.put("tagList", tagList);
        }
        if (searchKey != null && !searchKey.equals("")) {
            qIn.put("searchKey", searchKey);
        }
        ArrayList<NoteView> noteViews = iNoteMiddle.listNote(qIn);
        Integer total = iNoteMiddle.totalNote(qIn);
        Map out = new HashMap();
        out.put("noteList", noteViews);
        out.put("totalNote", total);

        return out;
    }

    @Override
    public Map getMyNote(Map in) throws Exception {
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
            noteView.setUserEncodeKey(outCode);
        } else {
            noteView.setUserEncodeKey(null);
        }

        /**
         * 如果有父笔记，就读取父笔记标题
         */
        if (noteView.getPid() != null) {
            NoteView pNote = iNoteMiddle.getNoteTiny(noteView.getPid(), true, userView.getUserId());
            if (pNote != null) {
                noteView.setPTitle(pNote.getTitle());
            }
        }

        /**
         * 查询是否有回复的笔记
         */


        /**
         * 读取笔记标签
         */
        qIn = new HashMap();
        qIn.put("noteId", noteView.getNoteId());
        ArrayList<TagView> tagViews = iTagMiddle.listNoteTag(qIn);

        Map out = new HashMap();
        out.put("note", noteView);
        out.put("noteTagList", tagViews);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map saveNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String title = (String) in.get("title");
        String content = (String) in.get("content");
        String encryptKey = (String) in.get("encryptKey");
        Boolean encrypt = (Boolean) in.get("encrypt");
        String keyToken = (String) in.get("keyToken");
        ArrayList tagList = (ArrayList) in.get("tagList");
        String pid = (String) in.get("pid");

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = null;
        if (encrypt) {
            String privateKey = iSecurityMiddle.getRSAKey(keyToken);
            strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
            iSecurityMiddle.deleteRSAKey(keyToken);
        }

        /**
         * 首先读取用户信息
         */
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        if (pid != null) {
            NoteView pNote = iNoteMiddle.getNoteTiny(pid, true, null);
            if (pNote == null) {
                // 父笔记不存在
                throw new Exception("10087");
            }
            if (!pNote.getUserId().equals(userView.getUserId())) {
                //父笔记不是自己的笔记
                throw new Exception("10088");
            }

        }
        in.put("userId", userView.getUserId());
        if (noteId == null) {
            /**
             * 新增
             */
            if (strAESKey != null) {
                in.put("strAESKey", strAESKey);
            }
            noteId = createNote(in);

        } else {
            /**
             * 检查note是否当前用户的
             */
            NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());
            /**
             * 修改
             */
            in.put("strAESKey", strAESKey);
            updateNote(in);
        }

        /**
         * 保存tagList
         */
        if (tagList != null && tagList.size() > 0) {
            /**
             * 删除掉原来所有tag，再添加tag
             */
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            iTagMiddle.deleteTagNote(qIn);
            for (int i = 0; i < tagList.size(); i++) {
                Map tagMap = (Map) tagList.get(i);
                if (tagMap != null) {
                    String tagName = (String) tagMap.get("tagName");
                    if (tagName != null) {
                        saveTag(tagName, noteId, userView.getUserId());
                    }
                }
            }
        }


        Map out = new HashMap();
        out.put("noteId", noteId);
        return out;
    }

    @Override
    public Map totalNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String keyword = (String) in.get("keyword");

        Map qIn = new HashMap();
        qIn.put("token", token);
        /**
         * 读取当前登录用户，如果未登录，则返回错误
         */
        UserView userView = iUserMiddle.getUserLogin(qIn, false);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        if (keyword != null && !keyword.equals("")) {
            qIn.put("keyword", keyword);
        }
        Integer total = iNoteMiddle.totalNote(qIn);

        Map out = new HashMap();
        out.put("totalNote", total);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        iNoteMiddle.deleteNote(noteId);
    }

    @Override
    public Map getNoteTiny(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        Map note = new HashMap();
        note.put("noteId", noteView.getNoteId());
        note.put("categoryName", noteView.getCategoryName());
        note.put("createTime", noteView.getCreateTime());
        note.put("title", noteView.getTitle());
        Map out = new HashMap();
        out.put("note", note);

        return out;
    }

    @Override
    public void replyNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();
        String pid = (String) in.get("pid");
        String sengLogId = (String) in.get("sendLogId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (pid != null) {
            /**
             * 回复自己的笔记
             * 直接创建一个笔记，指定pid
             */
        } else {
            /**
             * 回复别人的笔记
             * 创建笔记，类型为回复，指定sendLogId
             */

        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMyNoteTags(Map in) throws Exception {
        String token = in.get("token").toString();
        ArrayList tagList = (ArrayList) in.get("tagList");
        String noteId = in.get("noteId").toString();

        if (tagList == null) {
            return;
        }
        if (tagList.size() == 0) {
            /**
             * 把该笔记的所有tag删除
             */
            Map qIn = new HashMap();
            qIn.put("noteId", noteId);
            iTagMiddle.deleteTagNote(qIn);
            return;
        }

        if (noteId == null) {
            return;
        }

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * 删除掉原来所有tag，再添加tag
         */
        qIn = new HashMap();
        qIn.put("noteId", noteId);
        iTagMiddle.deleteTagNote(qIn);
        for (int i = 0; i < tagList.size(); i++) {
            Map tagMap = (Map) tagList.get(i);
            String tagName = (String) tagMap.get("tagName");
            if (tagName != null) {
                saveTag(tagName, noteId, userView.getUserId());
            }
        }
    }

    @Override
    public Map loadNoteAllData(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取一个笔记的所有详情信息
         */

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map out = new HashMap();

        /**
         * 笔记详情
         */
        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userView.getUserId());

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = null;
        if (keyToken != null) {
            strAESKey = iSecurityMiddle.takeNoteAES(keyToken, encryptKey);
        }
        if (strAESKey == null) {
            //查询秘钥错误
            throw new Exception("10037");
        }
        if (noteView.getUserEncodeKey() != null) {
            String data = noteView.getUserEncodeKey();
            //用AES秘钥加密笔记内容的AES秘钥
            String outCode = GogoTools.encryptAESKey(data, strAESKey);
            noteView.setUserEncodeKey(outCode);
        } else {
            noteView.setUserEncodeKey(null);
        }
        out.put("noteDetail", noteView);

        /**
         * 父笔记detail
         */
        if (noteView.getPid() != null) {
            NoteView noteParent = noteParent = iNoteMiddle.getNoteDetail(noteView.getPid(), true, null);
            if (noteParent == null) {
                qIn = new HashMap();
                qIn.put("sendLogId", noteView.getPid());
                NoteSendLogView noteSendLogView = iNoteSendMiddle.getNoteSendLog(qIn, true, null);
                if (noteSendLogView.getUserEncodeKey() != null) {
                    String data = noteSendLogView.getUserEncodeKey();
                    //用AES秘钥加密笔记内容的AES秘钥
                    String outCode = GogoTools.encryptAESKey(data, strAESKey);
                    noteSendLogView.setUserEncodeKey(outCode);
                } else {
                    noteSendLogView.setUserEncodeKey(null);
                }
                Map map=new HashMap();
                map.put("sendLogId", noteSendLogView.getSendLogId());
                map.put("sendTime", noteSendLogView.getSendTime());
                map.put("title", noteSendLogView.getTitle());
                map.put("toEmail", noteSendLogView.getToEmail());
                map.put("toName", noteSendLogView.getToName());
                map.put("triggerType", noteSendLogView.getTriggerType());
                map.put("readTime", noteSendLogView.getReadTime());
                map.put("fromName", noteSendLogView.getFromName());
                map.put("noteType", ESTags.NOTE_SEND_LOG);
                out.put("noteParent", map);
            } else {
                if (noteParent.getUserEncodeKey() != null) {
                    String data = noteParent.getUserEncodeKey();
                    //用AES秘钥加密笔记内容的AES秘钥
                    String outCode = GogoTools.encryptAESKey(data, strAESKey);
                    noteParent.setUserEncodeKey(outCode);
                } else {
                    noteParent.setUserEncodeKey(null);
                }
                out.put("noteParent", noteParent);
            }
        }
        /**
         * 子笔记树列表
         */
        qIn = new HashMap();
        qIn.put("pid", noteView.getNoteId());
        ArrayList<NoteView> noteChild = iNoteMiddle.listHistoryNote(qIn);
        out.put("child", noteChild);
        /**
         * 触发器列表
         */

        /**
         * 接收事件
         */
        return out;
    }

    private String createNote(Map in) throws Exception {
        String content = in.get("content").toString();
        String userId = in.get("userId").toString();
        String strAESKey = (String) in.get("strAESKey");
        String title = (String) in.get("title");
        Boolean encrypt = (Boolean) in.get("encrypt");
        String pid = (String) in.get("pid");

        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setNoteId(GogoTools.UUID32());
        noteInfo.setContent(content);
        if (encrypt != null && !encrypt) {
            noteInfo.setEncrypt(0);
        } else {
            noteInfo.setEncrypt(1);
        }
        noteInfo.setUserEncodeKey(strAESKey);
        noteInfo.setCreateTime(new Date());
        noteInfo.setStatus(ESTags.ACTIVE.toString());
        noteInfo.setUserId(userId);
        noteInfo.setTitle(title);
        noteInfo.setPid(pid);
        iNoteMiddle.createNoteInfo(noteInfo);
        return noteInfo.getNoteId();
    }

    private void updateNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = (String) in.get("title");
        String content = (String) in.get("content");
        String encryptKey = (String) in.get("encryptKey");
        String keyToken = (String) in.get("keyToken");
        String userId = in.get("userId").toString();
        String strAESKey = (String) in.get("strAESKey");
        Boolean encrypt = (Boolean) in.get("encrypt");
        String pid = (String) in.get("pid");

        /**
         * 读取笔记信息，检查笔记是否存在，是否是用户本人的笔记
         */
        NoteView noteView = iNoteMiddle.getNoteDetail(noteId, false, userId);

        /**
         * 检查有没有要修改的属性
         */
        Map qInEdit = new HashMap();
        if (noteView.getTitle() != null) {
            if (title != null) {
                if (!noteView.getTitle().equals(title)) {
                    qInEdit.put("title", title);
                }
            }
        } else {
            if (title != null) {
                qInEdit.put("title", title);
            }
        }
        qInEdit.put("encodeKey", strAESKey);
        if (encrypt) {
            qInEdit.put("encrypt", 1);
        } else {
            qInEdit.put("encrypt", 0);
        }

        if (noteView.getContent() != null) {
            /**
             * 旧笔记里已经有详情内容了
             * 比较是否一致，如果一样，就不处理，不一样，才修改
             */
            if (!noteView.getContent().equals(content)) {
                qInEdit.put("content", content);
            }
        } else {
            /**
             * 还没有笔记内容
             */
            qInEdit.put("content", content);
        }

        qInEdit.put("noteId", noteId);
        qInEdit.put("pid", pid);
        iNoteMiddle.updateNoteInfo(qInEdit);
    }

    private void saveTag(String tagName, String noteId, String userId) throws Exception {
        /**
         * 新增加的tag
         * 1、检查tagName是否存在，不存在就创建tagBase
         * 2、获得tagId，创建到tagNote
         */
        TagView tagView = iTagMiddle.getTagBase(tagName, true);
        if (tagView == null) {
            TagBase tagBase = new TagBase();
            tagBase.setTagName(tagName);
            tagBase.setTagId(GogoTools.UUID32());
            iTagMiddle.createTagBase(tagBase);
            TagNote tagNote = new TagNote();
            tagNote.setNoteId(noteId);
            tagNote.setCreateTime(new Date());
            tagNote.setTagId(tagBase.getTagId());
            tagNote.setUserId(userId);
            iTagMiddle.createTagNote(tagNote);
        } else {
            TagNote tagNote = new TagNote();
            tagNote.setNoteId(noteId);
            tagNote.setCreateTime(new Date());
            tagNote.setTagId(tagView.getTagId());
            tagNote.setUserId(userId);
            iTagMiddle.createTagNote(tagNote);
            /**
             * 增加tagHot热度
             */
            Map qIn = new HashMap();
            if (tagView.getTagHot() == null) {
                qIn.put("tagHot", 1);
            } else {
                qIn.put("tagHot", tagView.getTagHot() + 1);
            }
            qIn.put("tagId", tagView.getTagId());
            iTagMiddle.updateTagBase(qIn);
        }
    }
}
