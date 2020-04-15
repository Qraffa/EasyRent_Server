package com.qraffa.easyrentboot;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class EasyrentbootApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        System.out.println(jwtUtil.getUserId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTg2NjI0NDc2fQ.2tKW2c8A7urYMLERrzqwKUUcQVGIKXSbMO8UtkteGwI"));
    }

}
