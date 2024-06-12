package cc.cdtime.lifecapsule.business.tag;

import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.note.entity.NoteView;
import cc.cdtime.lifecapsule.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.note.INoteMiddle;
import cc.cdtime.lifecapsule.middle.tag.ITagMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TagBService implements ITagBService {
    private final IUserMiddle iUserMiddle;
    private final ITagMiddle iTagMiddle;
    private final INoteMiddle iNoteMiddle;

    public TagBService(IUserMiddle iUserMiddle,
                       ITagMiddle iTagMiddle,
                       INoteMiddle iNoteMiddle) {
        this.iUserMiddle = iUserMiddle;
        this.iTagMiddle = iTagMiddle;
        this.iNoteMiddle = iNoteMiddle;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void AddTagNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String tagName = in.get("tagName").toString();
        String noteId = in.get("noteId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        NoteView noteView = iNoteMiddle.getNoteTiny(noteId, false, userView.getUserId());

        /**
         * 首先检查tagName是否已经在笔记里了
         */
        qIn = new HashMap();
        qIn.put("tagName", tagName);
        qIn.put("noteId", noteId);
        ArrayList<TagView> noteTags = iTagMiddle.listNoteTag(qIn);
        if (noteTags.size() > 0) {
            //该标签已经在添加到该笔记里了
            throw new Exception("10056");
        }

        /**
         * 查询标签是否在标签库里
         */

        TagView tagView = iTagMiddle.getTagBase(tagName, true);

        if (tagView != null) {
            /**
             * 有标签，直接创建tagNote
             */
            TagNote tagNote = new TagNote();
            tagNote.setNoteId(noteId);
            tagNote.setTagId(tagView.getTagId());
            tagNote.setCreateTime(new Date());
            iTagMiddle.createTagNote(tagNote);

            /**
             * 刷新tagHot
             */
            qIn = new HashMap();
            qIn.put("tagHot", tagView.getTagHot() + 1);
            qIn.put("tagId", tagView.getTagId());
            iTagMiddle.updateTagBase(qIn);
        } else {
            /**
             * 没有标签，添加到标签库
             */
            TagBase tagBase = new TagBase();
            tagBase.setTagId(GogoTools.UUID32());
            tagBase.setTagName(tagName);
            iTagMiddle.createTagBase(tagBase);

            /**
             * 添加到tagNote
             */
            TagNote tagNote = new TagNote();
            tagNote.setNoteId(noteId);
            tagNote.setTagId(tagBase.getTagId());
            tagNote.setCreateTime(new Date());
            iTagMiddle.createTagNote(tagNote);
        }
    }

    @Override
    public Map listNoteTag(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);
        ArrayList<TagView> noteTags = null;
        if (noteId != null) {
            /**
             * 读取一个笔记的所有tag
             */
            qIn = new HashMap();
            qIn.put("noteId", noteId);
            noteTags = iTagMiddle.listNoteTag(qIn);
        } else {
            /**
             * 读取用户的所有tag
             */
            qIn = new HashMap();
            qIn.put("userId", userView.getUserId());
            noteTags = iTagMiddle.listNoteTag(qIn);
        }


        Map out = new HashMap();
        out.put("tagList", noteTags);
        return out;
    }

    @Override
    public void removeNoteTag(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String tagId = in.get("tagId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("tagId", tagId);
        qIn.put("noteId", noteId);
        iTagMiddle.deleteTagNote(qIn);
    }

    @Override
    public Map listHotNoteTags(Map in) throws Exception {
        Map qIn = new HashMap();
        qIn.put("size", 10);
        ArrayList<TagView> tagViews = iTagMiddle.listBaseTag(qIn);

        Map out = new HashMap();
        out.put("tagList", tagViews);
        return out;
    }

    @Override
    public Map listUserNoteTag(Map in) throws Exception {
        String token = in.get("token").toString();
        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        ArrayList<TagView> tagViews = iTagMiddle.listTagGroup(qIn);

        Map out = new HashMap();
        out.put("tagList", tagViews);
        return out;
    }
}
