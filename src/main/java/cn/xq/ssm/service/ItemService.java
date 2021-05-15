package cn.xq.ssm.service;
import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.dto.ItemDto;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/6
 */
public interface ItemService {

    PageResult<ItemDto> getItemList(int pageNum, int pageSize);

    ItemDto getItemById(long id);

    void editItemById(long id,ItemDto itemDto);

    void delItemById(String ids);

    void addItem(ItemDto itemDto);

    void putAwayItem(String ids);

    void UnShelveItem(String ids);
}
