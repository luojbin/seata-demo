package com.luojbin.seata.at.ap1.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
@Component
@FeignClient(name = "seata-at-ap2")
public interface Ap2Feign {

    @PostMapping("/ap2/addMoney")
    String addMoney(@RequestParam("accountId") int accountId, @RequestParam("money") BigDecimal money) ;

    @PostMapping("/ap2/backup")
    String backup(@RequestParam("rollback") int rollback) ;


}
