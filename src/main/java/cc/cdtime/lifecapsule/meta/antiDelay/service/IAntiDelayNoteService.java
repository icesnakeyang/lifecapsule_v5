package cc.cdtime.lifecapsule.meta.antiDelay.service;

import cc.cdtime.lifecapsule.meta.antiDelay.entity.AntiDelayNote;
import cc.cdtime.lifecapsule.meta.antiDelay.entity.AntiDelayView;

import java.util.ArrayList;
import java.util.Map;

public interface IAntiDelayNoteService {
    /**
     * 创建一条方拖延笔记内容
     *
     * @param antiDelayNote
     */
    void createAntiDelayNote(AntiDelayNote antiDelayNote) throws Exception;

    /**
     * 读取防拖延笔记的子项内容列表
     *
     * @param qIn userId
     *            noteId
     * @return
     */
    ArrayList<AntiDelayView> listAntiDelayNote(Map qIn) throws Exception;

    AntiDelayView getAntiDelayNote(String antiDelayId)throws Exception;

    /**
     * 物理删除防拖延笔记
     * 按照noteId，删除所有的子内容
     * @param noteId
     */
    void deleteAntiDelayNote(String noteId) throws Exception;

    /**
     * 读取一个方拖延笔记的详细内容
     *
     * @param qIn userId
     *            antiDelayType
     * @return
     */
    AntiDelayView loadAntiDelayNote(Map qIn) throws Exception;
}
