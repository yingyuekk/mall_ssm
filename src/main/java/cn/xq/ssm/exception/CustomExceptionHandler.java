package cn.xq.ssm.exception;

import cn.xq.ssm.common.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiong.xie
 * @description 全局异常处理
 * @date 2021/5/14
 */
@ControllerAdvice
public class CustomExceptionHandler {

    private final static Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e){

        //是不是自定义异常
        if(e instanceof BaseException){
            BaseException baseException = (BaseException) e;
            LOG.error("[业务异常 {}]",e);
            return JsonData.buildCodeAndMsg(baseException.getCode(),baseException.getMsg());
        }else{

            LOG.error("[系统异常 {}]",e);
            return JsonData.buildError("全局异常，未知错误");
        }

    }
}
