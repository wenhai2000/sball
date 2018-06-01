package com.example.demo.support.config;

import com.example.demo.support.result.BusinessException;
import com.example.demo.support.result.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 陈宏 on 2017/8/24.
 */
@ControllerAdvice
//@Slf4j
public class ExceptionHandle {

    private static Logger log = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResult handle(Exception e){
        if(e.getClass() == BusinessException.class){
            BusinessException myException = (BusinessException) e;
            if(myException.getCode()==null || myException.getCode()==""){
                myException.setCode("1111");
            }
            log.info("自定义异常返回信息:{}",e.getMessage());
            return WebResult.ret(myException.getCode(),myException.getMessage());
        }else {
            log.error("系统异常：{}",e);
            return WebResult.ret("-1","系统错误");
        }
    }
}
