package my.fast.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;


/**
 * @Author: ChenQingSong
 * @Date: 2018/6/14 14:51
 * @Description:
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 5443537861551597506L;

    private String id;

    private String loginid;

    private String username;

    private String password;

    private String salt;

    private String orgid;

    @TableField( exist = false)
    private String orgname;

    private String telphone;

    private String email;

    private String addr;

    private String statue;

    private String memo;

    private String create_time;

    private String create_user;

    private String create_org;

}
