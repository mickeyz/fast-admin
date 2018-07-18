package my.fast.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-27
 */
@Data
public class SysOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;
    /**
     * 单位编码
     */
    private String orgCode;
    /**
     * 单位名称
     */
    private String orgName;
    /**
     * 父ID
     */
    private String orgPid;
    /**
     * 使用状态
     */
    private String statue;
    /**
     * 备注
     */
    private String memo;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建单位
     */
    private String createOrg;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改单位
     */
    private String updateOrg;

    /**
     * 上级单位名称
     */
    @TableField(exist = false)
    private String orgPname;

}
