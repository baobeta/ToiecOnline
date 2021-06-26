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

public class UserDaoImpl extends AbstractDao<Integer, UserEntity> implements UserDao {
    @Override
    public UserEntity findUserByUsernameAndPassword(String name, String password) {
        UserEntity entity = new UserEntity();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder("FROM UserEntity WHERE name= :name AND password =:password ");
            Query query =session.createQuery(sql.toString());
            query.setParameter("name",name);
            query.setParameter("password",password);
            entity = (UserEntity) query.getSingleResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;

        } finally {
            session.close();

        }
        return entity;

    }
}
