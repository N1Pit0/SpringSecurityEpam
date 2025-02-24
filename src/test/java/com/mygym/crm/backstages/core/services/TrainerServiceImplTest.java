package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.services.configs.ServiceTestConfig;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.exceptions.NoTrainerException;
import com.mygym.crm.backstages.persistence.daos.trainerdao.TrainerDaoImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class TrainerServiceImplTest {
    @Autowired
    private TrainerServiceImpl trainerService;
    @Autowired
    private TrainerDaoImpl trainerDao;
    @Autowired
    private UserService userService;
    private TrainerDto trainerDto;

    @Before
    public void clear(){
        trainerDao.getTrainerStorage().getStorage().clear();
        userService.getUsernameCounter().clear();
    }

    @Before
    public void setUp() {
        trainerDto = new TrainerDto();
        trainerDto.setFirstName("John");
        trainerDto.setLastName("Doe");
        trainerDto.setActive(true);

        UserService.uniqueID = 1;
    }

    @Test
    public void testCreateTrainer_Success() {
        trainerService.create(trainerDto);

        Optional<Trainer> createdTrainer = trainerDao.select(1);

        assertTrue(createdTrainer.isPresent());
        assertEquals(1, createdTrainer.get().getUserId());
        assertEquals("John", createdTrainer.get().getFirstName());
        assertEquals("Doe", createdTrainer.get().getLastName());
        assertEquals("John.Doe", createdTrainer.get().getUserName());
    }

    @Test
    public void testUpdateTrainer_Success() {
        trainerService.create(trainerDto);

        TrainerDto updatedDto = new TrainerDto();
        updatedDto.setFirstName("Jana");
        updatedDto.setLastName("Doe");
        updatedDto.setActive(false);

        trainerService.update(1, updatedDto);

        Optional<Trainer> updatedTrainer = trainerDao.select(1);
        assertTrue(updatedTrainer.isPresent());
        assertEquals("Jana", updatedTrainer.get().getFirstName());
        assertEquals("Doe", updatedTrainer.get().getLastName());
        assertFalse(updatedTrainer.get().isActive());
    }

    @Test(expected = NoTrainerException.class)
    public void testUpdateTrainer_Fail_NotFound() {
        TrainerDto updatedDto = new TrainerDto();

        updatedDto.setFirstName("Jana");
        updatedDto.setLastName("Doe");
        updatedDto.setActive(false);

        trainerService.update(2, updatedDto); // ID does not exist
    }

    @Test
    public void testGetById_Success() {
        trainerService.create(trainerDto);

        Optional<Trainer> foundTrainer = trainerService.getById(1);

        assertTrue(foundTrainer.isPresent());
        assertEquals("John", foundTrainer.get().getFirstName());
    }

    @Test
    public void testGetById_NotFound() {
        Optional<Trainer> foundTrainer = trainerService.getById(999);

        assertFalse(foundTrainer.isPresent());
    }
}
