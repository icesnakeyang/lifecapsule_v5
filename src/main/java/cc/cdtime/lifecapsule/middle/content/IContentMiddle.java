package cc.cdtime.lifecapsule.middle.content;

import cc.cdtime.lifecapsule.meta.content.entity.Content;

public interface IContentMiddle {
    Content getContent(String indexId) throws Exception;
}
