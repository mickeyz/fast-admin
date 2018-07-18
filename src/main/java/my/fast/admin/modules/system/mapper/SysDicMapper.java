package my.fast.admin.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import my.fast.admin.modules.system.entity.SysDic;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-06
 */
public interface SysDicMapper extends BaseMapper<SysDic> {
    @Select("select diccode as code,dicname as value from sys_dic where dicdefine = #{dicdefine}")
    List<Map> getDic(String dicdefine);
}
