package cn.xq.ssm.interceptor;

import cn.xq.ssm.common.CookieUtils;
import cn.xq.ssm.entity.User;
import cn.xq.ssm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiong.xie
 * @description 用户登录处理拦截器
 * @date 2021/5/13
 */
public class LoginInterceptor  implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //前处理，执行handle之前执行此方法
        //返回true，放行 false，拦截
        /** 1、从cookie中取token*/
        String token = CookieUtils.getCookieValue(request, "token");
        /** 2、如果没有token,未登录状态，直接放行 */
        if (!StringUtils.isNoneBlank(token)){
            return true;
        }
        /** 3、根据token获取用户信息 */
        User user = userService.getUserByToken(token);
        request.setAttribute("loginUser",user);
        return true;
    }
}
