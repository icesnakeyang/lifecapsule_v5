package cc.cdtime.lifecapsule.meta.cash.entity;

import lombok.Data;

/**
 * 用户现金汇总账户
 */
@Data
public class CashAccount {
    private Integer ids;
    private String cashAccountId;
    private String userId;
    private Double amountIn;
    private Double amountOut;
    private Double balance;
}
