package com.newidea;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newidea.entity.Student;
import com.newidea.util.DbUtil;
import com.newidea.util.HibernateUtil;

public class StudentDaoImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);
	
	//I i = new A();
	
	//info
	//warn
	//error
	//debug

	static SessionFactory sf = null;
	static Session session = null;

//	public static void runningCurdOperations() {
//		System.out.println("running curd operations===================");
//	}

	static {
		openDbObjects();
	}

	private static void openDbObjects() {
		logger.info("openDbObjects opened session and sesson factory");
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
	
	/**
	 * @param student
	 * This is used for updating student
	 */
	public static void updateStudent(Student student) {
		logger.info("updateStudent updating student");
		try {
			if (student != null) {
				logger.info("updateStudent updating student id {} name {}", student.getId(), student.getName());  
				openDbObjects();
				session.saveOrUpdate(student);
				DbUtil.doTransaction(session);
			} else {
				logger.warn("updateStudent student is {}", student);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateStudent error {}", e.getMessage());
		}finally {
			DbUtil.closeDbObjects(session, sf);
		}
	}
	
	
	public static List<Student> getAllStudent() {
		List<Student> ls = null;
		try {
			// logger.info("getAllStudent started");
			openDbObjects();
			 ls = session.createCriteria(Student.class).list();
			System.out.println("get all student successfully========size========"+ls.size());
			return ls;
		} catch (HibernateException e) {
			e.printStackTrace();
			// logger.error("getAllStudent error {}", e.getMessage());
			System.out.println("getAllStudent error {}" + e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}
		return ls;

	}
	
	public static void getAllStudentUsingPagination(int pageNumber, int pageSize) {
		try {
			openDbObjects();
			
		//	int pageSize = 10;
			int startIndex = (pageNumber - 1) * pageSize;

			List<Student> ls = session.createCriteria(Student.class)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.list();
			
			for(Student st: ls) {
				System.out.println("getAllStudentUsingPagination========> "+st.getId());
			}

			System.out.println("Page " + pageNumber + " student count: " + ls.size()); //10

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getAllStudent error: " + e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}
	}


	public static Student getStudent(int id) {
		Student student = null;
		try {
			openDbObjects();
			// logger.info("getStudent using id {} ", id);
			 student = session.get(Student.class, id);
			if (student != null) {
				//System.out.println(student.getListOfCourses().size());
				System.out.println("get student successfully by id================"+id);
				
				return student;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("getStudent id {} error {}", id, e.getMessage());
			System.out.println("getStudent id " + id + " error " + e.getMessage());
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}
		return student;
	}
	
	public static Student getStudent2(int id, Session session) {
		Student student = null;
		try {
			 student = session.get(Student.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	

	public static void saveStudent(Student s) {
		
		session.save(s); // gave check to cashier 5 lacs
		 DbUtil.doTransaction(session); // now cashier doing transaction
		 System.out.println("Saved student successfully================");
			DbUtil.closeDbObjects(session, sf);
	}
	
	public static void deleteStudent(Student student) {
		openDbObjects();
	    session.delete(student); // instruct Hibernate to delete the object
	    DbUtil.doTransaction(session); // now cashier doing transaction
System.out.println("deleted student succsessfulky====");
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
	
	
	
	public static void getAllStudentsUsingHQL() {
	    try {
	        openDbObjects();
	        String hql = "FROM Student"; // HQL is case-sensitive for entity names
	        List<Student> students = session.createQuery(hql, Student.class).list();

	        for (Student s : students) {
	            System.out.println("HQL Student: ==============" + s.getName());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbUtil.closeDbObjects(session, sf);
	    }
	}
	
	// @NamedQuery(name = "Student.findByName", query = "FROM Student s WHERE s.name = :name")
//	@NamedQueries({
//	    @NamedQuery(name = "Student.findAll", query = "FROM Student"),
//	    @NamedQuery(name = "Student.findByName", query = "FROM Student s WHERE s.name = :name")
//	})
//	
//	List<Student> list = session.createNamedQuery("Student.findByName", Student.class)
//            .setParameter("name", "John")
//            .list();
	public static void getStudentsUsingNamedQuery() {
	    try {
	    	session = HibernateUtil.getSessionFactory().openSession();
	        List<Student> students = session.createNamedQuery("getAllStudents", Student.class).list();

	        for (Student s : students) {
	            System.out.println("=========NamedQuery Student: " + s.getName());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbUtil.closeDbObjects(session, sf);
	        HibernateUtil.shutdown();
	    }
	}
	
	public static void getStudentsUsingNamedQueryFindByName(String studentName) {
	    try {
	    	session = HibernateUtil.getSessionFactory().openSession();
	        List<Student> students = session.createNamedQuery("studentFindByName", Student.class)
	        		.setParameter("name", studentName)
	        		.list();

	        for (Student s : students) {
	            System.out.println("=========NamedQuery Student find by name: " + s.getName());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbUtil.closeDbObjects(session, sf);
	        HibernateUtil.shutdown();
	    }
	}

	
	public static void getStudentsUsingCriteria(String nameFilter) {
	    try {
	        openDbObjects();

	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<Student> query = builder.createQuery(Student.class);
	        Root<Student> root = query.from(Student.class);
	        //root.get("name")  //for apply property names // here name property will take from Student class

	        if (nameFilter != null && !nameFilter.isEmpty()) {
	            query.select(root).where(builder.like(root.get("name"), "%" + nameFilter + "%")); //%es%
	            //query.select(root).where(builder.gt(root.get("age"), 20));
	        } else {
	            query.select(root);
	        }

	        List<Student> students = session.createQuery(query).getResultList();

	        for (Student s : students) {
	            System.out.println("===============Criteria Student: nameFilter " + s.getName());  //this list of names conatins es word
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbUtil.closeDbObjects(session, sf);
	    }
	}
	
	
	
	public static void usingHibernateFilter() {

		try {
			openDbObjects();
			session.beginTransaction();

			// Enable filter by name
			Filter filterByName = session.enableFilter("studentFilterByName");
			filterByName.setParameter("studentName", "s1");

			// Enable filter by ID
//			Filter filterById = session.enableFilter("studentFilterById");
//			filterById.setParameter("studentId", 9);
			session.disableFilter("studentFilterById");

			// Now when you query, filters will be applied
			List<Student> students = session.createQuery("FROM Student", Student.class).getResultList();

			for (Student student : students) {
				System.out.println("hibernate filter===== "+student.getName());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.closeDbObjects(session, sf);
		}

	}
}
