package com.qraffa.easyrentboot.service;

import com.qraffa.easyrentboot.dao.CommodityDao;
import com.qraffa.easyrentboot.dao.OrderDao;
import com.qraffa.easyrentboot.dao.UserDao;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Commodity;
import com.qraffa.easyrentboot.model.entity.Order;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommodityDao commodityDao;

    /**
     * 检查用户是否存在
     * @param uid
     * @throws ExceptionModel
     */
    public void checkUserById(Integer uid) throws ExceptionModel {
        int isUser=userDao.checkUserById(uid);
        if(isUser==0){
            // 用户不存在
            throw new ExceptionModel(StatusEnum.USER_NONEXISTENT);
        }
    }

    /**
     * 检查商品是否存在
     * @param cid
     * @throws ExceptionModel
     */
    public void checkCommodityById(Integer cid) throws ExceptionModel {
        int isCommodity=commodityDao.checkCommodity(cid);
        if(isCommodity==0){
            // 商品不存在
            throw new ExceptionModel(StatusEnum.COMMODITY_NONEXISTENT);
        }
    }

    /**
     * 检查订单是否存在
     * @param oid
     * @throws ExceptionModel
     */
    public void checkOrderById(Integer oid) throws ExceptionModel {
        int isOrder=orderDao.checkOrder(oid);
        if(isOrder==0){
            // 订单不存在
            throw new ExceptionModel(StatusEnum.ORDER_NONEXISTENT);
        }
    }


    /**
     * 添加新订单
     * @param order
     * @throws ExceptionModel
     */
    public void insertOrder(Order order) throws ExceptionModel {
        // 检查用户和商品是否存在
        checkUserById(order.getBuyerId());
        checkUserById(order.getSellerId());
        checkCommodityById(order.getCommodityId());
        // 检查商品是否被租借
        Commodity commodity=commodityDao.queryCommodityById(order.getCommodityId());
        if(commodity.getStatus()==0){
            // 商品已经被租借
            throw new ExceptionModel(StatusEnum.COMMODITY_RENTED);
        } else if(commodity.getUserId()!=order.getSellerId()){
            // 检查商品的所属id是否与sellerId是否相同
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        } else{
            // 获取时间戳
            Long createTime=new Date().getTime();
            order.setCreationTime(createTime);
            // 添加订单
            orderDao.insertOrder(order);
            // 修改商品租借状态
            commodity.setStatus(0);
            commodityDao.updateCommodity(commodity);
        }
    }

    /**
     * 查询单个订单
     * @param oid
     * @return
     * @throws ExceptionModel
     */
    public Order queryOrderById(Integer oid) throws ExceptionModel {
        // 检查订单是否存在
        checkOrderById(oid);
        // 查询单个订单信息
        Order order=orderDao.queryOrderById(oid);
        order.setCommodityId(order.getCommodity().getCid());
        return order;
    }

    /**
     * 查询用户的订单列表
     * @param pno
     * @param psize
     * @param uid
     * @return
     */
    public List<Order> queryUserOrder(Integer pno, Integer psize, Integer uid) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(uid);
        // 计算数据库起始查询位置
        Integer startPos=(pno-1)*psize;
        // 查询个数
        Integer count=psize;
        // 查询指定数量的商品
        List<Order> orderList=orderDao.queryAllOrder(uid);
        List<Order> returnList=new ArrayList<>();
        int listSize=orderList.size();
        for(int i=startPos,countList=0;i<listSize && countList<count;++i){
            returnList.add(orderList.get(i));
            countList++;
        }
        return returnList;
    }

    /**
     * 计算用户的订单数量
     * @return
     */
    public Integer countUserOrder(Integer uid){
        Integer count=orderDao.countOrder(uid);
        return count;
    }

    /**
     * 计算订单列表页数
     * @param count
     * @param psize
     * @return
     */
    public Integer countUserOrderListPages(Integer count,Integer psize){
        // 计算页数
        double doubleCount=count.doubleValue();
        double doublePsize=psize.doubleValue();
        Integer totalPages = new Integer((int)Math.ceil(doubleCount/doublePsize));
        return totalPages;
    }

    /**
     * 更新订单
     * @param order
     * @throws ExceptionModel
     */
    public void updateOrder(Order order) throws ExceptionModel {
        // 检查订单是否存在
        checkOrderById(order.getOid());
        // 更新订单
        orderDao.updateOrder(order);
        // 根据订单id查询订单
        order=orderDao.queryOrderById(order.getOid());
        if(order.getStatus()==4){
            // 订单取消,商品变为未出租状态
            Commodity commodity=commodityDao.queryCommodityById(order.getCommodity().getCid());
            commodity.setStatus(1);
            commodityDao.updateCommodity(commodity);
        }
    }

    /**
     * 根据用户id查询用户nickname
     * @param uid
     * @return
     */
    public String getNickNameById(Integer uid) throws ExceptionModel {
        checkUserById(uid);
        User user=userDao.queryUserById(uid);
        return user.getNickName();
    }

}
