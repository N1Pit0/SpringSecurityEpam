package com.mygym.crm.backstages.mapper;

import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapSelectTrainerDto;
import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapUpdateTrainerDto;
import com.mygym.crm.backstages.core.dtos.response.traineedto.select.SelectTraineeDto;
import com.mygym.crm.backstages.core.dtos.response.traineedto.select.SelectTraineeTrainingsDtoSet;
import com.mygym.crm.backstages.core.dtos.response.traineedto.update.UpdateTraineeDto;
import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    @Mapping(target = "trainers", expression = "java(mapTrainingsSelectTrainersDto(trainee.getTrainings()))")
    SelectTraineeDto traineeToSelectTraineeDto(Trainee trainee);

    @Mapping(target = "trainingTypeName", source = "trainingType.trainingTypeName")
    MapSelectTrainerDto trainerToSelectTrainerDto(Trainer trainer);

    @Mapping(target = "trainers", expression = "java(mapTrainingsUpdateTrainerDto(trainee.getTrainings()))")
    UpdateTraineeDto traineeToUpdateTraineeDto(Trainee trainee);

    @Mapping(target = "trainingTypeName", source = "trainingType.trainingTypeName")
    MapUpdateTrainerDto trainerToUpdateTrainerDto(Trainer trainer);

    @Mapping(target = "trainerName", source = "trainer.firstName")
    @Mapping(target = "trainingType", expression = "java(trainingTypeToTrainingTypeDto(training.getTrainingType()))")
    SelectTraineeTrainingsDtoSet.SelectTraineeTrainingsDto mapTrainingToSelectTraineeTrainingsDto(Training training);

    SelectTrainingTypeDtoSet.SelectTrainingType trainingTypeToTrainingTypeDto(TrainingType trainingType);

    default SelectTraineeTrainingsDtoSet trainingToSelectTraineeTrainingDtoSet(Set<Training> trainings) {
        SelectTraineeTrainingsDtoSet selectTraineeTrainingsDtoSet = new SelectTraineeTrainingsDtoSet();

        if (trainings == null || trainings.isEmpty()) {
            return selectTraineeTrainingsDtoSet;
        }

        Set<SelectTraineeTrainingsDtoSet.SelectTraineeTrainingsDto> mappedSet = trainings.stream()
                .filter(Objects::nonNull)
                .map(this::mapTrainingToSelectTraineeTrainingsDto)
                .collect(Collectors.toSet());

        selectTraineeTrainingsDtoSet.setTraineeTrainings(mappedSet);

        return selectTraineeTrainingsDtoSet;
    }

    default Set<MapSelectTrainerDto> mapTrainingsSelectTrainersDto(Set<Training> trainings) {
        if (trainings == null || trainings.isEmpty()) {
            return Collections.emptySet();
        }

        return trainings.stream()
                .map(Training::getTrainer)
                .filter(Objects::nonNull)
                .map(this::trainerToSelectTrainerDto)
                .collect(Collectors.toSet());
    }

    default Set<MapUpdateTrainerDto> mapTrainingsUpdateTrainerDto(Set<Training> trainings) {
        if (trainings == null || trainings.isEmpty()) {
            return Collections.emptySet();
        }

        return trainings.stream()
                .map(Training::getTrainer)
                .filter(Objects::nonNull)
                .map(this::trainerToUpdateTrainerDto)
                .collect(Collectors.toSet());
    }
}
