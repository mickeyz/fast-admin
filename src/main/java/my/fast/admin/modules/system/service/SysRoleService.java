package my.fast.admin.modules.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import my.fast.admin.modules.system.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-02
 */
public interface SysRoleService extends IService<SysRole> {
    Page<SysRole> getListData(Map map);
    boolean sysRole2Save(SysRole sysRole);
    boolean sysRole2Update(SysRole sysRole);
    boolean sysRole2Delete(List<SysRole> sysRoles);
    boolean sysRole2UserGrant(Map map);
    boolean sysRole2MenuGrant(Map map);
}
