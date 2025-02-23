package com.mygym.crm.backstages.core.dtos;

import com.mygym.crm.backstages.core.dtos.common.UserDTO;
import com.mygym.crm.backstages.domain.models.TrainingTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainerDTO extends UserDTO {
    private TrainingTypeEnum trainingType;

}
