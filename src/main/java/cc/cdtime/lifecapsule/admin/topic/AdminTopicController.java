package cc.cdtime.lifecapsule.admin.topic;

import cc.cdtime.lifecapsule.business.adminTopic.IAdminTopicBService;
import cc.cdtime.lifecapsule.framework.vo.Response;
import cc.cdtime.lifecapsule.framework.vo.TopicRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/admin/topic")
public class AdminTopicController {
    private final IAdminTopicBService iAdminTopicBService;

    public AdminTopicController(IAdminTopicBService iAdminTopicBService) {
        this.iAdminTopicBService = iAdminTopicBService;
    }

    @ResponseBody
    @PostMapping("/listTopic")
    public Response listTopic(@RequestBody TopicRequest request, HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("includeChildren", request.getIncludeChildren());
            in.put("status", request.getStatus());

            Map out = iAdminTopicBService.listTopic(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin listTopic error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/getTopic")
    public Response getTopic(@RequestBody TopicRequest request, HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("topicId", request.getTopicId());

            Map out = iAdminTopicBService.getTopic(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin getTopic error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/removeTopic")
    public Response removeTopic(@RequestBody TopicRequest request, HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("topicId", request.getTopicId());

            iAdminTopicBService.removeTopic(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin removeTopic error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 设置话题为正常状态
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/activeTopic")
    public Response activeTopic(@RequestBody TopicRequest request, HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("topicId", request.getTopicId());

            iAdminTopicBService.activeTopic(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("Admin activeTopic error:" + ex.getMessage());
            }
        }
        return response;
    }
}
