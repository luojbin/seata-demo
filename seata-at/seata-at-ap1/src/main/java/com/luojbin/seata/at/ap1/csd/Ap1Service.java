package com.luojbin.seata.at.ap1.csd;

import com.luojbin.seata.at.ap1.api.Ap2Feign;
import com.luojbin.seata.entity.BankOfChina;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class Ap1Service {
    @Autowired
    private Ap2Feign ap2Feign;

    @Autowired
    private BankOfChinaDao dao;

    @Transactional(rollbackFor = Exception.class)
    public String addMoney(int accountId, BigDecimal money) {
        BankOfChina account = dao.selectByPrimaryKey(accountId);
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

    @GlobalTransactional
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
        ap2Result = ap2Feign.addMoney(toId, bd);
        return "成功, 已转出" + bd + ap2Result;
    }

    @Transactional(rollbackFor = Exception.class)
    public String backup(int rollback) {
        dao.dumpData();

        if (rollback == 1) {
            throw new RuntimeException("测试回滚");
        }
        return "操作成功";
    }

    @GlobalTransactional
    public String backupGlobal(int rollback) {
        dao.dumpData();
        String result2 = ap2Feign.backup(rollback);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (rollback == 1) {
            throw new RuntimeException("测试回滚");
        }
        return "ap1操作成功, " + result2;
    }

    @GlobalLock(lockRetryInternal = 1000, lockRetryTimes = 15)
    @Transactional
    public String bakCount() {
        int count = dao.getBakCount();
        return "bak表计数: " + count;
    }

    // region 读隔离测试
    // tx1, 插入数据, sleep 10s

    @GlobalTransactional
    public String readLockInsert(int rollback) {
        BankOfChina bank = new BankOfChina();
        bank.setName("古中医");
        bank.setMoney(BigDecimal.valueOf(rollback));
        bank.setCreateTime(new Date());
        bank.setUpdateTime(new Date());
        int id = dao.insert(bank);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (rollback == 69) {
            throw new RuntimeException("插入回滚");
        }
        return "插入成功, id= " + id;
    }

    // tx2, 查询全表计数, count, 能否查到insert的数据?
    @GlobalLock(lockRetryInternal = 1000, lockRetryTimes = 15)
    @Transactional
    public String readLockCount() {
        int count = dao.readLockCount();
        return "tx2, 查询全表计数" + count;
    }

    // tx3, 查询 where id > n(含新增的数据), 能否查到 insert 的数据?
    @GlobalLock(lockRetryInternal = 1000, lockRetryTimes = 15)
    @Transactional
    public String readLockIdGt() {
        int count = dao.readLockIdGt();
        return "tx2, 查询全表计数" + count;
    }
    // tx4, 查询 where id < n(不含新数据), 会不会被阻塞?
    @GlobalLock(lockRetryInternal = 1000, lockRetryTimes = 15)
    @Transactional
    public String readLockIdLt() {
        int count = dao.readLockIdLt();
        return "tx2, 查询全表计数" + count;
    }
    // tx5, 查询 where money > x(无索引, 含新增数据), 能否查到 insert 的数据?
    @GlobalLock(lockRetryInternal = 1000, lockRetryTimes = 15)
    @Transactional
    public String readLockMoneyGt() {
        int count = dao.readLockMoneyGt();
        return "tx5, 查询 where money > x, " + count;
    }

    // tx6, 查询 where money = x(无索引, 不含新增数据), 会不会被阻塞?
    @GlobalLock(lockRetryInternal = 1000, lockRetryTimes = 15)
    @Transactional
    public String readLockMoneyEq() {
        List<BankOfChina> list = dao.readLockMoneyEq();
        return "tx6, 查询 where money = x, " + list.size();
    }
    //endregion
}
