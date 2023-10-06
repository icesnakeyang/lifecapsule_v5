package cc.cdtime.lifecapsule.meta.contact.service;

import cc.cdtime.lifecapsule.meta.contact.entity.Contact;
import cc.cdtime.lifecapsule.meta.contact.entity.ContactView;

import java.util.ArrayList;
import java.util.Map;

public interface IContactService {
    /**
     * 创建一个联系人
     *
     * @param contact
     */
    void createContact(Contact contact) throws Exception;

    /**
     * 读取联系人列表
     *
     * @param qIn userId
     *            offset
     *            size
     * @return
     */
    ArrayList<ContactView> listContact(Map qIn) throws Exception;
    Integer totalContact(Map qIn) throws Exception;

    /**
     * 读取一个联系人详情
     *
     * @param qIn contactId
     *            phone
     *            email
     *            userId
     * @return
     */
    ContactView getContact(Map qIn) throws Exception;

    /**
     * 修改一个联系人信息
     *
     * @param qIn contactName
     *            phone
     *            remark
     *            email
     *            contactId
     */
    void updateContact(Map qIn) throws Exception;

    /**
     * 物理删除一个联系人
     *
     * @param contactId
     */
    void deleteContact(String contactId) throws Exception;
}
