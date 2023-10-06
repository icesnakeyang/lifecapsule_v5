package cc.cdtime.lifecapsule.meta.security.entity;

import lombok.Data;

/**
 * 钥匙类
 */
@Data
public class SecurityKey {
    private String keyToken;
    private String privateRSA;
}
