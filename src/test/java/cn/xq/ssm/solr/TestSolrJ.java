package cn.xq.ssm.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/9
 */
public class TestSolrJ {

    /**
     * 简单查询
     */
    @Test
    public void queryIndex() throws IOException, SolrServerException {
        //创建一个solrServer对象
        HttpSolrClient solrClient = new HttpSolrClient.Builder("http://192.168.25.128:8983/solr/collection1").build();
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.set("q","*:*");
        //执行查询，QueryResponse对象
        QueryResponse queryResponse = solrClient.query(query);
        //取文档列表，去查询结果的总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println(solrDocumentList.getNumFound());
        //遍历文档列表，从文档中取域的值
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("product_title"));
            System.out.println(solrDocument.get("product_sell_point"));
            System.out.println(solrDocument.get("product_price"));
            System.out.println(solrDocument.get("product_num"));
            System.out.println(solrDocument.get("product_barcode"));
            System.out.println(solrDocument.get("product_image"));
            System.out.println(solrDocument.get("product_cid"));
            System.out.println(solrDocument.get("product_status"));
            System.out.println(solrDocument.get("product_created"));
            System.out.println(solrDocument.get("product_updated"));
            System.out.println(solrDocument.get("product_param_data"));
            System.out.println(solrDocument.get("product_desc"));
        }
    }

    /**
     * 复杂查询
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void query() throws IOException, SolrServerException {
        HttpSolrClient solrClient = new HttpSolrClient.Builder("http://192.168.25.128:8983/solr/collection1").build();
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("手机");
        query.setStart(0);
        query.setRows(20);
        query.set("df", "product_title");
        query.setHighlight(true);
        query.addHighlightField("product_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //执行查询，QueryResponse对象
        QueryResponse queryResponse = solrClient.query(query);
        //取文档列表，去查询结果的总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println(solrDocumentList.getNumFound());
        //遍历文档列表，从文档中取域的值
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("product_title");
            String title = "";
            if(list != null && list.size()>0) {
                title = list.get(0);
            }else {
                title = (String) solrDocument.get("product_title");
            }
            System.out.println(title);
            System.out.println(solrDocument.get("product_sell_point"));
            System.out.println(solrDocument.get("product_price"));
            System.out.println(solrDocument.get("product_num"));
            System.out.println(solrDocument.get("product_barcode"));
        }
    }

    /**
     * 简单查询 2
     */
    @Test
    public void queryIndex2() throws IOException, SolrServerException {
        //获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        HttpSolrClient solrClient = (HttpSolrClient)context.getBean("solrClient");
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.set("q","*:*");
        //执行查询，QueryResponse对象
        QueryResponse queryResponse = solrClient.query(query);
        //取文档列表，去查询结果的总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println(solrDocumentList.getNumFound());
        //遍历文档列表，从文档中取域的值
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("product_title"));
            System.out.println(solrDocument.get("product_sell_point"));
            System.out.println(solrDocument.get("product_price"));
            System.out.println(solrDocument.get("product_num"));
            System.out.println(solrDocument.get("product_barcode"));
            System.out.println(solrDocument.get("product_image"));
            System.out.println(solrDocument.get("product_cid"));
            System.out.println(solrDocument.get("product_status"));
            System.out.println(solrDocument.get("product_created"));
            System.out.println(solrDocument.get("product_updated"));
            System.out.println(solrDocument.get("product_param_data"));
            System.out.println(solrDocument.get("product_desc"));
        }
    }
}
