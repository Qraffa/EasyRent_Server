package com.qraffa.easyrentboot.dao;

import com.qraffa.easyrentboot.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 用户模块接口
 */
@Mapper
@Repository
public interface UserDao {

    /***
     * 查询所有用户
     * @return
     */
    List<User> queryAllUser();

    /***
     * 用户注册
     * @param user
     * @return
     */
    int insertUser(User user);

    /***
     * 根据用户uname属性和phone属性查询是否有该用户
     * @param user
     * @return
     */
    int checkUserByUser(User user);

    /***
     * 根据id查询用户是否存在
     * @param uid
     * @return
     */
    int checkUserById(@Param("uid") Integer uid);

    /***
     * 查询用户名和密码
     * @param uname
     * @param upwd
     * @return
     */
    User checkUserLogin(@Param("uname") String uname, @Param("upwd") String upwd);

    /***
     * 查询用户管理员权限
     * @param uname
     * @return
     */
    int queryUserAdmin(@Param("uname") String uname);

    /***
     * 查询单个用户信息
     * @param uid
     * @return
     */
    User queryUserById(@Param("uid") Integer uid);

    /***
     * 查询单个用户信息
     * @param uname
     * @return
     */
    User queryUserByName(@Param("uname") String uname);

    /***
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /***
     * 更新用户信息（管理员权限）
     * @param user
     * @return
     */
    int updateUserAdmin(User user);

    /***
     * 查询用户密码是否一致
     * @param uid
     * @param upwd
     * @return
     */
    int checkUserPassword(@Param("uid") Integer uid, @Param("upwd") String upwd);

    /***
     * 更新用户密码
     * @param uid
     * @param npwd
     * @return
     */
    int updateUserPassword(@Param("uid") Integer uid, @Param("npwd") String npwd);

    /***
     * 更新用户头像
     * @param uid
     * @param avatar
     * @return
     */
    int updateUserAvatar(@Param("uid") Integer uid, @Param("avatar") String avatar);

    /***
     * 根据id删除用户
     * @param uid
     * @return
     */
    int deleteUserById(@Param("uid") Integer uid);

    /***
     * 查询用户数量
     * @return
     */
    int countUser();
}
