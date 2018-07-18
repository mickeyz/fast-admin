package my.fast.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-02
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(type = IdType.UUID)
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 使用状态
     */
    private String roleStatus;
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
     * 授权用户
     */
    @TableField(exist = false)
    private String roleUser;
    /**
     * 授权菜单
     */
    @TableField(exist = false)
    private String roleMenu;
    /**
     * 授权菜单名
     */
    @TableField(exist = false)
    private String roleMenuName;

}
