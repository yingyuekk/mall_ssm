package cn.xq.ssm.controller;

import cn.xq.ssm.common.JsonData;
import com.github.tobato.fastdfs.service.FastDFSClient;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiong.xie
 * @description FastDFS 文件处理
 * @date 2021/5/7
 */
@Api(value = "FastDFS 文件处理",tags = "FastDFS 文件处理")
@RestController
public class FastDFSController {

    @Resource
    private FastDFSClient fastDFSClient;

    /**
     * 单个文件上传
     * @param img
     * @return
     */
    @ApiOperation(value = "单个文件上传", notes = "单个文件上传")
    @RequestMapping(value = "/upload_img",method = RequestMethod.POST)
    public JsonData fileImgUpload(@RequestParam("img")MultipartFile img){
        String path = null;
        try {
            path = this.fastDFSClient.uploadFile(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonData.buildSuccess(path);
    }

    /**
     * 批量上传文件
     * @param imgs
     * @return
     */
    @ApiOperation(value = "批量上传文件", notes = "批量上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgs",value = "img1",required = true,dataType = "file",paramType = "form"),
            @ApiImplicitParam(name = "imgs",value = "img2",required = true,dataType = "file",paramType = "form"),
            @ApiImplicitParam(name = "imgs",value = "img3",required = true,dataType = "file",paramType = "form")
    })
    @RequestMapping(value = "/batch_upload_img",method = RequestMethod.POST)
    public JsonData batchFileImgUpload(@RequestParam("imgs")MultipartFile[] imgs){
        List<String> paths = new ArrayList<>();
        for (MultipartFile img:imgs){
            try {
                String path = this.fastDFSClient.uploadFile(img);
                paths.add(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JsonData.buildSuccess(paths);
    }
}
