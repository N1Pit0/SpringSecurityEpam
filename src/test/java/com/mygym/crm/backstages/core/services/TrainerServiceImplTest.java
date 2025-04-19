//package com.mygym.crm.backstages.core.services;
//
//import com.mygym.crm.backstages.core.dtos.request.trainerdto.TrainerDto;
//import com.mygym.crm.backstages.core.services.configs.ServiceTestConfig;
//import com.mygym.crm.backstages.domain.models.Trainer;
//import com.mygym.crm.backstages.exceptions.custom.NoTrainerException;
//import com.mygym.crm.backstages.persistence.daos.TrainerDaoImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ServiceTestConfig.class})
//public class TrainerServiceImplTest {
//    @Autowired
//    private TrainerServiceImplCommon trainerService;
//    @Autowired
//    private TrainerDaoImpl trainerDao;
//    @Autowired
//    private CommonUserService userService;
//    private TrainerDto trainerDto;
//
////    @Before
////    public void clear(){
////        trainerDao.getTrainerStorage().getStorage().clear();
////        userService.getUsernameCounter().clear();
////    }
//
//    @Before
//    public void setUp() {
//        trainerDto = new TrainerDto();
//        trainerDto.setFirstName("John");
//        trainerDto.setLastName("Doe");
//        trainerDto.setActive(true);
//
////        CommonUserService.uniqueID = 1L;
//    }
//
//    @Test
//    public void testCreateTrainer_Success() {
//        trainerService.create(trainerDto);
//
//        Optional<Trainer> createdTrainer = trainerDao.select(1L);
//
//        assertTrue(createdTrainer.isPresent());
//        assertEquals(1L, createdTrainer.get().getUserId().longValue());
//        assertEquals("John", createdTrainer.get().getFirstName());
//        assertEquals("Doe", createdTrainer.get().getLastName());
//        assertEquals("John.Doe", createdTrainer.get().getUserName());
//    }
//
//    @Test
//    public void testUpdateTrainer_Success() {
//        trainerService.create(trainerDto);
//
//        TrainerDto updatedDto = new TrainerDto();
//        updatedDto.setFirstName("Jana");
//        updatedDto.setLastName("Doe");
//        updatedDto.setActive(false);
//
//        trainerService.update(1L, updatedDto);
//
//        Optional<Trainer> updatedTrainer = trainerDao.select(1L);
//        assertTrue(updatedTrainer.isPresent());
//        assertEquals("Jana", updatedTrainer.get().getFirstName());
//        assertEquals("Doe", updatedTrainer.get().getLastName());
//        assertFalse(updatedTrainer.get().getIsActive());
//    }
//
//    @Test(expected = NoTrainerException.class)
//    public void testUpdateTrainer_Fail_NotFound() {
//        TrainerDto updatedDto = new TrainerDto();
//
//        updatedDto.setFirstName("Jana");
//        updatedDto.setLastName("Doe");
//        updatedDto.setActive(false);
//
//        trainerService.update(2L, updatedDto); // ID does not exist
//    }
//
//    @Test
//    public void testGetById_Success() {
//        trainerService.create(trainerDto);
//
//        Optional<Trainer> foundTrainer = trainerService.getById(1L);
//
//        assertTrue(foundTrainer.isPresent());
//        assertEquals("John", foundTrainer.get().getFirstName());
//    }
//
//    @Test
//    public void testGetById_NotFound() {
//        Optional<Trainer> foundTrainer = trainerService.getById(999L);
//
//        assertFalse(foundTrainer.isPresent());
//    }
//}
