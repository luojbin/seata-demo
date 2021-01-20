package com.luojbin.seata.at.ap1.csd;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("transMoney")
    @GlobalTransactional
    public String transMoney(int fromId, int toId, double money) {
        return ap1Service.transMoney(fromId, toId, money);
    }
}
