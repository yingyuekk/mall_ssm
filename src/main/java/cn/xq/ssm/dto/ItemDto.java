package cn.xq.ssm.dto;

import cn.xq.ssm.entity.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/7
 */
@ApiModel(value="商品对象封装",description="封装商品规格和商品描述")
public class ItemDto extends Item{


    @ApiModelProperty(value = "商品规格",name = "paramData",dataType = "String",required = true,example = "[{'group':'主体','params':[{'k':'品牌','v':'苹果（Apple）'},{'k':'型号','v':'iPhone 6 A1586'},{'k':'颜色','v':'金色'},{'k':'上市年份','v':'2014'}]},{'group':'网络','params':[{'k':'4G网络制式','v':'移动4G(TD-LTE)/联通4G(FDD-LTE)/电信4G(FDD-LTE)'},{'k':'3G网络制式','v':'移动3G(TD-SCDMA)/联通3G(WCDMA)/电信3G（CDMA2000）'},{'k':'2G网络制式','v':'移动2G/联通2G(GSM)/电信2G(CDMA)'}]},{'group':'存储','params':[{'k':'机身内存','v':'16GB ROM'},{'k':'储存卡类型','v':'不支持'}]}]")
    private String paramData;


    @ApiModelProperty(value = "商品描述",name = "itemDesc",dataType = "String",required = true)
    private String itemDesc;

    public ItemDto() {
    }

    public ItemDto(Long id, String title, String sellPoint, Long price, Integer num, String barcode, String image, Long cid, Byte status, Date created, Date updated) {
        super(id, title, sellPoint, price, num, barcode, image, cid, status, created, updated);
    }

    public ItemDto(String paramData, String itemDesc) {
        this.paramData = paramData;
        this.itemDesc = itemDesc;
    }

    public ItemDto(Long id, String title, String sellPoint, Long price, Integer num, String barcode, String image, Long cid, Byte status, Date created, Date updated, String paramData, String itemDesc) {
        super(id, title, sellPoint, price, num, barcode, image, cid, status, created, updated);
        this.paramData = paramData;
        this.itemDesc = itemDesc;
    }

    public ItemDto(Item item) {
        super(item.getId(),item.getTitle(),item.getSellPoint(),item.getPrice(),item.getNum(),item.getBarcode(),item.getImage(),item.getCid(),item.getStatus(),item.getCreated(),item.getUpdated());
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
