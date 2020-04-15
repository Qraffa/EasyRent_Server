package com.qraffa.easyrentboot.model.req.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class PostAvatar {
    @NotNull
    private MultipartFile avatar;
}
