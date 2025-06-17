package com.newidea;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.newidea.entity.Employee;
import com.newidea.entity.Student;

public class MyMainClass {

	static SessionFactory sf = null;
	static Session session = null;

	public static void main(String[] args) {

//		sf = MyHibernateConfiguration.getSessionFactory();
//		session = sf.openSession();

		doStudentOperations();

		EmployeeDaoImpl.saveEmployee(getNewEmp());

	}

	private static void doStudentOperations() {
		StudentDaoImpl.saveStudent(getNewStudent());
		updateStudentUsingId(1);
		StudentDaoImpl.getStudent(1);
		StudentDaoImpl.getAllStudent();
		deleteStudentUsingId(1);
	}

	private static void deleteStudentUsingId(int id) {
		Student stu = StudentDaoImpl.getStudent(id);
		if (stu != null) {
			StudentDaoImpl.deleteStudent(stu);
		} else {
			System.out.println("not foud student for delete");
		}
	}

	private static void updateStudentUsingId(int id) {
		Student stu = StudentDaoImpl.getStudent(id);
		if (stu != null) {
			stu.setName("new name updated");
			StudentDaoImpl.updateStudent(stu);
		} else {
			System.out.println("not foud student for update");
		}
	}

//here we don't have controller class and service classes, 
//so I am using this class for call the dao class

	private static Student getNewStudent() {
		Student s = new Student();
		s.setName("s1");
		return s;
	}

	private static Employee getNewEmp() {
		Employee employee = null;
		try {
			employee= new Employee();
			employee.setName("s1");
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

}
