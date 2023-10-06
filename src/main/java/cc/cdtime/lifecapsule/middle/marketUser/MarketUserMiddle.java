package cc.cdtime.lifecapsule.middle.marketUser;

import cc.cdtime.lifecapsule.meta.market.entity.MarketUser;
import cc.cdtime.lifecapsule.meta.market.entity.MarketUserView;
import cc.cdtime.lifecapsule.meta.market.service.IMarketUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MarketUserMiddle implements IMarketUserMiddle {
    private final IMarketUserService iMarketUserService;

    public MarketUserMiddle(IMarketUserService iMarketUserService) {
        this.iMarketUserService = iMarketUserService;
    }

    @Override
    public void createMarketUser(MarketUser marketUser) throws Exception {
        iMarketUserService.createMarketUser(marketUser);
    }

    @Override
    public MarketUserView getMarketUser(Map qIn) throws Exception {
        MarketUserView marketUserView = iMarketUserService.getMarketUser(qIn);
        return marketUserView;
    }
}
