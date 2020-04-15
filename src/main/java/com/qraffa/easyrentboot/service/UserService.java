package com.qraffa.easyrentboot.service;

import com.qraffa.easyrentboot.dao.UserDao;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.utils.ImgSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /***
     * 检查用户是否存在
     * @param uid
     * @return
     * @throws ExceptionModel
     */
    public void checkUserById(Integer uid) throws ExceptionModel {
        // 检查用户是否存在
        int isUser=userDao.checkUserById(uid);
        if(isUser==0){
            // 用户不存在
            throw new ExceptionModel(StatusEnum.USER_NONEXISTENT);
        }
    }

    /***
     * 用户注册
     * @param user
     * @return
     * @throws ExceptionModel
     */
    public void registerUser(User user) throws ExceptionModel {
        int haveRegister = userDao.checkUserByUser(user);
        if(haveRegister>0){
            // 用户名或电话已被注册
            throw new ExceptionModel(StatusEnum.USER_EXISTENT);
        }else {
            user.setUpwd(DigestUtils.md5DigestAsHex(user.getUpwd().getBytes()));
            userDao.insertUser(user);
        }
    }

    /***
     * 用户登录
     * @param user
     * @return
     * @throws ExceptionModel
     */
    public User loginUser(User user) throws ExceptionModel{
        // 密码加密
        user.setUpwd(DigestUtils.md5DigestAsHex(user.getUpwd().getBytes()));
        // 验证用户名和密码
        User loginUser=userDao.checkUserLogin(user.getUname(),user.getUpwd());
        if(loginUser==null){
            // 用户名或密码错误
            throw new ExceptionModel(StatusEnum.PASSWORD_INCORRECT);
        }else{
            // 用户名密码正确，获取管理员权限
            return loginUser;
        }
    }

    /***
     * 查询用户信息
     * @param uid
     * @return
     * @throws ExceptionModel
     */
    public User getUser(Integer uid) throws ExceptionModel{
        // 检查用户是否存在
        userDao.checkUserById(uid);
        // 查询用户
        User user=userDao.queryUserById(uid);
        return user;
    }

    /***
     * 更新用户信息
     * @param user
     * @throws ExceptionModel
     */
    public void updateUser(User user) throws ExceptionModel {
        // 检查用户是否存在queryUserByName
        userDao.checkUserById(user.getUid());
        // 更新信息
        userDao.updateUser(user);
    }

    /***
     * 更新用户密码
     * @param user
     * @param npwd
     * @throws ExceptionModel
     */
    public void updatePassword(User user,String npwd) throws ExceptionModel {
        // 检查用户是否存在
        userDao.checkUserById(user.getUid());
        // 密码加密
        user.setUpwd(DigestUtils.md5DigestAsHex(user.getUpwd().getBytes()));
        // 验证旧密码是否正确
        int passwordCheck=userDao.checkUserPassword(user.getUid(),user.getUpwd());
        if(passwordCheck>0){
            // 密码加密
            npwd= DigestUtils.md5DigestAsHex(npwd.getBytes());
            // 更新用户密码
            userDao.updateUserPassword(user.getUid(),npwd);
        }else{
            // 用户密码不正确
            throw new ExceptionModel(StatusEnum.PASSWORD_INCORRECT);
        }
    }

    /***
     * 更新用户头像
     * @param uid
     * @param multipartFile
     * @param fileName
     * @return
     * @throws Exception
     */
    public String updateUserAvatar(Integer uid, MultipartFile multipartFile, String fileName) throws Exception {
        // 检查用户是否存在
        userDao.checkUserById(uid);
        // 保存图片
        String url= ImgSave.saveFile(multipartFile,fileName);
        // 将用户头像图片路径存入数据库
        userDao.updateUserAvatar(uid,url);
        return url;
    }

    /**
     * 根据用户名查询用户
     * @param uname
     * @return
     */
    public User getOtherUser(String uname) throws ExceptionModel {
        User user = userDao.queryUserByName(uname);
        if(user==null) {
            // 用户不存在
            throw new ExceptionModel(StatusEnum.USER_NONEXISTENT);
        }
        return user;
    }

}
