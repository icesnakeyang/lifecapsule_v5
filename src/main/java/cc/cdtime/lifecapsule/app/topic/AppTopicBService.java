package cc.cdtime.lifecapsule.app.topic;

import cc.cdtime.lifecapsule.business.topic.ITopicBService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AppTopicBService implements IAppTopicBService {
    private final ITopicBService iTopicBService;

    public AppTopicBService(ITopicBService iTopicBService) {
        this.iTopicBService = iTopicBService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishNoteToTopic(Map in) throws Exception {
        iTopicBService.publishNoteToTopic(in);
    }

    @Override
    public Map listTopicPublic(Map in) throws Exception {
        Map out = iTopicBService.listTopicPublic(in);
        return out;
    }

    @Override
    public Map getTopicDetail(Map in) throws Exception {
        Map out = iTopicBService.getTopicDetail(in);
        return out;
    }

    @Override
    public void replyComment(Map in) throws Exception {
        iTopicBService.replyComment(in);
    }

    @Override
    public Map listHotTopicTags(Map in) throws Exception {
        in.put("size", 10);
        Map out = iTopicBService.listTopicPublic(in);
        return out;
    }
}
