package my.fast.admin.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import my.fast.admin.modules.system.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-03
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    List<String> getParmByUser(String loginid);
}
