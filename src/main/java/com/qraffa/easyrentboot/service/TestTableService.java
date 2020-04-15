package com.qraffa.easyrentboot.service;

import com.qraffa.easyrentboot.dao.TestTableDao;
import com.qraffa.easyrentboot.model.entity.TestTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestTableService {

    @Autowired
    private TestTableDao testTableDao;

    public List<TestTable> queryAll(){
        return testTableDao.queryAll();
    }
}
