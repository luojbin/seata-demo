package com.luojbin.seata.at.ap1;

import com.luojbin.seata.entity.BankOfChina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class Ap1Service {
    @Autowired
    private Ap2Feign ap2Feign;

    @Autowired
    private BankOfChinaDao dao;

    @Transactional
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

    @Transactional
    public String transMoney(int fromId, int toId, double money) {
        BankOfChina account = dao.selectByPrimaryKey(fromId);
        if (account == null) {
            return "失败, 转出账户不存在";
        }
        BigDecimal bd = new BigDecimal(money);
        if (account.getMoney().subtract(bd).doubleValue() < 0.0) {
            return "失败, 转出账户余额不足";
        }
        int result = dao.minusMoney(fromId, bd);
        if (result != 1) {
            return "失败, 没有更新任何记录";
        }
        String ap2Result;
        try {
            ap2Result = ap2Feign.addMoney(toId, bd);
        } catch (Exception e) {
            System.out.println("ap2 异常, ap1 正常提交事务");
            ap2Result = "ap2 有毛病, 没收到钱";
        }
        return "成功, 已转出" + bd + ap2Result;
    }
}