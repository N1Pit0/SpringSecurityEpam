package com.mygym.crm.backstages.core.dtos.response.trainerdto.select;

import lombok.Data;

import java.util.Set;

@Data
public class SelectTrainerNotAssignedDtoSet {

    private Set<SelectTrainerNotAssignedDto> notAssignedTrainers;

    @Data
    public static class SelectTrainerNotAssignedDto {
        private String userName;

        private String firstName;

        private String lastName;

        private String trainingTypeName;
    }
}
