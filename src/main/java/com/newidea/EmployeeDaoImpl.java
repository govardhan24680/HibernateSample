package com.newidea;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;
import com.newidea.entity.Employee;
import com.newidea.util.DbUtil;

public class EmployeeDaoImpl {
	
	
	
	
	static SessionFactory sf = null;
	static Session session = null;
	static {
		openDbObjects();
	}

	private static void openDbObjects() {
		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();
	}
	
	public static void saveEmployee(Employee e) {
		try {
			session.save(e); // gave check to cashier 5 lacs
			DbUtil.doTransaction(session);
			System.out.println("Saved emp successfully================");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // now cashier doing transaction
		finally {
			DbUtil.closeDbObjects(session, sf);
		}
	}
	
	private static void UpdateEmployee(Employee e) {
		// TODO Auto-generated method stub
		openDbObjects();

		
		DbUtil.closeDbObjects(session, sf);
	}
	
	
	
	
	
	

}
