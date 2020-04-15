package com.qraffa.easyrentboot.model.req.admin;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class PostCommodityPicReq {
    @NotNull
    private Integer cid;
    @NotNull
    private MultipartFile commodityPic;
}
