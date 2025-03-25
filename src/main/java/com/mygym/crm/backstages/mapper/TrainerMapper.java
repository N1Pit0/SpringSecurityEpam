package com.mygym.crm.backstages.mapper;

import com.mygym.crm.backstages.core.dtos.response.trainerdto.mapping.MapSelectTraineeDto;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.mapping.MapUpdateTraineeDto;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.select.SelectTrainerDto;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.select.SelectTrainerTrainingsDtoSet;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.update.UpdateTrainerDto;
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
public interface TrainerMapper {

    @Mapping(target = "trainees", expression = "java(mapTrainingsToSelectTraineeDto(trainer.getTrainings()))")
    @Mapping(target = "trainingTypeName", source = "trainingType.trainingTypeName")
    SelectTrainerDto trainerToSelectTrainerDto(Trainer trainer);

    MapSelectTraineeDto traineeToMapSelectTraineeDto(Trainee trainee);

    @Mapping(target = "trainees", expression = "java(mapTrainingsToUpdateTraineeDto(trainer.getTrainings()))")
    @Mapping(target = "trainingTypeName", source = "trainingType.trainingTypeName")
    UpdateTrainerDto trainerToUpdateTrainerDto(Trainer trainer);

    MapUpdateTraineeDto traineeToMapUpdateTraineeDto(Trainee trainee);

    @Mapping(target = "traineeName", source = "trainee.firstName")
    @Mapping(target = "trainingType", expression = "java(trainingTypeToTrainingTypeDto(training.getTrainingType()))")
    SelectTrainerTrainingsDtoSet.SelectTrainerTrainingsDto mapTrainingToSelectTrainerTrainingsDto(Training training);

    SelectTrainingTypeDtoSet.SelectTrainingType trainingTypeToTrainingTypeDto(TrainingType trainingType);

    default SelectTrainerTrainingsDtoSet trainingToSelectTrainerTrainingDtoSet(Set<Training> trainings) {
        SelectTrainerTrainingsDtoSet selectTrainerTrainingsDtoSet = new SelectTrainerTrainingsDtoSet();

        if (trainings == null || trainings.isEmpty()) {
            return selectTrainerTrainingsDtoSet;
        }

        Set<SelectTrainerTrainingsDtoSet.SelectTrainerTrainingsDto> mappedSet = trainings.stream()
                .filter(Objects::nonNull)
                .map(this::mapTrainingToSelectTrainerTrainingsDto)
                .collect(Collectors.toSet());

        selectTrainerTrainingsDtoSet.setTrainerTrainings(mappedSet);

        return selectTrainerTrainingsDtoSet;
    }

    default Set<MapSelectTraineeDto> mapTrainingsToSelectTraineeDto(Set<Training> trainings) {
        if (trainings == null || trainings.isEmpty()) {
            return Collections.emptySet();
        }

        return trainings.stream()
                .map(Training::getTrainee)
                .filter(Objects::nonNull)
                .map(this::traineeToMapSelectTraineeDto)
                .collect(Collectors.toSet());
    }

    default Set<MapUpdateTraineeDto> mapTrainingsToUpdateTraineeDto(Set<Training> trainings) {
        if (trainings == null || trainings.isEmpty()) {
            return Collections.emptySet();
        }

        return trainings.stream()
                .map(Training::getTrainee)
                .filter(Objects::nonNull)
                .map(this::traineeToMapUpdateTraineeDto)
                .collect(Collectors.toSet());
    }

}
