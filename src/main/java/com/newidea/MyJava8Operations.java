package com.newidea;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.newidea.entity.Student;

public class MyJava8Operations {

	public static void doJava8OPerations(List<Student> list) {
		// TODO Auto-generated method stub

		// apply all java 8 operaions
		
		doJava8MapOperations(list);

		List<Student> ls = list.stream().filter(s -> s.getId() > 2).collect(Collectors.toList());

		List<Integer> listOfId = list.stream().map(s -> s.getId()).filter(id -> id > 2).collect(Collectors.toList());

		getNamesBasedOnCOndition(list);

	}

	private static void doJava8MapOperations(List<Student> list) {
		Map<Integer, String> studentMap = list.stream()
			    .collect(Collectors.toMap(
			        Student::getId,     // key: student ID
			        Student::getName    // value: student name
			    ));
		
		
		
	}

	private static void getNamesBasedOnCOndition(List<Student> list) {
		List<String> names = list.stream().filter(s -> s.getId() > 2).map(s -> s.getName()) // get only the names
				.collect(Collectors.toList());

		names.forEach(System.out::println);
	}

}
