package com.mygym.crm.backstages.core.dtos.request.trainerdto;

import com.mygym.crm.backstages.core.dtos.request.common.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainerDto extends UserDto {
    @NotNull
    private String trainingTypeName;

}
