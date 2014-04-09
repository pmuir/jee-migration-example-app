package com.acme.anvil.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.acme.anvil.vo.Item;

@Remote
public interface ItemLookup {
	public Item lookupItem(long id);
}
