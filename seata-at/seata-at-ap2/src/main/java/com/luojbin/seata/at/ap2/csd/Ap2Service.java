package com.luojbin.seata.at.ap2.csd;

import com.luojbin.seata.entity.ChinaGuangfaBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class Ap2Service {

    @Autowired
    private ChinaGuangfaBankDao dao;

    public String addMoney(int accountId, BigDecimal money) {
        ChinaGuangfaBank account = dao.selectByPrimaryKey(accountId);
        if (account == null) {
            return "失败, 账户不存在";
        }
        int result = dao.addMoney(accountId, money.doubleValue());
        if (result != 1) {
            return "失败, 没有更新任何记录";
        }
        if (money.equals(new BigDecimal("123"))) {
            throw new RuntimeException("测试回滚");
        }
        return "成功, 已存入" + money + "元";
    }

    public String backup(int rollback) {
        dao.dumpData(rollback);
        return null;
    }
}
