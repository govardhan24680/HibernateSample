package com.newidea;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.newidea.entity.Employee;
import com.newidea.util.DbUtil;

public class EmployeeDaoImpl {
	
	
	
	
	static SessionFactory sf = null;
	static Session session = null;
	static {
		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();
	}
	
	public static void saveEmployee(Employee e) {
		session.save(e); // gave check to cashier 5 lacs
		DbUtil.doTransaction(session); // now cashier doing transaction
		DbUtil.closeDbObjects(session, sf);
	}
	
	
	
	
	
	

}
