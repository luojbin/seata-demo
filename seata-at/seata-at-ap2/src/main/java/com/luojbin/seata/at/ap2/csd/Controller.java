package com.luojbin.seata.at.ap2.csd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("ap2")
public class Controller {

    @Autowired
    private Ap2Service ap2Service;

    @PostMapping("addMoney")
    public String addMoney(int accountId, BigDecimal money) {
        return ap2Service.addMoney(accountId, money);
    }
}
