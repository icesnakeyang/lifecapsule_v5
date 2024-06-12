package cc.cdtime.lifecapsule.admin.note;

import cc.cdtime.lifecapsule.framework.vo.NoteRequest;
import cc.cdtime.lifecapsule.framework.vo.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/admin/note")
public class AdminNoteController {
    private final IAdminNoteBService iAdminNoteBService;

    public AdminNoteController(IAdminNoteBService iAdminNoteBService) {
        this.iAdminNoteBService = iAdminNoteBService;
    }

    /**
     * 管理员查看所有的笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/list_note")
    public Response listNote(@RequestBody NoteRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("userId", request.getUserId());

            Map out = iAdminNoteBService.listNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
