package com.qraffa.easyrentboot.model.entity;

import lombok.Data;

/***
 * 用户
 */
@Data
public class User {
    // 用户id
    private Integer uid;
    // 用户名
    private String uname;
    // 用户昵称
    private String nickName;
    // 用户密码
    private String upwd;
    // 用户头像
    private String avatar;
    // 用户电话
    private String phone;
    // 用户邮箱
    private String email;
    // 用户性别
    private Integer gender;
    // 用户校区
    private Integer campus;
    // 用户管理员标识
    private Integer admin;
}
