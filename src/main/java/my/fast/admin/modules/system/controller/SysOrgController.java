package my.fast.admin.modules.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.DateFormat;
import my.fast.admin.framework.utils.ParamUtils;
import my.fast.admin.framework.utils.R;
import my.fast.admin.framework.utils.Ztree;
import my.fast.admin.modules.system.entity.SysOrg;
import my.fast.admin.modules.system.service.SysOrgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ChenQingSong
 * @since 2018-06-27
 */
@RestController
@RequestMapping("/sys/org")
public class SysOrgController {

    @Autowired
    SysOrgService sysOrgService;

    @RequestMapping("/tree")
    @Cacheable(value = "sys_org:sys/org/tree")
    public R tree(){
        EntityWrapper<SysOrg> ew = new EntityWrapper<>();
        ew.orderBy("org_code", true);
        List<SysOrg> orgList = sysOrgService.selectList(ew);
        List<Ztree> ztreeList = new ArrayList<>();
        // 设置根节点
        ztreeList.add(Ztree.treeRoot());
        for (SysOrg org : orgList) {
            Ztree tree = new Ztree();
            tree.setId(org.getOrgCode());
            tree.setName(org.getOrgName());
            tree.setpId(org.getOrgPid());
            tree.setOpen("false");
            ztreeList.add(tree);
        }
        return R.success(ztreeList);
    }

    @RequestMapping("/list")
    public Object list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("org/list");
        return mav;
    }

    @RequestMapping("/listData")
    public R listData(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        Page<SysOrg> listByPage = sysOrgService.getOrgListByPage(ParamUtils.params2Page(map), map);
        return R.success(listByPage.getRecords(), listByPage.getTotal());
    }

    @RequestMapping("/add")
    public Object add() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("org/add");
        return mav;
    }

    @RequestMapping("/save")
    @CacheEvict(value = "sys_org:*", allEntries = true)
    @RequiresPermissions("org:add")
    public R save(@RequestBody SysOrg sysOrg) {
        sysOrg.setCreateUser(ShiroUtils.getLoginId());
        sysOrg.setCreateOrg(ShiroUtils.getOrgId());
        sysOrg.setCreateTime(DateFormat.getStringDate());
        sysOrgService.insert(sysOrg);
        return R.success();
    }

    @RequestMapping("/update")
    @CacheEvict(value = "sys_org:*", allEntries = true)
    @RequiresPermissions("org:update")
    public R update(@RequestBody SysOrg sysOrg) {
        sysOrg.setUpdateUser(ShiroUtils.getLoginId());
        sysOrg.setUpdateOrg(ShiroUtils.getOrgId());
        sysOrg.setUpdateTime(DateFormat.getStringDate());
        sysOrgService.updateById(sysOrg);
        return R.success();
    }

    @RequestMapping("/del")
    @CacheEvict(value = "sys_org:*", allEntries = true)
    @RequiresPermissions("org:del")
    public R delete(@RequestBody List<SysOrg> orgs) {
        List<String> ids = new ArrayList<>();
        for (SysOrg sysOrg : orgs) {
            ids.add(sysOrg.getId());
        }
        sysOrgService.deleteBatchIds(ids);
        return R.success();
    }

}

