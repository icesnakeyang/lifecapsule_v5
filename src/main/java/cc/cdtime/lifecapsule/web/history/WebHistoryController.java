package cc.cdtime.lifecapsule.web.history;


import cc.cdtime.lifecapsule.framework.vo.HistoryRequest;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/history")
public class WebHistoryController {
    private final IWebHistoryBService iWebHistoryBService;

    public WebHistoryController(IWebHistoryBService iWebHistoryBService) {
        this.iWebHistoryBService = iWebHistoryBService;
    }

    /**
     * Web端查询我的历史首页需要显示的数据
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loadHistoryHome")
    public Response loadHistoryHome(@RequestBody HistoryRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("searchKey", request.getSearchKey());

            Map out = iWebHistoryBService.loadHistoryHome(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web loadHistoryHome error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * Web端用户回复自己的笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/replyMyNote")
    public Response replyMyNote(@RequestBody HistoryRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pid", request.getPid());
            in.put("content", request.getContent());
            in.put("title", request.getTitle());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            iWebHistoryBService.replyMyNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web replyMyNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * Web端用户读取自己的笔记的所有历史信息
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listSubNoteList")
    public Response listSubNoteList(@RequestBody HistoryRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iWebHistoryBService.listSubNoteList(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listSubNoteList error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * Web端用户读取自己的笔记的父笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyPNote")
    public Response getMyPNote(@RequestBody HistoryRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iWebHistoryBService.getMyPNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyPNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
