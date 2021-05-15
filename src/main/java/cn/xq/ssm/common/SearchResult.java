package cn.xq.ssm.common;

import cn.xq.ssm.dto.ItemDto;

import java.util.List;

/**
 * @author qiong.xie
 * @description 商品搜索返回数据封装
 * @date 2021/5/9
 */
public class SearchResult {

    private long recordCount; // 总量
    private int totalPages; // 总页面
    private List<ItemDto> itemList; // 数据

    public long getRecordCount() {
        return recordCount;
    }
    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<ItemDto> getItemList() {
        return itemList;
    }
    public void setItemList(List<ItemDto> itemList) {
        this.itemList = itemList;
    }
}
