package my.fast.admin.modules.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-07-06
 */
@Data
public class SysDic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典表编号
     */
    private String id;
    /**
     * 字典定义
     */
    private String dicdefine;
    /**
     * 字典描述
     */
    private String dicdesc;
    /**
     * 字典编码
     */
    private String diccode;
    /**
     * 字典名称
     */
    private String dicname;
    /**
     * 使用状态
     */
    private String isuse;
    /**
     * 创建人
     */
    private String crtUser;
    /**
     * 创建时间
     */
    private String crtTime;

}
