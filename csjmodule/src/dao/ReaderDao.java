package dao;

import cn.itcast.commons.CommonUtils;
import domain.Reader;
import domain.PageBean;
import utils.HibernateUtil;

import java.util.*;

import java.sql.SQLException;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.hibernate.Session;
import org.hibernate.query.Query;

import cn.itcast.jdbc.TxQueryRunner;



public class ReaderDao {

    //private QueryRunner qr = new TxQueryRunner();

    private Session session;

    public ReaderDao()
    {
    }

    public void add (Reader r)
    {
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();

            r.setId(CommonUtils.uuid());
            Reader reader = new Reader(r);

            session.save(reader);
            session.getTransaction().commit();

            /*
            String sql = "INSERT INTO t_reader VALUES(?,?,?,?,?,?)";
            Object[] params = {r.getId(), r.getName(), r.getGender(), r.getPhone(), r.getEmail(), r.getDescription()};
            qr.update(sql, params);
            */
        }
        catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            HibernateUtil.closeSession(session);
        }
    }

    public Reader find(String id) {
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();

            Reader reader = session.get(Reader.class, id);

            session.getTransaction().commit();
            return reader;

            /*
            String sql = "SELECT * FROM t_reader WHERE id=?";
            return qr.query(sql, new BeanHandler<Reader>(Reader.class),id);
            */
        } catch (Exception e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        finally {
            HibernateUtil.closeSession(session);
        }
    }

    public void edit(Reader r)
    {
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();

            String hql = "UPDATE Reader SET name=?,gender=?,phone=?,email=?,description=? WHERE id=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, r.getName());
            query.setParameter(1, r.getGender());
            query.setParameter(2, r.getPhone());
            query.setParameter(3, r.getEmail());
            query.setParameter(4, r.getDescription());
            query.setParameter(5, r.getId());
            int result = query.executeUpdate();


            session.getTransaction().commit();

            /*
            String sql = "UPDATE t_reader SET name=?,gender=?,phone=?,email=?,description=? WHERE id=?";
            Object[] params = new Object[]{r.getName(),r.getGender(),r.getPhone(),r.getEmail(),r.getDescription(),r.getId()};
            qr.update(sql,params);
            */
        }
        catch(Exception e){
            //throw new RuntimeException(e);
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            HibernateUtil.closeSession(session);
        }
    }

    public void delete(String id)
    {
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();

            Reader reader = session.get(Reader.class, id);
            session.delete(reader);

            session.getTransaction().commit();

            /*
            String sql = "DELETE FROM t_reader WHERE id = ?";
            qr.update(sql,id);
            */
        }
        catch (Exception e){
            //throw new RuntimeException(e);
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            HibernateUtil.closeSession(session);
        }
    }

    public PageBean findAll(int pc, int pr)
    {
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();

            PageBean pb = new PageBean();
            pb.setPc(pc);
            pb.setPr(pr);

            /*
            String sql = "SELECT COUNT(*) FROM t_reader";
            Number number = (Number)qr.query(sql,new ScalarHandler());
            */

            String hql = "SELECT COUNT(r) FROM Reader r";
            Query query = session.createQuery(hql);
            Number number = (Number)query.uniqueResult();

            int tr = number.intValue();
            pb.setTr(tr);

            /*
            sql = "SELECT * FROM t_reader LIMIT ?,?";
            Object[] params = {(pc - 1)*pr,pr};
            List<Reader> beanList = qr.query(sql,new BeanListHandler<Reader>(Reader.class),params);
            */



            hql = "FROM Reader";
            List<Reader> beanList = session.createQuery(hql).setFirstResult((pc-1)*pr).setMaxResults(pr).list();

            pb.setBeanList(beanList);
            return pb;
        }
        catch(Exception e){
            //throw new RuntimeException(e);
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        finally {
            HibernateUtil.closeSession(session);
        }
    }

    public PageBean query(Reader r, int pc, int pr)
    {
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();

            PageBean pb = new PageBean();
            pb.setPc(pc);
            pb.setPr(pr);


            StringBuilder cntsql = new StringBuilder("SELECT COUNT(r) FROM Reader r");

            StringBuilder wheresql = new StringBuilder(" WHERE 1 = 1");

            String name = r.getName();
            if(name != null && !name.trim().isEmpty())
            {
                wheresql.append(" AND r.name= '" + name + "'");
            }


            String gender = r.getGender();
            if(gender != null && !gender.trim().isEmpty())
            {
                wheresql.append(" AND r.gender= '" + gender +"'");
            }

            String phone = r.getPhone();
            if(phone != null && !phone.trim().isEmpty())
            {
                wheresql.append(" AND r.phone='" + phone + "'");
            }

            String email = r.getEmail();
            if(email != null && !email.trim().isEmpty())
            {
                wheresql.append(" AND r.email='" + email + "'");
            }

            String description = r.getDescription();
            if(description != null && !description.trim().isEmpty())
            {
                wheresql.append("AND r.description='" + description + "'");
            }

            Number number = (Number)session.createQuery(cntsql.append(wheresql).toString()).uniqueResult();
            int tr = number.intValue();
            pb.setTr(tr);

            StringBuilder initsql = new StringBuilder("FROM Reader r");
            List<Reader> beanList = session.createQuery(initsql.append(wheresql).toString()).setFirstResult((pc-1)*pr).setMaxResults(pr).list();

            /*
            StringBuilder cntsql = new StringBuilder("SELECT COUNT(*) FROM t_reader");
            StringBuilder wheresql = new StringBuilder(" WHERE 1 = 1");
            List<Object> params = new ArrayList<>();

            String name = r.getName();
            if(name != null && !name.trim().isEmpty())
            {
                wheresql.append(" AND name LIKE ?");
                params.add("%" + name + "%");
            }

            String gender = r.getGender();
            if(gender != null && !gender.trim().isEmpty())
            {
                wheresql.append(" AND gender=?");
                params.add(gender);
            }

            String phone = r.getPhone();
            if(phone != null && !phone.trim().isEmpty())
            {
                wheresql.append(" AND phone LIKE ?");
                params.add("%" + phone + "%");
            }

            String email = r.getEmail();
            if(email != null && !email.trim().isEmpty())
            {
                wheresql.append(" AND email LIKE ?");
                params.add("%" + email + "%");
            }

            String description = r.getDescription();
            if(description != null && !description.trim().isEmpty())
            {
                wheresql.append("AND description=?");
                params.add("%" + description + "%");
            }

            Number number = (Number)qr.query(cntsql.append(wheresql).toString(),new ScalarHandler(),params.toArray());
            int tr = number.intValue();
            pb.setTr(tr);

            StringBuilder initsql = new StringBuilder("SELECT * FROM t_reader");
            StringBuilder limitsql = new StringBuilder(" LIMIT ?,?");
            params.add((pc-1)*pr);
            params.add(pr);
            List<Reader> beanList = qr.query(initsql.append(wheresql).append(limitsql).toString(),new BeanListHandler<Reader>(Reader.class),params.toArray());
            */

            pb.setBeanList(beanList);
            System.out.println(1);
            return pb;

        }
        catch(Exception e){
            //throw new RuntimeException(e);
            e.printStackTrace();
            return null;
        }
        finally{
            HibernateUtil.closeSession(session);
        }
    }

}
