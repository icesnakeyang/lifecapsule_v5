package cc.cdtime.lifecapsule.app.theme.controller;

import cc.cdtime.lifecapsule.app.theme.business.IAppThemeBService;
import cc.cdtime.lifecapsule.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/app/theme")
public class AppThemeController {
    private final IAppThemeBService iAppThemeBService;

    public AppThemeController(IAppThemeBService iAppThemeBService) {
        this.iAppThemeBService = iAppThemeBService;
    }

    /**
     * 读取手机端主题列表
     */
    @ResponseBody
    @GetMapping("/listTheme")
    public Response listTheme() {
        Response response = new Response();
        Map in = new HashMap();
        try {
            Map out = iAppThemeBService.listTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listTheme error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取手机端默认主题
     */
    @ResponseBody
    @GetMapping("/getDefaultTheme")
    public Response getDefaultTheme() {
        Response response = new Response();
        Map in = new HashMap();
        try {
            Map out = iAppThemeBService.getDefaultTheme(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getDefaultTheme error:" + ex.getMessage());
            }
        }
        return response;
    }
}
