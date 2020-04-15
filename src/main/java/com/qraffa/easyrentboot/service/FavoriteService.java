package com.qraffa.easyrentboot.service;

import com.qraffa.easyrentboot.dao.CommodityDao;
import com.qraffa.easyrentboot.dao.FavoriteDao;
import com.qraffa.easyrentboot.dao.UserDao;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Favorite;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommodityDao commodityDao;

    /**
     * 查询用户是否存在
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
     * 查询商品是否存在
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
     * 查询收藏是否存在
     * @param fid
     */
    public void checkFavoriteById(Integer fid) throws ExceptionModel {
        int isFavorite=favoriteDao.checkFavorite(fid);
        if(isFavorite==0){
            // 收藏不存在
            throw new ExceptionModel(StatusEnum.FAVORITE_NONEXISTENT);
        }
    }

    /**
     * 添加收藏
     * @param favorite
     * @throws ExceptionModel
     */
    public void updateFavorite(Favorite favorite) throws ExceptionModel {
        // 检查用户和商品是否存在
        checkUserById(favorite.getUserId());
        checkCommodityById(favorite.getCommodity().getCid());
        // 检查收藏是否重复
        int isFavorite=favoriteDao.checkFavoriteExistent(favorite.getUserId(),favorite.getCommodity().getCid());
        if(isFavorite>0){
            // 收藏重复
            throw new ExceptionModel(StatusEnum.FAVORITE_EXISTENT);
        }else{
            // 添加新收藏
            favoriteDao.insertFavorite(favorite.getUserId(),favorite.getCommodity().getCid());
        }
    }

    /**
     * 删除收藏
     * @param fid
     * @throws ExceptionModel
     */
    public void deleteFavorite(Integer fid) throws ExceptionModel {
        // 检查收藏是否存在
        checkFavoriteById(fid);
        // 删除收藏
        favoriteDao.deleteFavoriteById(fid);
    }

    /**
     * 查询收藏夹列表
     * @param uid
     * @param pno
     * @param psize
     * @return
     */
    public List<Favorite> queryAllFavorite(Integer uid,Integer pno,Integer psize){
        // 计算数据库起始查询位置
        Integer startPos=(pno-1)*psize;
        // 查询个数
        Integer count=psize;
        // 查询指定数量的收藏
        List<Favorite> favoriteList=favoriteDao.queryAllFavoriteById(uid);
        List<Favorite> returnList=new ArrayList<>();
        int listSize=favoriteList.size();
        for(int i=startPos,countList=0;i<listSize && countList<count;++i){
            returnList.add(favoriteList.get(i));
            countList++;
        }
        return returnList;
    }

    /**
     * 查询收藏个数
     * @return
     */
    public Integer countFavorite(Integer uid){
        // 查询收藏个数
        Integer count=favoriteDao.countFavorite(uid);
        return count;
    }

    /**
     * 计算收藏列表页数
     * @param count
     * @param psize
     * @return
     */
    public Integer countFavoriteListPages(Integer count,Integer psize){
        // 计算页数
        double doubleCount=count.doubleValue();
        double doublePsize=psize.doubleValue();
        Integer totalPages = new Integer((int)Math.ceil(doubleCount/doublePsize));;
        return totalPages;
    }

}
