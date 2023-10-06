package cc.cdtime.lifecapsule.meta.user.entity;

import lombok.Data;

/**
 * 用户的秘钥
 */
@Data
public class UserEncodeKey {
    private Integer ids;
    private String encodeKey;
    private String indexId;
}
