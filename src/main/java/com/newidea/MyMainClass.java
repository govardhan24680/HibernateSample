package com.newidea;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.newidea.entity.Employee;
import com.newidea.entity.Passport;
import com.newidea.entity.Student;

public class MyMainClass {

	private static final String PASSPORT_ID = "P123";
	private static final String NEW_STUDENT_NAME = "s1";
	private static final String UPDATED_STUDENT_NAME = "new name updated";
	static SessionFactory sf = null;
	static Session session = null;

	public static void main(String[] args) {

//		sf = MyHibernateConfiguration.getSessionFactory();
//		session = sf.openSession();
		//StudentDaoImpl.getStudent(10);
		doStudentOperations();
		//doPassportOperations();

		//EmployeeDaoImpl.saveEmployee(createNewEmp());

	}

	private static void doStudentOperations() {
		StudentDaoImpl.saveStudent(createNewStudent());
		updateStudentUsingId(1);
		StudentDaoImpl.getStudent(10);
		StudentDaoImpl.getAllStudent();
		deleteStudentUsingId(1);
	}
	private static void doPassportOperations() {
	    PassportDaoImpl.savePassport(createNewPassport());
	    updatePassportUsingId(1);
	    PassportDaoImpl.getPassport(1);
	    PassportDaoImpl.getAllPassports();
	    deletePassportUsingId(1);
	}


	private static void deleteStudentUsingId(int id) {
		Student stu = StudentDaoImpl.getStudent(id);
		if (stu != null) {
			StudentDaoImpl.deleteStudent(stu);
		} else {
			System.out.println("not foud student for delete");
		}
	}
	private static void deletePassportUsingId(int id) {
	    Passport passport = PassportDaoImpl.getPassport(id);
	    if (passport != null) {
	        PassportDaoImpl.deletePassport(passport);
	    } else {
	        System.out.println("Passport not found for delete");
	    }
	}


	private static void updateStudentUsingId(int id) {
		Student stu = StudentDaoImpl.getStudent(id);
		if (stu != null) {
			stu.setName(UPDATED_STUDENT_NAME);
			StudentDaoImpl.updateStudent(stu);
		} else {
			System.out.println("not foud student for update");
		}
	}
	
	
	private static void updatePassportUsingId(int id) {   
	    Passport passport = PassportDaoImpl.getPassport(id);
	    if (passport != null) {
	       // passport.setExpiryDate(LocalDate.now().plusYears(5)); // Example update
	        PassportDaoImpl.updatePassport(passport);
	    } else {
	        System.out.println("Passport not found for update");
	    }
	}


//here we don't have controller class and service classes, 
//so I am using this class for call the dao class

	private static Student createNewStudent() {
		Student s = new Student();
		s.setName(NEW_STUDENT_NAME);
		
		Passport passport = new Passport();
		passport.setPassportId(PASSPORT_ID);
		
		s.setPassport(passport);
		
//		with out cascade type all
//		session.save(s.getPassport());  // Save passport first
//		session.save(s);                // Then save student
//		DbUtil.doTransaction(session);
//		
		
		return s;
	}
	
	
	private static Passport createNewPassport() {
	    Passport p = new Passport();
	    p.setPassportId("P123456");
	    
	    return p;
	}


	private static Employee createNewEmp() {
		Employee employee = null;
		try {
			employee= new Employee();
			employee.setName(NEW_STUDENT_NAME);
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

}
