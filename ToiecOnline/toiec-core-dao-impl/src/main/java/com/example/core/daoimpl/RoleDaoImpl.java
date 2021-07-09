package com.example.core.daoimpl;

import com.example.core.dao.RoleDao;
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

public class RoleDaoImpl extends AbstractDao<Integer, RoleEntity> implements RoleDao {


    @Override
    public List<RoleEntity> findByRoles(List<String> roles) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<RoleEntity> roleEntities = new ArrayList<RoleEntity>();
        try {
            StringBuilder sql = new StringBuilder(" FROM RoleEntity re WHERE re.name IN(:roles) ");
            Query query = session.createQuery(sql.toString());
            query.setParameter("roles", roles);
            roleEntities = query.getResultList();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return roleEntities;
    }
}
