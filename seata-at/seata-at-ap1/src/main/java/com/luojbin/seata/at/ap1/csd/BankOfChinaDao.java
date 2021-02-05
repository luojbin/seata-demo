package com.luojbin.seata.at.ap1.csd;


import com.luojbin.seata.entity.BankOfChina;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BankOfChinaDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BankOfChina record);

    int insertSelective(BankOfChina record);

    BankOfChina selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankOfChina record);

    int updateByPrimaryKey(BankOfChina record);

    int addMoney(@Param("accountId") int accountId, @Param("addMoney") double addMoney);

    int minusMoney(@Param("fromId") int fromId, @Param("subMoney") BigDecimal subMoney);

    int dumpData();

    List<BankOfChina> getList();
}