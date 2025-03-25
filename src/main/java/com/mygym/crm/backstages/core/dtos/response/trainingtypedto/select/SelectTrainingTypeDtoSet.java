package com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select;

import lombok.Data;

import java.util.Set;

@Data
public class SelectTrainingTypeDtoSet {

    private Set<SelectTrainingType> trainingTypes;

    @Data
    public static class SelectTrainingType {

        private Long trainingTypeId;

        private String trainingTypeName;
    }
}
