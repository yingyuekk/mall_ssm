package cn.xq.ssm.listener;

import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.dto.ItemDto;
import cn.xq.ssm.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.io.Resources.getResource;

/**
 * @author qiong.xie
 * @description 商品修改、添加监听器
 * @date 2021/5/12
 */
public class ItemChangeListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    @Override
    public void onMessage(Message message) {
        try {
            /** 1、从消息中取出商品ID */
            TextMessage textMessage = (TextMessage) message;
            Long itemId = Long.valueOf(textMessage.getText());
            /** 2、等待事务提交 */
            Thread.sleep(1000);
            /** 3、根据商品id查询商品信息 */
            ItemDto itemDto = itemService.getItemById(itemId);
            /** 4、创建一个数据集，把商品数据封装 */
            Map<Object, Object> mapData = new HashMap<>();
            mapData.put("itemInfo",itemDto);
            /** 5、加载模板对象 */
            Configuration configuration = freemarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            /** 6、创建一个输出流,制定输出的目录及文件名 */

            FileWriter fileWriter = new FileWriter("F:/xqDevelop/学习/在线商城ssm-Freemarker/" + itemId + ".html");
            /** 7、生成静态页面 */
            template.process(mapData,fileWriter);
            /** 8、关闭流 */
            fileWriter.close();
        } catch (JMSException | InterruptedException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
