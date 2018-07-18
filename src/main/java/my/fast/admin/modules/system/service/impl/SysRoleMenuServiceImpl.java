package my.fast.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import my.fast.admin.modules.system.entity.SysRoleMenu;
import my.fast.admin.modules.system.mapper.SysRoleMenuMapper;
import my.fast.admin.modules.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-03
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Cacheable(value = "sys_role:sys/shiro/parm", keyGenerator = "keyGenerator")
    public List<String> getParmByUser(String loginid) {
        return sysRoleMenuMapper.getParmByUser(loginid);
    }
}
