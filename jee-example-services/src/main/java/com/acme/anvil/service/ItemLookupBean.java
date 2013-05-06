package com.acme.anvil.service;

import javax.ejb.SessionBean;

import org.apache.log4j.Logger;

import com.acme.anvil.vo.Item;

import weblogic.ejb.GenericSessionBean;

public class ItemLookupBean extends GenericSessionBean implements SessionBean {

	private static final Logger LOG = Logger.getLogger(ItemLookup.class);
	
	public Item lookupItem(long id) {
		LOG.info("Calling lookupItem.");
		
		//stubbed out.
		Item item = new Item();
		item.setId(id);
		
		return item;
	}
}
