package com.qraffa.easyrentboot.model;

import lombok.Data;

@Data
public class ReturnModel {
    private Integer code;
    private String msg;
    private Object data;

    public ReturnModel withOkData(Object data){
        this.withStatus(StatusEnum.OK).withData(data);
        return this;
    }

    public ReturnModel withData(Object data){
        this.setData(data);
        return this;
    }

    public ReturnModel withStatus(StatusEnum status){
        this.setCode(status.getCode());
        this.setMsg(status.getMsg());
        return this;
    }
}
