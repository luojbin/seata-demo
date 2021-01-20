package com.luojbin.seata.at.ap2.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Component
@FeignClient(name = "seata-at-ap1")
public interface Ap1Feign {

    @PostMapping("/ap1/addMoney")
    String addMoney(@RequestParam("accountId") int accountId, @RequestParam("money") BigDecimal money) ;


}
