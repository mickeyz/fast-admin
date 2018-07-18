package my.fast.admin.modules.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.framework.exception.MyException;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.DateFormat;
import my.fast.admin.framework.utils.ParamUtils;
import my.fast.admin.framework.utils.R;
import my.fast.admin.framework.utils.SysUtils;
import my.fast.admin.modules.system.entity.SysUser;
import my.fast.admin.modules.system.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/22 16:03
 * @Description:
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private static final String PWD = "123456";

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("/info")
    public Object userinfo(){
        return R.success(ShiroUtils.getUserName());
    }

    @RequestMapping("/list")
    public Object userManager() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/list");
        return mav;
    }

    @RequestMapping("/listData")
    public R getUserList(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        Page selectPage = sysUserService.getListByPage(ParamUtils.params2Page(map),map);
        return R.success(selectPage.getRecords(),selectPage.getTotal());
    }

    @RequestMapping("/add")
    public Object userAdd() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/add");
        return mav;
    }

    @RequestMapping("/save")
    // 权限验证
    @RequiresPermissions("user:add")
    @CacheEvict(value = "sys_user:*", allEntries = true)
    public R userSave(@RequestBody SysUser user) {
        EntityWrapper<SysUser> ew = new EntityWrapper<>();
        ew.eq("loginid",user.getLoginid());
        SysUser selectOne = sysUserService.selectOne(ew);
        if (selectOne != null) {
            return R.error("用户已存在!");
        }
        // 创建16位的盐
        String salt = SysUtils.createSalt(16);
        user.setSalt(salt);
        // 以MD5码+盐 散列加密
        SimpleHash password = new SimpleHash("MD5", PWD, salt);
        user.setPassword(password.toString());
        user.setCreate_time(DateFormat.getStringDate());
        user.setCreate_user(ShiroUtils.getLoginId());
        user.setCreate_org(ShiroUtils.getUserEntity().getOrgid());
        return R.success(sysUserService.insert(user));
    }

    @RequestMapping("/update")
    @RequiresPermissions("user:update")
    @CacheEvict(value = "sys_user:*", allEntries = true)
    public R userUpdate(@RequestBody SysUser user){
        sysUserService.updateById(user);
        return R.success();
    }

    @RequestMapping("/del")
    @RequiresPermissions("user:del")
    @CacheEvict(value = "sys_user:*", allEntries = true)
    public R userDel(@RequestBody List<SysUser> users){
        List<String> ids = new ArrayList<>();
        for (SysUser user : users) {
            ids.add(user.getId());
        }
        sysUserService.deleteBatchIds(ids);
        return R.success();
    }

    @RequestMapping("/reset")
    @RequiresPermissions("user:resetPwd")
    @CacheEvict(value = "sys_user:*", allEntries = true)
    public R resetPwd(@RequestBody List<SysUser> users){
        List<SysUser> list = new ArrayList<>();
        for (SysUser user : users) {
            SysUser u = new SysUser();
            String salt = SysUtils.createSalt(16);
            SimpleHash password = new SimpleHash("MD5", PWD, salt);
            u.setId(user.getId());
            u.setSalt(salt);
            u.setPassword(password.toString());
            list.add(u);
        }
        sysUserService.updateBatchById(list);
        return R.success();
    }

    @RequestMapping("/updatePwd")
    @CacheEvict(value = "sys_user:*", allEntries = true)
    public R updatePwd(String opwd, String npwd) {
        SimpleHash password = new SimpleHash("MD5", opwd, ShiroUtils.getUserEntity().getSalt());
        if (!password.toString().equals(ShiroUtils.getUserEntity().getPassword())) {
            throw new MyException("原密码错误,请输入正确密码!");
        }
        String salt = SysUtils.createSalt(16);
        SimpleHash newPwd = new SimpleHash("MD5", npwd, salt);
        SysUser user = new SysUser();
        user.setPassword(newPwd.toString());
        user.setSalt(salt);
        user.setId(ShiroUtils.getUserEntity().getId());
        sysUserService.updateById(user);
        return R.success();
    }
}
