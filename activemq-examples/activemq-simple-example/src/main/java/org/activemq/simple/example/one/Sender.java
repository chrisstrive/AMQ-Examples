/**
 * 
 */
package org.activemq.simple.example.one;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author chris
 * @date 2014年6月11日
 * @version 1.0
 * 
 */
public class Sender {

	private static final int SEND_NUMBER = 5;

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
		// 消息的目的地
		Destination destination;
		// 消息发送者
		MessageProducer producer;

		// 构建ConnectionFactory实例对象
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
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 构造消息
			sendMessage(session, producer);
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

	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		for (int i = 1; i < SEND_NUMBER; i++) {
			TextMessage message = session.createTextMessage("AMQ发送的消息" + i);
			System.out.println("发送消息" + i);
			producer.send(message);
		}
	}

}
