package cn.xq.ssm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author qiong.xie
 * @description
 * @date   2021/05/06
 */
@ApiModel(value="内容对象",description="内容对象封装")
public class Content {
    /**
     *
     */
    @ApiModelProperty(value = "内容id",name = "id",dataType = "Long",hidden = true)
    private Long id;

    /**
     * 内容类目ID
     */
    @ApiModelProperty(value = "内容类目ID",name = "categoryId",dataType = "Long",required = true)
    private Long categoryId;

    /**
     * 内容标题
     */
    @ApiModelProperty(value = "内容标题",name = "title",dataType = "String",required = true)
    private String title;

    /**
     * 子标题
     */
    @ApiModelProperty(value = "子标题",name = "subTitle",dataType = "String",required = true)
    private String subTitle;

    /**
     * 标题描述
     */
    @ApiModelProperty(value = "标题描述",name = "titleDesc",dataType = "String",required = true)
    private String titleDesc;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接",name = "url",dataType = "String",required = true,example = "https://www.baidu.com/")
    private String url;

    /**
     * 图片绝对路径
     */
    @ApiModelProperty(value = "图片绝对路径",name = "pic",dataType = "String",required = true)
    private String pic;

    /**
     * 图片2
     */
    @ApiModelProperty(value = "图片2",name = "pic2",dataType = "String",required = true)
    private String pic2;

    /**
     *
     */
    @ApiModelProperty(value = "内容创建时间",name = "created",dataType = "Date",hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date created;

    /**
     *
     */
    @ApiModelProperty(value = "内容更新时间",name = "updated",dataType = "Date",hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updated;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容",name = "content",dataType = "String",required = true)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc == null ? null : titleDesc.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2 == null ? null : pic2.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
