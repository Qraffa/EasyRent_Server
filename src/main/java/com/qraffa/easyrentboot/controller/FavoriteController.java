package com.qraffa.easyrentboot.controller;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Commodity;
import com.qraffa.easyrentboot.model.entity.Favorite;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.favorite.DeleteFavoriteReq;
import com.qraffa.easyrentboot.model.req.favorite.GetFavoriteListReq;
import com.qraffa.easyrentboot.model.req.favorite.PostFavoriteReq;
import com.qraffa.easyrentboot.model.res.favorite.GetFavoriteListRes;
import com.qraffa.easyrentboot.model.utils.SessionUser;
import com.qraffa.easyrentboot.service.FavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "收藏夹接口")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 添加收藏
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @PostMapping("/favorite")
    @ApiOperation(value = "添加收藏")
    public ReturnModel postFavorite(@RequestBody @Validated PostFavoriteReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 构造favorite
        Favorite favorite=new Favorite();
        Commodity commodity=new Commodity();
        favorite.setUserId(Integer.valueOf(jwtUtil.getUserId()));
        commodity.setCid(req.getCid());
        favorite.setCommodity(commodity);
        // 添加收藏
        favoriteService.updateFavorite(favorite);
        return new ReturnModel().withOkData(null);
    }

    /**
     * 删除收藏
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @DeleteMapping("/favorite")
    @ApiOperation(value = "删除收藏")
    public ReturnModel deleteFavorite(@RequestBody @Validated DeleteFavoriteReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 删除收藏
        favoriteService.deleteFavorite(req.getFid());
        return new ReturnModel().withOkData(null);
    }

    /**
     * 查询收藏列表
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/favorite")
    @ApiOperation(value = "查询收藏列表")
    public ReturnModel getFavoriteList(@Validated GetFavoriteListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 校验数据
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");

        GetFavoriteListRes res=new GetFavoriteListRes();
        // 查询收藏数量
        Integer countFavorite=favoriteService.countFavorite(Integer.valueOf(jwtUtil.getUserId()));
        // 查询收藏列表页数
        Integer pages=favoriteService.countFavoriteListPages(countFavorite,req.getPsize());
        // 查询收藏列表
        List<Favorite> favoriteList=favoriteService.queryAllFavorite(Integer.valueOf(jwtUtil.getUserId()),req.getPno(),req.getPsize());
        // 设置收藏数量
        res.setTotalCommodities(countFavorite);
        // 设置页码数量
        res.setTotalPages(pages);
        // 设置页码
        res.setPno(req.getPno());
        // 设置收藏列表
        res.setFavoriteList(favoriteList);
        return new ReturnModel().withOkData(res);
    }
}
