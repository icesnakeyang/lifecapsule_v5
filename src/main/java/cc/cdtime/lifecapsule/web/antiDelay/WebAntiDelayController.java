package cc.cdtime.lifecapsule.web.antiDelay;

import cc.cdtime.lifecapsule.framework.common.ICommonService;
import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.vo.AntiDelayRequest;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/anti_delay")
public class WebAntiDelayController {
    private final ICommonService iCommonService;
    private final IWebAntiDelayBService iWebAntiDelayBService;

    public WebAntiDelayController(IWebAntiDelayBService iWebAntiDelayBService,
                                  ICommonService iCommonService) {
        this.iWebAntiDelayBService = iWebAntiDelayBService;
        this.iCommonService = iCommonService;
    }

    /**
     * web端用户读取自己的防拖延笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyAntiDelayNote")
    public Response listMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_LIST_ANTI_DELAY_NOTE);

            Map out = iWebAntiDelayBService.listMyAntiDelayNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user listMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web user listMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * web端用户读取一个防拖延笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyAntiDelayNote")
    public Response getMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_GET_ANTI_DELAY_NOTE);
            memoMap.put("noteId", request.getNoteId());

            Map out = iWebAntiDelayBService.getMyAntiDelayNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user getMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web user getMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * web端用户读取上一次防拖延笔记的内容作为预设内容
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadLastMyAntiDelayNote")
    public Response loadLastMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                            HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out = iWebAntiDelayBService.loadLastMyAntiDelayNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user loadLastMyAntiDelayNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户创建自己的防拖延笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createMyAntiDelayNote")
    public Response createMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("tasks", request.getTasks());
            in.put("happyYesterday", request.getHappyYesterday());
            in.put("longGoal", request.getLongGoal());
            in.put("todayGoal", request.getTodayGoal());
            in.put("title", request.getTitle());
            in.put("myThought", request.getMyThought());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_CREATE_ANTI_DELAY_NOTE);
            memoMap.put("title", request.getTitle());

            iWebAntiDelayBService.createMyAntiDelayNote(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user createMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web user createMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * web端用户修改自己的防拖延笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyAntiDelayNote")
    public Response updateMyAntiDelayNote(@RequestBody AntiDelayRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("tasks", request.getTasks());
            in.put("happyYesterday", request.getHappyYesterday());
            in.put("longGoal", request.getLongGoal());
            in.put("todayGoal", request.getTodayGoal());
            in.put("title", request.getTitle());
            in.put("myThought", request.getMyThought());
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.USER_UPDATE_ANTI_DELAY_NOTE);
            memoMap.put("noteId", request.getNoteId());

            iWebAntiDelayBService.updateMyAntiDelayNote(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web user updateMyAntiDelayNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web user updateMyAntiDelayNote user act error:" + ex3.getMessage());
        }
        return response;
    }
}
