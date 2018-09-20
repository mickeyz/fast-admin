package my.fast.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import my.fast.admin.modules.system.entity.SysUser;
import my.fast.admin.modules.system.mapper.SysUserMapper;
import my.fast.admin.modules.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/15 10:48
 * @Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Page<SysUser> getListByPage(Page page, Map map) {
        return page.setRecords(sysUserMapper.getListByPage(page, map));
    }
}
