package my.fast.admin.modules.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.DateFormat;
import my.fast.admin.framework.utils.ParamUtils;
import my.fast.admin.framework.utils.R;
import my.fast.admin.modules.system.entity.SysDic;
import my.fast.admin.modules.system.service.SysDicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @since 2018-07-06
 */
@RestController
@RequestMapping("/sys/dic")
public class SysDicController {

    @Autowired
    private SysDicService sysDicService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/getDicByDefine")
    @Cacheable(value = "sys_dic", key = "#dicdefine")
    public R getDicByDefine(@RequestParam String dicdefine) {
        return R.success(sysDicService.getDic(dicdefine));
    }

    @RequestMapping("/list")
    public Object list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dic/list");
        return mav;
    }

    @RequestMapping("/listData")
    public R listData(HttpServletRequest request) {
        Map<String, Object> map = ParamUtils.res2ParMap(request.getParameterMap());
        Page page = sysDicService.selectPage(ParamUtils.params2Page(map), ParamUtils.params2Ew(map));
        return R.success(page.getRecords(), page.getTotal());
    }

    @RequestMapping("/getDic")
    public R getDic() {
        return R.success(sysDicService.getDic("sex"));
    }

    @RequestMapping("/add")
    public Object add() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dic/add");
        return mav;
    }

    @RequestMapping("/save")
    @RequiresPermissions("dic:add")
    public R save(@RequestBody SysDic sysDic) {
        sysDic.setCrtTime(DateFormat.getStringDate());
        sysDic.setCrtUser(ShiroUtils.getLoginId());
        sysDicService.insert(sysDic);
        return R.success();
    }

    @RequestMapping("/update")
    @CacheEvict(value = "sys_dic", key = "#sysDic.dicdefine", allEntries = true)
    @RequiresPermissions("dic:update")
    public R update(@RequestBody SysDic sysDic) {
        sysDicService.updateById(sysDic);
        return R.success();
    }

    @RequestMapping("/del")
    @RequiresPermissions("dic:del")
    public R del(@RequestBody List<SysDic> dics) {
        List<String> ids = new ArrayList<>();
        for (SysDic dic : dics) {
            ids.add(dic.getId());
            redisTemplate.delete("sys_dic::" + dic.getDicdefine());
        }
        sysDicService.deleteBatchIds(ids);
        return R.success();
    }

}

