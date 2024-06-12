package cc.cdtime.lifecapsule.web.noteSend;

import cc.cdtime.lifecapsule.framework.vo.NoteSendRequest;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/noteSend")
public class WebNoteSendController {
    private final IWebNoteSendBService iWebNoteSendBService;

    public WebNoteSendController(IWebNoteSendBService iWebNoteSendBService) {
        this.iWebNoteSendBService = iWebNoteSendBService;
    }

    /**
     * web端用户读取自己接收的记录列表
     * 未读note数
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyNoteReceiveLog")
    public Response listMyNoteSendLogReceive(@RequestBody NoteSendRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out=iWebNoteSendBService.listMyNoteReceiveLog(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyNoteReceiveLog error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * web端用户读取自己发送的记录列表
     * 未读note数
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyNoteSendOutLog")
    public Response listMyNoteSendOutLog(@RequestBody NoteSendRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out=iWebNoteSendBService.listMyNoteSendOutLog(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web listMyNoteSendOutLog error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * Web端用户读取一个发送的笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyNoteSendOutLog")
    public Response getMyNoteSendOutLog(@RequestBody NoteSendRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("sendLogId", request.getSendLogId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out=iWebNoteSendBService.getMyNoteSendOutLog(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyNoteSendOutLog error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * Web端用户读取一个收到的笔记详情
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyReceiveNote")
    public Response getMyReceiveNote(@RequestBody NoteSendRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("sendLogId", request.getSendLogId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out=iWebNoteSendBService.getMyReceiveNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Web getMyReceiveNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
