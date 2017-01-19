package com.acme.anvil.service;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

import com.acme.anvil.service.jms.LogEventPublisher;
import com.acme.anvil.vo.Item;
import com.acme.anvil.vo.LogEvent;

@SuppressWarnings("serial")
public class ItemLookupBean implements SessionBean {

	private static final Logger LOG = Logger.getLogger(ItemLookup.class);
	
	public Item lookupItem(long id) {
		LOG.info("Calling lookupItem.");
		
		//stubbed out.
		Item item = new Item();
		item.setId(id);
		
		final LogEvent le = new LogEvent(new Date(), "Returning Item: "+id); 
		LogEventPublisher.publishLogEvent(le);
		
		return item;
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}
}
