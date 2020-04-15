package com.qraffa.easyrentboot.controller;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Order;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.order.GetOrderListReq;
import com.qraffa.easyrentboot.model.req.order.GetOrderReq;
import com.qraffa.easyrentboot.model.req.order.PostOrderReq;
import com.qraffa.easyrentboot.model.req.order.PutOrderReq;
import com.qraffa.easyrentboot.model.res.order.GetOrderListRes;
import com.qraffa.easyrentboot.model.res.order.GetOrderRes;
import com.qraffa.easyrentboot.model.utils.SessionUser;
import com.qraffa.easyrentboot.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 添加订单
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PostMapping("/order")
    @ApiOperation(value = "添加订单")
    public ReturnModel postOrder(@RequestBody @Validated PostOrderReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        Order order=new Order();
        BeanUtils.copyProperties(req,order);
        order.setBuyerId(Integer.valueOf(jwtUtil.getUserId()));
        // 添加订单
        orderService.insertOrder(order);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 查询单个订单
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/order_detail")
    @ApiOperation(value = "查询单个订单")
    public ReturnModel getOrderDetail(@Validated GetOrderReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 查询单个订单
        Order order=orderService.queryOrderById(req.getOid());
        GetOrderRes res=new GetOrderRes();
        res.setOrder(order);
        // 根据订单查询买家和卖家nickName
        res.setBuyerNickName(orderService.getNickNameById(order.getBuyerId()));
        res.setSellerNickName(orderService.getNickNameById(order.getSellerId()));
        return new ReturnModel().withOkData(res);
    }

    /**
     * 查询用户的订单列表
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/order")
    @ApiOperation(value = "查询用户的订单列表")
    public ReturnModel getOrderList(@Validated GetOrderListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 构造返回数据
        GetOrderListRes res=new GetOrderListRes();
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 查询订单数量
        Integer count=orderService.countUserOrder(Integer.valueOf(jwtUtil.getUserId()));
        // 查询订单列表
        List<Order> orderList=orderService.queryUserOrder(req.getPno(),req.getPsize(),Integer.valueOf(jwtUtil.getUserId()));
        // 查询订单列表页数
        Integer pages=orderService.countUserOrderListPages(count,req.getPsize());
        // 设置订单数量
        res.setTotalOrders(count);
        // 设置页码数量
        res.setTotalPages(pages);
        // 设置页码
        res.setPno(req.getPno());
        // 设置订单列表
        res.setOrderList(orderList);
        return new ReturnModel().withOkData(res);
    }

    /**
     * 更新订单
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PutMapping("/order")
    @ApiOperation(value = "更新订单")
    public ReturnModel putOrder(@RequestBody @Validated PutOrderReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors() || (req.getActualRentalTime()==null && req.getStatus()==2) || (req.getActualTime()==null && req.getStatus()==3)){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        Order order=new Order();
        BeanUtils.copyProperties(req,order);
        // 更新订单信息
        orderService.updateOrder(order);
        return new ReturnModel().withOkData(null);
    }

}
