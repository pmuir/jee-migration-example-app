package com.acme.anvil.service.jms;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;

import com.acme.anvil.vo.LogEvent;

@Stateless
@LocalBean
public class LogEventPublisher {
	private static final Logger LOG = Logger.getLogger(LogEventPublisher.class);
	
	private static final String TOPIC_JNDI_NAME = "java:jboss/jms/topic/LogEventTopic";
	private static final String QUEUE_FACTORY_JNDI_NAME = "java:/JmsXA";
	private static final String TX_MANAGER = "java:jboss/TransactionManager";
	
	@Resource(mappedName = TX_MANAGER)
	private TransactionManager txManager;

	@Resource(mappedName = QUEUE_FACTORY_JNDI_NAME) 
    private ConnectionFactory factory;
    
    @Resource(mappedName = TOPIC_JNDI_NAME)
    private Topic logEventTopic;    

	public void publishLogEvent(LogEvent log) throws InvalidTransactionException, IllegalStateException, SystemException {
		// Get a reference to the transaction manager to suspend the current transaction incase of exception.
		Transaction saveTx = txManager.getTransaction();
		
		try {
			saveTx = txManager.suspend(); // Forced
			

			try {
				Context ic = getContext();
				QueueSession session = getQueueSession(ic);
				Destination queue = getDestination(ic);
				MessageProducer producer = session.createProducer(queue);
				ObjectMessage logMsg = session.createObjectMessage(log);

				producer.send(logMsg);
			} catch (JMSException e) {
				LOG.error("Exception sending message.", e);
			} catch (NamingException e) {
				LOG.error("Exception looking up required resource.", e);
			}
			
			
		} finally {
			txManager.resume(saveTx);
		}
	}
	

	private static Context getContext() throws NamingException {
		Context context = new InitialContext();
		return context;
	}

	
	private static Destination getDestination(Context context) throws NamingException {
		return (Destination) context.lookup(TOPIC_JNDI_NAME);
	}

	private static QueueSession getQueueSession(Context context) throws JMSException, NamingException {
		QueueConnectionFactory cf = (QueueConnectionFactory) context
				.lookup(QUEUE_FACTORY_JNDI_NAME);
		QueueConnection connection = cf.createQueueConnection();
		return (QueueSession) connection.createSession(false, 1);
	}

}
