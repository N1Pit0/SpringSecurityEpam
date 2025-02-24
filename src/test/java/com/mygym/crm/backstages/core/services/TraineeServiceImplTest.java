package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.services.configs.ServiceTestConfig;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.exceptions.NoTraineeException;
import com.mygym.crm.backstages.persistence.daos.traineedao.TraineeDaoImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class TraineeServiceImplTest {

    @Autowired
    private TraineeServiceImpl traineeService;
    @Autowired
    private TraineeDaoImpl traineeDao;
    @Autowired
    private UserService userService;
    private TraineeDto traineeDto;

    @Before
    public void clear(){
        traineeDao.getTraineeStorage().getStorage().clear();
        userService.getUsernameCounter().clear();
    }

    @Before
    public void setUp() {
        traineeDto = new TraineeDto();
        traineeDto.setFirstName("John");
        traineeDto.setLastName("Doe");
        traineeDto.setActive(true);
        traineeDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        traineeDto.setAddress("123 Main St");
        UserService.uniqueID = 1;
    }

    @Test
    public void testCreateTrainee_Success() {
        traineeService.create(traineeDto);

        Optional<Trainee> createdTrainee = traineeDao.select(1);

        assertTrue(createdTrainee.isPresent());
        assertEquals(1, createdTrainee.get().getUserId());
        assertEquals("John", createdTrainee.get().getFirstName());
        assertEquals("Doe", createdTrainee.get().getLastName());
        assertEquals("John.Doe", createdTrainee.get().getUserName());
    }

    @Test
    public void testUpdateTrainee_Success() {
        traineeService.create(traineeDto);

        TraineeDto updatedDto = new TraineeDto();
        updatedDto.setFirstName("Jana");
        updatedDto.setLastName("Doe");
        updatedDto.setActive(false);

        traineeService.update(1, updatedDto);

        Optional<Trainee> updatedTrainee = traineeDao.select(1);
        assertTrue(updatedTrainee.isPresent());
        assertEquals("Jana", updatedTrainee.get().getFirstName());
        assertEquals("Doe", updatedTrainee.get().getLastName());
        assertFalse(updatedTrainee.get().isActive());
    }
//
    @Test(expected = NoTraineeException.class)
    public void testUpdateTrainee_Fail_NotFound() {
        TraineeDto updatedDto = new TraineeDto();

        updatedDto.setFirstName("Jana");
        updatedDto.setLastName("Doe");
        updatedDto.setActive(false);

        traineeService.update(2, updatedDto); // ID does not exist
    }
//
    @Test
    public void testDeleteTrainee_Success() {
        traineeService.create(traineeDto);

        traineeService.delete(1);

        Optional<Trainee> deletedTrainee = traineeDao.select(1);
        assertFalse(deletedTrainee.isPresent());
    }
//
    @Test
    public void testGetById_Success() {
        traineeService.create(traineeDto);

        Optional<Trainee> foundTrainee = traineeService.getById(1);

        assertTrue(foundTrainee.isPresent());
        assertEquals("John", foundTrainee.get().getFirstName());
    }

    @Test
    public void testGetById_NotFound() {
        Optional<Trainee> foundTrainee = traineeService.getById(999);

        assertFalse(foundTrainee.isPresent());
    }
}
