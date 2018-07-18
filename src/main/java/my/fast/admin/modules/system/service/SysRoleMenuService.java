package my.fast.admin.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import my.fast.admin.modules.system.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-03
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    List<String> getParmByUser(String loginid);
}
