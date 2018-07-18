package my.fast.admin.modules.system.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.modules.system.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/15 10:34
 * @Description:
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> getListByPage(Page page, Map map);
}
