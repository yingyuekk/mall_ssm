package cn.xq.ssm.service;

import cn.xq.ssm.entity.ItemCat;

import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/8
 */
public interface ItemCatService {

    List<ItemCat> getItemCatList(long parentId);

    void addItemCat(long parentId, String text);

    void delItemCat(String ids);

    void editItemCat(long id, String text);
}
