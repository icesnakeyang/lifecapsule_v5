package cc.cdtime.lifecapsule.web.donate;

import cc.cdtime.lifecapsule.framework.common.ICommonService;
import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/donate")
public class WebDonateController {
    private final IWebDonateBService iWebDonateBService;
    private final ICommonService iCommonService;

    public WebDonateController(IWebDonateBService iWebDonateBService,
                               ICommonService iCommonService) {
        this.iWebDonateBService = iWebDonateBService;
        this.iCommonService = iCommonService;
    }

    @ResponseBody
    @GetMapping("/userClickDonate")
    public Response userClickDonate(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            logMap.put("UserActType", ESTags.USER_ACCESS_DONATE_PAGE);
            logMap.put("token", token);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("User access donate page error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("User access donate page user act error:" + ex3.getMessage());
        }
        return response;
    }
}
