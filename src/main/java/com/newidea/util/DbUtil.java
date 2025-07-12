package com.newidea.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DbUtil {

	public static void closeDbObjects(Session session, SessionFactory sessionFactory) {
		if (session != null) {
			session.close();
		}
		if(sessionFactory != null) {
			sessionFactory.close();
		}
	}

	public static void doTransaction(Session session) {
		Transaction tr = session.beginTransaction();
		tr.commit();
	}

}
