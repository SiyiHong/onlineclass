package onlineclass.exception;

import onlineclass.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class CustomExceptionHandler {

    private final static Logger logger= LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)//指示处理哪一类的异常
    @ResponseBody//表示返回json数据
    public JsonData handle(Exception e){
        if(e instanceof SelfException){
            logger.error("[系统异常] {}",e);
            SelfException selfException = (SelfException) e;
            return JsonData.buildError(selfException.getCode(),selfException.getMsg());
        }else{
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}
