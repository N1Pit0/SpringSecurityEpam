package com.mygym.crm.backstages.persistence.daos;

import com.mygym.crm.backstages.domain.models.common.User;
import com.mygym.crm.backstages.repositories.daorepositories.UserReadOnlyDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class UserReadOnlyDaoImpl implements UserReadOnlyDao {

    private static final Logger logger = LoggerFactory.getLogger(UserReadOnlyDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> findByUserName(String username) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String sql = """
                    SELECT u\s
                    FROM User u\s
                    WHERE u.userName = :username
                    """;

            User user = (User) session.createQuery(sql.strip())
                    .setParameter("username", username)
                    .uniqueResult();

            Optional<User> userOptional = Optional.ofNullable(user);

            userOptional.ifPresentOrElse(
                    (user1) -> logger.info("User with userName: {} has been found", username),
                    () -> logger.warn("User with userName: {} was not found", username)
            );

            return userOptional;

        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Long countSpecificUserName(String specificUserName) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String sql = """
                        SELECT count(*)\s
                        FROM user_table\s
                        WHERE username LIKE :name
                    """;
            return (Long) session.createNativeQuery(sql.strip()).addScalar("count", StandardBasicTypes.LONG)
                    .setParameter("name", specificUserName + "%").uniqueResult();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
