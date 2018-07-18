package my.fast.admin.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import my.fast.admin.modules.system.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-25
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getMenu(String loginid);
    List<SysMenu> getMenuListByPage(Pagination page, Map map);
}
