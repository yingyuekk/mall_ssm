package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.dto.ItemDto;
import cn.xq.ssm.service.ItemService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiong.xie
 * @description 商品控制器
 * @date 2021/5/6
 */
@Api(value = "商品管理",tags = "商品管理")
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "获取商品列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示大小",defaultValue = "5", required = true, paramType = "query")
    })
    @GetMapping("/list")
    @ResponseBody
    public JsonData getItemList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageResult<ItemDto> pageInfo = itemService.getItemList(pageNum,pageSize);
        return JsonData.buildSuccess(pageInfo);
    }

    /**
     * 获取商品信息
     * @param id 商品id
     * @return
     */
    @ApiOperation(value = "获取商品信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品id", required = true, paramType = "path")
    })
    @GetMapping("/info/{id}")
    @ResponseBody
    public JsonData getItemById(@PathVariable long id){
        ItemDto item = itemService.getItemById(id);
        return JsonData.buildSuccess(item);
    }


    @ApiOperation(value = "修改商品内容",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品id", required = true, paramType = "path")
    })
    @PutMapping("/edit/{id}")
    @ResponseBody
    public JsonData editItemById(@PathVariable long id,@RequestBody ItemDto itemDto){
        itemService.editItemById(id,itemDto);
        return JsonData.buildSuccess();
    }


    @ApiOperation(value = "删除商品 单个删除/批量删除",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "商品id", required = true, paramType = "path")
    })
    @DeleteMapping("/del/{ids}")
    @ResponseBody
    public JsonData delItemById(@PathVariable String ids){
        itemService.delItemById(ids);
        return JsonData.buildSuccess();
    }


    @ApiOperation(value = "新增商品",httpMethod = "POST")
    @PostMapping("/add")
    @ResponseBody
    public JsonData addItem(@RequestBody ItemDto itemDto){
        try {
            itemService.addItem(itemDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "商品上架 单个上架/批量上架",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "商品id", required = true, paramType = "path")
    })
    @PutMapping("/putaway/{ids}")
    @ResponseBody
    public JsonData putAwayItem(@PathVariable String ids){
        itemService.putAwayItem(ids);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "商品下架 单个下架/批量下架",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "商品id", required = true, paramType = "path")
    })
    @PutMapping("/Unshelve/{ids}")
    @ResponseBody
    public JsonData UnShelveItem(@PathVariable String ids){
        itemService.UnShelveItem(ids);
        return JsonData.buildSuccess();
    }
}
