package my.fast.admin.modules.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import my.fast.admin.modules.system.entity.SysUser;

import java.util.Map;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/15 10:47
 * @Description:
 */
public interface SysUserService extends IService<SysUser> {
    Page<SysUser> getListByPage(Page page, Map map);
}
