package cc.cdtime.lifecapsule.web.theme;

import cc.cdtime.lifecapsule.framework.vo.Response;
import cc.cdtime.lifecapsule.framework.vo.ThemeRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/theme")
public class WebThemeController {
    private final IWebThemeBService iWebThemeBService;

    public WebThemeController(IWebThemeBService iWebThemeBService) {
        this.iWebThemeBService = iWebThemeBService;
    }

    /**
     * web端用户查询主题颜色列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listTheme")
    public Response listTheme(@RequestBody ThemeRequest request,
                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("themeId", request.getThemeId());

            Map out = iWebThemeBService.listTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listTheme error:" + ex.getMessage());
            }
        }
        return response;
    }
}
