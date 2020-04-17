package com.qraffa.easyrentboot.controller;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.user.*;
import com.qraffa.easyrentboot.model.res.user.GetOtherUserRes;
import com.qraffa.easyrentboot.model.res.user.GetUserRes;
import com.qraffa.easyrentboot.model.res.user.PostAvatarRes;
import com.qraffa.easyrentboot.model.res.user.PostLoginRes;
import com.qraffa.easyrentboot.model.utils.SessionUser;
import com.qraffa.easyrentboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@RestController
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     * @param req
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/front/user")
    @ApiOperation(value = "用户注册")
    public ReturnModel postUser(@RequestBody @Validated PostUserReq req, BindingResult bindingResult) throws Exception{
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        User user = new User();
        BeanUtils.copyProperties(req,user);
        // 用户注册
        userService.registerUser(user);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 用户登录
     * @param req
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/front/login")
    @ApiOperation(value = "用户登录")
    public ReturnModel postLogin(@RequestBody @Validated PostLoginReq req, BindingResult bindingResult) throws Exception{
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        User user = new User();
        BeanUtils.copyProperties(req,user);
        // 检验账号密码登录
        User loginUser = userService.loginUser(user);
        String token = jwtUtil.sign(String.valueOf(loginUser.getUid()));
        // session值,将uid和admin赋值
        //SessionUser sessionUser=new SessionUser();
        //BeanUtils.copyProperties(loginUser,sessionUser);
        // 登录完成后设置session
        //httpSession.setAttribute("user_session_key",sessionUser);
        PostLoginRes res = new PostLoginRes(loginUser,token);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 用户获取个人信息
     * @return
     * @throws Exception
     */
    @GetMapping("/api/v1/user")
    @ApiOperation(value = "用户获取个人信息")
    public ReturnModel getUser() throws Exception{
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 获取用户信息
        User user = userService.getUser(Integer.valueOf(jwtUtil.getUserId()));
        GetUserRes res = new GetUserRes();
        res.setUser(user);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 用户更新个人信息
     * @param putUserReq
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PutMapping("/api/v1/user")
    @ApiOperation(value = "用户更新个人信息")
    public ReturnModel putUser(@RequestBody @Validated PutUserReq putUserReq, BindingResult bindingResult) throws ExceptionModel {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        User user = new User();
        BeanUtils.copyProperties(putUserReq,user);
        user.setUid(Integer.valueOf(jwtUtil.getUserId()));
        // 更新用户信息
        userService.updateUser(user);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 用户修改个人密码
     * @param putUserPassword
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PutMapping("/api/v1/password")
    @ApiOperation(value = "用户修改个人密码")
    public ReturnModel putPassword(@RequestBody @Validated PutUserPassword putUserPassword, BindingResult bindingResult) throws ExceptionModel {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        User user = new User();
        BeanUtils.copyProperties(putUserPassword,user);
        user.setUid(Integer.valueOf(jwtUtil.getUserId()));
        // 更新用户密码
        userService.updatePassword(user,putUserPassword.getNpwd());
        return new ReturnModel().withOkData(null);
    }

    /**
     * 用户退出登录
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/front/logout")
    @ApiOperation(value = "用户退出登录")
    public ReturnModel getLogout() throws ExceptionModel {
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 检查用户存在
        //userService.checkUserById(Integer.valueOf(jwtUtil.getUserId()));
        // 退出登录，移除session中的值
        //httpSession.removeAttribute("user_session_key");
        return new ReturnModel().withOkData(null);
    }

    /**
     * 更新用户头像
     * @param postAvatar
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/api/v1/avatar")
    @ApiOperation(value = "更新用户头像")
    public ReturnModel postAvatar(PostAvatar postAvatar, BindingResult bindingResult) throws Exception {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 获取传入文件
        MultipartFile excelFile = postAvatar.getAvatar();
        // 校验传入文件有效性
        if (excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("文件传入错误");
        }

        // 存储文件
        String fileName = String.format("%s_%s",new Date().getTime(),excelFile.getOriginalFilename());
        String url = userService.updateUserAvatar(Integer.valueOf(jwtUtil.getUserId()),excelFile,fileName);

        // 设置返回的url
        PostAvatarRes postAvatarRes = new PostAvatarRes();
        postAvatarRes.setAvatarURL(url);

        // 构建返回模板
        return  new ReturnModel().withOkData(postAvatarRes);
    }

    @GetMapping("/api/v1/otheruser")
    @ApiOperation(value = "获取其他用户的信息")
    public ReturnModel getOtherUser(@Validated GetOtherUser getOtherUser, BindingResult bindingResult) throws ExceptionModel {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        GetOtherUserRes res = new GetOtherUserRes();
        // 查询用户信息
        User user = userService.getOtherUser(getOtherUser.getUname());
        BeanUtils.copyProperties(user,res);
        return new ReturnModel().withOkData(res);
    }
}
