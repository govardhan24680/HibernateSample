package com.newidea;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.newidea.entity.Passport;
import com.newidea.util.DbUtil;

public class PassportDaoImpl {
	
	

    static SessionFactory sf = null;
    static Session session = null;

    static {
        openDbObjects();
    }

    private static void openDbObjects() {
        sf = MyHibernateConfiguration.getSessionFactory();
        session = sf.openSession();
    }

    public static void savePassport(Passport passport) {
        try {
            openDbObjects();
            session.save(passport);
            DbUtil.doTransaction(session);
            System.out.println("Saved passport successfully===============");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeDbObjects(session, sf);
        }
    }

    public static void updatePassport(Passport passport) {
        try {
            if (passport != null) {
                openDbObjects();
                session.saveOrUpdate(passport);
                DbUtil.doTransaction(session);
                System.out.println("Updated passport successfully===============");
            } else {
                System.out.println("Passport not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeDbObjects(session, sf);
        }
    }

    public static Passport getPassport(int id) {
        Passport passport = null;
        try {
            openDbObjects();
            passport = session.get(Passport.class, id);
            if (passport != null) {
                System.out.println("Fetched passport by ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeDbObjects(session, sf);
        }
        return passport;
    }

    public static List<Passport> getAllPassports() {
        List<Passport> list = null;
        try {
            openDbObjects();
            list = session.createCriteria(Passport.class).list();
            System.out.println("Fetched all passports. Count: " + list.size());
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeDbObjects(session, sf);
        }
        return list;
    }

    public static void deletePassport(Passport passport) {
        try {
            openDbObjects();
            session.delete(passport);
            DbUtil.doTransaction(session);
            System.out.println("Deleted passport successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeDbObjects(session, sf);
        }
    }
}
