package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.request.ChangePasswordDto;
import com.mygym.crm.backstages.core.dtos.request.common.CombineUserDtoWithSecurityDto;
import com.mygym.crm.backstages.core.dtos.request.trainerdto.TrainerDto;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.select.SelectTrainerDto;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.select.SelectTrainerTrainingsDtoSet;
import com.mygym.crm.backstages.core.dtos.response.trainerdto.update.UpdateTrainerDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.NoResourceException;
import com.mygym.crm.backstages.exceptions.custom.NoTrainerException;
import com.mygym.crm.backstages.exceptions.custom.ResourceCreationException;
import com.mygym.crm.backstages.mapper.TrainerMapper;
import com.mygym.crm.backstages.interfaces.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "users/trainers")
public class TrainerController {
    private TrainerService trainerService;
    private UserService userService;
    private TrainerMapper mapper;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTrainerMapper(TrainerMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping(value = "/{userName:.+}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SelectTrainerDto> getTrainerProfile(@PathVariable("userName") String userName,
                                                              @RequestBody SecurityDto securityDto) {

        userService.validateDto(securityDto);

        Optional<Trainer> optionalTrainer = trainerService.getByUserName(securityDto, userName);

        return optionalTrainer.map(mapper::trainerToSelectTrainerDto)
                .map((trainer) -> new ResponseEntity<>(trainer, HttpStatus.OK))
                .orElseThrow(() -> new NoResourceException("No resource found for " + userName));
    }

    @GetMapping(value = "/{userName:.+}/list-trainer-trainings")
    public ResponseEntity<SelectTrainerTrainingsDtoSet> getTrainerTrainings(@PathVariable("userName") String userName,
                        @RequestBody SecurityDto securityDto,
                        @RequestParam(name = "periodFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodFrom,
                        @RequestParam(name = "periodTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodTo,
                        @RequestParam(name = "traineeName", required = false) String traineeName) {

        userService.validateDto(securityDto);

        Optional<Set<Training>> optionalTrainings = trainerService.getTrainerTrainings(
                securityDto,
                userName,
                periodFrom,
                periodTo,
                traineeName
        );

        return optionalTrainings
                .map(mapper::trainingToSelectTrainerTrainingDtoSet)
                .map(trainings -> new ResponseEntity<>(trainings, HttpStatus.OK))
                .orElseThrow(() -> new NoResourceException("No resource found for " + userName));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SecurityDto> registerTrainer(@RequestBody TrainerDto trainerDto) {

        userService.validateDto(trainerDto);

        Optional<Trainer> optionalTrainer = trainerService.create(trainerDto);

        Trainer trainer1 = optionalTrainer.orElseThrow(() -> new ResourceCreationException("could not create Trainer"));

        return new ResponseEntity<>(new SecurityDto(trainer1.getUserName(), trainer1.getPassword()),
                HttpStatus.CREATED);
    }

    @PutMapping(value = {"/{userName:.+}"}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateTrainerDto> updateTrainerProfile(@PathVariable("userName") String userName,
                                                                 @RequestBody CombineUserDtoWithSecurityDto<TrainerDto> updateTrainerDtoWithSecurityDto) {

        userService.validateDto(updateTrainerDtoWithSecurityDto);
        userService.validateDto(updateTrainerDtoWithSecurityDto.getUserDto());
        userService.validateDto(updateTrainerDtoWithSecurityDto.getSecurityDto());

        Optional<Trainer> optionalTrainer = trainerService.updateByUserName(updateTrainerDtoWithSecurityDto.getSecurityDto(),
                userName,
                updateTrainerDtoWithSecurityDto.getUserDto());

        return optionalTrainer
                .map(mapper::trainerToUpdateTrainerDto)
                .map((trainee) -> new ResponseEntity<>(trainee, HttpStatus.OK))
                .orElseThrow(() -> new ResourceCreationException("could not update Trainer Profile"));
    }

    @PutMapping(value = "/{userName:.+}/change-login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> changeLogin(@PathVariable("userName") String userName,
                                            @RequestBody ChangePasswordDto changePasswordDto) {
        userService.validateDto(changePasswordDto);

        boolean isPassed = trainerService.changePassword(
                new SecurityDto(userName, changePasswordDto.getOldPassword()),
                userName,
                changePasswordDto.getNewPassword()
        );

        if (isPassed) return ResponseEntity.ok().build();

       throw new ResourceCreationException("could not update Trainer Profile");
    }

    @PatchMapping(value = "/{userName:.+}/toggleActive", consumes = "application/json")
    public ResponseEntity<Void> toggleIsActive(@PathVariable("userName") String userName,
                                               @RequestBody SecurityDto securityDto) {

        userService.validateDto(securityDto);

        boolean isPerformed = trainerService.toggleIsActive(securityDto, userName);

        if (isPerformed) return new ResponseEntity<>(HttpStatus.valueOf(200));

        throw new ResourceCreationException("could not update Trainer Profile");
    }
}
