package cc.cdtime.lifecapsule.business.author;

import java.util.Map;

public interface IAuthorBService {
    Map getAuthorInfo(Map in) throws Exception;

    Map listMyAuthorInfo(Map in) throws Exception;
}
