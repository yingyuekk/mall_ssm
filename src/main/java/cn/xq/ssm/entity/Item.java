package cn.xq.ssm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qiong.xie
 * @description
 * @date   2021/05/06
 */
@Data
public class Item {
    /**
     * 商品id，同时也是商品编号
     */
    @ApiModelProperty(value = "商品id，同时也是商品编号",name = "id",dataType = "Long",hidden = true)
    private Long id;

    /**
     * 商品标题
     */
    @ApiModelProperty(value = "商品标题",name = "title",dataType = "String",required = true)
    private String title;

    /**
     * 商品卖点
     */
    @ApiModelProperty(value = "商品卖点",name = "sellPoint",dataType = "String",required = true)
    private String sellPoint;

    /**
     * 商品价格，单位为：分
     */
    @ApiModelProperty(value = "商品价格，单位为：分",name = "price",dataType = "Long",required = true)
    private Long price;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量",name = "num",dataType = "Integer",required = true)
    private Integer num;

    /**
     * 商品条形码
     */
    @ApiModelProperty(value = "商品条形码",name = "barcode",dataType = "String",required = true)
    private String barcode;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片",name = "image",dataType = "String",required = true)
    private String image;

    /**
     * 所属类目，叶子类目
     */
    @ApiModelProperty(value = "所属类目，叶子类目",name = "cid",dataType = "Long",required = true)
    private Long cid;

    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    @ApiModelProperty(value = "商品状态，1-正常，2-下架，3-删除",name = "status",dataType = "Byte",hidden = true)
    private Byte status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建时间",name = "created",dataType = "Date",hidden = true)
    private Date created;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "更新时间",name = "updated",dataType = "Date",hidden = true)
    private Date updated;

    public Item() {
    }

    public Item(Long id, String title, String sellPoint, Long price, Integer num, String barcode, String image, Long cid, Byte status, Date created, Date updated) {
        this.id = id;
        this.title = title;
        this.sellPoint = sellPoint;
        this.price = price;
        this.num = num;
        this.barcode = barcode;
        this.image = image;
        this.cid = cid;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
}
