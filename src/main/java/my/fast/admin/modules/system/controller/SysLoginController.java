package my.fast.admin.modules.system.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import my.fast.admin.framework.shiro.ShiroUtils;
import my.fast.admin.framework.utils.ParamUtils;
import my.fast.admin.framework.utils.R;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/14 14:25
 * @Description:
 */
@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;

    @RequestMapping("/")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login.html");
        return mav;
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index/index");
        return mav;
    }

    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @RequestMapping(value = "/sys/login")
    public R login(HttpServletRequest request) {
        Map<String, Object> parMap = ParamUtils.res2ParMap(request.getParameterMap());
        // 从shiro中获取验证码进行比对
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!parMap.get("captcha").toString().equalsIgnoreCase(kaptcha)){
            return R.error("验证码不正确!");
        }
        try{
            Subject subject = ShiroUtils.getSubject();
            String username = parMap.get("username").toString();
            String password = parMap.get("password").toString();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e){
            return R.error("用户名或密码不正确!");
        }catch (IncorrectCredentialsException e){
            return R.error("用户名或密码不正确!");
        }catch (Exception e){
            return R.error("登录认证失败!");
        }
        return R.success("登录成功!");
    }


    @RequestMapping("/getUserEntity")
    public Object getUserEntity() {
        return R.success(ShiroUtils.getUserEntity());
    }

    @RequestMapping("/sys/baseUser")
    public Object baseUser() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", ShiroUtils.getUserEntity());
        mav.setViewName("index/baseUser");
        return mav;
    }

    @RequestMapping("/sys/updatePwd")
    public Object updatePwd() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index/updatePwd");
        return mav;
    }
 }
