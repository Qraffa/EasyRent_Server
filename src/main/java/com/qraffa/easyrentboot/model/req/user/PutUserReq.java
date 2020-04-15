package com.qraffa.easyrentboot.model.req.user;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class PutUserReq {
    private String nickName;
    private String email;
    @Range(min = 0,max = 1)
    private Integer gender;
    @Range(min = 0,max = 2)
    private Integer campus;
}
