package my.fast.admin.modules.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.*;
import my.fast.admin.modules.system.entity.SysMenu;
import my.fast.admin.modules.system.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-25
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/user")
    public Object user() {
        List<SysMenu> menus = sysMenuService.getMenu(ShiroUtils.getLoginId());
        MenuTree mt = new MenuTree(menus);
        return mt.buildTree();
    }

    @RequestMapping("/list")
    public Object list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("menu/list");
        return mav;
    }

    @RequestMapping("/listData")
    public R listData(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        Page page = sysMenuService.getMenuListByPage(ParamUtils.params2Page(map), map);
        return R.success(page.getRecords(), page.getTotal());
    }

    @RequestMapping("/add")
    public Object add() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("menu/add");
        return mav;
    }

    @RequestMapping("/save")
    @RequiresPermissions("menu:add")
    @CacheEvict(value = "sys_menu:*", allEntries = true)
    public R save(@RequestBody SysMenu sysMenu) {
        String menuType = sysMenu.getMenuType();
        if (menuType.equals(GlobalConst.MenuType.CATALOG.getValue())) {
            EntityWrapper ew = new EntityWrapper();
            ew.eq("menu_type","0")
              .setSqlSelect("max(menu_id)");
            String o = (String) sysMenuService.selectObj(ew);
            String menuId = (Integer.parseInt(o) + 1) + "";
            sysMenu.setMenuId(menuId);
            sysMenu.setMenuPid("0");
        }else {
            EntityWrapper ew = new EntityWrapper();
            ew.eq("menu_pid", sysMenu.getMenuPid())
                    .eq("menu_type", sysMenu.getMenuType())
                    .setSqlSelect("max(menu_id)");
            String o = (String) sysMenuService.selectObj(ew);
            String menuId = "";
            if (StringUtils.isNotBlank(o)) {
                menuId = (Integer.parseInt(o) + 1) + "";
            }else {
                menuId = sysMenu.getMenuPid() + "01";
            }
            sysMenu.setMenuId(menuId);
        }
        sysMenu.setCreateTime(DateFormat.getNowDate());
        sysMenu.setCreateUser(ShiroUtils.getLoginId());
        sysMenu.setCreateOrg(ShiroUtils.getUserEntity().getOrgid());
        sysMenuService.insert(sysMenu);
        return R.success();
    }

    @RequestMapping("/update")
    @RequiresPermissions("menu:update")
    @CacheEvict(value = "sys_menu:*", allEntries = true)
    public R update(@RequestBody SysMenu sysMenu){
        sysMenu.setUpdateUser(ShiroUtils.getLoginId());
        sysMenu.setUpdateOrg(ShiroUtils.getUserEntity().getOrgid());
        sysMenuService.updateById(sysMenu);
        return R.success();
    }

    @RequestMapping("/del")
    @RequiresPermissions("menu:del")
    @CacheEvict(value = "sys_menu:*", allEntries = true)
    public R delete(@RequestBody List<SysMenu> menus){
        List<String> ids = new ArrayList<>();
        for (SysMenu menu : menus) {
            ids.add(menu.getId());
        }
        sysMenuService.deleteBatchIds(ids);
        return R.success();
    }

    @RequestMapping("/tree")
    @Cacheable(value = "sys_menu:sys/menu/tree")
    public R tree() {
        EntityWrapper<SysMenu> ew = new EntityWrapper<>();
        ew.ne("menu_type", "2").orderBy("menu_sort+0", true);
        List<SysMenu> menus = sysMenuService.selectList(ew);
        List<Ztree> ztreeList = new ArrayList<>();
        // 设置根节点
        ztreeList.add(Ztree.treeRoot());
        for (SysMenu menu : menus) {
            Ztree tree = new Ztree();
            tree.setId(menu.getMenuId());
            tree.setName(menu.getMenuName());
            tree.setpId(menu.getMenuPid());
            tree.setOpen("false");
            ztreeList.add(tree);
        }
        return R.success(ztreeList);
    }
}

