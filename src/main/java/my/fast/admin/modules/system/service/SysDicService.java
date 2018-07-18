package my.fast.admin.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import my.fast.admin.modules.system.entity.SysDic;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-06
 */
public interface SysDicService extends IService<SysDic> {

    List<Map> getDic(String dicdefine);
}
