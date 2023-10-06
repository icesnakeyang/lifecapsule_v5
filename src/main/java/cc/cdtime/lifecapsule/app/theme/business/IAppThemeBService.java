package cc.cdtime.lifecapsule.app.theme.business;

import java.util.Map;

public interface IAppThemeBService {
    /**
     * 读取手机端主题列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTheme(Map in) throws Exception;

    /**
     * 读取手机端默认主题
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getDefaultTheme(Map in) throws Exception;
}
