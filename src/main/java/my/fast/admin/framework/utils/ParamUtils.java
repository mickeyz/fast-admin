package my.fast.admin.framework.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/7 16:03
 * @Description:
 */
public class ParamUtils {
    /**
     * 根据request中的getParameterMap获取页面传递的参数（得到所有输入框的name和value）
     * @param map
     * @return
     */
    public static Map<String,Object> res2ParMap(Map<String, String[]> map){
        Map<String,Object> maps = new HashMap<>();
        for (String key:map.keySet()
                ) {
            String[] val = map.get(key);
            maps.put(key,val[0]);
        }
        return maps;
    }

    /**
     * 返回分页参数
     * @param parMap
     * @return
     */
    public static Page params2Page(Map<String, Object> parMap){
        int size = 10;
        int curr = 1;
        String limit = parMap.get("limit").toString();
        String pages = parMap.get("page").toString();
        Page page = new Page();
        page.setSize(limit == null ? size :Integer.parseInt(limit));
        page.setCurrent(pages == null ? curr : Integer.parseInt(pages));
        return page;
    }

    /**
     * 返回查询参数
     * @param parMap
     * @return
     */
    public static EntityWrapper params2Ew(Map<String, Object> parMap){
        EntityWrapper ew = new EntityWrapper();
        Set keySet = parMap.keySet();
        Iterator iterator = keySet.iterator();
        for (Iterator it = iterator; it.hasNext(); ) {
            Object next = it.next();
            if (!next.equals("limit") && !next.equals("page")) {
                if (StringUtils.isNotBlank(parMap.get(next).toString())){
                    ew.eq((String) next,parMap.get(next));
                }
            }
        }
        return ew;
    }

}
