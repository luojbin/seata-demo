package com.luojbin.seata.at.ap1;


import com.luojbin.seata.entity.BankOfChina;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankOfChinaDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BankOfChina record);

    int insertSelective(BankOfChina record);

    BankOfChina selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankOfChina record);

    int updateByPrimaryKey(BankOfChina record);

    List<BankOfChina> getList();
}