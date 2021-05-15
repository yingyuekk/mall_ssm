package cn.xq.ssm.service;

import cn.xq.ssm.entity.Item;

import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
public interface CartService {

    void addCart(Long id, Long itemId, Integer num);

    void mergeCart(Long id, List<Item> cartList);

    List<Item> getCartList(Long id);

    void updateCartNum(Long id, Long itemId, Integer num);

    void deleteCartItem(Long id, Long itemId);

    void clearCartItem(long userId);
}
