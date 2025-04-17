package com.mygym.crm.backstages.repositories.daos;

import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.exceptions.custom.NoTrainingTypeException;
import com.mygym.crm.backstages.interfaces.daorepositories.TrainingTypeReadOnlyDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class TrainingTypeRadOnlyDaoImpl implements TrainingTypeReadOnlyDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeRadOnlyDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TrainingType> getTrainingTypeByUserName(String trainingTypeName) {
        try {
            String sql = """
                    SELECT t\s
                    FROM TrainingType t\s
                    WHERE t.trainingTypeName = :trainingTypeName
                    """;

            TrainingType trainingType = (TrainingType) entityManager.createQuery(sql.strip())
                    .setParameter("trainingTypeName", trainingTypeName)
                    .getSingleResult();

            Optional<TrainingType> optionalTrainingType = Optional.ofNullable(trainingType);

            optionalTrainingType.ifPresentOrElse(
                    (trainingType1) -> logger.info("Training type with trainingTypeName: {} has been found", trainingTypeName),
                    () -> logger.warn("Training type with trainingTypeName: {} was not found", trainingTypeName)
            );

            return optionalTrainingType;
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new NoTrainingTypeException(e.getMessage());
        }
    }

    @Override
    public Optional<Set<TrainingType>> getTrainingTypes() {
        try {

            String sql = """
                    SELECT t\s
                    FROM TrainingType t\s
                    """;

            Set<TrainingType> trainingType = new HashSet<>(entityManager.createQuery(sql.strip(), TrainingType.class)
                    .getResultList());

            Optional<Set<TrainingType>> optionalTrainingType = Optional.of(trainingType);

            optionalTrainingType.ifPresentOrElse(
                    (trainingTypes) -> logger.info("Training types found"),
                    () -> logger.warn("Training types not found")
            );

            return optionalTrainingType;
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new NoTrainingTypeException(e.getMessage());
        }

    }
}
