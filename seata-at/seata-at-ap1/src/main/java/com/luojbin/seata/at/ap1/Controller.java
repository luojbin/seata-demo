package com.luojbin.seata.at.ap1;

import com.luojbin.seata.entity.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ap1")
public class Controller {

    @Autowired
    private BankOfChinaDao dao;

    @GetMapping("getList")
    public ResultUtil getList(){
        return ResultUtil.ok().data(dao.getList());
    }
}
