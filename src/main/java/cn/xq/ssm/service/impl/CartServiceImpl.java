package cn.xq.ssm.service.impl;

import cn.xq.ssm.common.JedisClient;
import cn.xq.ssm.common.JsonUtils;
import cn.xq.ssm.entity.Item;
import cn.xq.ssm.mapper.ItemMapper;
import cn.xq.ssm.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void addCart(Long id, Long itemId, Integer num) {
        /** 向Redis中添加购物车数据 */
        /** 数据类型为hash key：用户id；field：商品id；value：商品信息 */
        /** 判断商品是否存在 */
        Boolean heXiSts = jedisClient.heXiSts(REDIS_CART_PRE + ":" + id, itemId + "");
        /** 如果存在数量相加 */
        if (heXiSts){
            String json = jedisClient.hGet(REDIS_CART_PRE + ":" + id, itemId + "");
            /** 把json数据转成 Item */
            Item item = JsonUtils.jsonToPojo(json, Item.class);
            item.setNum(item.getNum()+num);
            /** 存回数据 */
            jedisClient.hSet(REDIS_CART_PRE+":"+id,itemId+"",JsonUtils.objectToJson(item));
        }
        /** 如果不存在 */
        Item item = itemMapper.selectByPrimaryKey(itemId);
        item.setNum(num);
        String images = item.getImage();
        if (StringUtils.isNoneBlank(images)){
            item.setImage(images.split(",")[0]);
        }
        /** 添家商品到购物车列表 */
        jedisClient.hSet(REDIS_CART_PRE+":"+id,itemId+"",JsonUtils.objectToJson(item));
    }

    @Override
    public void mergeCart(Long id, List<Item> cartList) {
        //遍历商品列表
        //把列表添加到购物车
        //判断购物车中是否有此商品
        //如果有，数量相加
        //没有，添加一个新的商品
        for (Item item : cartList){
            addCart(id,item.getId(),item.getNum());
        }
    }

    @Override
    public List<Item> getCartList(Long id) {
        /** 根据用户id查询购物车列表 */
        List<String> jsonList = jedisClient.hVaLs(REDIS_CART_PRE + ":" + id);
        List<Item> list = new ArrayList<>();
        for (String json: jsonList){
            Item item = JsonUtils.jsonToPojo(json, Item.class);
            list.add(item);
        }
        return list;
    }

    @Override
    public void updateCartNum(Long id, Long itemId, Integer num) {
        /** 从redis中取出商品 */
        String json = jedisClient.hGet(REDIS_CART_PRE+":"+id,itemId+"");
        /** 更新商品数量 */
        Item item = JsonUtils.jsonToPojo(json, Item.class);
        item.setNum(num);
        /** 写入 redis */
        jedisClient.hSet(REDIS_CART_PRE+":"+id,itemId+"",JsonUtils.objectToJson(item));
    }

    @Override
    public void deleteCartItem(Long id, Long itemId) {
        /** 删除购物车中的商品 */
        jedisClient.hDel(REDIS_CART_PRE+":"+id,itemId+"");
    }

    @Override
    public void clearCartItem(long userId) {
        /** 删除购物车信息 */
        jedisClient.hDel(REDIS_CART_PRE+":"+userId);
    }

}
