package cc.cdtime.lifecapsule.web.timer;

import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/timer")
public class WebTimerController {
    private final IWebTimerBService iWebTimerBService;

    public WebTimerController(IWebTimerBService iWebTimerBService) {
        this.iWebTimerBService = iWebTimerBService;
    }

    /**
     * 用户重置主倒计时
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/snooze")
    public Response snooze(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out=iWebTimerBService.snooze(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web snooze error:" + ex.getMessage());
            }
        }
        return response;
    }
}
