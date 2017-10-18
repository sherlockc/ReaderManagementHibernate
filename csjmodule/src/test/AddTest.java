package test;

import cn.itcast.commons.CommonUtils;
import domain.Reader;
import org.hibernate.Session;
import org.junit.Test;
import utils.HibernateUtil;

/*
public class AddTest {
    @Test
    public void add ()
    {
        Session session = null;
        try {
            Reader reader = new Reader();
            reader.setId("123");
            reader.setName("csj");
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(reader);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            HibernateUtil.closeSession(session);
        }
    }
}

*/
