package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.entity.ItemCat;
import cn.xq.ssm.service.ItemCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiong.xie
 * @description 商品类目管理
 * @date 2021/5/8
 */
@Api(value = "商品类目管理",tags = "商品类目管理")
@RestController
@RequestMapping("/item_cat")
public class ItemCatController {

    // TODO 根据前端需要格式改造这里使用DTreeHelper https://gitee.com/miniwatermelon/DTreeHelper http://www.wisdomelon.com/DTreeHelper/
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 查询节点列表
     * @param parentId
     * @return
     */
    @ApiOperation(value = "查询商品类目节点列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId",value = "分类节点ID", required = true, paramType = "query")
    })
    @GetMapping("/list")
    public JsonData getItemCatList(@RequestParam(value = "parentId",defaultValue = "0") long parentId){
        List<ItemCat> list = itemCatService.getItemCatList(parentId);
        return JsonData.buildSuccess(list);
    }

    @ApiOperation(value = "添加商品类目",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId",value = "分类节点ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "text",value = "分类节点昵称", required = true, paramType = "query")
    })
    @PostMapping("/add/{parentId}")
    public JsonData addItemCat(@PathVariable long parentId,@RequestParam String text){
        itemCatService.addItemCat(parentId,text);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "删除商品类目节点",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "节点ID", required = true, paramType = "path")
    })
    @DeleteMapping("/del/{ids}")
    public JsonData delItemCat(@PathVariable String ids){
        itemCatService.delItemCat(ids);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "修改商品类目节点",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "节点ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "text",value = "分类节点昵称", required = true, paramType = "query")
    })
    @PutMapping("/edit/{id}")
    public JsonData editItemCat(@PathVariable long id,@RequestParam String text){
        itemCatService.editItemCat(id,text);
        return JsonData.buildSuccess();
    }
}
