package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.common.SearchResult;
import cn.xq.ssm.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/9
 */
@Api(value = "商品搜索管理",tags = "商品搜索管理")
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "根据关键词搜索商品",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词", required = true, paramType = "query"),
            @ApiImplicitParam(name = "page",value = "页码",defaultValue = "1", required = true, paramType = "query")
    })
    @GetMapping("/search")
    @ResponseBody
    public JsonData searchItemList(String keyword, @RequestParam(defaultValue = "1")Integer page, Model model){
        //查询商品列表
        SearchResult searchResult = searchService.search(keyword, page, 20);
        //把结果传递给页面
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
        return JsonData.buildSuccess(model);
    }
}
