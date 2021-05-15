package cn.xq.ssm.service;

import cn.xq.ssm.common.SearchResult;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/9
 */
public interface SearchService {

    SearchResult search(String keyword, int page, int rows);
}
