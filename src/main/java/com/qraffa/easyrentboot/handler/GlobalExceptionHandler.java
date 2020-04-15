package com.qraffa.easyrentboot.handler;

import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理所有异常
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    ReturnModel handleException(ExceptionModel e){
        e.printStackTrace();
        return new ReturnModel().withStatus(e.getStatus());
    }
}

