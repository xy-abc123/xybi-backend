package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MultiConsumer {

    private static final String TASK_QUEUE_NAME = "multi_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        for (int i = 0; i < 2; i++) {
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicQos(1);
            int finalI = i;
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                try {
                    //处理工作
                    System.out.println(" [x] Received '" + "编号:"+ finalI +" "+ message + "'");
                    //指定确认某条消息 第二个参数multiple 批量确认:是指是否要一次性确认所有的历史消息直到当前这条
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                    Thread.sleep(20000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //指定拒绝某条消息 第三个参数表示是否重新入队:是指是否要一次性确认所有的历史消息直到当前这条
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false,false);
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            //开启消费监听
            channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
            });
        }

    }

}