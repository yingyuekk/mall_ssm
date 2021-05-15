package cn.xq.ssm.service;

import cn.xq.ssm.entity.ContentCategory;

import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/8
 */
public interface ContentCategoryService {

    List<ContentCategory> getContentCategoryList(long parentId);

    void addContentCategory(long parentId, String text);

    void delContentCategory(long id,long parentId);

    void editContentCategory(long id, String text);
}
