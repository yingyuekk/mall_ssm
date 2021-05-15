package cn.xq.ssm.controller;

import cn.xq.ssm.common.CookieUtils;
import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.common.JsonUtils;
import cn.xq.ssm.dto.ItemDto;
import cn.xq.ssm.entity.Item;
import cn.xq.ssm.entity.User;
import cn.xq.ssm.service.CartService;
import cn.xq.ssm.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
@Api(value = "购物车管理", tags = "购物车管理")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "添加商品到购物车",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId",value = "商品ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "num",value = "商品数量", defaultValue = "1",required = true, paramType = "query")
    })
    @PostMapping("/add/{itemId}")
    public JsonData addCart(@PathVariable Long itemId,@RequestParam(defaultValue = "1") int num,HttpServletRequest request,HttpServletResponse response){
        /** 判断用户是否登录 */
        User user = (User) request.getAttribute("loginUser");
        /** 已经登录，将其写入redis */
        if (user != null){
            /** 保存到服务端 */
            cartService.addCart(user.getId(),itemId,num);
            /** 返回成功 */
            return JsonData.buildSuccess("添加商品"+itemId+"到购物车成功！！！");
        }
        /** 如果没有登录使用cookie 从cookie中取购物车列表 */
        List<Item> cartList = getCartListFromCookie(request);

        /** 判断商品在商品列表中是存在 如果存在数量相加 */
        boolean flag = false;
        for (Item item: cartList){
            if(item.getId() == itemId.longValue()) {
                flag = true;
                item.setNum(item.getNum() + num);
                //跳出循环
                break;
            }
        }
        if (!flag){
            /** 如果不存在，根据商品id查询信息，得到一个Item对象 */
            ItemDto item = itemService.getItemById(itemId);
            /** 设置商品数量 */
            item.setNum(num);
            /** 取一张图片 */
            String image = item.getImage();
            if(StringUtils.isNoneBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            //把商品添加到商品列表
            cartList.add(item);
        }
        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回添加成功
        return JsonData.buildSuccess("添加商品"+itemId+"到购物车成功！！！");
    }

    /**
     * 从cookie中取出购物车列表
     * @param request
     * @return
     */
    private List<Item> getCartListFromCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);

        /** 判断json是否为空 */
        if(StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }

        /** 把json转换成商品列表 */
        return JsonUtils.jsonToList(json, Item.class);
    }

    @ApiOperation(value = "获取购物车列表",httpMethod = "GET")
    @GetMapping("/list")
    public JsonData showCartList(HttpServletRequest request,HttpServletResponse response ) {
        //从cookie中取购物车列表
        List<Item> cartList = getCartListFromCookie(request);
        //判断用户是否为登录状态
        User user = (User) request.getAttribute("loginUser");
        //如果为登录状态
        if(user != null) {
            //从cookie中取购物车列表
            //如果不为空，把cookie中的购物车商品和服务端的购物车商品合并
            cartService.mergeCart(user.getId(), cartList);
            //把cookie中的购物车删除
            CookieUtils.deleteCookie(request, response, "cart");
            //从服务端取购物车列表
            cartList = cartService.getCartList(user.getId());
        }
        return JsonData.buildSuccess(cartList);
    }

    @ApiOperation(value = "更新购物车商品数量",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId",value = "商品ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "num",value = "商品数量",required = true, paramType = "query")
    })
    @GetMapping("/update/num/{itemId}/{num}")
    public JsonData updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response) {
        /** 判断用户是否为登录状态 */
        User user = (User) request.getAttribute("loginUser");
        if(user != null) {
            cartService.updateCartNum(user.getId(), itemId, num);
            return JsonData.buildSuccess("从购物车更新商品"+itemId+"数量成功!!!");
        }
        //从cookie中取购物车列表
        List<Item> cartList = getCartListFromCookie(request);
        //遍历商品列表找到对应的商品
        for (Item tbItem : cartList) {
            if(tbItem.getId().longValue() == itemId) {
                //更新数量
                tbItem.setNum(num);
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回成功
        return JsonData.buildSuccess("从购物车更新商品"+itemId+"数量成功!!!");
    }

    @ApiOperation(value = "删除购物车中的商品",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId",value = "商品ID", required = true, paramType = "query")
    })
    @DeleteMapping("/delete/{itemId}")
    public JsonData deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) {
        //判断用户是否为登录状态
        User user = (User) request.getAttribute("loginUser");
        if(user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return JsonData.buildSuccess("从购物车删除商品"+itemId+"成功!!!");
        }

        //从cookie中取购物车列表
        List<Item> cartList = getCartListFromCookie(request);
        //遍历列表，找到要删除的商品
        for (Item tbItem : cartList) {
            if(tbItem.getId().longValue() == itemId) {
                //删除商品
                cartList.remove(tbItem);
                //跳出循环
                break;
            }
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回逻辑视图
        return JsonData.buildSuccess("从购物车删除商品"+itemId+"成功!!!");
    }

    @ApiOperation(value = "清空购物车中的商品",httpMethod = "DELETE")
    @DeleteMapping("/clear")
    public JsonData clearCartItem(HttpServletRequest request,HttpServletResponse response){
        //判断用户是否为登录状态
        User user = (User) request.getAttribute("loginUser");
        if (user == null){
            return JsonData.buildSuccess("请登录！！！！");
        }
        cartService.clearCartItem(user.getId());
        return JsonData.buildSuccess("清空用户"+user.getId()+"购物车成功！！！！");
    }
}
