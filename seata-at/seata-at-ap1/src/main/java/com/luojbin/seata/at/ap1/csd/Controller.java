package com.luojbin.seata.at.ap1.csd;

import io.seata.spring.annotation.GlobalTransactional;
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
    private Ap1Service ap1Service;

    @PostMapping("addMoney")
    public String addMoney(int accountId, BigDecimal money) {
        return ap1Service.addMoney(accountId, money);
    }

    @PostMapping("backup")
    public String backup(int rollback) {
        return ap1Service.backup(rollback);
    }
    @PostMapping("backupGlobal")
    public String backupGlobal(int rollback) {
        return ap1Service.backupGlobal(rollback);
    }

    @PostMapping("transMoney")
    @GlobalTransactional
    public String transMoney(int fromId, int toId, double money) {
        return ap1Service.transMoney(fromId, toId, money);
    }

    @GetMapping("bakCount")
    public String bakCount() {
        return ap1Service.bakCount();
    }

    // region 读隔离测试
    // tx1, 插入数据, sleep 10s
    // tx2, 查询全表计数, count, 能否查到insert的数据?
    // tx3, 查询 where id > n(含新增的数据), 能否查到 insert 的数据?
    // tx4, 查询 where id < n(不含新数据), 会不会被阻塞?
    // tx5, 查询 where money > x(无索引, 含新增数据), 能否查到 insert 的数据?
    // tx6, 查询 where money = x(无索引, 不含新增数据), 会不会被阻塞?
    @GetMapping("readLock/insert")
    public String readLockInsert(int rollback){
        return ap1Service.readLockInsert(rollback);
    }

    // tx2, 查询全表计数, count, 能否查到insert的数据? 应该阻塞
    @GetMapping("readLock/count")
    public String readLockCount(){
        return ap1Service.readLockCount();
    }
    // tx3, 查询 where id > n(含新增的数据), 能否查到 insert 的数据? 应该会阻塞
    @GetMapping("readLock/idgt")
    public String readLockIdGt(){
        return ap1Service.readLockIdGt();
    }
    // tx4, 查询 where id < n(不含新数据), 会不会被阻塞? 不会阻塞
    @GetMapping("readLock/idlt")
    public String readLockIdLt(){
        return ap1Service.readLockIdLt();
    }
    // tx5, 查询 where money > x(无索引, 含新增数据), 能否查到 insert 的数据?
    @GetMapping("readLock/moneyGt")
    public String readLockIdLt(){
        return ap1Service.readLockMoneyGt();
    }
    // tx6, 查询 where money = x(无索引, 不含新增数据), 会不会被阻塞?
    @GetMapping("readLock/moneyEq")
    public String readLockIdLt(){
        return ap1Service.readLockMoneyEq();
    }
    //endregion
}
