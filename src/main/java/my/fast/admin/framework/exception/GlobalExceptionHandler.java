package my.fast.admin.framework.exception;

import lombok.extern.slf4j.Slf4j;
import my.fast.admin.framework.utils.R;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/6 16:57
 * @Description:
 * 异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handleMyException(HttpServletRequest req, Exception me) {
        log.error("###Exception###,Url:{}, Msg:{}" ,req.getRequestURI(), me.getMessage());
        String msg = "操作失败，程序内部错误!";
        if(me instanceof MyException){
            msg = me.getMessage();
            return R.error(500, msg);
        }else if(me instanceof DataAccessException){
            msg = "数据库操作失败!";
        }else if(me instanceof NullPointerException){
            msg = "调用了未经初始化的对象或者是不存在的对象!";
        }else if(me instanceof IOException){
            msg = "IO异常!";
        }else if(me instanceof ClassNotFoundException){
            msg = "指定的类不存在!";
        }else if(me instanceof ArithmeticException){
            msg = "数学运算异常!";
        }else if(me instanceof ArrayIndexOutOfBoundsException){
            msg = "数组下标越界!";
        }else if(me instanceof NoSuchMethodException){
            msg = "未找到方法!";
        }else if(me instanceof IllegalArgumentException){
            msg = "方法的参数错误!";
        }else if(me instanceof IllegalAccessException){
            msg = "非法访问错误!";
        }else if(me instanceof InvocationTargetException){
            msg = "目标调用出现异常!";
        }else if(me instanceof ClassCastException){
            msg = "类型强制转换错误!";
        }else if(me instanceof SecurityException){
            msg = "违背安全原则异常!";
        }else if(me instanceof SQLException){
            msg = "操作数据库异常!";
        }
        return R.error(500, msg, me.getMessage());
    }
}
