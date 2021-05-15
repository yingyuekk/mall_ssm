package cn.xq.ssm.service;

import cn.xq.ssm.entity.User;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
public interface UserService {

    String login(String username, String password);
    void register(User user);
    User getUserByToken(String token);
    Integer checData(String param, int type);
}
