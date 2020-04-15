package com.qraffa.easyrentboot.dao;

import com.qraffa.easyrentboot.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 订单接口
 */
@Mapper
@Repository
public interface OrderDao {

    /**
     * 查询所有订单
     * uid表示查询某个用户的订单列表,非必须
     * @param uid
     * @return
     */
    List<Order> queryAllOrder(@Param("uid") Integer uid);

    /***
     * 根据id查询是否有该订单
     * @param oid
     * @return
     */
    int checkOrder(@Param("oid") Integer oid);

    /***
     * 根据id查询单个订单信息
     * @param oid
     * @return
     */
    Order queryOrderById(@Param("oid") Integer oid);

    /***
     * 插入订单信息
     * @param order
     * @return
     */
    int insertOrder(Order order);

    /***
     * 修改订单
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /***
     * 根据id删除订单
     * @param oid
     * @return
     */
    int deleteOrderById(@Param("oid") Integer oid);

    /**
     * 查询订单数量
     * @return
     */
    int countOrder(@Param("uid") Integer uid);

}
