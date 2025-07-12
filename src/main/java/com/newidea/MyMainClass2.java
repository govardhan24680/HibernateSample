package com.newidea;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newidea.constants.HibernateSampleConstants;
import com.newidea.entity.School;
import com.newidea.entity.Student;
import com.newidea.util.DbUtil;

public class MyMainClass2 {

	private static final Logger logger = LoggerFactory.getLogger(MyMainClass2.class);

	public static void main(String[] args) {

		// applyOneToMany();
		// applyOneToManyCase2();
		joinExistingStudentsInAnyExistingSchool();

	}

	private static void applyOneToManyCase2() {
		// TODO Auto-generated method stub

		School sc = new School();
		sc.setName("Jansi MJ");
		List<Student> listOfStudents = createListOfNewStudents(sc);
		sc.setListOfStudents(listOfStudents);

		SchoolDaoImpl.saveOrUpdateSchool(sc);

	}

	// here cascade type all will work, no need to save every student saparately,
	// why bcz student are new objects
	private static void applyOneToMany() {
		// TODO Auto-generated method stub

		School sc = SchoolDaoImpl.getSchool(13);

		List<Student> listOfStudents = createListOfNewStudents(sc);
		sc.setListOfStudents(listOfStudents);

		SchoolDaoImpl.saveOrUpdateSchool(sc);

	}

	/**
	 * @param sc
	 * @return
	 * This method developed for create new students
	 */
	private static List<Student> createListOfNewStudents(School sc) {
		// TODO Auto-generated method stub
		List list = new ArrayList<Student>();
		Student s1 = new Student();

		s1.setName("mahesh");
		// s1.setSchool(sc);
		list.add(s1);
		s1 = new Student();
		s1.setName("Suresh");
		// s1.setSchool(sc);
		list.add(s1);
		s1 = new Student();
		s1.setName("Ramesh");
		// s1.setSchool(sc);
		list.add(s1);
		return list;
	}

	public static void joinExistingStudentsInAnyExistingSchool() {
		// TODO Auto-generated method stub
		SessionFactory sf = null;
		Session session = null;
		try {
			School school = SchoolDaoImpl.getSchool(HibernateSampleConstants.SCHOOL_ID);

			sf = MyHibernateConfiguration.getSessionFactory();
			session = sf.openSession();
			List<Student> studentsWithoutSchool = getStudentsWhoNotJoinedINSchool(session);
			logger.info("joinExistingStudentsInAnyExistingSchool studentsWithoutSchool size {}",
					studentsWithoutSchool.size());
			logger.info("Get school details using school id {}", HibernateSampleConstants.SCHOOL_ID);

			for (Student st : studentsWithoutSchool) {
				st.setSchool(school);
				session.saveOrUpdate(st); // for saving, why bcz cascade will work only for new objects
				DbUtil.doTransaction(session);
			}
			school.setListOfStudents(studentsWithoutSchool);
			SchoolDaoImpl.saveOrUpdateSchool(school);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error while get school details school id {} error message {}",
					HibernateSampleConstants.SCHOOL_ID, e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}

		// Error while get school details school id 13
		// Error while get school details school id 13 error message

	}

	// new school means, not exist school details in db
	public static void joinExistingStudentsInNewSchool() {

		// TODO Auto-generated method stub
		SessionFactory sf = null;
		Session session = null;

		try {
			sf = MyHibernateConfiguration.getSessionFactory();
			session = sf.openSession();
			List<Student> studentsWithoutSchool = getStudentsWhoNotJoinedINSchool(session);
			// SELECT * FROM myhibernatedb.student where schoolIdForeignKey is null
			logger.info("joinExistingStudentsInAnyExistingSchool studentsWithoutSchool size {}",
					studentsWithoutSchool.size());
			logger.info("Get school details using school id {}", HibernateSampleConstants.SCHOOL_ID);

			// if(studentsWithoutSchool != null && studentsWithoutSchool.size() > 0)
			if (CollectionUtils.isNotEmpty(studentsWithoutSchool)) {
				School school = new School();
				school.setName("RND school");
				for (Student st : studentsWithoutSchool) {
					st.setSchool(school); // setting/adding new school to every student
					session.saveOrUpdate(st); // for saving, why bcz cascade will work only for new objects
					DbUtil.doTransaction(session);
				}
				school.setListOfStudents(studentsWithoutSchool);
				SchoolDaoImpl.saveOrUpdateSchool(school);
			} else {
				saveNewStudents(HibernateSampleConstants.SAVE_NEW_STUDENTS_SIZE);
				joinExistingStudentsInNewSchool();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error while get school details school id {} error message {}",
					HibernateSampleConstants.SCHOOL_ID, e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}

		// Error while get school details school id 13
		// Error while get school details school id 13 error message

	}

	private static List<Student> getStudentsWhoNotJoinedINSchool(Session session) {
		Criteria criteria = session.createCriteria(Student.class);

		// private School school; this school reference alreay mentioned in student
		// class
		criteria.add(Restrictions.isNull("school")); // apply the conditon
		// criteria.setMaxResults(2);//get max 2 objects
		List<Student> studentsWithoutSchool = criteria.list();
		return studentsWithoutSchool;
	}

	private static void saveNewStudents(int saveNewStudentsSize) {
		SessionFactory sf = null;
		Session session = null;
		try {
			// TODO Auto-generated method stub

			sf = MyHibernateConfiguration.getSessionFactory();
			session = sf.openSession();

			Student s1 = null;

			for (int i = 1; i <= saveNewStudentsSize; i++) {
				s1 = new Student();
				s1.setName("Maheh");
				session.save(s1);
				DbUtil.doTransaction(session);
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}

	}
}
