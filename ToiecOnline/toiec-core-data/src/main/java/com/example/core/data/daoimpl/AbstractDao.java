package com.example.core.data.daoimpl;

import com.example.core.data.dao.GenericDao;
import com.example.toiec.core.common.constant.CoreConstant;
import com.example.toiec.core.common.utils.HibernateUtil;
import javassist.tools.rmi.ObjectNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AbstractDao<ID extends Serializable,T> implements GenericDao<ID,T> {

    private final Logger log = Logger.getLogger(this.getClass());
    private Class<T> persistentClass;
    public AbstractDao(){
        
        // get type parameters of T
        this.persistentClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }

    //convert persistent class to persistent class name
    public String getPersistentClassName() {
        return persistentClass.getSimpleName();
    }
    // get session

    @Override
    public List<T> findAll() {

        Session session =HibernateUtil.getSessionFactory().openSession();
        List<T> list = new ArrayList<>();
        Transaction transaction =null;
        try {
            //initialize transaction
            transaction = session.beginTransaction();
            //using StringBuilder help performance
            StringBuilder sql = new StringBuilder("from ");
            sql.append(this.getPersistentClassName());
            //using HQL
            Query query =session.createQuery(sql.toString());
            try {
                list = query.getResultList();
            } catch (ConstraintViolationException e )
            {
                throw e;
            }

            transaction.commit();

        }
        catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }
        finally {
            session.close();
        }
        return list;
    }

    @Override
    public T update(T entity) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        T result =null;
        Transaction transaction = session.beginTransaction();
        try {
            Object object = session.merge(entity);
            result = (T) object;
            transaction.commit();
        }
        catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public void save(T entity) throws ConstraintViolationException{
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }
        finally {
            session.close();
        }

    }

    @Override
    public T findById(ID id) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        T result = null;
        try {
            result = (T) session.get(persistentClass,id);
            if(result==null)
            {
                new ObjectNotFoundException("Not found "+ id,null);
            }

        }catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public Object[] findByProperty(Map<String,Object> property, String sortExpression, String sortDirection,
                                   Integer offset, Integer limit) {
        List<T> list = new ArrayList<T>();
        Session session =HibernateUtil.getSessionFactory().openSession();
        //initialize transaction
        Transaction transaction = session.beginTransaction();
        Object totalItem =0;
        String[] params = new String[property.size()];
        Object[] values = new Object[property.size()];
        int i=0;
        for(Map.Entry item : property.entrySet()) {
            params[i]= (String) item.getKey();
            values[i]=item.getValue();
            i++;
        }
        try {

            //using StringBuilder help performance
            StringBuilder sql = new StringBuilder("from ");
            sql.append(this.getPersistentClassName()).append(" where 1=1 ");
            if(property.size()>0) {
                for(int i1=0;i1<params.length;i1++) {
                    sql.append(" and ").append(" LOWER("+params[i1]+") LIKE '%' || :"+params[i1]+" || '%'");
                }
            }
            if(sortExpression!=null&&sortDirection!=null)
            {
                sql.append(" order by ").append(sortExpression);
                sql.append(" "+(sortDirection.equals(CoreConstant.SORT_ASC)?"asc":"desc"));
            }
            //using HQL
            Query query1 =session.createQuery(sql.toString());
            if(property.size()>0) {
                for(int i2=0;i2<params.length;i2++) {
                    query1.setParameter(params[i2],values[i2]);
                }

            }
            if(offset!=null&&offset>=0) {
                query1.setFirstResult(offset);
            }
            if(limit != null &&limit>0) {
                query1.setMaxResults(limit);
            }
            list =query1.getResultList();
            StringBuilder sql2 = new StringBuilder("select count(*) from ");
            sql2.append(getPersistentClassName()).append(" where 1=1 ");
            if(property.size()>0) {
                for(int k=0;k<params.length;k++) {
                    sql2.append(" and ").append(" LOWER("+params[k]+") LIKE '%' || :"+params[k]+" || '%'");
                }
            }
            Query query2 = session.createQuery(sql2.toString());
            if(property.size()>0) {
                for(int k1=0;k1<params.length;k1++) {
                    query2.setParameter(params[k1],values[k1]);
                }

            }
            totalItem =query2.getResultList().get(0);
            transaction.commit();

        }
        catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw e;
        }
        finally {
            session.close();
        }

        return new Object[] {totalItem,list};
    }

    @Override
    public Integer delete(List<ID> ids) {
        Integer count =0;
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
                for(ID item : ids )
                {
                    T t = (T)session.get(persistentClass,item);
                    session.delete(t);
                    count++;
                }
            transaction.commit();
        } catch (HibernateException e)
        {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw  e;
        } finally {
            session.close();
        }
        return  count;
    }


    public T findEqualUnique(String property, Object value) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        T result = null;
        try {
        String sql =" FROM "+getPersistentClassName()+" model WHERE "+property+"= :value";
        Query query = session.createQuery(sql.toString());
        query.setParameter("value",value);
        result = (T)query.getSingleResult();
        transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(),e);
            throw  e;

        } finally {
            session.close();
        }
        return result;
    }
}
