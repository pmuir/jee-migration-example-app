package com.acme.anvil.service;

import javax.ejb.Remote;

@Remote
public interface ProductCatalog {
	public void populateCatalog();
}
