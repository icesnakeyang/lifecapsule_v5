package cc.cdtime.lifecapsule.middle.content;

import cc.cdtime.lifecapsule.meta.content.entity.Content;
import cc.cdtime.lifecapsule.meta.content.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentMiddle implements IContentMiddle {
    private final IContentService iContentService;

    public ContentMiddle(IContentService iContentService) {
        this.iContentService = iContentService;
    }

    @Override
    public Content getContent(String indexId) throws Exception {
        Content content = iContentService.getContent(indexId);
        return content;
    }
}
