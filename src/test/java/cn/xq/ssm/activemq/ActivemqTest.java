package cn.xq.ssm.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/12
 */
public class ActivemqTest {

    private static final String ACTIVEMQ_URL = "tcp://192.168.25.128:61616";
    private static final String TOPIC_NAME = "topic_13";

    @Test
    public void producer() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        for (int i =1;i<= 100;i++){
            TextMessage textMessage = session.createTextMessage("测试持久化-----TextMessage："+i);
            /**public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException
             * destination: 这里可以指定每个消息的目的地
             * message: 要发送的信息
             * deliveryMode: 持久模式和非持久模式
             * priority: 消息优先级，从0-9十个级别，0-4是普通消息5-9是加急消息。
             * timeToLive: 消息在一定时间后过期，默认是永不过期
             * */
            producer.send(topic,textMessage,DeliveryMode.PERSISTENT,10,0);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("  **** "+TOPIC_NAME+" ****消息发送到MQ完成 ****");
    }

    @Test
    public void consumer() throws JMSException, IOException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        /** 设置客户端ID。向MQ服务器注册自己的名称 */
        connection.setClientID("topicSubscriber_13");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        /** 创建一个topic订阅者对象。一参是topic，二参是订阅者名称 */
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "topicSubscriber_13...");
        /** 之后再开启连接 */
        connection.start();
        topicSubscriber.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        session.close();
        connection.close();
    }
}
