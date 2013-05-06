package com.acme.anvil.service.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.Transaction;	
import com.acme.anvil.vo.LogEvent;

public class LogEventPublisher {

	private static final Logger LOG = Logger.getLogger(LogEventPublisher.class);
	private static final String TOPIC_JNDI_NAME = "jms/LogEventTopic";
	private static final String TOPIC_FACTORY_JNDI_NAME = "jms/LogEventTopic";

	public void publishLogEvent(LogEvent log) {
		//get a reference to the transaction manager to suspend the current transaction incase of exception.
		ClientTransactionManager ctm = TransactionHelper.getTransactionHelper().getTransactionManager();
		Transaction saveTx = null;
		try {
			saveTx = ctm.forceSuspend();

			try {
				Context ic = getContext();
				TopicSession session = getTopicSession(ic);
				Topic topic = getTopic(ic);
				TopicPublisher publisher = session.createPublisher(topic);
				ObjectMessage logMsg = session.createObjectMessage(log);

				publisher.publish(logMsg);
			} catch (JMSException e) {
				LOG.error("Exception sending message.", e);
			} catch (NamingException e) {
				LOG.error("Exception looking up required resource.", e);
			}

		} finally {
			ctm.forceResume(saveTx);
		}
	}

	private Context getContext() throws NamingException {
		TopicSession session = null;
		TopicPublisher publisher = null;

		Properties environment = new Properties();
		environment.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		environment.put(Context.PROVIDER_URL, "t3://localhost:7001");
		Context context = new InitialContext(environment);

		return context;
	}

	public Topic getTopic(Context context) throws NamingException {
		return (Topic) context.lookup(TOPIC_JNDI_NAME);
	}

	public TopicSession getTopicSession(Context context) throws JMSException,
			NamingException {
		TopicConnectionFactory cf = (TopicConnectionFactory) context
				.lookup(TOPIC_FACTORY_JNDI_NAME);
		TopicConnection connection = cf.createTopicConnection();
		return (TopicSession) connection.createSession(false, 1);
	}
}
