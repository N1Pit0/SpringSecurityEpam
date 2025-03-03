package com.mygym.crm.backstages.persistence.daos.userdaoreadonly;

import com.mygym.crm.backstages.repositories.daorepositories.UserDaoReadOnly;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoReadOnlyImpl implements UserDaoReadOnly {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<String> findAllUserNames() {
        try{
            Session session = this.sessionFactory.getCurrentSession();
            return session.createNativeQuery("SELECT username FROM user_table", String.class).list();
        } catch(HibernateException e){
            e.printStackTrace();
            //for logging purposes
            throw new HibernateException(e);
        }

    }

    public Long countSpecificUserName(String specificUserName) {
        try{
            Session session = this.sessionFactory.getCurrentSession();
            String sql = """
                SELECT count(*)\s
                FROM user_table\s
                WHERE username LIKE :name
            """;
            return (Long) session.createNativeQuery(sql.strip()).addScalar("count", StandardBasicTypes.LONG)
                    .setParameter("name", specificUserName + "%").uniqueResult();
        } catch(HibernateException e){
            e.printStackTrace();
            //for logging purposes
            throw new HibernateException(e);
        }
    }
}
