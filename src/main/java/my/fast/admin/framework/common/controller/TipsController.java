package my.fast.admin.framework.common.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/28 15:35
 * @Description:
 */
@Controller
public class TipsController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "tips/error";
    }
    @RequestMapping("/error")
    public String error() {
        return getErrorPath();
    }
}
