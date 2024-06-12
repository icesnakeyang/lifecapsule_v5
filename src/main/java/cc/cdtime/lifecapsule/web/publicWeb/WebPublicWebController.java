package cc.cdtime.lifecapsule.web.publicWeb;

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
@RequestMapping("/lifecapsule_api/web/webpublicweb")
public class WebPublicWebController {
    private final IWebPublicWebBService iWebPublicWebBService;
    private final ICommonService iCommonService;

    public WebPublicWebController(IWebPublicWebBService iWebPublicWebBService,
                                  ICommonService iCommonService) {
        this.iWebPublicWebBService = iWebPublicWebBService;
        this.iCommonService = iCommonService;
    }

    /**
     * Web端用户把笔记发布到公共网页上
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/publishNoteToPublicWeb")
    public Response publishNoteToPublicWeb(@RequestBody PublicWebRequest request,
                                           HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("title", request.getTitle());
            in.put("authorName", request.getAuthorName());
            in.put("content", request.getContent());

            logMap.put("UserActType", ESTags.PUBLISH_NOTE_PUBLIC_WEB);
            logMap.put("token", token);
            memoMap.put("title", request.getTitle());

            iWebPublicWebBService.publishNoteToPublicWeb(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web publishNoteToPublicWeb error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web publishNoteToPublicWeb user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * Web端用户查询我的公开笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyPublicNote")
    public Response listMyPublicNote(@RequestBody PublicWebRequest request,
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

            logMap.put("UserActType", ESTags.LIST_PUBLISH_NOTE);
            logMap.put("token", token);
            memoMap.put("title", request.getTitle());

            Map out = iWebPublicWebBService.listMyPublicNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyPublicNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web listMyPublicNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * Web端用户查询我的公开笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyPublicNote")
    public Response getMyPublicNote(@RequestBody PublicWebRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            logMap.put("UserActType", ESTags.GET_PUBLISH_NOTE);
            logMap.put("token", token);
            memoMap.put("noteId", request.getNoteId());

            Map out = iWebPublicWebBService.getMyPublicNote(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyPublicNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web getMyPublicNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * Web端用户修改我的公开笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyPublicNote")
    public Response updateMyPublicNote(@RequestBody PublicWebRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("title", request.getTitle());
            in.put("content", request.getContent());

            logMap.put("UserActType", ESTags.UPDATE_PUBLISH_NOTE);
            logMap.put("token", token);
            memoMap.put("noteId", request.getNoteId());
            memoMap.put("title", request.getTitle());

            iWebPublicWebBService.updateMyPublicNote(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web updateMyPublicNote error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web updateMyPublicNote user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * 互联网公共用户查看笔记详情
     */
    @ResponseBody
    @GetMapping("/getArticle/{articleId}")
    public Response getPublicNote(@PathVariable("articleId") String articleId) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            in.put("noteId", articleId);

            logMap.put("UserActType", ESTags.VIEW_PUBLIC_ARTICLE);
            memoMap.put("noteId", articleId);

            Map out = iWebPublicWebBService.getArticle(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getArticle error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("Web getArticle user act error:" + ex3.getMessage());
        }
        return response;
    }
}
