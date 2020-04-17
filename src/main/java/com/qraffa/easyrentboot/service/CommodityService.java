package com.qraffa.easyrentboot.service;

import com.qraffa.easyrentboot.dao.CommodityDao;
import com.qraffa.easyrentboot.dao.CommodityPicDao;
import com.qraffa.easyrentboot.dao.UserDao;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Commodity;
import com.qraffa.easyrentboot.model.entity.CommodityPic;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.res.commodity.entity.ListCommodity;
import com.qraffa.easyrentboot.model.utils.ImgSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommodityService {

    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private CommodityPicDao commodityPicDao;
    @Autowired
    private UserDao userDao;

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
     * 查询商品列表
     * title表示模糊查询标题,非必须
     * uid表示查询某用户的商品列表,非必须
     * @param pno
     * @param psize
     * @param title
     * @return
     */
    public List<ListCommodity> queryAllCommodity(Integer pno,Integer psize,String title,Integer uid) throws ExceptionModel {
        // 计算数据库起始查询位置
        Integer startPos=(pno-1)*psize;
        // 查询个数
        Integer count=psize;
        // 查询指定数量的商品
        List<Commodity> commodityList=commodityDao.queryAllCommodity(title,uid);
        List<ListCommodity> returnList=new ArrayList<>();
        int listSize=commodityList.size();
        for(int i=startPos,countList=0;i<listSize && countList<count;++i){
            ListCommodity listCommodity=new ListCommodity();
            // 设置商品
            listCommodity.setCommodity(commodityList.get(i));
            // 设置nickName
            listCommodity.setNickName(getNickNameById(commodityList.get(i).getUserId()));
            returnList.add(listCommodity);
            countList++;
        }
        return returnList;
    }

    /**
     * 查询商品数量
     * title表示模糊查询标题,非必须
     * uid表示查询某用户的商品列表,非必须
     * @param title
     * @param uid
     * @return
     */
    public Integer countCommodity(String title,Integer uid){
        Integer count = commodityDao.countCommodity(title,uid);
        return count;
    }

    /**
     * 计算商品列表页数
     * @param count
     * @param psize
     * @return
     */
    public Integer countPages(Integer count,Integer psize){
        // 计算页数
        double doubleCount=count.doubleValue();
        double doublePsize=psize.doubleValue();
        Integer totalPages = new Integer((int)Math.ceil(doubleCount/doublePsize));
        return totalPages;
    }

    /**
     * 查询单个商品的信息
     * @param cid
     * @return
     */
    public Commodity queryCommodity(Integer cid) throws ExceptionModel {
        // 检查商品是否存在
        checkCommodityById(cid);
        // 查询单个商品信息
        Commodity commodity=commodityDao.queryCommodityById(cid);
        return commodity;
    }

    /**
     * 添加商品
     * @param commodity
     * @throws ExceptionModel
     */
    public Integer insertCommodity(Commodity commodity) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(commodity.getUserId());
        // 添加商品
        commodityDao.insertCommodity(commodity);
        Integer cid = commodity.getCid();
        return cid;
    }

    /**
     * 更新商品信息
     * @param commodity
     * @throws ExceptionModel
     */
    public void updateCommodity(Commodity commodity,Integer uid) throws ExceptionModel {
        // 检查商品和用户是否存在
        checkUserById(uid);
        checkCommodityById(commodity.getCid());
        // 查询商品的用户id
        Integer commodityUid=commodityDao.queryUserIdByCommodityId(commodity.getCid());
        // 检查session中的id和商品的用户id是否相同
        if(uid!=commodityUid){
            // 两个id不相同
            throw new ExceptionModel(StatusEnum.UNAUTHORIZED);
        }
        // 更新商品信息
        commodityDao.updateCommodity(commodity);
    }

    /**
     * 删除商品信息
     * @param cid
     * @param uid
     * @throws ExceptionModel
     */
    public void deleteCommodity(Integer cid,Integer uid) throws ExceptionModel {
        // 检查商品和用户是否存在
        checkUserById(uid);
        checkCommodityById(cid);
        // 查询商品的用户用户id
        Integer checkUid=commodityDao.queryUserIdByCommodityId(cid);
        // 检查id是否相同
        if(checkUid!=uid){
            // 两个id不相同
            throw new ExceptionModel(StatusEnum.UNAUTHORIZED);
        }
        // 删除商品
        commodityDao.deleteCommodity(cid);
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

    /**
     * 更新商品图片
     * @param cid
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public void updateCommodityPicture(Integer cid, MultipartFile[] multipartFile) throws Exception {
        // 检查商品是否存在
        checkCommodityById(cid);
        int len = multipartFile.length;
        for (int i = 0; i < len; i++) {
            MultipartFile excelFile = multipartFile[i];
            // 校验传入文件有效性
            if (excelFile==null || excelFile.isEmpty()){
                throw new RuntimeException("文件传入错误");
            }
            String fileName = String.format("%s_%s",new Date().getTime(),excelFile.getOriginalFilename());
            // 保存图片
            String url= ImgSave.saveFile(excelFile,fileName);
            // 将商品图片路径存入数据库
            commodityPicDao.insertCommodityPicture(cid,url);
        }
    }
}
