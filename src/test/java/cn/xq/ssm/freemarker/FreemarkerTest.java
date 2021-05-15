package cn.xq.ssm.freemarker;

import cn.xq.ssm.common.PageResult;
import cn.xq.ssm.dto.ItemDto;
import cn.xq.ssm.entity.User;
import cn.xq.ssm.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class FreemarkerTest {

    @Autowired
    private ItemService itemService;

    /**
     * 生成模板文件 ----map
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test1() throws IOException, TemplateException {
        /** 1、创建一个Configuration对象 */
        Configuration configuration = new Configuration(Configuration.getVersion());
        /** 2、设置模板文件所在的路径 */
        File file = new File("F:/xqDevelop/学习/在线商城ssm/src/main/webapp/WEB-INF/ftl");
        configuration.setDirectoryForTemplateLoading(file);
        /** 3、设置模板文件使用的字符集。一般就是utf-8 */
        configuration.setDefaultEncoding("UTF-8");
        /** 4、加载一个模板，创建一个模板对象 */
        Template template = configuration.getTemplate("hello.ftl");
        /** 5、创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。 */
        Map<Object, Object> map = new HashMap<>();
        /** 5-1、向数据集中添加数据 */
        map.put("hello","this is my first freemarker test.");
        /** 6、创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。 */
        FileWriter fileWriter = new FileWriter(new File("F:/xqDevelop/学习/在线商城ssm/out/hello.html"));
        /** 7、调用模板对象的process方法输出文件。*/
        template.process(map,fileWriter);
        /** 8、关闭流 */
        fileWriter.close();
    }

    /**
     * 生成模板文件 ----pojo
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test2() throws IOException, TemplateException {
        /** 1、创建一个Configuration对象 */
        Configuration configuration = new Configuration(Configuration.getVersion());
        /** 2、设置模板文件所在的路径 */
        File file = new File("F:/xqDevelop/学习/在线商城ssm/src/main/webapp/WEB-INF/ftl");
        configuration.setDirectoryForTemplateLoading(file);
        /** 3、设置模板文件使用的字符集。一般就是utf-8 */
        configuration.setDefaultEncoding("UTF-8");
        /** 4、加载一个模板，创建一个模板对象 */
        Template template = configuration.getTemplate("user.ftl");
        /** 5、创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。 */
        User user = new User();
        user.setId(1001L);
        user.setUsername("梅长苏");
        user.setPassword("1234");
        user.setEmail("135@qq.com");
        user.setPhone("15555555555555");
        user.setCreated(new Date());
        user.setUpdated(new Date());
        /** 6、创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。 */
        FileWriter fileWriter = new FileWriter(new File("F:/xqDevelop/学习/在线商城ssm/out/user.html"));
        /** 7、调用模板对象的process方法输出文件。*/
        template.process(user,fileWriter);
        /** 8、关闭流 */
        fileWriter.close();
    }

    /**
     * 生成模板文件 ----map list
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test3() throws IOException, TemplateException {
        /** 1、创建一个Configuration对象 */
        Configuration configuration = new Configuration(Configuration.getVersion());
        /** 2、设置模板文件所在的路径 */
        File file = new File("F:/xqDevelop/学习/在线商城ssm/src/main/webapp/WEB-INF/ftl");
        configuration.setDirectoryForTemplateLoading(file);
        /** 3、设置模板文件使用的字符集。一般就是utf-8 */
        configuration.setDefaultEncoding("UTF-8");
        /** 4、加载一个模板，创建一个模板对象 */
        Template template = configuration.getTemplate("item.ftl");
        /** 5、创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。 */
        PageResult<ItemDto> pageResult = itemService.getItemList(1, 5);
        Map<Object, Object> map = new HashMap<>();
        map.put("itemList",pageResult.getData());
        /** 6、创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。 */
        FileWriter fileWriter = new FileWriter(new File("F:/xqDevelop/学习/在线商城ssm/out/user.html"));
        /** 7、调用模板对象的process方法输出文件。*/
        template.process(map,fileWriter);
        /** 8、关闭流 */
        fileWriter.close();
    }
}
