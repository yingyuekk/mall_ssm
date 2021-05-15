package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.entity.ContentCategory;
import cn.xq.ssm.service.ContentCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiong.xie
 * @description 内容分类管理
 * @date 2021/5/8
 */
@Api(value = "内容分类管理",tags = "内容分类管理")
@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @ApiOperation(value = "获取内容分类列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id",value = "父类ID", required = true, paramType = "path")
    })
    @GetMapping("/list/{parent_id}")
    public JsonData getContentCategoryList(@PathVariable(value = "parent_id") long parentId){
        List<ContentCategory> list = contentCategoryService.getContentCategoryList(parentId);
        return JsonData.buildSuccess(list);
    }

    @ApiOperation(value = "内容分类下添加子节点",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id",value = "父类ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "text",value = "节点昵称",required = true, paramType = "query")
    })
    @PostMapping("/add/{parent_id}")
    public JsonData addContentCategory(@PathVariable(value = "parent_id") long parentId,String text){
        contentCategoryService.addContentCategory(parentId,text);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "删除内容分类下的子节点",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "节点ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "parentId",value = "父节点ID",required = true, paramType = "query")
    })
    @DeleteMapping("/del/{id}")
    public JsonData delContentCategory(@PathVariable(value = "id") long id,long parentId){
        contentCategoryService.delContentCategory(id,parentId);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "修改节点昵称",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "节点ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "text",value = "节点新昵称",required = true, paramType = "query")
    })
    @PutMapping("/edit/{id}")
    public JsonData editContentCategory(@PathVariable(value = "id") long id,String text){
        contentCategoryService.editContentCategory(id,text);
        return JsonData.buildSuccess();
    }

}
