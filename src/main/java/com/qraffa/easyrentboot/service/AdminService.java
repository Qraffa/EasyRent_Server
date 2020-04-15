package com.qraffa.easyrentboot.service;

import com.qraffa.easyrentboot.dao.CommodityDao;
import com.qraffa.easyrentboot.dao.CommodityPicDao;
import com.qraffa.easyrentboot.dao.OrderDao;
import com.qraffa.easyrentboot.dao.UserDao;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Commodity;
import com.qraffa.easyrentboot.model.entity.Order;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.utils.ImgSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private CommodityPicDao commodityPicDao;
    @Autowired
    private OrderDao orderDao;


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

    /**
     * 查询用户列表
     * @param pno
     * @param psize
     * @return
     */
    public List<User> queryAllUser(Integer pno,Integer psize){
        // 计算数据库起始查询位置
        Integer startPos=(pno-1)*psize;
        // 查询个数
        Integer count=psize;
        // 查询指定数量的用户
        List<User> userList=userDao.queryAllUser();
        List<User> returnList=new ArrayList<>();
        int listSize=userList.size();
        for(int i=startPos,countList=0;i<listSize && countList<count;++i){
            returnList.add(userList.get(i));
            countList++;
        }
        return returnList;
    }

    /**
     * 计算用户个数
     * @return
     */
    public Integer countUser(){
        // 查询用户个数
        Integer countUser=userDao.countUser();
        return countUser;
    }

    /**
     * 计算用户列表页数
     * @param count
     * @param psize
     * @return
     */
    public Integer countUserListPages(Integer count,Integer psize){
        // 计算页数
        double doubleCount=count.doubleValue();
        double doublePsize=psize.doubleValue();
        Integer totalPages = new Integer((int)Math.ceil(doubleCount/doublePsize));;
        return totalPages;
    }

    /**
     * 查询单个用户信息
     * @param uid
     * @return
     */
    public User queryUserById(Integer uid) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(uid);
        // 查询用户信息
        User user=userDao.queryUserById(uid);
        return user;
    }

    /***
     * 更新用户信息
     * @param user
     * @throws ExceptionModel
     */
    public void updateUser(User user) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(user.getUid());
        // 检查是否有更改密码,如果有,则对密码加密
        if(user.getUpwd()!=null){
            // 密码加密
            user.setUpwd(DigestUtils.md5DigestAsHex(user.getUpwd().getBytes()));
        }
        // 更新用户信息
        userDao.updateUser(user);
    }

    /***
     * 删除用户
     * @param uid
     */
    public void deleteUser(Integer uid) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(uid);
        // 删除用户
        userDao.deleteUserById(uid);
    }

    /**
     * 检查商品是否存在
     * @param cid
     * @throws ExceptionModel
     */
    public void checkCommodityById(Integer cid) throws ExceptionModel {
        // 检查商品是否存在
        int isCommodity=commodityDao.checkCommodity(cid);
        if(isCommodity==0){
            // 商品不存在
            throw new ExceptionModel(StatusEnum.COMMODITY_NONEXISTENT);
        }
    }

    /**
     * 查询商品列表
     * @param pno
     * @param psize
     * @return
     */
    public List<Commodity> queryAllCommodity(Integer pno,Integer psize){
        // 计算数据库起始查询位置
        Integer startPos=(pno-1)*psize;
        // 查询个数
        Integer count=psize;
        // 查询指定数量的商品
        List<Commodity> commodityList=commodityDao.queryAllCommodity(null,null);
        List<Commodity> returnList=new ArrayList<>();
        int listSize=commodityList.size();
        for(int i=startPos,countList=0;i<listSize && countList<count;++i){
            returnList.add(commodityList.get(i));
            countList++;
        }
        return returnList;
    }

    /**
     * 计算商品个数
     * @return
     */
    public Integer countCommodity(){
        // 查询商品个数
        Integer count=commodityDao.countCommodity(null,null);
        return count;
    }

    /**
     * 计算商品列表页数
     * @param count
     * @param psize
     * @return
     */
    public Integer countCommodityPages(Integer count,Integer psize){
        // 计算页数
        double doubleCount=count.doubleValue();
        double doublePsize=psize.doubleValue();
        Integer totalPages = new Integer((int)Math.ceil(doubleCount/doublePsize));
        return totalPages;
    }

    /**
     * 查询单个商品信息
     * @param cid
     * @return
     * @throws ExceptionModel
     */
    public Commodity queryCommodityById(Integer cid) throws ExceptionModel {
        // 检查商品是否存在
        checkCommodityById(cid);
        // 查询商品信息
        Commodity commodity=commodityDao.queryCommodityById(cid);
        return commodity;
    }

    /**
     * 更新商品信息
     * @param commodity
     * @throws ExceptionModel
     */
    public void updateCommodity(Commodity commodity) throws ExceptionModel {
        // 检查商品是否存在
        checkCommodityById(commodity.getCid());
        // 更新商品信息
        commodityDao.updateCommodity(commodity);
    }

    /**
     * 更新商品图片
     * @param cid
     * @param multipartFile
     * @param fileName
     * @return
     * @throws Exception
     */
    public String updateCommodityPicture(Integer cid, MultipartFile multipartFile, String fileName) throws Exception {
        // 检查商品是否存在
        checkCommodityById(cid);
        // 保存图片
        String url= ImgSave.saveFile(multipartFile,fileName);
        // 将商品图片路径存入数据库
        commodityPicDao.insertCommodityPicture(cid,url);
        return url;
    }

    /**
     * 删除商品图片
     * @param pid
     * @throws ExceptionModel
     */
    public void deleteCommodityPicture(Integer pid) throws ExceptionModel {
        // 检查商品图片是否存在
        int isPic=commodityPicDao.checkPicture(pid);
        if(isPic>0){
            // 删除商品图片
            commodityPicDao.deleteCommodityPicture(pid);
        }else{
            // 商品图片不存在
            throw new ExceptionModel(StatusEnum.COMMODITY_PICTURE_NONEXISTENT);
        }
    }

    /**
     * 删除商品
     * @param cid
     * @throws ExceptionModel
     */
    public void deleteCommodity(Integer cid) throws ExceptionModel {
        // 检查商品是否存在
        checkCommodityById(cid);
        // 删除商品
        commodityDao.deleteCommodity(cid);
    }

    /**
     * 查询订单列表
     * @param pno
     * @param psize
     * @return
     */
    public List<Order> queryAllOrder(Integer pno,Integer psize){
        // 计算数据库起始查询位置
        Integer startPos=(pno-1)*psize;
        // 查询个数
        Integer count=psize;
        // 查询指定数量的商品
        List<Order> orderList=orderDao.queryAllOrder(null);
        List<Order> returnList=new ArrayList<>();
        int listSize=orderList.size();
        for(int i=startPos,countList=0;i<listSize && countList<count;++i){
            returnList.add(orderList.get(i));
            countList++;
        }
        return returnList;
    }

    /**
     * 计算订单数量
     * @return
     */
    public Integer countOrder(){
        Integer count=orderDao.countOrder(null);
        return count;
    }

    /**
     * 计算订单列表页数
     * @param count
     * @param psize
     * @return
     */
    public Integer countOrderListPages(Integer count,Integer psize){
        // 计算页数
        double doubleCount=count.doubleValue();
        double doublePsize=psize.doubleValue();
        Integer totalPages = new Integer((int)Math.ceil(doubleCount/doublePsize));
        return totalPages;
    }

    /**
     * 检查订单是否存在
     * @param oid
     * @throws ExceptionModel
     */
    public void checkOrderById(Integer oid) throws ExceptionModel {
        // 检查订单是否存在
        int isOrder=orderDao.checkOrder(oid);
        if(isOrder==0){
            // 订单不存在
            throw new ExceptionModel(StatusEnum.ORDER_NONEXISTENT);
        }
    }

    /**
     * 查询单个订单信息
     * @param oid
     * @return
     * @throws ExceptionModel
     */
    public Order queryOrderById(Integer oid) throws ExceptionModel {
        // 检查订单是否存在
        checkOrderById(oid);
        // 查询订单
        Order order=orderDao.queryOrderById(oid);
        return order;
    }

    /**
     * 删除订单
     * @param oid
     * @throws ExceptionModel
     */
    public void deleteOrder(Integer oid) throws ExceptionModel {
        // 检查订单是否存在
        checkOrderById(oid);
        // 删除订单
        orderDao.deleteOrderById(oid);
    }

}
