package com.mygym.crm.backstages.mapper;

import com.mygym.crm.backstages.core.dtos.traineedto.response.select.SelectTraineeDto;
import com.mygym.crm.backstages.core.dtos.trainerdto.response.select.SelectTrainerDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SelectTraineeMapper {

    // Map from Trainee to SelectTraineeDto
    @Mapping(target = "trainers", expression = "java(mapTrainingsToTrainersDto(trainee.getTrainings()))")
    SelectTraineeDto traineeToSelectTraineeDto(Trainee trainee);

    // Map from Trainer to SelectTrainerDto
    @Mapping(target = "trainingTypeName", source = "trainingType.trainingTypeName")
    SelectTrainerDto trainerToSelectTrainerDto(Trainer trainer);

    // Custom method to map Training entities to Trainer DTOs
    default Set<SelectTrainerDto> mapTrainingsToTrainersDto(Set<Training> trainings) {
        if (trainings == null || trainings.isEmpty()) {
            return Collections.emptySet();
        }

        return trainings.stream()
                .map(Training::getTrainer)
                .filter(Objects::nonNull)
                .map(this::trainerToSelectTrainerDto)
                .collect(Collectors.toSet());
    }
}
