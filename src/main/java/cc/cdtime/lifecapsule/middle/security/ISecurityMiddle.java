package cc.cdtime.lifecapsule.middle.security;


import cc.cdtime.lifecapsule.meta.security.entity.SecurityKey;

public interface ISecurityMiddle {
    /**
     * 保存一个私钥
     *
     * @param securityKey
     */
    void saveRSA(SecurityKey securityKey) throws Exception;

    String getRSAKey(String keyToken) throws Exception;

    /**
     * 删除一个私钥
     *
     * @param keyToken
     */
    void deleteRSAKey(String keyToken) throws Exception;

    String takeNoteAES(String keyToken, String encryptKey) throws Exception;
}
