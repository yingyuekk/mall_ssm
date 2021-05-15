package cn.xq.ssm.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiong.xie
 * @description 页面控制器
 * @date 2021/5/6
 */
@Api(value = "页面控制器",tags = "页面控制器")
@Controller
public class PageController {

    @ApiOperation(value = "跳转到首页",httpMethod = "GET")
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
}
