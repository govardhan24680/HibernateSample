package com.newidea;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.newidea.entity.Employee;
import com.newidea.entity.Student;

public class MyMainClass {

	static SessionFactory sf = null;
	static Session session = null;

	public static void main(String[] args) {

		sf = MyHibernateConfiguration.getSessionFactory();
		session = sf.openSession();

		StudentDaoImpl.saveStudent(getNewStudent());
		EmployeeDaoImpl.saveEmployee(getNewEmp());
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
		// TODO Auto-generated method stub
		Student s = new Student();
		s.setName("s1");
		return s;

	}

	private static Employee getNewEmp() {
		// TODO Auto-generated method stub
		Employee e = new Employee();
		e.setName("s1");
		return e;

	}

}
