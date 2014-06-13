/**
 * 
 */
package org.activemq.simple.example.one;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author chris
 * @date 2014年6月12日
 * @version 1.0
 * 
 */
public class Receive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 连接工厂，JMS用它创建连接
		ConnectionFactory connectionFactory;
		// JMS客户端到JMS Provider的连接
		Connection connection = null;
		// 一个发送或接收消息的线程
		Session session;
		// 消息的目的地;消息发送给谁.
		Destination destination;
		// 消费者
		MessageConsumer consumer;

		// 构建ConnectionFactory实例对象,fialover表示断线续连
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "failover:(tcp://10.2.169.191:61616)");
		try {
			// 从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// 指定目的地
			destination = session.createQueue("activemq.simple.example.one.sender");
			// 得到生产者者
			consumer = session.createConsumer(destination);

			// 接收消息
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) consumer.receive(100000);
				if (null != message) {
					System.out.println("收到消息" + message.getText());
				} else {
					break;
				}
			}
			session.commit();

		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection) {
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
