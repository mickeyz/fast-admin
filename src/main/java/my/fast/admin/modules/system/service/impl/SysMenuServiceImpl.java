package my.fast.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import my.fast.admin.modules.system.entity.SysMenu;
import my.fast.admin.modules.system.mapper.SysMenuMapper;
import my.fast.admin.modules.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    @Cacheable(value = "sys_menu:sys/menu/user", keyGenerator = "keyGenerator")
    public List<SysMenu> getMenu(String loginid) {
        return sysMenuMapper.getMenu(loginid);
    }


    @Override
    public Page<SysMenu> getMenuListByPage(Page page,Map map) {
        return page.setRecords(sysMenuMapper.getMenuListByPage(page,map));
    }
}
