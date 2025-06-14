package com.newidea;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MyHibernateConfiguration {

	
	public static SessionFactory getSessionFactory() {
		
//		
//		 if (MyHibernateConfiguration.class.getClassLoader()
//				 .getResource("hibernate.cfg.xml") == null) {
//		        System.err.println("❌ hibernate.cfg.xml NOT FOUND in classpath!");
//		    } else {
//		        System.out.println("✅ hibernate.cfg.xml found.");
//		    }
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
	
			SessionFactory sf  = cfg.buildSessionFactory();
		
		return sf;
	}
	

}
