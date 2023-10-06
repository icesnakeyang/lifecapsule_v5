package cc.cdtime.lifecapsule.web.project;

import java.util.Map;

public interface IWebProjectBService {
    void saveMyProject(Map in) throws Exception;

    Map listMyProject(Map in) throws Exception;

    Map getProject(Map in) throws Exception;
}
