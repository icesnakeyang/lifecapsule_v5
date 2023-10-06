package cc.cdtime.lifecapsule.meta.cash.entity;

import lombok.Data;

@Data
public class CashCategory {
    private Integer ids;
    private String cashCategoryId;
    private String cashCategoryName;
    private String userId;
    private String status;
    private String remark;
}
