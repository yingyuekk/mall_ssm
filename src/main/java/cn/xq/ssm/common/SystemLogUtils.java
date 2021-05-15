package cn.xq.ssm.common;

import cn.xq.ssm.dto.Log;
import cn.xq.ssm.entity.User;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/14
 */
public class SystemLogUtils {

    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogUtils. class);

    public void writeLog(JoinPoint joinPoint){
        // TODO 日志管理
    }
}
