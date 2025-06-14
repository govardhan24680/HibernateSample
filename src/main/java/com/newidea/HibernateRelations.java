package com.newidea;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.newidea.entity.Course;
import com.newidea.entity.Passport;
import com.newidea.entity.School;
import com.newidea.entity.Student;

public class HibernateRelations {
	
	static SessionFactory  sf = null;
	static Session session = null;
	
	public static void getConfig() {
		 sf = MyHibernateConfiguration.getSessionFactory();
	session = sf.openSession();
	}
	
	public static void executeRelations() {
		//doOneToOne();
		//doManyToOne(); 
		doManyToMany();
	}
	
	public static void doManyToMany() {
		getConfig();
		
		Course c1 = new Course();
		c1.setName("Java");
		
		Course c2 = new Course();
		c2.setName("Angular");
		
		List<Course>  listCourse = new ArrayList();
		listCourse.add(c1);
		listCourse.add(c2);
		
		
		Student s1 = new Student();
		s1.setName("s1-Many to many");
		s1.setCourse(listCourse);
		
		session.save(c1);
		session.save(c2);
		session.save(s1);
	
		doTransaction();
		
	}
	
	public static void doManyToOne() {
		getConfig();
		
		School school = new School();
		school.setName("new idea");
		
		Student s1 = new Student();
		s1.setName("s1");
		s1.setSchool(school);
		
		Student s2 = new Student();
		s2.setName("s2");
		s2.setSchool(school);
		
		session.save(school);
		session.save(s1);
		session.save(s2);
		
		doTransaction();
	}
	
	public static void doOneToOne() {
		getConfig();
		Student s = new Student();
		s.setName("one to one");
		
		Passport p = new Passport();
		p.setName("p1");
		
		s.setPassport(p);
		session.save(p);
		session.save(s);
		doTransaction();
		
	}
	
	private static void doTransaction() {
		Transaction tr = session.beginTransaction();
		tr.commit();
		session.close();
		sf.close();
	}

}
