package com.qraffa.easyrentboot.controller;

import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Commodity;
import com.qraffa.easyrentboot.model.entity.Order;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.admin.*;
import com.qraffa.easyrentboot.model.res.admin.*;
import com.qraffa.easyrentboot.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * uname:master
 * upwd:a204
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 查询用户列表（√）
     * @param getUserListReq
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/user")
    public ReturnModel getUserList(@Validated GetUserListReq getUserListReq, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        GetUserListRes res = new GetUserListRes();
        // 查询用户数量
        Integer countUser=adminService.countUser();
        // 查询用户列表
        List<User> userList=adminService.queryAllUser(getUserListReq.getPno(),getUserListReq.getPsize());
        // 查询页数
        Integer pages=adminService.countUserListPages(countUser, getUserListReq.getPsize());
        // 设置用户数量
        res.setTotalUsers(countUser);
        // 设置页码数量
        res.setTotalPages(pages);
        // 设置页码
        res.setPno(getUserListReq.getPno());
        // 设置用户列表
        res.setUserList(userList);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 查询单个用户信息（√）
     * @param getUserReq
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/user_detail")
    public ReturnModel getUser(@Validated GetUserReq getUserReq, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 查询单个用户信息
        User user = adminService.queryUserById(getUserReq.getUid());
        GetUserRes res = new GetUserRes();
        res.setUser(user);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 更新用户信息（√）
     * @param putUserReq
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PutMapping("/user")
    public ReturnModel putUser(@RequestBody @Validated PutUserReq putUserReq, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获得user值
        User user=new User();
        BeanUtils.copyProperties(putUserReq,user);
        // 更新用户信息
        adminService.updateUser(user);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 删除用户（√）
     * @param deleteUserReq
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @DeleteMapping("/user")
    public ReturnModel deleteUser(@RequestBody @Validated DeleteUserReq deleteUserReq, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 删除用户
        adminService.deleteUser(deleteUserReq.getUid());
        return new ReturnModel().withOkData(null);
    }

    /**
     * 查询商品列表（**返回的商品中的pid和picPath为null**）
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/commodity")
    public ReturnModel getCommodityList(@Validated GetCommodityListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        GetCommodityListRes res = new GetCommodityListRes();
        // 查询商品数量
        Integer countCommodity=adminService.countCommodity();
        // 查询商品列表
        List<Commodity> commodityList=adminService.queryAllCommodity(req.getPno(),req.getPsize());
        // 查询商品列表页数
        Integer pages=adminService.countCommodityPages(countCommodity,req.getPsize());
        // 设置商品数量
        res.setTotalCommodities(countCommodity);
        // 设置页码数量
        res.setTotalPages(pages);
        // 设置页码
        res.setPno(req.getPno());
        // 设置商品列表
        res.setCommodityList(commodityList);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 查询单个商品信息（√）
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/commodity_detail")
    public ReturnModel getCommodity(@Validated GetCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 查询商品信息
        Commodity commodity=adminService.queryCommodityById(req.getCid());
        GetCommodityRes res = new GetCommodityRes();
        res.setCommodity(commodity);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 更新单个商品信息（√）
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PutMapping("/commodity")
    public ReturnModel putCommodity(@RequestBody @Validated PutCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取commodity值
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(req,commodity);
        // 更新商品信息
        adminService.updateCommodity(commodity);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 更新商品图片信息（**未测试**）
     * @param req
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/commodity_pic")
    public ReturnModel postCommodityPic(@RequestBody @Validated PostCommodityPicReq req, BindingResult bindingResult) throws Exception {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        // 获取传入文件
        MultipartFile excelFile = req.getCommodityPic();
        // 校验传入文件有效性
        if (excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("文件传入错误");
        }
        // 存储文件
        String fileName = String.format("%s_%s",new Date().getTime(),excelFile.getOriginalFilename());
        String url = adminService.updateCommodityPicture(req.getCid(),excelFile,fileName);
        // 设置返回的url
        PostCommodityPicRes res = new PostCommodityPicRes();
        res.setCommodityPicURL(url);
        // 构建返回模板
        return  new ReturnModel().withOkData(res);
    }

    /**
     * 删除商品图片（√）
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @DeleteMapping("/commodity_pic")
    public ReturnModel deleteCommodityPic(@RequestBody @Validated DeleteCommodityPicReq req, BindingResult bindingResult) throws ExceptionModel {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        adminService.deleteCommodityPicture(req.getPid());
        return new ReturnModel().withOkData(null);
    }

    /**
     * 删除商品（√）
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @DeleteMapping("/commodity")
    public ReturnModel deleteCommodity(@RequestBody @Validated DeleteCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 数据校验
        if (bindingResult.hasErrors()){
            return new ReturnModel().withStatus(StatusEnum.USER_MESSAGE_INCOMPLETE);
        }
        // 删除商品
        adminService.deleteCommodity(req.getCid());
        return new ReturnModel().withOkData(null);
    }

    /**
     * 查询订单列表
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/order")
    public ReturnModel getOrderList(@Validated GetOrderListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        GetOrderListRes res = new GetOrderListRes();
        // 查询订单数量
        Integer countOrder=adminService.countOrder();
        // 查询订单列表
        List<Order> orderList=adminService.queryAllOrder(req.getPno(),req.getPsize());
        // 查询订单列表页数
        Integer pages=adminService.countOrderListPages(countOrder,req.getPsize());
        // 设置订单数量
        res.setTotalOrders(countOrder);
        // 设置页码数量
        res.setTotalPages(pages);
        // 设置页码
        res.setTotalPages(req.getPno());
        // 设置订单列表
        res.setOrderList(orderList);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 查看单个订单信息
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/order_detail")
    public ReturnModel getOrder(@Validated GetOrderReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 查询单个订单信息
        Order order=adminService.queryOrderById(req.getOid());
        GetOrderRes res=new GetOrderRes();
        res.setOrder(order);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 删除订单
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @DeleteMapping("/order")
    public ReturnModel deleteOrder(@RequestBody @Validated DeleteOrderReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 删除订单
        adminService.deleteOrder(req.getOid());
        return new ReturnModel().withOkData(null);
    }

}
