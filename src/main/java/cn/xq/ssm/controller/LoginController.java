package cn.xq.ssm.controller;

import cn.xq.ssm.common.CookieUtils;
import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.entity.User;
import cn.xq.ssm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
@Api(value = "用户管理",tags = "用户管理")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @ApiOperation(value = "用户登录",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户昵称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password",value = "用户密码", required = true, paramType = "query")
    })
    @PostMapping("/login")
    public JsonData login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response){
        String token = userService.login(username, password);
        CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        return JsonData.buildSuccess(token);
    }

    @ApiOperation(value = "用户注册",httpMethod = "POST")
    @PostMapping("/register")
    public JsonData register(@RequestBody User user){
        userService.register(user);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "校验表单数据(用户名/手机号/邮箱)",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",value = "表单数据", required = true, paramType = "path"),
            @ApiImplicitParam(name = "type",value = "数据类型(1、用户名，2、手机号，3、邮箱)", required = true, paramType = "path")
    })
    @GetMapping("/check/{param}/{type}")
    public JsonData checkDate(@PathVariable String param, @PathVariable Integer type) {
        Integer result = userService.checData(param, type);
        if (result == 1){
            return JsonData.buildSuccess(false);
        }else if (result == 2){
            return JsonData.buildCodeAndMsg(400, "数据类型错误");
        }
        return JsonData.buildSuccess(true);
    }

    @ApiOperation(value = "根据token获取用户信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token", required = true, paramType = "path")
    })
    @GetMapping(value = "/token/{token}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token){
        User user = userService.getUserByToken(token);
        return user;
    }
}
