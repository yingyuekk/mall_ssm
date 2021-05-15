package cn.xq.ssm.service.impl;

import cn.xq.ssm.common.IDUtils;
import cn.xq.ssm.common.JsonData;
import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.dto.ItemDto;
import cn.xq.ssm.entity.*;
import cn.xq.ssm.enums.CodeEnum;
import cn.xq.ssm.exception.BaseException;
import cn.xq.ssm.mapper.ItemDescMapper;
import cn.xq.ssm.mapper.ItemMapper;
import cn.xq.ssm.mapper.ItemParamItemMapper;
import cn.xq.ssm.service.ItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/6
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;

    @Override
    public PageResult<ItemDto> getItemList(int pageNum, int pageSize) {
        ItemExample example = new ItemExample();
        example.setOrderByClause("created DESC");
        List<Item> listTemp = itemMapper.selectByExample(example);
        List<ItemDto> list = new ArrayList<>();
        for (Item item: listTemp){
            ItemDto dto = FillItemDto(item);
            list.add(dto);
        }
        PageInfo<ItemDto> info = new PageInfo<>(list);
        // TODO 根据前端需要的格式进行修改返回内容
        return new PageResult<ItemDto>(info.getTotal(),info.getList());
    }

    @Override
    public ItemDto getItemById(long id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        return FillItemDto(item);
    }

    @Override
    public void editItemById(long id,ItemDto itemDto){
        Item item = new Item(id, itemDto.getTitle(), itemDto.getSellPoint(), itemDto.getPrice(), itemDto.getNum(), itemDto.getBarcode(), itemDto.getImage(), itemDto.getCid(), itemDto.getStatus(), itemDto.getCreated(), new Date());
        itemMapper.updateByPrimaryKeySelective(item);

        JsonData descResult = editItemDesc(id,itemDto.getItemDesc());
        if (descResult.getCode() != 0){
            throw new BaseException(CodeEnum.ITEM_EDIT_DESC_EX);
        }
        ItemParamItem itemParamItem = getItemParamItemByItemId(id);
        JsonData itemParamItemResult = editItemParamItem(itemParamItem == null?null:itemParamItem.getId(),id,itemDto.getParamData());
        if (itemParamItemResult.getCode() != 0){
            throw new BaseException(CodeEnum.ITEM_EDIT_PARAM_EX);
        }
        sendMessage(item.getId());
    }

    @Override
    public void delItemById(String ids){
        String[] idArr = ids.split(",");
        for (String id: idArr){
            itemMapper.deleteByPrimaryKey(Long.valueOf(id));

            JsonData descResult = delItemDesc(Long.valueOf(id));
            if (descResult.getCode() != 0){
                throw new BaseException(CodeEnum.ITEM_DEL_DESC_EX);
            }

            JsonData itemParamItemResult = delItemParamItem(Long.valueOf(id));
            if (itemParamItemResult.getCode() != 0){
                throw new BaseException(CodeEnum.ITEM_DEL_PARAM_EX);
            }
        }
    }

    @Override
    public void addItem(ItemDto itemDto){
        //1正常 2下架 3删除
        Item item = new Item(IDUtils.genItemId(),itemDto.getTitle(),itemDto.getSellPoint(),itemDto.getPrice(),itemDto.getNum(),itemDto.getBarcode(),itemDto.getImage(),itemDto.getCid(),(byte)1,new Date(),new Date());
        itemMapper.insert(item);
        JsonData descResult = insertItemDesc(item.getId(), itemDto.getItemDesc());
        if (descResult.getCode() != 0){
            throw new BaseException(CodeEnum.ITEM_ADD_DESC_EX);
        }
        JsonData itemParamItemResult = insertItemParamItem(item.getId(),itemDto.getParamData());
        if (itemParamItemResult.getCode() != 0){
            throw new BaseException(CodeEnum.ITEM_ADD_PARAM_EX);
        }
        sendMessage(item.getId());
    }

    @Override
    public void putAwayItem(String ids) {
        //1正常 2下架 3删除
        String[] idArr = ids.split(",");
        for (String id: idArr){
            Item item = new Item();
            item.setId(Long.valueOf(id));
            item.setStatus((byte)1);
            itemMapper.updateByPrimaryKeySelective(item);
        }
    }

    @Override
    public void UnShelveItem(String ids) {
        //1正常 2下架 3删除
        String[] idArr = ids.split(",");
        for (String id: idArr){
            Item item = new Item();
            item.setId(Long.valueOf(id));
            item.setStatus((byte)2);
            itemMapper.updateByPrimaryKeySelective(item);
        }
    }

    private ItemDto FillItemDto(Item item){
        ItemDto dto = new ItemDto(item);
        ItemParamItem itemParamItem = getItemParamItemByItemId(item.getId());
        ItemDesc itemDesc = getItemDescByItemId(item.getId());
        dto.setParamData(itemParamItem == null?null:itemParamItem.getParamData());
        dto.setItemDesc(itemDesc == null?null:itemDesc.getItemDesc());
        return dto;
    }

    private ItemParamItem getItemParamItemByItemId(long itemId){
        ItemParamItemExample itemParamItemExample = new ItemParamItemExample();
        itemParamItemExample.createCriteria().andItemIdEqualTo(itemId);
        List<ItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
        return list.size() > 0?list.get(0):null;
    }

    private ItemDesc getItemDescByItemId(long itemId){
        ItemDescExample itemDescExample = new ItemDescExample();
        itemDescExample.createCriteria().andItemIdEqualTo(itemId);
        List<ItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(itemDescExample);
        return list.size() > 0?list.get(0):null;
    }

    private JsonData editItemParamItem(long id,long itemId,String paramData) {
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(paramData);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItem.setId(id);
        /** 有则更新，无则插入 */
        itemParamItemMapper.insertOrUpdate(itemParamItem);

        return JsonData.buildSuccess();
    }

    private JsonData editItemDesc(long id,String desc) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        /** 有则更新，无则插入 */
        itemDescMapper.insertOrUpdate(itemDesc);

        return JsonData.buildSuccess();
    }

    private JsonData delItemDesc(Long itemId) {

        ItemDescExample itemDescExample = new ItemDescExample();
        itemDescExample.createCriteria().andItemIdEqualTo(itemId);

        itemDescMapper.deleteByExample(itemDescExample);

        return JsonData.buildSuccess();
    }

    private JsonData delItemParamItem(Long itemId){

        ItemParamItemExample paramItemExample = new ItemParamItemExample();
        paramItemExample.createCriteria().andItemIdEqualTo(itemId);

        itemParamItemMapper.deleteByExample(paramItemExample);

        return JsonData.buildSuccess();
    }

    private JsonData insertItemDesc(long itemId,String desc){

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());

        itemDescMapper.insert(itemDesc);

        return JsonData.buildSuccess();
    }

    private JsonData insertItemParamItem(Long id, String paramData) {

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(id);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItem.setParamData(paramData);

        itemParamItemMapper.insert(itemParamItem);

        return JsonData.buildSuccess();
    }

    public void sendMessage(Long itemId){
        jmsTemplate.send(topicDestination,new MessageCreator() {
            @Override
            public Message createMessage(Session session){
                TextMessage textMessage;
                try {
                    textMessage = session.createTextMessage(itemId+"");
                } catch (JMSException e) {
                    throw new BaseException(210011,e.getMessage());
                }
                return textMessage;
            }
        });
    }
}
