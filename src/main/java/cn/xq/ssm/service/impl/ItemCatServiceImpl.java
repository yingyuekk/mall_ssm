package cn.xq.ssm.service.impl;

import cn.xq.ssm.entity.ItemCat;
import cn.xq.ssm.entity.ItemCatExample;
import cn.xq.ssm.mapper.ItemCatMapper;
import cn.xq.ssm.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author qiong.xie
 * @description 商品管理
 * @date 2021/5/8
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> getItemCatList(long parentId) {
        ItemCatExample example = new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        example.setOrderByClause("sort_order DESC");
        List<ItemCat> list = itemCatMapper.selectByExample(example);
        return list;
    }

    @Override
    public void addItemCat(long parentId, String text) {
        ItemCat itemCat =null;
        if (parentId != 0){
            itemCat = itemCatMapper.selectByPrimaryKey(parentId);
        }

        ItemCat cat = new ItemCat();
        // 该类目是否为父类目，1为true，0为false
        cat.setIsParent(false);
        cat.setName(text);
        cat.setParentId(itemCat == null?0:itemCat.getId());
        cat.setSortOrder(itemCatMapper.selectMaxSortOrderByParentId(cat.getParentId())+1);
        // 状态。可选值:1(正常),2(删除)
        cat.setStatus(1);
        cat.setCreated(new Date());
        cat.setUpdated(new Date());
        itemCatMapper.insertSelective(cat);
        if (itemCat != null && !itemCat.getIsParent()){
            ItemCatExample example = new ItemCatExample();
            example.createCriteria().andIdEqualTo(itemCat.getId()).andParentIdEqualTo(itemCat.getParentId());
            itemCat.setUpdated(new Date());
            itemCat.setIsParent(true);
            itemCatMapper.updateByExampleSelective(itemCat,example);
        }
    }

    @Override
    public void delItemCat(String ids) {
        String[] idsArr = ids.split(",");
        for (String id:idsArr){
            ItemCat itemCat = itemCatMapper.selectByPrimaryKey(Long.valueOf(id));
            itemCatMapper.deleteByPrimaryKey(Long.valueOf(id));
            ItemCatExample example = new ItemCatExample();
            example.createCriteria().andParentIdEqualTo(itemCat.getParentId());
            List<ItemCat> list = itemCatMapper.selectByExample(example);
            if (list.size() == 0){
                ItemCatExample catExample = new ItemCatExample();
                catExample.createCriteria().andIdEqualTo(itemCat.getParentId());
                ItemCat cat = new ItemCat();
                cat.setIsParent(false);
                cat.setUpdated(new Date());
                itemCatMapper.updateByExampleSelective(cat,catExample);
            }
        }
    }

    @Override
    public void editItemCat(long id, String text) {
        ItemCatExample example = new ItemCatExample();
        example.createCriteria().andIdEqualTo(id);
        ItemCat itemCat = new ItemCat();
        itemCat.setUpdated(new Date());
        itemCat.setName(text);
        itemCatMapper.updateByExampleSelective(itemCat,example);
    }
}
