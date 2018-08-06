package my.fast.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.DateFormat;
import my.fast.admin.framework.utils.ParamUtils;
import my.fast.admin.modules.system.entity.SysMenu;
import my.fast.admin.modules.system.entity.SysRole;
import my.fast.admin.modules.system.entity.SysRoleMenu;
import my.fast.admin.modules.system.entity.SysRoleUser;
import my.fast.admin.modules.system.mapper.SysRoleMapper;
import my.fast.admin.modules.system.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public Page<SysRole> getListData(Map map) {
        List<SysRole> list = sysRoleMapper.selectPage(ParamUtils.params2Page(map), ParamUtils.params2Ew(map));
        for (SysRole sysRole : list) {
            String roleUser = "", roleMenu = "", roleMenuName = "";
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
        return new Page<SysRole>().setRecords(list);
    }

    @Override
    @Transactional
    public boolean sysRole2Save(SysRole sysRole) {
        // 插入sys_role
        sysRoleMapper.insert(sysRole);
        // 插入sys_role_user
        insertSysRoleUser(sysRole);
        // 插入sys_role_menu
        insertSysRoleMenu(sysRole);
        return true;
    }

    @Override
    @Transactional
    public boolean sysRole2Update(SysRole sysRole) {
        // 更新sys_role
        sysRoleMapper.updateById(sysRole);
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
        return true;
    }

    @Override
    @Transactional
    public boolean sysRole2Delete(List<SysRole> sysRoles) {
        List<String> ids = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            ids.add(sysRole.getRoleId());
            EntityWrapper ew = new EntityWrapper();
            ew.eq("role_id",sysRole.getRoleId());
            sysRoleUserService.delete(ew);
            sysRoleMenuService.delete(ew);
        }
        sysRoleMapper.deleteBatchIds(ids);
        return false;
    }

    @Override
    @Transactional
    public boolean sysRole2UserGrant(Map map) {
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
        return true;
    }

    @Override
    @Transactional
    public boolean sysRole2MenuGrant(Map map) {
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
        return true;
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
                sysRoleUser.setCreateTime(sysRole.getCreateTime());
                sysRoleUser.setCreateUser(sysRole.getCreateUser());
                sysRoleUser.setCreateOrg(sysRole.getCreateOrg());
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
                sysRoleMenu.setCreateTime(sysRole.getCreateTime());
                sysRoleMenu.setCreateUser(sysRole.getCreateUser());
                sysRoleMenu.setCreateOrg(sysRole.getCreateOrg());
                sysRoleMenuService.insert(sysRoleMenu);
            }
        }
    }
}
