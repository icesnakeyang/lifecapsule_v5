package cc.cdtime.lifecapsule.app.cash;

import cc.cdtime.lifecapsule.framework.common.ICommonService;
import cc.cdtime.lifecapsule.framework.constant.ESTags;
import cc.cdtime.lifecapsule.framework.vo.CashRequest;
import cc.cdtime.lifecapsule.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/app/cash")
public class AppCashController {
    private final IAppCashBService iAppCashBService;
    private final ICommonService iCommonService;

    public AppCashController(IAppCashBService iAppCashBService,
                             ICommonService iCommonService) {
        this.iAppCashBService = iAppCashBService;
        this.iCommonService = iCommonService;
    }

    /**
     * app用户创建一个现金流水记录
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createLedger")
    public Response createLedger(@RequestBody CashRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("amountIn", request.getAmountIn());
            in.put("amountOut", request.getAmountOut());
            in.put("ledgerType", request.getLedgerType());
            in.put("remark", request.getRemark());
            in.put("cashCategoryId", request.getCashCategoryId());
            in.put("transactionTime", request.getTransactionTime());

            logMap.put("", ESTags.CASH_CREATE_LEDGER);
            logMap.put("token", token);
            memoMap.put("ledgerType", request.getLedgerType());
            memoMap.put("amountIn", request.getAmountIn());
            memoMap.put("amountOut", request.getAmountOut());

            iAppCashBService.createLedger(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createLedger error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App createLedger user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * app用户查询自己的默认现金账户分类
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyDefaultCashCategory")
    public Response getMyDefaultCashCategory(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            Map out = iAppCashBService.getMyDefaultCashCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyDefaultCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户查询自己所有的现金账户分类列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/listMyCashCategory")
    public Response listMyCashCategory(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            Map out = iAppCashBService.listMyCashCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户创建一个新的现金账户分类
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createMyCashCategory")
    public Response createMyCashCategory(@RequestBody CashRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("cashCategoryName", request.getCashCategoryName());
            in.put("remark", request.getRemark());
            iAppCashBService.createMyCashCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App createMyCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户修改一个现金账户分类
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyCashCategory")
    public Response updateMyCashCategory(@RequestBody CashRequest request,
                                         HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("cashCategoryId", request.getCashCategoryId());
            in.put("cashCategoryName", request.getCashCategoryName());
            in.put("remark", request.getRemark());
            iAppCashBService.updateMyCashCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App updateMyCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户查询自己的现金流水账列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listMyCashLedger")
    public Response listMyCashLedger(@RequestBody CashRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("startTime", request.getStartTime());
            in.put("endTime", request.getEndTime());

            logMap.put("UserActType", ESTags.USER_LIST_CASH_LEDGER);
            logMap.put("token", token);

            Map out = iAppCashBService.listMyCashLedger(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App listMyCashLedger error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App listMyCashLedger user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * app用户查询自己的现金汇总账户
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyCashAccount")
    public Response getMyCashAccount(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            Map out = iAppCashBService.getMyCashAccount(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyCashAccount error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户查询自己的现金账分类详情
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyCashCategory")
    public Response getMyCashCategory(@RequestBody CashRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("cashCategoryId", request.getCashCategoryId());

            Map out = iAppCashBService.getMyCashCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyCashCategory error:" + ex.getMessage());
            }
        }
        return response;
    }

    /**
     * app用户查询自己的现金流水账详情
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getMyCashLedger")
    public Response getMyCashLedger(@RequestBody CashRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("cashLedgerId", request.getCashLedgerId());

            logMap.put("UserActType", ESTags.USER_GET_CASH_LEDGER);
            logMap.put("token", token);
            memoMap.put("cashLedgerId", request.getCashLedgerId());

            Map out = iAppCashBService.getMyCashLedger(in);
            response.setData(out);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App getMyCashLedger error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App getMyCashLedger user act error:" + ex3.getMessage());
        }
        return response;
    }

    /**
     * app用户修改自己的现金流水账记录
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateMyCashLedger")
    public Response updateMyCashLedger(@RequestBody CashRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("cashLedgerId", request.getCashLedgerId());
            in.put("amountIn", request.getAmountIn());
            in.put("amountOut", request.getAmountOut());
            in.put("cashCategoryId", request.getCashCategoryId());
            in.put("remark", request.getRemark());
            in.put("transactionTime", request.getTransactionTime());

            logMap.put("UserActType", ESTags.UPDATE_CASH_LEDGER);
            logMap.put("token", token);
            memoMap.put("cashLedgerId", request.getCashLedgerId());

            iAppCashBService.updateMyCashLedger(in);

            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App updateMyCashLedger error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App updateMyCashLedger user act error:" + ex3.getMessage());
        }
        return response;
    }


    /**
     * app用户查询自己每月的现金账户汇总额
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/statisticCashLedgerMonth")
    public Response statisticCashLedgerMonth(@RequestBody CashRequest request,
                                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        Map logMap = new HashMap();
        Map memoMap = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);

            logMap.put("UserActType", ESTags.CASH_LEDGER_STATISTIC);
            logMap.put("token", token);

            Map out = iAppCashBService.statisticCashLedgerMonth(in);
            response.setData(out);
            logMap.put("result", ESTags.SUCCESS);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("App statisticCashLedgerMonth error:" + ex.getMessage());
            }
            logMap.put("result", ESTags.FAIL);
            memoMap.put("error", ex.getMessage());
        }
        try {
            logMap.put("memo", memoMap);
            iCommonService.createUserActLog(logMap);
        } catch (Exception ex3) {
            log.error("App statisticCashLedgerMonth user act error:" + ex3.getMessage());
        }
        return response;
    }


}
