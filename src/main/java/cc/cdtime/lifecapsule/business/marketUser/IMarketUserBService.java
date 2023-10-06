package cc.cdtime.lifecapsule.business.marketUser;

import cc.cdtime.lifecapsule.meta.market.entity.MarketUserView;

import java.util.Map;

public interface IMarketUserBService {
    /**
     * 创建一个市场运营人员
     */
    void createMarketUser(Map in) throws Exception;

    /**
     * 查询一个市场运营人员账号
     */
    Map marketUserLogin(Map in) throws Exception;
}
