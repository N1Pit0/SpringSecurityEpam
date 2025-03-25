package com.mygym.crm.backstages.mapper;

import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import com.mygym.crm.backstages.domain.models.TrainingType;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {

    SelectTrainingTypeDtoSet.SelectTrainingType trainingTypeToTrainingTypeDto(TrainingType trainingType);

    default SelectTrainingTypeDtoSet toTrainingTypeDtoSet(Set<TrainingType> trainingTypeset) {
        SelectTrainingTypeDtoSet trainingTypeDtoSet = new SelectTrainingTypeDtoSet();

        if (trainingTypeset == null || trainingTypeset.isEmpty()) {

            trainingTypeDtoSet.setTrainingTypes(Collections.emptySet());

            return trainingTypeDtoSet;
        }

        Set<SelectTrainingTypeDtoSet.SelectTrainingType> mappedSet = trainingTypeset.stream()
                .filter(Objects::nonNull)
                .map(this::trainingTypeToTrainingTypeDto)
                .collect(Collectors.toSet());

        trainingTypeDtoSet.setTrainingTypes(mappedSet);

        return trainingTypeDtoSet;
    }
}
