package com.qraffa.easyrentboot.model.req.commodity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCommodityPicReq {

    private Integer cid;

    private MultipartFile[] multipartFiles;
}
