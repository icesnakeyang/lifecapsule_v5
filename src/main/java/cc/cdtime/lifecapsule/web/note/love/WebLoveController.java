package cc.cdtime.lifecapsule.web.note.love;

import cc.cdtime.lifecapsule.app.lastWords.LastWordsRequest;
import cc.cdtime.lifecapsule.app.loveLetter.LoveLetterRequest;
import cc.cdtime.lifecapsule.framework.common.ICommonService;
import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/note/love")
public class WebLoveController {
    private final IWebLoveBService iWebLoveBService;
    private final ICommonService iCommonService;

    public WebLoveController(IWebLoveBService iWebLoveBService,
                             ICommonService iCommonService) {
        this.iWebLoveBService = iWebLoveBService;
        this.iCommonService = iCommonService;
    }

    /**
     * Web用户查询自己的情书列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listLoveLetter")
    public Response listLoveLetter(@RequestBody LoveLetterRequest request,
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
            in.put("searchKey", request.getSearchKey());

            logMap.put("UserActType", ESTags.LIST_LOVE_LETTER);
            logMap.put("token", token);

            Map out = iWebLoveBService.listLoveLetter(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web listLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * Web用户查询自己的情书详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getLoveLetter")
    public Response getLoveLetter(@RequestBody LoveLetterRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            logMap.put("UserActType", ESTags.GET_LOVE_LETTER);
            logMap.put("token", token);

            Map out = iWebLoveBService.getLoveLetter(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web getLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户创建或者修改一封情书
     */
    @ResponseBody
    @PostMapping("/saveLoveLetter")
    public Response saveLoveLetter(@RequestBody LoveLetterRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("toEmail", request.getToEmail());
            in.put("content", request.getContent());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("title", request.getTitle());
            in.put("toName", request.getToName());
            in.put("fromName", request.getFromName());
            in.put("noteId", request.getNoteId());
            in.put("sendDateTime", request.getSendDateTime());
            in.put("triggerType", request.getTriggerType());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.SAVE_LOVE_LETTER);

            memoMap.put("title", request.getTitle());

            iWebLoveBService.saveLoveLetter(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web saveLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web saveLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户删除自己的情书
     */
    @ResponseBody
    @PostMapping("/deleteMyLoveLetter")
    public Response deleteMyLoveLetter(@RequestBody LastWordsRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.DELETE_LOVE_LETTER);
            memoMap.put("noteId", request.getNoteId());

            iWebLoveBService.deleteMyLoveLetter(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web deleteMyLoveLetter error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web deleteMyLoveLetter user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 用户查询一个情书的触发器
     */
    @ResponseBody
    @PostMapping("/getLoveLetterTrigger")
    public Response getLoveLetterTrigger(@RequestBody LoveLetterRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            logMap.put("token", token);
            logMap.put("UserActType", ESTags.GET_LOVE_LETTER_TRIGGER);
            memoMap.put("triggerId", request.getTriggerId());

            Map out = iWebLoveBService.getLoveLetterTrigger(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getLoveLetterTrigger error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web getLoveLetterTrigger user act error:" + ex3.getMessage());
        }
        return response;
    }
}
