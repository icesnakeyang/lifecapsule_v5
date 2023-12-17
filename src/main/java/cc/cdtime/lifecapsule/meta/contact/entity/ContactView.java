package cc.cdtime.lifecapsule.meta.contact.entity;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class ContactView {
    private Integer ids;
    private String contactId;
    private String userId;
    private String contactName;
    private String phone;
    private String email;
    private String remark;
    private String userCode;
}
