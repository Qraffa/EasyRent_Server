package com.qraffa.easyrentboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/***
 * 商品图片接口
 */
@Mapper
@Repository
public interface CommodityPicDao {

    /***
     * 根据id插入图片信息
     * @param cid
     * @param file
     * @return
     */
    int insertCommodityPicture(@Param("cid") Integer cid, @Param("file") String file);

    /***
     * 根据pid查询是否有该图片
     * @param pid
     * @return
     */
    int checkPicture(@Param("pid") Integer pid);

    /***
     * 根据id删除图片
     * @param pid
     * @return
     */
    int deleteCommodityPicture(@Param("pid") Integer pid);

}
