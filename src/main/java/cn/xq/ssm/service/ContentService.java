package cn.xq.ssm.service;

import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.entity.Content;

import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/8
 */
public interface ContentService {

    PageResult<Content> getContentList(long categoryId, int pageNum, int pageSize);

    void addContent(Content content);

    void delContent(String ids);

    void editContent(long id, Content content);

    Content getContentInfo(long id);

    List<Content> getCarouselData(long categoryId);
}
