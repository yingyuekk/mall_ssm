package cn.xq.ssm.dao;

import cn.xq.ssm.common.SearchResult;
import cn.xq.ssm.dto.ItemDto;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/9
 */
@Repository
public class SearchDao {

    @Autowired
    private HttpSolrClient solrClient;

    public SearchResult search(SolrQuery query) throws IOException, SolrServerException, ParseException {
        /** 根据条件查询索引 */
        /** 执行查询，QueryResponse对象 */
        QueryResponse response = solrClient.query(query);
        /** 获取查询结果 */
        SolrDocumentList solrDocumentList = response.getResults();
        /** 获取结果总数 */
        long numFound = solrDocumentList.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);
        /** 取商品列表，需要取高亮显示 */
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        ArrayList<ItemDto> itemDtoArrayList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(solrDocument.get("id")==null?null:Long.parseLong(solrDocument.get("id").toString()));
            itemDto.setSellPoint(solrDocument.get("product_sell_point")==null?null:solrDocument.get("product_sell_point").toString());
            itemDto.setPrice(solrDocument.get("product_price")==null?null:Long.parseLong(solrDocument.get("product_price").toString()));
            itemDto.setNum(solrDocument.get("product_num").toString()==null?null:Integer.valueOf(solrDocument.get("product_num").toString()));
            itemDto.setBarcode(solrDocument.get("product_barcode") == null?null:solrDocument.get("product_barcode").toString());
            itemDto.setImage(solrDocument.get("product_image")== null?null:solrDocument.get("product_image").toString());
            itemDto.setCid(solrDocument.get("product_cid")==null?null:Long.parseLong(solrDocument.get("product_cid").toString()));
            itemDto.setStatus(solrDocument.get("product_status")==null?null:Byte.valueOf(solrDocument.get("product_status").toString()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
            itemDto.setCreated(solrDocument.get("product_created")==null?null:simpleDateFormat.parse(solrDocument.get("product_created").toString()));
            itemDto.setUpdated(solrDocument.get("product_updated")==null?null:simpleDateFormat.parse(solrDocument.get("product_updated").toString()));
            itemDto.setParamData(solrDocument.get("product_param_data")==null?null:solrDocument.get("product_param_data").toString());
            itemDto.setItemDesc(solrDocument.get("product_desc")==null?null:solrDocument.get("product_desc").toString());
            /** 设置商品标题高亮 */
            List<String> list = highlighting.get(solrDocument.get("id")).get("product_title");
            String title = "";
            if (title != null && list.size()>0){
                title = list.get(0);
            }else {
                title = solrDocument.get("product_title")==null?null:solrDocument.get("product_title").toString();
            }
            itemDto.setTitle(title);
            itemDtoArrayList.add(itemDto);
        }
        result.setItemList(itemDtoArrayList);
        //返回结果
        return result;
    }
}
