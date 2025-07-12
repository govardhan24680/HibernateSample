package com.newidea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.newidea.entity.Course;
import com.newidea.entity.Passport;
import com.newidea.entity.School;
import com.newidea.entity.Student;

public class HibernateRelations {

	static SessionFactory sf = null;
	static Session session = null;

	public static void getConfig() {
		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();
	}

	public static void main(String[] args) {
		executeRelations();
	}

	public static void executeRelations() {
		// doOneToOne();
		// doManyToOne();
		//doManyToManyCase2();
		//readDataBidirectionalInManyToManyCase();
		//doOneToOneTransactionFailure();
		doOneToOneTransactionSuccess();
	}

	public static void doManyToMany() {
		try {
			getConfig();

			Course c1 = new Course();
			c1.setName("Java");

			Course c2 = new Course();
			c2.setName("React");

			List<Course> listCourse = new ArrayList();
			listCourse.add(c1);
			listCourse.add(c2);

			Student s1 = new Student();
			s1.setName("Suresh");
			s1.setListOfCourses(listCourse);

//		session.save(c1);
//		session.save(c2);  //no need to save course details saparately, why bcz cascadeTYpe.all
			session.save(s1);

			doTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && sf != null) {
				session.close();
				sf.close();
			}
		}

	}

	public static void doManyToManyCase2() {
		try {
			getConfig();

			Course c = CourseDaoImpl.getCourse(44);
			Course c2 = CourseDaoImpl.getCourse(45);

		//	Student sss = StudentDaoImpl.getStudent(43);
			
//			System.out.println(sss.getListOfCourses().size());
//			System.out.println(c.getListOfStudents().size());   //try to get real objects

			List<Course> listCourse = new ArrayList();
			listCourse.add(c);
			listCourse.add(c2);

			Student s1 = new Student();
			s1.setName("Suresh2");
			s1.setListOfCourses(listCourse);

//		session.save(c1);
//		session.save(c2);  //no need to save course details saparately, why bcz cascadeTYpe.all
			session.save(s1);

			doTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && sf != null) {
				session.close();
				sf.close();
			}
		}

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
		p.setPassportId("p1");

		s.setPassport(p);
		session.save(p);
		session.save(s);
		doTransaction();

	}
	
	
	public static void doOneToOneTransactionSuccess() {
		  //  Session session = null;
		    Transaction tx = null;

		    try {
		        getConfig();
		        tx = session.beginTransaction();

		        // Step 2: Create Student and Passport objects
		        Student student = new Student();
		        student.setName("transactionSuccessStudent");

		        Passport p = new Passport();
		        p.setPassportId("P8765");  // Simulate failure (e.g., passportId is @NotNull)
		        p.setCountryName("Inida");
                p.setOfficeName("Hyd-Ameerpet");
		        // Step 3: Set the relationship
		        student.setPassport(p);

		        // Step 4: Save both
		        session.save(student);
		        session.save(p); // This will fail due to null ID (if constraints applied)
		        

		        // Step 5: Commit only if both succeed
		        tx.commit();
		        System.out.println("Transaction committed.");

		    } catch (Exception e) {
		        if (tx != null) {
		            tx.rollback();  // roll back if any save fails
		            System.out.println("Transaction rolled back due to error: " + e.getMessage());
		        }
		        e.printStackTrace();

			} finally {
				if (session != null) {
					session.close();
				}
				if (sf != null) {
					sf.close();
				}
			}
		}

	
	public static void doOneToOneTransactionFailure() {
	  //  Session session = null;
	    Transaction tx = null;

	    try {
	        getConfig();
	        tx = session.beginTransaction();

	        // Step 2: Create Student and Passport objects
	        Student student = new Student();
	        student.setName("transaction failure student");

	        Passport p = new Passport();
	        p.setPassportId(null);  // Simulate failure (e.g., passportId is @NotNull)

	        // Step 3: Set the relationship
	        student.setPassport(p);

	        // Step 4: Save both
	        session.save(student);
	        session.save(p); // This will fail due to null passportId
	        

	        // Step 5: Commit only if both succeed
	        tx.commit();
	        System.out.println("Transaction committed.");

	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();  // roll back if any save fails
	            System.out.println("Transaction rolled back due to error: " + e.getMessage());
	        }
	        e.printStackTrace();

	    } finally {
	        if (session != null) {
	            session.close();
	        }
	        if (sf != null) {
	            sf.close();
	        }
	    }
	}


	private static void doTransaction() {
		Transaction tr = session.beginTransaction();
		tr.commit();
		session.close();
		sf.close();
	}
	
	
	private static void readDataBidirectionalInManyToManyCase() {
		try {
			sf = MyHibernateConfiguration.getSessionFactory();
			session = sf.openSession();
			getStudentAndHisCourses();
			getCourseAndStudentsOfThisCourse();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally {
			if (session != null && sf != null) {
				session.close();
				sf.close();
			}
		}
	}

	private static void getCourseAndStudentsOfThisCourse() {
		Course course = CourseDaoImpl.getCourse2(44, session);
		System.out.println("===========student list size from course"+course.getListOfStudents().size());
		List<Student> listOfStudents = course.getListOfStudents();
		
		for(Student s : listOfStudents) {
			System.out.println(s.getId()+"----------"+s.getName());
		}
	}

	private static void getStudentAndHisCourses() {
		Student student = StudentDaoImpl.getStudent2(43, session);
		
		System.out.println("===========course list size from student"+student.getListOfCourses().size());
		List<Course> list = student.getListOfCourses();
		for(Course c : list) {
			System.out.println(c.getId()+"-------------"+c.getName());
		}
	}
	
	

}
