package utils;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {
    /*
      *读取Hibernate.cfg.xml文件
      *
    */
    private static SessionFactory sessionFactory;
    static
    {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
             sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    /*
      *打开Session
    */

    public static Session getSession() {
        return sessionFactory.openSession();
    }
    /*
      *关闭Session
    */

    public static void closeSession(Session session) {
        if (session != null) {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}