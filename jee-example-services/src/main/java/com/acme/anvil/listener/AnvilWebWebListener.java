package com.acme.anvil.listener;

import weblogic.i18n.logging.NonCatalogLogger;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/***
 * Prior to Weblogic 7, the T3StartupDef was a way of implementing startup listeners.
 *  See: http://docs.oracle.com/cd/E13222_01/wls/docs100/javadocs/weblogic/common/T3StartupDef.html
 *  See: http://docs.oracle.com/cd/E13222_01/wls/docs81/config_xml/StartupClass.html
 */
//@WebServlet(loadOnStartup = 1)
@WebListener
public class AnvilWebWebListener implements ServletContextListener {

	private static final String MBEAN_NAME = "com.acme:Name=anvil,Type=com.acme.anvil.management.AnvilInvokeBeanT3StartupDef"; 
	private NonCatalogLogger log;
	
	public AnvilWebWebListener() {
		// Yes, this should be static final, but just for demo sake...
		log = new NonCatalogLogger("AnvilWebStartupListener");
	}

    
    public void contextInitialized( ServletContextEvent sce ) {
		log.info("Initialized context, calling listener: "+ AnvilWebWebListener.class.getSimpleName() +" with properties: ");
        
		String name;
        Enumeration<String> names = sce.getServletContext().getAttributeNames();
        while( names.hasMoreElements() ) {
            name = names.nextElement();
			log.info("Attribute["+name+"] = Value["+sce.getServletContext().getAttribute(name)+"]");
		}
    }

    public void contextDestroyed( ServletContextEvent sce ) {
        //
    }
    
}
