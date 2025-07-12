package com.newidea;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.newidea.constants.HibernateSampleConstants;
import com.newidea.entity.Student;

public class Level2CacheDemo {

    public static void main(String[] args) {
//        System.out.println("===== Without L2 Cache =====");
//        testWithoutCache();

        System.out.println("\n===== With L2 Cache =====");
        testWithCache();
    }

    private static void testWithoutCache() {
        // First session
        SessionFactory factory = MyHibernateConfiguration.getSessionFactory();
        Session session1 = factory.openSession();
        Student s1 = session1.get(Student.class, HibernateSampleConstants.STUDENT_ID);
        System.out.println("========testWithoutCache==========Session 1: Student name = " + s1.getName());
        session1.close();

        // Second session
        Session session2 = factory.openSession();
        Student s2 = session2.get(Student.class, HibernateSampleConstants.STUDENT_ID);
        System.out.println("===========testWithoutCache  ===Session 2: Student name = " + s2.getName());
        session2.close();

        factory.close();
    }

    private static void testWithCache() {
        SessionFactory factory = MyHibernateConfiguration.getSessionFactory();

        Session session1 = factory.openSession();
        Student s1 = session1.get(Student.class, HibernateSampleConstants.STUDENT_ID);
        System.out.println("Session 1: Student name = " + s1.getName());
        session1.close();

        System.out.println("================Sleeping for 2 seconds to show second session reuse cache...");
        try { Thread.sleep(2000); } catch (Exception e) {}

        Session session2 = factory.openSession();
        Student s2 = session2.get(Student.class, HibernateSampleConstants.STUDENT_ID);
        System.out.println("==============Session 2: Student name = " + s2.getName());
        session2.close();

        factory.close();
    }
}
