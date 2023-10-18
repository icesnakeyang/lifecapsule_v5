package cc.cdtime.lifecapsule.web.note;

import java.util.Map;

public interface IWebNoteBService {
    Map listMyNote(Map in) throws Exception;

    Map getMyNote(Map in) throws Exception;

    Map saveMyNote(Map in) throws Exception;

    /**
     * 用户物理删除一篇笔记
     *
     * @param in
     * @throws Exception
     */
    void deleteMyNote(Map in) throws Exception;

    /**
     * web端用户读取自己的note发送和接收统计信息
     * 未读note数
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map loadMyNoteSendStatistic(Map in) throws Exception;

    /**
     * 保存笔记标签的更改
     *
     * @param in
     * @throws Exception
     */
    void saveMyNoteTags(Map in) throws Exception;

    Map listLoveLetter(Map in) throws Exception;

    Map getLoveLetter(Map in) throws Exception;

    void saveLoveLetter(Map in) throws Exception;

    void deleteMyLoveLetter(Map in) throws Exception;
}
