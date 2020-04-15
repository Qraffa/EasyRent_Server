package com.qraffa.easyrentboot.model.res.admin;

import com.qraffa.easyrentboot.model.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class GetUserListRes {
    private Integer totalUsers;
    private Integer totalPages;
    private Integer pno;
    private List<User> userList;
}
