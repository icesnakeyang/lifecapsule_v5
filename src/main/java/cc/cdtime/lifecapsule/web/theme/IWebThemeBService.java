package cc.cdtime.lifecapsule.web.theme;

import java.util.Map;

public interface IWebThemeBService {
    /**
     * web端用户查询主题颜色列表
     * @param in
     * @return
     * @throws Exception
     */
    Map listTheme(Map in) throws Exception;
}
