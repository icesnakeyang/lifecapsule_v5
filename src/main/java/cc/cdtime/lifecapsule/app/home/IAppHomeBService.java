package cc.cdtime.lifecapsule.app.home;

import java.util.Map;

public interface IAppHomeBService {
    /**
     * App端用户首页查询统计数据
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map loadMyNoteSendStatistic(Map in) throws Exception;
}
