package com.qraffa.easyrentboot.model.exception;

import com.qraffa.easyrentboot.model.StatusEnum;

public class ExceptionModel extends Exception{
    private StatusEnum status;
    public ExceptionModel(){
        this.status = StatusEnum.INTERNAL_SERVER_ERROR;
    }

    public ExceptionModel(StatusEnum status){
        this.status = status;
    }

    public StatusEnum getStatus(){
        return this.status;
    }
}
