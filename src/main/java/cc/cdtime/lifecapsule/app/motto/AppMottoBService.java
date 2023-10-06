package cc.cdtime.lifecapsule.app.motto;

import cc.cdtime.lifecapsule.business.motto.IMottoBService;
import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.motto.entity.Motto;
import cc.cdtime.lifecapsule.meta.motto.entity.MottoView;
import cc.cdtime.lifecapsule.middle.motto.IMottoMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppMottoBService implements IAppMottoBService {
    private final IMottoBService iMottoBService;
    private final IMottoMiddle iMottoMiddle;

    public AppMottoBService(IMottoBService iMottoBService,
                            IMottoMiddle iMottoMiddle) {
        this.iMottoBService = iMottoBService;
        this.iMottoMiddle = iMottoMiddle;
    }

    @Override
    public void publishMotto(Map in) throws Exception {
        iMottoBService.publishMotto(in);
    }

    @Override
    public Map getMottoRandom(Map in) throws Exception {
        Map out = iMottoBService.getMottoRandom(in);

        /**
         * 刷新motto的阅读次数
         */
        MottoView mottoView = (MottoView) out.get("motto");
        if(mottoView!=null) {
            Integer views = mottoView.getViews() + 1;
            Map qIn = new HashMap();
            qIn.put("views", views);
            qIn.put("mottoId", mottoView.getMottoId());
            iMottoMiddle.updateMotto(qIn);
        }

        return out;
    }

    @Override
    public void likeMotto(Map in) throws Exception {
        iMottoBService.likeMotto(in);
    }
}
