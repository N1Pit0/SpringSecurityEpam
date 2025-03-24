package com.mygym.crm.backstages.persistence.daos;

import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingTypeReadOnlyDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class TrainingTypeRadOnlyDaoImpl implements TrainingTypeReadOnlyDao {

    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeRadOnlyDaoImpl.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<TrainingType> getTrainingTypeByUserName(String trainingTypeName) {
        try{
            Session session = sessionFactory.openSession();
            String sql = """
                    SELECT t\s
                    FROM TrainingType t\s
                    WHERE t.trainingTypeName = :trainingTypeName
                    """;

            TrainingType trainingType = (TrainingType)session.createQuery(sql.strip())
                    .setParameter("trainingTypeName", trainingTypeName)
                    .uniqueResult();

            Optional<TrainingType> optionalTrainingType = Optional.ofNullable(trainingType);

            optionalTrainingType.ifPresentOrElse(
                    (trainingType1) -> logger.info("Training type with trainingTypeName: {} has been found", trainingTypeName),
                    () -> logger.warn("Training type with trainingTypeName: {} was not found", trainingTypeName)
            );

            return optionalTrainingType;
        } catch (HibernateException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Set<TrainingType>> getTrainingTypes() {
        try {
            Session session = sessionFactory.openSession();

            String sql = """
                    SELECT t\s
                    FROM TrainingType t\s
                    """;

            Set<TrainingType> trainingType = new HashSet<>(session.createQuery(sql.strip(), TrainingType.class)
                    .getResultList());

            Optional<Set<TrainingType>> optionalTrainingType = Optional.of(trainingType);

            optionalTrainingType.ifPresentOrElse(
                    (trainingTypes) -> logger.info("Training types found"),
                    () -> logger.warn("Training types not found")
            );

            return optionalTrainingType;
        } catch (HibernateException e){
            logger.error(e.getMessage());
            throw e;
        }

    }
}
