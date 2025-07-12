package com.newidea;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newidea.constants.HibernateSampleConstants;
import com.newidea.entity.Student;

public class MyNonUniqueObjectException {
	
	private static final Logger logger = LoggerFactory.getLogger(MyNonUniqueObjectException.class);

	public static void main(String[] args) {
		keepSameIdTwiceInPersistentSate();
	}

	static SessionFactory sf = null;
	static Session session = null;

	private static void keepSameIdTwiceInPersistentSate() {

		try {
			sf = MyHibernateConfiguration.getSessionFactory();
			session = sf.openSession();
			Transaction tx = session.beginTransaction();

			// Load an object into the session (persistent instance)
			Student obj1 = session.get(Student.class, HibernateSampleConstants.STUDENT_ID);
			//object STUDENT_ID is in persistent state

			// Create a new object with the same ID manually (detached)
			Student obj2 = new Student();
			obj2.setId(HibernateSampleConstants.STUDENT_ID);
			obj2.setName("New Name");

			// Try to update the session with obj2 (conflicts with obj1)
			session.update(obj2); // ❌ Throws NonUniqueObjectException
			//here  same object STUDENT_ID try to move in to persistent state

			tx.commit();
		//	session.close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("keepSameIdTwiceInPersistentSate while updating student id {}, error {}", HibernateSampleConstants.STUDENT_ID, e.getMessage());
			//keepSameIdTwiceInPersistentSate while updating student id 10, error
		}finally {
			session.close();
			sf.close();
		}

	}

}
