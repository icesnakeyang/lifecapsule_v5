package cc.cdtime.lifecapsule.meta.market.service;

import cc.cdtime.lifecapsule.meta.market.entity.MarketUser;
import cc.cdtime.lifecapsule.meta.market.entity.MarketUserView;

import java.util.Map;

public interface IMarketUserService {
    /**
     * 创建一个市场运营人员
     *
     * @param marketUser
     */
    void createMarketUser(MarketUser marketUser) throws Exception;

    /**
     * 查询一个市场运营人员账号
     *
     * @param qIn marketUserId
     *            token
     *            loginName
     *            loginPassword
     * @return
     */
    MarketUserView getMarketUser(Map qIn) throws Exception;
}
