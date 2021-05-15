package cn.xq.ssm.service.impl;

import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.entity.Content;
import cn.xq.ssm.entity.ContentExample;
import cn.xq.ssm.mapper.ContentMapper;
import cn.xq.ssm.service.ContentService;
import com.github.pagehelper.PageInfo;
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
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public PageResult<Content> getContentList(long categoryId, int pageNum, int pageSize) {
        ContentExample example = new ContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<Content> list = contentMapper.selectByExample(example);
        PageInfo<Content> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),info.getList());
    }

    @Override
    public void addContent(Content content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insertSelective(content);
    }

    @Override
    public void delContent(String ids) {
        String[] idsArr = ids.split(",");
        for (String id: idsArr){
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
    }

    @Override
    public void editContent(long id, Content content) {
        ContentExample example = new ContentExample();
        example.createCriteria().andIdEqualTo(id);
        content.setUpdated(new Date());
        contentMapper.updateByExampleSelective(content,example);
    }

    @Override
    public Content getContentInfo(long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        return content;
    }

    @Override
    public List<Content> getCarouselData(long categoryId) {
        ContentExample example = new ContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<Content> list = contentMapper.selectByExample(example);
        return list;
    }
}
