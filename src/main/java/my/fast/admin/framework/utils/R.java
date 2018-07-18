package my.fast.admin.framework.utils;


import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/6 15:59
 * @Description:
 *  定义统一返回对象
 */
public class R extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 7484335096174415226L;

    public R() {
        put("code", 0);
    }

    public static R success(){
        return new R();
    }

    public static R success(String msg){
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R success(int code, String msg){
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R success(Object o){
        R r = new R();
        r.put("data", o);
        return r;
    }

    public static R success(Object o, long count){
        R r = new R();
        r.put("data", o);
        r.put("count", count);
        return r;
    }

    public static R error(int code, String msg, Object o) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        r.put("data", o);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(String msg){
        return error(500, msg);
    }

    public static R error(){
        return error(500, "未知错误，请联系管理员!");
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
