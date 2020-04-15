package com.qraffa.easyrentboot.dao;

import com.qraffa.easyrentboot.model.entity.TestTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestTableDao {
    List<TestTable> queryAll();
}
