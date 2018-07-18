package my.fast.admin.framework.utils;

import my.fast.admin.modules.system.entity.SysMenu;

import java.util.Comparator;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/27 16:14
 * @Description:
 * 菜单排序比较器
 */
public class MenuSortCompare implements Comparator<SysMenu> {
    @Override
    public int compare(SysMenu o1, SysMenu o2) {
        if (Integer.parseInt(o1.getMenuSort()) < Integer.parseInt(o2.getMenuSort())) {
            return -1;
        } else {
            return 1;
        }
    }
}
