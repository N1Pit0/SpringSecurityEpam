package com.mygym.crm.core.services.dtos;

import com.mygym.crm.core.services.dtos.common.UserDTO;
import com.mygym.crm.domain.models.TrainingTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainerDTO extends UserDTO {
    private TrainingTypeEnum trainingType;

}
