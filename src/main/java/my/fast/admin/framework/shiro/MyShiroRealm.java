package my.fast.admin.framework.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import my.fast.admin.modules.system.entity.SysUser;
import my.fast.admin.modules.system.service.SysRoleMenuService;
import my.fast.admin.modules.system.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @Author: ChenQingSong
 * @Date: 2018/6/15 10:22
 * @Description:
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleMenuService menuService;

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo = (SysUser) principals.getPrimaryPrincipal();
        String loginid = userInfo.getLoginid();
        List<String> parmByUser = menuService.getParmByUser(loginid);
        //用户权限列表
        Set<String> parmsSet = new HashSet<String>();
        for (String parm : parmByUser) {
            if (StringUtils.isNotBlank(parm)) {
                parmsSet.add(parm);
            }
        }
        authorizationInfo.setStringPermissions(parmsSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal();
        //获取SysUser对象
        EntityWrapper<SysUser> ew = new EntityWrapper<>();
        ew.eq("loginid",username);
        SysUser userInfo = userService.selectOne(ew);
        if (userInfo == null) {
            return null;
        }
        // 交给shiro进行密码比较
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getSalt()),
                getName()  //realm name
        );
        return authenticationInfo;
    }

}