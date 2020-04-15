package com.qraffa.easyrentboot.dao;

import com.qraffa.easyrentboot.model.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 商品接口
 */
@Mapper
@Repository
public interface CommodityDao {
    /***
     * 查询所有商品信息
     * startPos,count为必须参数
     * title代表模糊查询标题,非必须
     * uid代表查询卖家id,非必须
     * @return
     */
    List<Commodity> queryAllCommodity(@Param("title") String title, @Param("uid") Integer uid);

    /***
     * 根据id查询商品是否存在
     * @param cid
     * @return
     */
    int checkCommodity(@Param("cid") Integer cid);

    /***
     * 根据id查询单个商品信息
     * 需要商品图片表查询
     * @param cid
     * @return
     */
    Commodity queryCommodityById(@Param("cid") Integer cid);

    /***
     * 更新商品信息
     * @param commodity
     * @return
     */
    int updateCommodity(Commodity commodity);

    /***
     * 根据id查询商品的需要归还时间
     * @param cid
     * @return
     */
    Integer queryRequestTime(@Param("cid") Integer cid);

    /***
     * 根据商品id查询所属用户id
     * @param cid
     * @return
     */
    int queryUserIdByCommodityId(@Param("cid") Integer cid);

    /***
     * 插入商品信息
     * @param commodity
     * @return
     */
    int insertCommodity(Commodity commodity);

    /***
     * 更新商品信息
     * @param commodity
     * @return
     */
    int updateCommodityById(Commodity commodity);

    /***
     * 删除商品
     * @param cid
     * @return
     */
    int deleteCommodity(@Param("cid") Integer cid);

    /***
     * 查询商品数量
     * title代表模糊查询标题,非必须
     * uid代表查询卖家id,非必须
     * @return
     */
    int countCommodity(@Param("title") String title, @Param("uid") Integer uid);

}
