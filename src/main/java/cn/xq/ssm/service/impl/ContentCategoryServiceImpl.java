package cn.xq.ssm.service.impl;

import cn.xq.ssm.entity.ContentCategory;
import cn.xq.ssm.entity.ContentCategoryExample;
import cn.xq.ssm.entity.ContentExample;
import cn.xq.ssm.mapper.ContentCategoryMapper;
import cn.xq.ssm.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/8
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public List<ContentCategory> getContentCategoryList(long parentId) {
        ContentCategoryExample example = new ContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public void addContentCategory(long parentId, String text) {
        ContentCategory category = new ContentCategory();
        // 该类目是否为父类目，1为true，0为false
        category.setIsParent(false);
        category.setName(text);
        category.setParentId(parentId);
        category.setSortOrder(contentCategoryMapper.selectMaxSortOrder(parentId)+1);
        // 状态。可选值:1(正常),2(删除)
        category.setStatus(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        contentCategoryMapper.insertSelective(category);
        ContentCategoryExample example = new ContentCategoryExample();
        example.createCriteria().andIdEqualTo(Long.valueOf(parentId));
        List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
        ContentCategory contentCategory = list.get(0);
        if (!contentCategory.getIsParent()){
            contentCategory.setUpdated(new Date());
            // 该类目是否为父类目，1为true，0为false
            contentCategory.setIsParent(true);
            contentCategoryMapper.updateByExampleSelective(contentCategory,example);
        }

    }

    @Override
    public void delContentCategory(long id,long parentId) {
        contentCategoryMapper.deleteByPrimaryKey(id);
        ContentCategoryExample example = new ContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list.size() == 0){
            ContentCategoryExample example1 = new ContentCategoryExample();
            example.createCriteria().andIdEqualTo(parentId);
            ContentCategory category = new ContentCategory();
            category.setUpdated(new Date());
            // 该类目是否为父类目，1为true，0为false
            category.setIsParent(false);
            contentCategoryMapper.updateByExampleSelective(category,example1);
        }
    }

    @Override
    public void editContentCategory(long id, String text) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setUpdated(new Date());
        contentCategory.setName(text);
        ContentCategoryExample example = new ContentCategoryExample();
        example.createCriteria().andIdEqualTo(id);
        contentCategoryMapper.updateByExampleSelective(contentCategory,example);
    }
}
