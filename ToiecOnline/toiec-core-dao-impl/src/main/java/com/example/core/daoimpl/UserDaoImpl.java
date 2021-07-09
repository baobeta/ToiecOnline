package com.example.core.daoimpl;

import com.example.core.dao.RoleDao;
import com.example.core.dao.UserDao;
import com.example.core.data.daoimpl.AbstractDao;
import com.example.core.persistence.entity.RoleEntity;
import com.example.core.persistence.entity.UserEntity;
import com.example.toiec.core.common.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoImpl extends AbstractDao<Integer, UserEntity> implements UserDao {
    @Override
    public Object[] checkLogin(String username, String password) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        boolean isUserExist = false;
        String roleName = null;
        try {
            StringBuilder sql = new StringBuilder("FROM UserEntity ue WHERE ue.name= :name AND ue.password= :password ");
            Query query = session.createQuery(sql.toString());
            query.setParameter("name",username);
            query.setParameter("password",password);
            if(query.getResultList().size()>0) {
                isUserExist = true;
                UserEntity userEntity = (UserEntity) query.getSingleResult();
                roleName = userEntity.getRoleEntity().getName();
            }
        } catch (HibernateException e)
        {
            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }

        return new Object[] {isUserExist, roleName};
    }

    public List<UserEntity> findByUsers(List<String> names) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserEntity> userEntities = new ArrayList<UserEntity>();
        try {
            StringBuilder sql = new StringBuilder(" FROM UserEntity ue WHERE ue.name IN(:names) ");
            Query query = session.createQuery(sql.toString());
            query.setParameter("names", names);
            userEntities = query.getResultList();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userEntities;
    }
//    @Override
//    public UserEntity findUserByUsernameAndPassword(String name, String password) {
//        UserEntity entity = new UserEntity();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            StringBuilder sql = new StringBuilder("FROM UserEntity WHERE name= :name AND password =:password ");
//            Query query =session.createQuery(sql.toString());
//            query.setParameter("name",name);
//            query.setParameter("password",password);
//            entity = (UserEntity) query.getSingleResult();
//            transaction.commit();
//        } catch (HibernateException e) {
//            transaction.rollback();
//            throw e;
//
//        } finally {
//            session.close();
//
//        }
//        return entity;
//
//    }
}
