package my.fast.admin.framework.utils;

import my.fast.admin.modules.system.entity.SysMenu;

import java.util.*;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/22 10:00
 * @Description: 菜单树工具类
 */
public class MenuTree {

    private List<SysMenu> nodes;

    public MenuTree(List<SysMenu> nodes) {
        this.nodes = nodes;
    }

    /**
     * 组装菜单树
     *
     * @return
     */
    public List<Map<String, Object>> buildTree() {
        List<SysMenu> list = new ArrayList<>();
        for (SysMenu menu : nodes) {
            // 判断菜单类型为目录
            if (menu.getMenuPid().equals(GlobalConst.MenuType.CATALOG.getValue())) {
                list.add(menu);
            }
        }
        list = getMenuSort(list);
        // 获取子菜单
        for (SysMenu menu : list) {
            loopChildren(menu);
        }
        return menuTree(list);
    }

    /**
     * 子菜单递归
     *
     * @param menu
     */
    private void loopChildren(SysMenu menu) {
        List<SysMenu> children = getChildren(menu);
        if (!children.isEmpty()) {
            menu.setChildList(children);
            for (SysMenu child : children) {
                loopChildren(child);
            }
        }
    }

    /**
     * 子菜单组装
     *
     * @param menu
     * @return
     */
    private List<SysMenu> getChildren(SysMenu menu) {
        List<SysMenu> children = new ArrayList<>();
        String menuId = menu.getMenuId();
        for (SysMenu child : nodes) {
            // 如果是按钮直接跳出
            if (child.getMenuType().equals(GlobalConst.MenuType.BUTTON.getValue())) {
                continue;
            }
            if (menuId.equals(child.getMenuPid())) {
                children.add(child);
            }
        }
        return getMenuSort(children);
    }

    /**
     * 菜单排序
     *
     * @param menus
     * @return
     */
    private List<SysMenu> getMenuSort(List<SysMenu> menus) {
        MenuSortCompare msc = new MenuSortCompare();
        Collections.sort(menus, msc);
        return menus;
    }

    /**
     * 返回前端菜单所需项
     *
     * @param menuList
     * @return
     */
    public static List<Map<String, Object>> menuTree(List<SysMenu> menuList) {
        List<Map<String, Object>> resultMenus = new ArrayList<>();
        boolean spread = true;
        for (SysMenu menu : menuList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", nullTran(menu.getId()));
            map.put("menuid", nullTran(menu.getMenuId()));
            map.put("title", nullTran(menu.getMenuName()));
            map.put("icon", nullTran(menu.getMenuIcon()));
            map.put("url", nullTran(menu.getMenuUrl()));
            map.put("spread", spread == true ? true : false);
            List<Map<String, Object>> children = new ArrayList<>();
            List<SysMenu> childList = menu.getChildList();
            if (childList != null) {
                for (SysMenu cmenu : childList) {
                    Map<String, Object> cmap = new LinkedHashMap<>();
                    cmap.put("id", nullTran(cmenu.getId()));
                    cmap.put("menuid", nullTran(cmenu.getMenuId()));
                    cmap.put("title", nullTran(cmenu.getMenuName()));
                    cmap.put("icon", nullTran(cmenu.getMenuIcon()));
                    cmap.put("url", nullTran(cmenu.getMenuUrl()));
                    children.add(cmap);
                }
            }
            map.put("children", children);
            resultMenus.add(map);
            spread = false;
        }
        return resultMenus;
    }

    public static Object nullTran(Object o) {
        return o == null ? "" : o;
    }
}
