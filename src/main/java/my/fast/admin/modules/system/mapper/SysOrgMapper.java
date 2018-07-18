package my.fast.admin.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.modules.system.entity.SysOrg;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-27
 */
public interface SysOrgMapper extends BaseMapper<SysOrg> {
    List<SysOrg> getOrgListByPage(Page page, Map map);
}
