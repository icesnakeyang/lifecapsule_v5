package cc.cdtime.lifecapsule.framework.vo;

import lombok.Data;

@Data
public class ContactRequest extends Request{
    private String contactId;
    private String contactName;
    private String phone;
    private String email;
    private String remark;
}
