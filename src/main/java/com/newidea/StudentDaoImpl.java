package com.newidea;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.newidea.entity.Employee;
import com.newidea.entity.Student;
import com.newidea.util.DbUtil;

public class StudentDaoImpl {

	static SessionFactory sf = null;
	static Session session = null;

//	public static void runningCurdOperations() {
//		System.out.println("running curd operations===================");
//	}

	static {
		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();
	}

//	public static void updateStudent(SessionFactory sf, Session session) {
//		Student student = session.get(Student.class, 2);
//		// select * from student where id=2;
//		if (student != null) {
//			student.setName("new name");
//			session.saveOrUpdate(student);
//			doTransaction(sf, session);
//		} else {
//			System.out.println("not found student");
//		}
//	}
//	
	
	public static void updateStudent(Student student) {
		if (student != null) {
			session.saveOrUpdate(student);
			DbUtil.doTransaction(session); // now cashier doing transaction
			DbUtil.closeDbObjects(session, sf);
		} else {
			System.out.println("not found student");
		}
	}
	
	
	public static void getAllStudent() {
		try {
			// logger.info("getAllStudent started");
			List<Student> ls = session.createCriteria(Student.class).list();
			System.out.println("list size" + ls.size());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// logger.error("getAllStudent error {}", e.getMessage());
			System.out.println("getAllStudent error {}" + e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}

	}

	public static Student getStudent(int id) {
		Student student = null;
		try {
			// logger.info("getStudent using id {} ", id);
			 student = session.get(Student.class, id);
			if (student != null) {
				return student;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// logger.error("getStudent id {} error {}", id, e.getMessage());
			System.out.println("getStudent id " + id + " error " + e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}
		return student;  //null

		// doTransaction(sf, session);
	}

	public static void saveStudent(Student s) {
		session.save(s); // gave check to cashier 5 lacs
		 DbUtil.doTransaction(session); // now cashier doing transaction
			DbUtil.closeDbObjects(session, sf);
	}
	
	public static void deleteStudent(Student s) {
	    session.delete(s); // instruct Hibernate to delete the object
	    DbUtil.doTransaction(session); // now cashier doing transaction
		DbUtil.closeDbObjects(session, sf);
	}

//	public static void saveEmployee(SessionFactory sf, Session session) {
//		Employee s = new Employee();
//		s.setName("new idea");
//		session.save(s); // gave check to cashier 5 lacs
//		doTransaction(sf, session); // now cashier doing transaction
//	}
	


//	private static void doTransaction(SessionFactory sf, Session session) {
//		Transaction tr = session.beginTransaction();
//		tr.commit(); // now 5 lacs transffered
//		session.close();
//		sf.close();
//	}
	
	private static void deleteStudent() {
		Transaction tr = session.beginTransaction();
		tr.commit();
	}

}
