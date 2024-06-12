package cc.cdtime.lifecapsule.app.tag;

import cc.cdtime.lifecapsule.framework.vo.Response;
import cc.cdtime.lifecapsule.framework.vo.TagRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/app/tag")
public class AppTagController {
    private final IAppTagBService iAppTagBService;

    public AppTagController(IAppTagBService iAppTagBService) {
        this.iAppTagBService = iAppTagBService;
    }

    /**
     * App端用户添加一个笔记标签
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/AddTagNote")
    public Response AddTagNote(@RequestBody TagRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("tagName", request.getTagName());
            in.put("noteId", request.getNoteId());

            iAppTagBService.AddTagNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App AddTagNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户读取一个笔记的所有标签
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listNoteTag")
    public Response listNoteTag(@RequestBody TagRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iAppTagBService.listNoteTag(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listNoteTag error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取用户的所有笔记标签列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/listUserNoteTag")
    public Response listUserNoteTag(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppTagBService.listUserNoteTag(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listUserNoteTag error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App端用户删除一个笔记标签
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/removeNoteTag")
    public Response removeNoteTag(@RequestBody TagRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("tagId", request.getTagId());

            iAppTagBService.removeNoteTag(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App removeNoteTag error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * App查询最热的10条笔记标签
     */
    @ResponseBody
    @GetMapping("/listHotNoteTags")
    public Response listHotNoteTags() {
        Response response = new Response();
        Map in = new HashMap();
        try {
            Map out = iAppTagBService.listHotNoteTags(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listHotNoteTags error:" + ex.getMessage());
            }
        }
        return response;
    }
}
