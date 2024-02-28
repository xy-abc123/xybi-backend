package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SingleProducer {
  private final static String QUEUE_NAME = "hello";
  public static void main(String[] argv) throws Exception {
      //创建工厂
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      //建立连接 创建频道
      try (Connection connection = factory.newConnection();
           Channel channel = connection.createChannel()) {
          //创建消息队列
          /* 参数:
                queueName:消息队列名称
                durabale:消息队列重启后,消息是否丢失
                exclusive:是否只允许当前这个创建消息队列的连接操作消息队列
                autoDelete:没有人用队列后,是否删除删除队列
           */
          channel.queueDeclare(QUEUE_NAME,false,false,false,null);
          String message="hello world!";
          channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
          System.out.println("[x] Sent" + message + "");

      }
  }
}