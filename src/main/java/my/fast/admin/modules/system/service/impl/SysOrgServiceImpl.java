package my.fast.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import my.fast.admin.modules.system.entity.SysOrg;
import my.fast.admin.modules.system.mapper.SysOrgMapper;
import my.fast.admin.modules.system.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-27
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    @Autowired
    SysOrgMapper sysOrgMapper;

    @Override
    public Page<SysOrg> getOrgListByPage(Page page, Map map) {
        return page.setRecords(sysOrgMapper.getOrgListByPage(page, map));
    }
}
