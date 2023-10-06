package cc.cdtime.lifecapsule.middle.recipient;

import cc.cdtime.lifecapsule.meta.recipient.entity.NoteRecipient;
import cc.cdtime.lifecapsule.meta.recipient.entity.RecipientView;

import java.util.ArrayList;
import java.util.Map;

public interface IRecipientMiddle {
    /**
     * 创建一个笔记的接收人
     *
     * @param noteRecipient
     */
    void createNoteRecipient(NoteRecipient noteRecipient) throws Exception;

    /**
     * 查询接收人列表
     *
     * @param qIn noteId
     * @return
     */
    ArrayList<RecipientView> listNoteRecipient(Map qIn) throws Exception;

    /**
     * 查询一个接收人信息
     *
     * @param recipientId
     * @return
     */
    RecipientView getRecipient(String recipientId, Boolean returnNull) throws Exception;

    RecipientView getRecipientTiny(String recipientId, Boolean returnNull, String userId) throws Exception;

    RecipientView getRecipient(String recipientId, Boolean returnNull, String userId) throws Exception;

    /**
     * 删除一个接收人
     *
     * @param qIn recipientId
     *            noteId
     */
    void deleteNoteRecipient(Map qIn) throws Exception;

    /**
     * 修改接收人
     *
     * @param qIn name
     *            phone
     *            email
     *            remark
     *            status
     *            recipientId
     */
    void updateNoteRecipient(Map qIn) throws Exception;
}
