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
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
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
    SysRoleService sysRoleService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @RequestMapping("/list")
    public Object list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("role/list");
        return mav;
    }

    @RequestMapping("/listData")
    public R listData(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        Page page = sysRoleService.selectPage(ParamUtils.params2Page(map), ParamUtils.params2Ew(map));
        List<SysRole> pageRecords = page.getRecords();
        for (int i = 0; i < pageRecords.size(); i++) {
            String roleUser = "", roleMenu = "", roleMenuName = "";
            SysRole sysRole = pageRecords.get(i);
            String roleId = sysRole.getRoleId();
            // 查询角色对应用户
            EntityWrapper ew = new EntityWrapper();
            ew.eq("role_id", roleId).setSqlSelect("user_id");
            List<SysRoleUser> sysRoleUsers = sysRoleUserService.selectList(ew);
            for (int k = 0; k < sysRoleUsers.size(); k++) {
                SysRoleUser sysRoleUser = sysRoleUsers.get(k);
                roleUser += k > 0 ? "," + sysRoleUser.getUserId() : sysRoleUser.getUserId();
            }
            sysRole.setRoleUser(roleUser);
            // 查询角色对应菜单
            EntityWrapper ew1 = new EntityWrapper();
            ew1.eq("role_id", roleId).setSqlSelect("menu_id");
            List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.selectList(ew1);
            for (int j = 0; j < sysRoleMenus.size(); j++) {
                SysRoleMenu sysRoleMenu = sysRoleMenus.get(j);
                roleMenu += j > 0 ? "," + sysRoleMenu.getMenuId() : sysRoleMenu.getMenuId();
            }
            sysRole.setRoleMenu(roleMenu);
            // 查询菜单对应名称
            EntityWrapper ew2 = new EntityWrapper();
            ew2.in("menu_id", roleMenu).orderBy("menu_id", true);
            List<SysMenu> sysMenuList = sysMenuService.selectList(ew2);
            for (int l = 0; l < sysMenuList.size(); l++) {
                SysMenu sysMenu = sysMenuList.get(l);
                roleMenuName += l > 0 ? "," + sysMenu.getMenuName() : sysMenu.getMenuName();
            }
            sysRole.setRoleMenuName(roleMenuName);
        }
        return R.success(pageRecords, page.getTotal());
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
    @Transactional
    public R save(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(DateFormat.getStringDate());
        sysRole.setCreateUser(ShiroUtils.getLoginId());
        sysRole.setCreateOrg(ShiroUtils.getOrgId());
        // 插入sys_role
        sysRoleService.insert(sysRole);
        // 插入sys_role_user
        insertSysRoleUser(sysRole);
        // 插入sys_role_menu
        insertSysRoleMenu(sysRole);
        return R.success();
    }

    @RequestMapping("/update")
    @CacheEvict(value = "sys_role:*", allEntries = true)
    @RequiresPermissions("role:update")
    @Transactional
    public R update(@RequestBody SysRole sysRole) {
        // 更新sys_role
        sysRoleService.updateById(sysRole);
        String roleId = sysRole.getRoleId();
        // 删除sys_role_user
        EntityWrapper ew = new EntityWrapper();
        ew.eq("role_id", roleId);
        sysRoleUserService.delete(ew);
        // 插入sys_role_user
        insertSysRoleUser(sysRole);
        // 删除sys_role_menu
        sysRoleMenuService.delete(ew);
        // 插入sys_role_menu
        insertSysRoleMenu(sysRole);
        return R.success();
    }

    @RequestMapping("/del")
    @RequiresPermissions("role:del")
    @CacheEvict(value = "sys_role:*", allEntries = true)
    @Transactional
    public R del(@RequestBody List<SysRole> sysRoles) {
        List<String> ids = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            ids.add(sysRole.getRoleId());
            EntityWrapper ew = new EntityWrapper();
            ew.eq("role_id",sysRole.getRoleId());
            sysRoleUserService.delete(ew);
            sysRoleMenuService.delete(ew);
        }
        sysRoleService.deleteBatchIds(ids);
        return R.success();
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
    @Transactional
    public R userGrant(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        String roleId = map.get("roleId").toString();
        EntityWrapper ew = new EntityWrapper();
        ew.eq("role_id", roleId);
        sysRoleUserService.delete(ew);
        if (StringUtils.isNotBlank(map.get("roleUser").toString())) {
            String[] roleUser = map.get("roleUser").toString().split(",");
            for (String user : roleUser) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setRoleId(roleId);
                sysRoleUser.setUserId(user);
                sysRoleUser.setStatues("1");
                sysRoleUser.setCreateUser(ShiroUtils.getLoginId());
                sysRoleUser.setCreateTime(DateFormat.getStringDate());
                sysRoleUser.setCreateOrg(ShiroUtils.getOrgId());
                sysRoleUserService.insert(sysRoleUser);
            }
        }
        return R.success();
    }

    @RequestMapping("/menuGrant")
    @CacheEvict(value = {"sys_role:*","sys_menu:*"}, allEntries = true)
    @RequiresPermissions("role:update")
    @Transactional
    public R menuGrant(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        String roleId = map.get("roleId").toString();
        EntityWrapper ew = new EntityWrapper();
        ew.eq("role_id", roleId);
        sysRoleMenuService.delete(ew);
        if (StringUtils.isNotBlank(map.get("roleMenu").toString())) {
            String[] roleMenu = map.get("roleMenu").toString().split(",");
            for (String menu : roleMenu) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menu);
                sysRoleMenu.setStatues("1");
                sysRoleMenu.setCreateUser(ShiroUtils.getLoginId());
                sysRoleMenu.setCreateTime(DateFormat.getStringDate());
                sysRoleMenu.setCreateOrg(ShiroUtils.getOrgId());
                sysRoleMenuService.insert(sysRoleMenu);
            }
        }
        return R.success();
    }

    private void insertSysRoleUser(SysRole sysRole) {
        String roleId = sysRole.getRoleId();
        if (StringUtils.isNotBlank(sysRole.getRoleUser())) {
            String[] users = sysRole.getRoleUser().split(",");
            for (String user : users) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setRoleId(roleId);
                sysRoleUser.setUserId(user);
                sysRoleUser.setStatues("1");
                sysRoleUser.setCreateTime(DateFormat.getStringDate());
                sysRoleUser.setCreateUser(ShiroUtils.getLoginId());
                sysRoleUser.setCreateOrg(ShiroUtils.getOrgId());
                sysRoleUserService.insert(sysRoleUser);
            }
        }
    }

    private void insertSysRoleMenu(SysRole sysRole) {
        String roleId = sysRole.getRoleId();
        if (StringUtils.isNotBlank(sysRole.getRoleMenu())) {
            String[] menus = sysRole.getRoleMenu().split(",");
            for (String menu : menus) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menu);
                sysRoleMenu.setStatues("1");
                sysRoleMenu.setCreateTime(DateFormat.getStringDate());
                sysRoleMenu.setCreateUser(ShiroUtils.getLoginId());
                sysRoleMenu.setCreateOrg(ShiroUtils.getOrgId());
                sysRoleMenuService.insert(sysRoleMenu);
            }
        }
    }
}

