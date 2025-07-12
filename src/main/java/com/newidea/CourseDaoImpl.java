package com.newidea;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newidea.entity.Course;
import com.newidea.util.DbUtil;

public class CourseDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
	static SessionFactory sf = null;
	static Session session = null;

	private static void openDbObjects() {
		logger.info("openDbObjects opened session and sesson factory");
		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();
	}

	public static Course getCourse(int id) {
		Course course = null;
		try {
			openDbObjects();
			// logger.info("getStudent using id {} ", id);
			course = session.get(Course.class, id);
			if (course != null) {
				logger.info("get course successfully by id================" + id);
				//System.out.println(course.getListOfStudents().size()); 
				return course;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("getStudent id {} error {}", id, e.getMessage());
			logger.error("getCourse id " + id + " error " + e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}
		return course;
	}
	
	
	public static Course getCourse2(int id, Session session) {
		Course course = null;
		try {
			course = session.get(Course.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getCourse id " + id + " error " + e.getMessage());
		} 
		return course;
	}
	
	

}
