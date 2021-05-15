package cn.xq.ssm.service.impl;

import cn.xq.ssm.common.JedisClient;
import cn.xq.ssm.common.JsonUtils;
import cn.xq.ssm.entity.User;
import cn.xq.ssm.entity.UserExample;
import cn.xq.ssm.enums.CodeEnum;
import cn.xq.ssm.exception.BaseException;
import cn.xq.ssm.mapper.UserMapper;
import cn.xq.ssm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public String login(String username, String password){
        //1、判断用户名和密码是否正确
        //根据用户名查询用户信息
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(userExample);
        if (list == null || list.size() == 0){
            throw new BaseException(CodeEnum.USER_LOGIN_USERNAME_EX);
        }
        //获取用户信息
        User user = list.get(0);
        //判断密码是否正确
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            //2、如果不正确，返回登录失败
            //返回登录失败
            throw new BaseException(CodeEnum.USER_LOGIN_PASSWORD_EX);
        }
        //3、如果正确生成token
        String token = UUID.randomUUID().toString();
        //4、 把用户信息，写入reids,key:token,value:用户信息
        user.setPassword(null);
        jedisClient.set("SESSION:+" + token, JsonUtils.objectToJson(user));
        //5、设置session的过期时间
        jedisClient.expire("SESSION:+" + token, SESSION_EXPIRE);
        //6、把token返回
        return token;
    }

    @Override
    public void register(User user){
        /** 对数据有效性校验 */
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())) {
            throw new BaseException(CodeEnum.USER_CHECK_EX);
        }
        Integer result = checData(user.getUsername(),1);
        if(result == 1) {
            throw new BaseException(CodeEnum.USER_CHECK_USERNAME_EX);
        }
        result = checData(user.getPhone(), 2);
        if(result == 1) {
            throw new BaseException(CodeEnum.USER_CHECK_PHONE_EX);
        }
        //补全pojo属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对password进行MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //把用户数据插入到数据库中
        userMapper.insert(user);
    }

    @Override
    public User getUserByToken(String token){
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:+" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if(StringUtils.isBlank(json)) {
            throw new BaseException(CodeEnum.USER_TOKEN_OUT);
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire("SESSION:+" + token, SESSION_EXPIRE);
        //返回结果 E3Result其中包含TbUser对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        return user;
    }

    /**
     * @param param
     * @param type
     * @return 0:没有这个数据 / 1：有这个数据 / 2：数据类型错误
     */
    @Override
    public Integer checData(String param, int type){
        /** 根据不同的type生成不同的查询条件 */
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        /** 1、用户名，2、手机号，3、邮箱 */
        if(type == 1) {
            criteria.andUsernameEqualTo(param);
        }else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        }else if (type == 3) {
            criteria.andEmailEqualTo(param);
        }else{
            return 2;
        }
        //执行查询
        List<User> list = userMapper.selectByExample(userExample);
        //判断结果中是否包含数据
        if(list != null && list.size() > 0) {
            //如果有数据返回false
            return 1;
        }
        //若没有返回true
        return 0;
    }
}
