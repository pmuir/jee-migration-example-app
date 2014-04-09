package com.acme.anvil.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.security.RunAs;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import com.acme.anvil.service.jms.LogEventPublisher;
import com.acme.anvil.vo.Item;
import com.acme.anvil.vo.LogEvent;

//@RunAs(value="acme_user")
@Stateless
@LocalBean
@TransactionTimeout(unit=TimeUnit.SECONDS, value=180)
public class ItemLookupBean implements ItemLookup {

	@Inject
	private LogEventPublisher logEventPublisher;
	
	private static final Logger LOG = Logger.getLogger(ItemLookup.class);
	
	public Item lookupItem(long id) {
		LOG.info("Calling lookupItem.");
		
		//stubbed out.
		Item item = new Item();
		item.setId(id);
		
		final LogEvent le = new LogEvent(new Date(), "Returning Item: "+id); 

		try {
			logEventPublisher.publishLogEvent(le);
		} catch (InvalidTransactionException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return item;
	}
}
