package my.fast.admin.framework.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/25 15:31
 * @Description:
 * freemarker页面上使用shiro标签
 * 1.guest（游客）
 * 2.user（已经登录，或者记住我登录）
 * 3.authenticated（已经认证，排除记住我登录的）
 * 4.notAuthenticated（和authenticated相反）
 * 5.principal(获取登录信息)
 *  注：如果Realm实现类中是 return new SimpleAuthenticationInfo(user,user.getPswd(), getName());
 *  即第一个参数是对象，需要指定property <@shiro.principal property="username"/> 如果第一个参数是值则直接使用 <@shiro. principal/>
 * 6.hasRole标签（判断是否拥有这个角色）
 * 7.hasAnyRoles标签（判断是否拥有这些角色的其中一个）
 * 8.lacksRole标签（判断是否不拥有这个角色）
 * 9.hasPermission标签（判断是否有拥有这个权限）
 * 10.lacksPermission标签（判断是否没有这个权限）
 */
@Component
public class FreeMarkerShiroConfig implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
