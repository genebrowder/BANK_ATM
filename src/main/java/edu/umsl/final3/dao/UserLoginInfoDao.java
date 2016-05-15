package edu.umsl.final3.dao;

import edu.umsl.final3.domain.UserLoginInfo;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;


import edu.umsl.final3.domain.UserLoginInfo;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by genebrowder on 5/10/16.
 */
public class UserLoginInfoDao {

    public static boolean isValiduser(UserLoginInfo UserLoginInfo) {
        List<UserLoginInfo> alluserLoginInfoList = getAllUserLoginInfo();

        for (UserLoginInfo userLogininfo : alluserLoginInfoList) {

            if ((UserLoginInfo.getAccountNumber().equals(userLogininfo.getAccountNumber())) &&
                    (UserLoginInfo.getPin().equals(userLogininfo.getPin()))) {

                return true;
            }
        }

        return false;

    }


    public static UserLoginInfo getUser(UserLoginInfo userLoginInfo) {

        Session session = getSession();


        Transaction tx = null;

        List allUserLoginInfoList = new ArrayList();
        ;
        UserLoginInfo userLoginInfoDatabase = null;
        try {
            tx = session.beginTransaction();

            Query queryResult = session.createQuery("from UserLoginInfo");

            allUserLoginInfoList = queryResult.list();

            for (int i = 0; i < allUserLoginInfoList.size(); i++) {
                userLoginInfoDatabase = (UserLoginInfo) allUserLoginInfoList.get(i);
                if ((userLoginInfoDatabase.getAccountNumber().equals(userLoginInfo.getAccountNumber())) &&
                        (userLoginInfoDatabase.getPin().equals(userLoginInfo.getPin()))) {
                    return userLoginInfoDatabase;
                }
            }


            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    private static List getAllUserLoginInfo() {

        Session session = getSession();


        Transaction tx = null;

        List allUserLoginInfoList = new ArrayList();
        try {
            tx = session.beginTransaction();

            Query queryResult = session.createQuery("from UserLoginInfo");


            allUserLoginInfoList = queryResult.list();
            for (int i = 0; i < allUserLoginInfoList.size(); i++) {
                UserLoginInfo userLoginInfo = (UserLoginInfo) allUserLoginInfoList.get(i);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return allUserLoginInfoList;
    }

    public static long saveUserLoginInfo(UserLoginInfo UserLoginInfo) {

        Session session = getSession();
        Transaction tx = null;
        long userLoginInfoID = 0;
        try {
            tx = session.beginTransaction();

            //This allows a user and a password to be added only once
            if (!isValiduser(UserLoginInfo)) {
                userLoginInfoID = (long) session.save(UserLoginInfo);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return userLoginInfoID;
    }

    public static void updateUserLoginInfo(UserLoginInfo UserLoginInfo) {

        Session session = getSession();
        Transaction tx = null;
        long userLoginInfoID = 0;
        try {
            tx = session.beginTransaction();

            session.update(UserLoginInfo);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }


    }




    private static Session getSession() {
        //Hibernate Code  ----------------------------------------------------------

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());

        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        //Hibernate Code  ----------------------------------------------------------

        SessionFactory factory = new Configuration().configure().buildSessionFactory(serviceRegistry);

        //Hibernate Code  ----------------------------------------------------------

        return factory.openSession();
    }
}
