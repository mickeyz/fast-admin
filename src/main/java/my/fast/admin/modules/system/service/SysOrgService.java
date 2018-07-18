package my.fast.admin.modules.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import my.fast.admin.modules.system.entity.SysOrg;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-27
 */
public interface SysOrgService extends IService<SysOrg> {
    Page<SysOrg> getOrgListByPage(Page page, Map map);
}
