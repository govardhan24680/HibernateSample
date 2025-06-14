package com.newidea;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.newidea.entity.Student;

public class HibernateCurOperations {
	
	static SessionFactory  sf = null;
	static Session session = null;
	public static void runningCurdOperations() {
		System.out.println("running curd operations===================");
	}
	static {
		 sf = MyHibernateConfiguration.getSessionFactory();
	session = sf.openSession();
	saveStudent(sf, session);
	updateStudent(sf, session);
	getStudent(sf, session);
	getAllStudent(sf, session);
	}
	

	public static void updateStudent(SessionFactory sf1, Session session1) {
	// TODO Auto-generated method stub
	SessionFactory sf = MyHibernateConfiguration.getSessionFactory();
	Session session = sf.openSession();
	Student student = session.get(Student.class, 2);
	//select * from student where id=2;
	if(student != null) {
	student.setName("new name");
	session.saveOrUpdate(student);
	doTransaction(sf, session);
	}else {
		System.out.println("not found student");
	}
}

	public static void getAllStudent(SessionFactory sf1, Session session1) {
	SessionFactory sf = MyHibernateConfiguration.getSessionFactory();
	Session session = sf.openSession();
	// TODO Auto-generated method stub
	List<Student> ls = session.createCriteria(Student.class).list();
	System.out.println("list size"+ls.size());
	//doTransaction(sf, session);
}

	public static void getStudent(SessionFactory sf1, Session session1) {
	// TODO Auto-generated method stub
	SessionFactory sf = MyHibernateConfiguration.getSessionFactory();
	Session session = sf.openSession();
	Student student = session.get(Student.class, 25);
	student.getSchool().getName();
	System.out.println("=====student"+student);
	//doTransaction(sf, session);
}

	public static void saveStudent(SessionFactory sf, Session session) {
	Student s = new Student();
	s.setName("new idea");
	session.save(s);  //gave check to cashier 5 lacs
	doTransaction(sf, session); //now cashier doing transaction
}
	
	

//	public static void saveStudent(SessionFactory sf, Session session) {
//	Student s = new Student();
//	s.setName("new idea");
//	session.save(s);  //gave check to cashier 5 lacs
//	doTransaction(sf, session); //now cashier doing transaction
//}


private static void doTransaction(SessionFactory sf, Session session) {
	Transaction tr = session.beginTransaction();
	tr.commit(); //now 5 lacs transffered
	session.close();
	sf.close();
}

}
