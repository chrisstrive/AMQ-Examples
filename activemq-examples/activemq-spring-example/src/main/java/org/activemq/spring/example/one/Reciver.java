/**
 * 
 */
package org.activemq.spring.example.one;

import lombok.extern.java.Log;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author chris
 * @date 2014年6月13日
 * @version 1.0
 * 
 */
@Log
public class Reciver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");

		while (true) {
			String message = (String)jmsTemplate.receiveAndConvert();
//			Map<String, Object> map = (Map<String, Object>) jmsTemplate.receiveAndConvert();
//			System.out.println("收到消息：" + map.get("message"));
			log.info("收到消息：" + message);
        }
	}

}
