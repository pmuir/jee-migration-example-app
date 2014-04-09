package com.acme.anvil.service;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

@Stateless
@LocalBean
public class ProductCatalogBean implements ProductCatalog {

	private static final Logger LOG = Logger.getLogger(ProductCatalogBean.class);
	
	private SessionContext sessionContext;
	
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		this.sessionContext = sessionContext;
	}

	public void ejbRemove() throws EJBException, RemoteException {
		LOG.info("Called Remove.");
	}

	public void ejbActivate() throws EJBException, RemoteException {
		LOG.info("Called Activate");
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		LOG.info("Called Passivate");
	}
	
	public void populateCatalog() {
		LOG.info("Do something.");
	}
}
