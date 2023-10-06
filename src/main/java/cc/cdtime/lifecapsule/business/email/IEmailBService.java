package cc.cdtime.lifecapsule.business.email;

import java.util.Map;

public interface IEmailBService {
    void sendVerifyCodeToEmail(Map in) throws Exception;
}
