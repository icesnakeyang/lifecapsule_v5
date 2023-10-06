package cc.cdtime.lifecapsule.middle.email;

import cc.cdtime.lifecapsule.meta.email.entity.EmailLog;

public interface IEmailMiddle {
    void createEmailLog(EmailLog emailLog) throws Exception;
    EmailLog getEmailLog(String email, Boolean returnNull) throws Exception;
    void deleteEmailLog(String email)throws Exception;
}
