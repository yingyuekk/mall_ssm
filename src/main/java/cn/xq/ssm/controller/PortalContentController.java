package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.entity.Content;
import cn.xq.ssm.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qiong.xie
 * @description 前台首页内容显示
 * @date 2021/5/8
 */
@Api(value = "前台首页内容显示",tags = "前台首页内容显示")
@RestController
@RequestMapping("/home")
public class PortalContentController {

    @Autowired
    private ContentService contentService;

    // TODO 根据需求提供需要的内容数据
    @ApiOperation(value = "获取轮播图数据",httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonData getCarouselData(){
        List<Content> list = contentService.getCarouselData(89);
        return JsonData.buildSuccess(list);
    }
}
