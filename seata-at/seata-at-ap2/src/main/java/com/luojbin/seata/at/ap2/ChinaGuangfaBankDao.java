package com.luojbin.seata.at.ap2;

import com.luojbin.seata.entity.ChinaGuangfaBank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChinaGuangfaBankDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ChinaGuangfaBank record);

    int insertSelective(ChinaGuangfaBank record);

    ChinaGuangfaBank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChinaGuangfaBank record);

    int updateByPrimaryKey(ChinaGuangfaBank record);

    int addMoney(@Param("accountId") int accountId, @Param("addMoney") double addMoney);

    List<ChinaGuangfaBank> getList();
}