package cc.cdtime.lifecapsule.app.cash;

import cc.cdtime.lifecapsule.business.cash.ICashBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppCashBService implements IAppCashBService {
    private final ICashBService iCashBService;

    public AppCashBService(ICashBService iCashBService) {
        this.iCashBService = iCashBService;
    }

    @Override
    public void createLedger(Map in) throws Exception {
        iCashBService.createLedger(in);
    }

    @Override
    public Map getMyDefaultCashCategory(Map in) throws Exception {
        Map out = iCashBService.getMyDefaultCashCategory(in);
        return out;
    }

    @Override
    public Map listMyCashCategory(Map in) throws Exception {
        Map out = iCashBService.listMyCashCategory(in);
        return out;
    }

    @Override
    public void createMyCashCategory(Map in) throws Exception {
        iCashBService.createMyCashCategory(in);
    }

    @Override
    public Map listMyCashLedger(Map in) throws Exception {
        Map out = iCashBService.listMyCashLedger(in);
        return out;
    }

    @Override
    public Map getMyCashAccount(Map in) throws Exception {
        Map out = iCashBService.getMyCashAccount(in);
        return out;
    }

    @Override
    public Map getMyCashCategory(Map in) throws Exception {
        Map out = iCashBService.getMyCashCategory(in);
        return out;
    }

    @Override
    public void updateMyCashCategory(Map in) throws Exception {
        iCashBService.updateCashCategory(in);
    }

    @Override
    public Map getMyCashLedger(Map in) throws Exception {
        Map out = iCashBService.getCashLedger(in);
        return out;
    }

    @Override
    public void updateMyCashLedger(Map in) throws Exception {
        iCashBService.updateMyCashLedger(in);
    }

    @Override
    public Map statisticCashLedgerMonth(Map in) throws Exception {
        Map out = iCashBService.statisticCashLedgerMonth(in);
        return out;
    }
}
