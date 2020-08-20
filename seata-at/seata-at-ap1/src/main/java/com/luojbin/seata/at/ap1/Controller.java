package com.luojbin.seata.at.ap1;

import com.luojbin.seata.entity.BankOfChina;
import com.luojbin.seata.entity.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("ap1")
public class Controller {

    @Autowired
    private BankOfChinaDao dao;
    @Autowired
    private Ap2Feign ap2Feign;

    @GetMapping("getList")
    public ResultUtil getList(){
        return ResultUtil.ok().data(dao.getList());
    }

    @PostMapping("addMoney")
    public String addMoney(int accountId, BigDecimal money) {
        BankOfChina account = dao.selectByPrimaryKey(accountId);
        if (account == null) {
            return "失败, 账户不存在";
        }
        int result = dao.addMoney(accountId, money.doubleValue());
        if (result != 1) {
            return "失败, 没有更新任何记录";
        }
        return "成功, 已存入" + money + "元";
    }

    @PostMapping("transMoney")
    public String trandMoney(int fromId, int toId, double money) {
        BankOfChina account = dao.selectByPrimaryKey(fromId);
        if (account == null) {
            return "失败, 转出账户不存在";
        }
        BigDecimal bd = new BigDecimal(money);
        if (account.getMoney().subtract(bd).doubleValue() < 0.0) {
            return "失败, 转出账户余额不足";
        }
        int result = dao.minusMoney(fromId, money);
        if (result != 1) {
            return "失败, 没有更新任何记录";
        }
        String ap2Result = ap2Feign.addMoney(toId, bd);
        return "成功, 已转出" + money + ap2Result;
    }
}
