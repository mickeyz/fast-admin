package my.fast.admin.modules.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.DateFormat;
import my.fast.admin.framework.utils.ParamUtils;
import my.fast.admin.framework.utils.R;
import my.fast.admin.framework.utils.Ztree;
import my.fast.admin.modules.system.entity.*;
import my.fast.admin.modules.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-02
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/list")
    public Object list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("role/list");
        return mav;
    }

    @RequestMapping("/listData")
    public R listData(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        Page<SysRole> page = sysRoleService.getListData(map);
        return R.success(page.getRecords(), page.getTotal());
    }

    @RequestMapping("/add")
    public Object add() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("role/add");
        return mav;
    }

    /**
     * 多选下拉框，返回按单位分组的用户信息
     *
     * @return
     */
    @RequestMapping("/user")
//    @Cacheable(value = "sys_user:sys/role/user")
    public R user() {
        EntityWrapper<SysUser> ew = new EntityWrapper<>();
        ew.eq("statue", "1")
                .orderBy("orgname", true)
                .setSqlSelect("loginid,username,getOrgName(orgid) orgname".split(","));
        List<SysUser> list = sysUserService.selectList(ew);
        String orgname = "";
        List<Map<String, Object>> result = new LinkedList<>();
        for (SysUser user : list) {
            Map<String, Object> map = new HashMap<>();
            if (!orgname.equals(user.getOrgname())) {
                Map<String, Object> group = new HashMap<>();
                group.put("name", user.getOrgname());
                group.put("type", "optgroup");
                result.add(group);
                orgname = user.getOrgname();
            }
            map.put("name", user.getUsername());
            map.put("value", user.getLoginid());
            result.add(map);
        }
        return R.success(result);
    }

    /**
     * 菜单树
     *
     * @return
     */
    @RequestMapping("/menu")
    @Cacheable(value = "sys_menu:sys/role/menu")
    public R menu() {
        EntityWrapper<SysMenu> ew = new EntityWrapper<>();
        ew.orderBy("menu_id", true);
        List<SysMenu> menus = sysMenuService.selectList(ew);
        List<Ztree> ztreeList = new ArrayList<>();
        // 设置根节点
        ztreeList.add(Ztree.treeRoot());
        for (SysMenu menu : menus) {
            Ztree tree = new Ztree();
            tree.setId(menu.getMenuId());
            tree.setName(menu.getMenuName());
            tree.setpId(menu.getMenuPid());
            tree.setOpen("true");
            ztreeList.add(tree);
        }
        return R.success(ztreeList);
    }

    @RequestMapping("/save")
    @RequiresPermissions("role:add")
    @CacheEvict(value = "sys_role:*", allEntries = true)
    public R save(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(DateFormat.getStringDate());
        sysRole.setCreateUser(ShiroUtils.getLoginId());
        sysRole.setCreateOrg(ShiroUtils.getOrgId());
        return R.success(sysRoleService.sysRole2Save(sysRole));
    }

    @RequestMapping("/update")
    @CacheEvict(value = "sys_role:*", allEntries = true)
    @RequiresPermissions("role:update")
    public R update(@RequestBody SysRole sysRole) {
        return R.success(sysRoleService.sysRole2Update(sysRole));
    }

    @RequestMapping("/del")
    @RequiresPermissions("role:del")
    @CacheEvict(value = "sys_role:*", allEntries = true)
    public R del(@RequestBody List<SysRole> sysRoles) {
        return R.success(sysRoleService.sysRole2Delete(sysRoles));
    }

    @RequestMapping("/grant")
    public Object grant() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("role/grant");
        return mav;
    }

    @RequestMapping("/userGrant")
    @CacheEvict(value = "sys_role:*", allEntries = true)
    @RequiresPermissions("role:update")
    public R userGrant(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        return R.success(sysRoleService.sysRole2UserGrant(map));
    }

    @RequestMapping("/menuGrant")
    @CacheEvict(value = {"sys_role:*","sys_menu:*"}, allEntries = true)
    @RequiresPermissions("role:update")
    public R menuGrant(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        return R.success(sysRoleService.sysRole2MenuGrant(map));
    }


}

