package com.luojbin.seata.at.ap2;

import com.luojbin.seata.entity.ChinaGuangfaBank;
import com.luojbin.seata.entity.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("ap2")
public class Controller {

    @Autowired
    private ChinaGuangfaBankDao dao;

    @GetMapping("getList")
    public ResultUtil getList(){
        return ResultUtil.ok().data(dao.getList());
    }

    @PostMapping("addMoney")
    public String addMoney(int accountId, BigDecimal money) {
        ChinaGuangfaBank account = dao.selectByPrimaryKey(accountId);
        if (account == null) {
            return "失败, 账户不存在";
        }
        int result = dao.addMoney(accountId, money.doubleValue());
        if (result != 1) {
            return "失败, 没有更新任何记录";
        }
        return "成功, 已存入" + money + "元";
    }
}
