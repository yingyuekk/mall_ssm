package cn.xq.ssm.service.impl;

import cn.xq.ssm.common.SearchResult;
import cn.xq.ssm.dao.SearchDao;
import cn.xq.ssm.enums.CodeEnum;
import cn.xq.ssm.exception.BaseException;
import cn.xq.ssm.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/9
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows){
        /** 创建一个 SolrQuery 对象*/
        SolrQuery query = new SolrQuery();
        /** 设置查询条件 */
        query.setQuery(keyword);
        /** 设置分页条件 */
        if (page<=0){
            page = 1;
        }
        query.setStart((page-1)*rows);
        query.setRows(rows);
        /** 设置默认搜索域 */
        query.set("df", "product_title");
        /** 开启高亮显示 */
        query.setHighlight(true);
        query.addHighlightField("product_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        /** 调用dao执行查询 */
        SearchResult searchResult;
        try {
            searchResult = searchDao.search(query);
        } catch (IOException | SolrServerException | ParseException e) {
            throw new BaseException(210010,e.getMessage());
        }
        /** 计算总页数 */
        long recordCount = searchResult.getRecordCount();
        int totalPage = (int) (recordCount / rows);
        if(recordCount % rows > 0) {
            totalPage ++;
        }
        //添加到返回结果
        searchResult.setTotalPages(totalPage);
        //返回结果
        return searchResult;
    }
}
