package cc.cdtime.lifecapsule.business.contact;

import cc.cdtime.lifecapsule.framework.tools.GogoTools;
import cc.cdtime.lifecapsule.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule.meta.contact.entity.ContactView;
import cc.cdtime.lifecapsule.meta.email.entity.UserEmailView;
import cc.cdtime.lifecapsule.meta.user.entity.UserView;
import cc.cdtime.lifecapsule.middle.contact.IContactMiddle;
import cc.cdtime.lifecapsule.middle.user.IUserMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContactBService implements IContactBService {
    private final IContactMiddle iContactMiddle;
    private final IUserMiddle iUserMiddle;

    public ContactBService(IContactMiddle iContactMiddle,
                           IUserMiddle iUserMiddle) {
        this.iContactMiddle = iContactMiddle;
        this.iUserMiddle = iUserMiddle;
    }

    @Override
    public void saveContact(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactName = in.get("contactName").toString();
        String email = in.get("email").toString();
        String contactId = (String) in.get("contactId");
        String remark = (String) in.get("remark");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        if (contactId == null || contactId.equals("")) {
            /**
             * 新增联系人
             */
            Contact contact = new Contact();
            contact.setContactId(GogoTools.UUID32());
            contact.setContactName(contactName);
            contact.setEmail(email);
            contact.setUserId(userView.getUserId());
            contact.setRemark(remark);
            iContactMiddle.createContact(contact);
        } else {
            /**
             * 修改联系人
             */
            qIn = new HashMap();
            qIn.put("contactId", contactId);
            ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());
            int cc = 0;
            if (contactName != null) {
                if (contactView.getContactName() != null) {
                    if (!contactView.getContactName().equals(contactName)) {
                        qIn.put("contactName", contactName);
                        cc++;
                    }
                } else {
                    qIn.put("contactName", contactName);
                    cc++;
                }
            }
            if (!contactView.getEmail().equals(email)) {
                qIn.put("email", email);
                cc++;
            }
            if (remark != null) {
                if (contactView.getRemark() != null) {
                    if (!contactView.getRemark().equals(remark)) {
                        qIn.put("remark", remark);
                        cc++;
                    }
                } else {
                    qIn.put("remark", remark);
                    cc++;
                }
            }
            if (cc > 0) {
                qIn.put("contactId", contactId);
                iContactMiddle.updateContact(qIn);
            }
        }
    }

    @Override
    public Map listContact(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("userId", userView.getUserId());
        if (pageIndex != null) {
            Integer offset = (pageIndex - 1) * pageSize;
            qIn.put("offset", offset);
            qIn.put("size", pageSize);
        }
        ArrayList<ContactView> contactViews = iContactMiddle.listContact(qIn);
        Integer totalContact = iContactMiddle.totalContact(qIn);

        Map out = new HashMap();
        out.put("contactList", contactViews);
        out.put("totalContact", totalContact);

        return out;
    }

    @Override
    public Map getContact(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactId = (String) in.get("contactId");

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("contactId", contactId);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        Map out = new HashMap();
        out.put("contact", contactView);

        return out;
    }

    @Override
    public void deleteContact(Map in) throws Exception {
        String token = in.get("token").toString();
        String contactId = in.get("contactId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        qIn = new HashMap();
        qIn.put("contactId", contactId);
        ContactView contactView = iContactMiddle.getContact(qIn, false, userView.getUserId());

        iContactMiddle.deleteContact(contactId);
    }

    @Override
    public Map getToUser(Map in) throws Exception {
        String token = in.get("token").toString();
        String toUserKey = in.get("toUserKey").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        UserView userView = iUserMiddle.getUser(qIn, false, true);

        Map qIn2 = new HashMap();
        Map userMap = new HashMap();
        int cc = 0;
        if (toUserKey != null && !toUserKey.equals("")) {
            qIn2.put("userCode", toUserKey);
            UserView user2 = iUserMiddle.getUserTiny(qIn2, true, false);
            if (user2 != null) {
                userMap.put("userCode", user2.getUserCode());
                userMap.put("nickname", user2.getNickname());
                userMap.put("userId", user2.getUserId());
                cc++;
            }
        }
        if (cc == 0) {
            qIn2.put("email", toUserKey);
            UserEmailView user2 = iUserMiddle.getUserEmail(qIn2, true, null);
            if (user2 != null) {
                qIn2 = new HashMap();
                qIn2.put("userId", user2.getUserId());
                UserView userView1 = iUserMiddle.getUserTiny(qIn2, true, false);
                if (userView1 != null) {
                    userMap.put("nickname", userView1.getNickname());
                    userMap.put("userCode", userView1.getUserCode());
                    userMap.put("email", user2.getEmail());
                    userMap.put("userId", user2.getUserId());
                }
            }
        }
        if (userMap.size() == 0) {
            //没有查询到要发送的用户
            throw new Exception("10086");
        }
        Map out = new HashMap();
        out.put("toUser", userMap);
        return out;
    }
}
