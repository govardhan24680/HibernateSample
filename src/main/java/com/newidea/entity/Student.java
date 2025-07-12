package com.newidea.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;


@Entity
@FilterDef(name = "studentFilterByName", parameters = @ParamDef(name = "studentName", type = "string"))
@Filter(name = "studentFilterByName", condition = "name = :studentName")
@FilterDef(name = "studentFilterById", parameters = @ParamDef(name = "studentId", type = "int"))
@Filter(name = "studentFilterById", condition = "id = :studentId")
@NamedQueries({
@NamedQuery(name = "getAllStudents", query = "FROM Student"),
@NamedQuery(name = "studentFindByName", query = "FROM Student s WHERE s.name = :name")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable{
	
	@Id
	@GeneratedValue
	@PrimaryKeyJoinColumn
	private int id;
	
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Passport passport;
	
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolIdForeignKey")
	private School school;
	//student.getSchool().getName(); //non id prop, then get real data
	
	
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name="course_student",joinColumns= 
    {@JoinColumn(name="student_id")},inverseJoinColumns=
	{@JoinColumn(name="course_id")})
	private List<Course> listOfCourses;
	
	
	
	

	public List<Course> getListOfCourses() {
		return listOfCourses;
	}

	public void setListOfCourses(List<Course> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
}
