package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.interfaces.daorepositories.TrainingTypeReadOnlyDao;
import com.mygym.crm.backstages.interfaces.services.TrainingTypeRadOnlyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TrainingTypeReadOnlyServiceImpl implements TrainingTypeRadOnlyService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeReadOnlyServiceImpl.class);
    private TrainingTypeReadOnlyDao trainingTypeReadOnlyDao;

    @Autowired
    public void setTrainingTypeReadOnlyDao(TrainingTypeReadOnlyDao trainingTypeReadOnlyDao) {
        this.trainingTypeReadOnlyDao = trainingTypeReadOnlyDao;
    }

    @Transactional
    @Override
    public Optional<Set<TrainingType>> getTrainingType() {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            Optional<Set<TrainingType>> optionalTrainingTypes = trainingTypeReadOnlyDao.getTrainingTypes();

            return optionalTrainingTypes
                    .map((trainingTypes) -> {
                        logger.info("trainingTypes has been found");
                        return trainingTypes;
                    })
                    .or(() -> {
                        logger.info("trainingTypes not found");
                        return Optional.empty();
                    });
        } finally {
            MDC.remove("transactionId");
        }
    }
}
