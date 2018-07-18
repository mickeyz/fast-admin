package my.fast.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ChenQingSong
 * @since 2018-06-25
 */
@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;
    /**
     * 菜单ID
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单父级ID
     */
    private String menuPid;
    /**
     * 菜单地址
     */
    private String menuUrl;
    /**
     * 权限值
     */
    private String menuParm;
    /**
     * 菜单类型
     */
    private String menuType;
    /**
     * 菜单图片
     */
    private String menuIcon;
    /**
     * 菜单排序
     */
    private String menuSort;
    /**
     * 创建时间
     */
    private Date createTime;
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
    @TableField(update="now()")
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改单位
     */
    private String updateOrg;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> childList;

    /**
     * 上级菜单
     */
    @TableField(exist = false)
    private String menuPname;

    /**
     * 菜单类型名称
     */
    @TableField(exist = false)
    private String typeName;

}
