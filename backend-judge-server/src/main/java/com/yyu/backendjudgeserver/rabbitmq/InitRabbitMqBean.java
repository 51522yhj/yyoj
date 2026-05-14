package com.yyu.backendjudgeserver.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
@Slf4j
@Component
public class InitRabbitMqBean {

    @Value("${spring.rabbitmq.host:8.152.161.159}")
    private String host;

    @PostConstruct
    public void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "code_exchange";
            String EXCHANGE_NAME1 = "code_exchange1";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.exchangeDeclare(EXCHANGE_NAME1, "direct");
            // 创建队列，随机分配一个队列名称
            String queueName = "code_queue";
            String queueName1 = "code_queue1";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueDeclare(queueName1, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routingKey");
            channel.queueBind(queueName1, EXCHANGE_NAME1, "my_routingKey1");
            log.info("消息队列启动成功");
        } catch (Exception e) {
            log.error("消息队列启动失败", e);
        }
    }
}
