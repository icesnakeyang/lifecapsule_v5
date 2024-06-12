package cc.cdtime.lifecapsule.web.noteReceive;

import cc.cdtime.lifecapsule.framework.vo.InboxRequest;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/web/noteReceive")
public class WebNoteReceiveController {
    private final IWebNoteReceiveBService iWebNoteReceiveBService;

    public WebNoteReceiveController(IWebNoteReceiveBService iWebNoteReceiveBService) {
        this.iWebNoteReceiveBService = iWebNoteReceiveBService;
    }

    @ResponseBody
    @PostMapping("/replyReceiveNote")
    public Response replyReceiveNote(@RequestBody InboxRequest request,
                                     HttpServletRequest httpServletRequest){
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("pid", request.getPid());
            in.put("keyToken", request.getKeyToken());
            in.put("encryptKey", request.getEncryptKey());

            iWebNoteReceiveBService.replyReceiveNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App replyReceiveNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
