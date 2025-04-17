package com.mygym.crm.backstages.repositories.daos;

import com.mygym.crm.backstages.domain.models.common.User;
import com.mygym.crm.backstages.exceptions.custom.NoResourceException;
import com.mygym.crm.backstages.interfaces.daorepositories.UserReadOnlyDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class UserReadOnlyDaoImpl implements UserReadOnlyDao {

    private static final Logger logger = LoggerFactory.getLogger(UserReadOnlyDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<User> findByUserName(String username) {
        try {

            String sql = """
                    SELECT u\s
                    FROM User u\s
                    WHERE u.userName = :username
                    """;

            User user = entityManager.createQuery(sql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            Optional<User> userOptional = Optional.ofNullable(user);

            userOptional.ifPresentOrElse(
                    (user1) -> logger.info("User with userName: {} has been found", username),
                    () -> logger.warn("User with userName: {} was not found", username)
            );

            return userOptional;

        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new NoResourceException(e.getMessage());
        }
    }

    @Override
    public Long countSpecificUserName(String specificUserName) {
        try {

            String sql = """
                        SELECT count(*)\s
                        FROM User\s
                        WHERE userName LIKE :name
                    """;
            return ((Number) entityManager
                    .createQuery(sql)
                    .setParameter("name", specificUserName + "%")
                    .getSingleResult()).longValue();
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
