package com.qraffa.easyrentboot.dao;

import com.qraffa.easyrentboot.model.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 收藏夹接口
 */
@Mapper
@Repository
public interface FavoriteDao {

    /***
     * 根据所属用户id查询所有收藏
     * 需要商品表查询
     * @param uid
     * @return
     */
    List<Favorite> queryAllFavoriteById(@Param("uid") Integer uid);

    /***
     * 插入收藏夹
     * @Param uid
     * @Param cid
     * @return
     */
    int insertFavorite(@Param("uid") Integer uid, @Param("cid") Integer cid);

    /***
     * 根据id查询是否有该收藏
     * @param fid
     * @return
     */
    int checkFavorite(@Param("fid") Integer fid);

    /***
     * 根据id删除收藏
     * @param fid
     * @return
     */
    int deleteFavoriteById(@Param("fid") Integer fid);

    /**
     * 查询收藏数量(?)
     * @return
     */
    int countFavorite(@Param("uid") Integer uid);

    /**
     * 检查收藏是否存在
     * @param uid
     * @param cid
     * @return
     */
    int checkFavoriteExistent(@Param("uid") Integer uid, @Param("cid") Integer cid);

}
