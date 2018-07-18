package my.fast.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import my.fast.admin.modules.system.entity.SysDic;
import my.fast.admin.modules.system.mapper.SysDicMapper;
import my.fast.admin.modules.system.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-06
 */
@Service
public class SysDicServiceImpl extends ServiceImpl<SysDicMapper, SysDic> implements SysDicService {

    @Autowired
    SysDicMapper sysDicMapper;

    @Override
    public List<Map> getDic(String dicdefine) {
        return sysDicMapper.getDic(dicdefine);
    }
}
