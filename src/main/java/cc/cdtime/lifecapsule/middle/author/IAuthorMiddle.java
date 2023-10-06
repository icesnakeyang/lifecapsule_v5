package cc.cdtime.lifecapsule.middle.author;

import cc.cdtime.lifecapsule.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;

import java.util.ArrayList;
import java.util.Map;

public interface IAuthorMiddle {
    void createAuthorLog(AuthorLog authorLog) throws Exception;

    /**
     * 查询一个用户信息日志
     *
     * @param qIn userInfoLogId
     *            nickname
     *            userId
     *            status
     * @return
     * @throws Exception
     */
    AuthorLogView getAuthorLog(Map qIn) throws Exception;

    /**
     * 读取我的笔名列表
     *
     * @param qIn userId
     * @return
     */
    ArrayList<AuthorLogView> listAuthorLog(Map qIn) throws Exception;

    /**
     * 修改用户信息日志
     *
     * @param qIn status
     *            authorLogId
     */
    void updateAuthorLog(Map qIn) throws Exception;
}
