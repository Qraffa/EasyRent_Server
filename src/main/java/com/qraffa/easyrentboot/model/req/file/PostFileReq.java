package com.qraffa.easyrentboot.model.req.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostFileReq {
    private MultipartFile file;
}
