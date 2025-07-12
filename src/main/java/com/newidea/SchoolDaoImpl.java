package com.newidea;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newidea.entity.School;
import com.newidea.entity.Student;
import com.newidea.util.DbUtil;

public class SchoolDaoImpl {





	
	private static final Logger logger = LoggerFactory.getLogger(SchoolDaoImpl.class);


	static SessionFactory sf = null;
	static Session session = null;

	static {
		openDbObjects();
	}

	private static void openDbObjects() {
		logger.info("openDbObjects opened session and sesson factory");
		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();
	}

	
	public static void getAllSchool() {
		try {
			openDbObjects();
			List<School> ls = session.createCriteria(School.class).list();
			logger.info("get all school{}", ls.size());
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("getAllSchool error {}",e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}

	}

	public static School getSchool(int id) {
		School school = null;
		try {
			openDbObjects();
			 school = session.get(School.class, id);
			if (school != null) {
				logger.info("getSchool successfully by id================{} ",id);
				return school;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSchool id {} error {} ", id, e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}
		return school;
	}

	public static void saveOrUpdateSchool(School s) {
		try {
			openDbObjects();
			logger.info("saving school info");
			session.saveOrUpdate(s); // gave check to cashier 5 lacs
			 DbUtil.doTransaction(session); // now cashier doing transaction
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveSchool  error {} ", e.getMessage());
		}finally {
			DbUtil.closeDbObjects(session, sf);
		}
	}


}
