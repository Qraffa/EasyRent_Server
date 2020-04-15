package com.qraffa.easyrentboot.controller;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Commodity;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.commodity.*;
import com.qraffa.easyrentboot.model.res.commodity.GetCommodityListRes;
import com.qraffa.easyrentboot.model.res.commodity.GetCommodityRes;
import com.qraffa.easyrentboot.model.res.commodity.GetMyCommodityListRes;
import com.qraffa.easyrentboot.model.res.commodity.entity.ListCommodity;
import com.qraffa.easyrentboot.model.utils.SessionUser;
import com.qraffa.easyrentboot.service.CommodityService;
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
@Api(tags = "商品接口")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询商品列表
     * 可以带标题查询
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/commodity")
    @ApiOperation(value = "查询商品列表")
    public ReturnModel getCommodityList(@Validated GetCommodityListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        GetCommodityListRes res = new GetCommodityListRes();
        // 查询商品数量
        Integer count=commodityService.countCommodity(req.getTitle(),null);
        // 查询商品列表
        List<ListCommodity> commodityList=commodityService.queryAllCommodity(req.getPno(),req.getPsize(),req.getTitle(),null);
        // 查询商品列表页数
        Integer pages=commodityService.countPages(count,req.getPsize());
        // 设置商品数量
        res.setTotalCommodities(count);
        // 设置商品列表
        res.setCommodityList(commodityList);
        // 设置商品列表页数
        res.setTotalPages(pages);
        // 设置商品页码
        res.setPno(req.getPno());
        return new ReturnModel().withOkData(res);
    }

    /**
     * 查询用户自己发布的商品列表
     * 可以带标题查询
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/commodity_my")
    @ApiOperation(value = "查询用户自己发布的商品列表")
    public ReturnModel getMyCommodityList(@Validated GetMyCommodityListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        GetMyCommodityListRes res = new GetMyCommodityListRes();
        // 查询商品数量
        Integer count=commodityService.countCommodity(req.getTitle(),Integer.valueOf(jwtUtil.getUserId()));
        // 查询商品列表
        List<ListCommodity> commodityList=commodityService.queryAllCommodity(req.getPno(),req.getPsize(),req.getTitle(),Integer.valueOf(jwtUtil.getUserId()));
        // 查询商品列表页数
        Integer pages=commodityService.countPages(count,req.getPsize());
        // 设置商品数量
        res.setTotalCommodities(count);
        // 设置商品列表
        res.setCommodityList(commodityList);
        // 设置商品列表页数
        res.setTotalPages(pages);
        // 设置商品页码
        res.setPno(req.getPno());
        return new ReturnModel().withOkData(res);
    }

    /**
     * 查询单个商品信息
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/commodity_detail")
    @ApiOperation(value = "查询单个商品信息")
    public ReturnModel getCommodity(@Validated GetCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 查询商品信息
        Commodity commodity=commodityService.queryCommodity(req.getCid());
        GetCommodityRes res = new GetCommodityRes();
        res.setCommodity(commodity);
        // 根据商品查询商品发布者的nickName
        res.setNickName(commodityService.getNickNameById(commodity.getUserId()));
        return new ReturnModel().withOkData(res);
    }

    /**
     * 发布商品
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PostMapping("/commodity")
    @ApiOperation(value = "发布商品")
    public ReturnModel postCommodity(@RequestBody @Validated PostCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        Commodity commodity=new Commodity();
        BeanUtils.copyProperties(req,commodity);
        commodity.setUserId(Integer.valueOf(jwtUtil.getUserId()));
        // 添加商品
        commodityService.insertCommodity(commodity);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 修改商品信息
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PutMapping("/commodity")
    @ApiOperation(value = "修改商品信息")
    public ReturnModel putCommodity(@RequestBody @Validated PutCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        Commodity commodity=new Commodity();
        BeanUtils.copyProperties(req,commodity);
        // 更新商品信息
        commodityService.updateCommodity(commodity,Integer.valueOf(jwtUtil.getUserId()));
        return new ReturnModel().withOkData(null);
    }

    /**
     * 删除商品
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @DeleteMapping("/commodity")
    @ApiOperation(value = "删除商品")
    public ReturnModel deleteCommodity(@RequestBody @Validated DeleteCommodityReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 删除商品
        commodityService.deleteCommodity(req.getCid(),Integer.valueOf(jwtUtil.getUserId()));
        return new ReturnModel().withOkData(null);
    }

}
