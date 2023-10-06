package cc.cdtime.lifecapsule.app.author;


import java.util.Map;

public interface IAppAuthorBService {
    Map getMyAuthorInfo(Map in) throws Exception;

    Map listMyAuthorInfo(Map in) throws Exception;
}
