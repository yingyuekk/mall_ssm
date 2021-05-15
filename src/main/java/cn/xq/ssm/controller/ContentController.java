package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.entity.Content;
import cn.xq.ssm.service.ContentService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiong.xie
 * @description 内容管理
 * @date 2021/5/8
 */
@Api(value = "内容管理",tags = "内容管理")
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @ApiOperation(value = "分类节点下的内容列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id",value = "分类节点ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum",value = "页码", defaultValue = "1",required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量", defaultValue = "5",required = true, paramType = "query")
    })
    @GetMapping("/list/{category_id}")
    public JsonData getContentList(@PathVariable(value = "category_id") long categoryId, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,@RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageResult<Content> list = contentService.getContentList(categoryId,pageNum,pageSize);
        return JsonData.buildSuccess(list);
    }

    @ApiOperation(value = "分类下添加内容",httpMethod = "POST")
    @PostMapping("/add")
    public JsonData addContent(@RequestBody Content content){
        contentService.addContent(content);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "删除节点下的子节点",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "分类节点ID", required = true, paramType = "path")
    })
    @DeleteMapping("/del/{ids}")
    public JsonData delContent(@PathVariable String ids){
        contentService.delContent(ids);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "修改内容",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "内容ID", required = true, paramType = "path")
    })
    @PutMapping("/edit/{id}")
    public JsonData editContent(@PathVariable long id, @RequestBody Content content){
        contentService.editContent(id,content);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "获取内容信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "内容ID", required = true, paramType = "path")
    })
    @GetMapping("/info/{id}")
    public JsonData getContentInfo(@PathVariable long id){
        Content content = contentService.getContentInfo(id);
        return JsonData.buildSuccess(content);
    }
}
