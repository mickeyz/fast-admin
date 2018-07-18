package my.fast.admin.modules.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import my.fast.admin.modules.system.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-25
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getMenu(String loginid);
    Page<SysMenu> getMenuListByPage(Page page, Map map);
}
