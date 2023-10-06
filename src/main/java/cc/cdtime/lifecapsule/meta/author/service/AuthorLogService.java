package cc.cdtime.lifecapsule.meta.author.service;

import cc.cdtime.lifecapsule.meta.author.dao.AuthorLogDao;
import cc.cdtime.lifecapsule.meta.author.entity.AuthorLog;
import cc.cdtime.lifecapsule.meta.author.entity.AuthorLogView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AuthorLogService implements IAuthorLogService {
    private final AuthorLogDao authorLogDao;

    public AuthorLogService(AuthorLogDao authorLogDao) {
        this.authorLogDao = authorLogDao;
    }

    @Override
    public void createAuthorLog(AuthorLog authorLog) throws Exception {
        authorLogDao.createAuthorLog(authorLog);
    }

    @Override
    public AuthorLogView getAuthorLog(Map qIn) throws Exception {
        AuthorLogView authorLogView = authorLogDao.getAuthorLog(qIn);
        return authorLogView;
    }

    @Override
    public ArrayList<AuthorLogView> listAuthorLog(Map qIn) throws Exception {
        ArrayList<AuthorLogView> authorLogViews = authorLogDao.listAuthorLog(qIn);
        return authorLogViews;
    }

    @Override
    public void updateAuthorLog(Map qIn) throws Exception {
        authorLogDao.updateAuthorLog(qIn);
    }
}
