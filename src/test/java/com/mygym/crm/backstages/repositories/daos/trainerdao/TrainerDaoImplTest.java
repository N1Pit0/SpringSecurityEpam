//package com.mygym.crm.backstages.persistence.daos.trainerdao;
//
//import com.mygym.crm.backstages.domain.models.Trainer;
//import com.mygym.crm.backstages.persistence.daos.configs.DaoTestConfig;
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
//@ContextConfiguration(classes = {DaoTestConfig.class})
//public class TrainerDaoImplTest {
//    @Autowired
//    private TrainerDaoImpl trainerDao;
//    private Trainer trainer;
//
////    @Before
////    public void clearStorage(){
////        trainerDao.getTrainerStorage().getStorage().clear();
////    }
//
//    @Before
//    public void setUpTrainer() throws Exception {
//        trainer = new Trainer();
//        trainer.setUserId(1L);
//        trainer.setFirstName("John");
//        trainer.setLastName("Doe");
//        trainer.setIsActive(true);
//    }
//
//    @Test
//    public void test_AutowiringDao_NotNull() {
//        assertNotNull("Storage should have been autowired", trainerDao);
//    }
//
//    @Test
//    public void testCreateTrainer_Success() {
//        Optional<Trainer> result = trainerDao.create(trainer);
//
//        assertTrue(result.isPresent());
//        assertEquals(trainer, result.get());
//    }
//
//    @Test
//    public void testCreateTrainer_Fail_AlreadyExists() {
//        trainerDao.create(trainer);
//        Trainer newTrainer = new Trainer();
//        newTrainer.setUserId(1L);
//        Optional<Trainer> result = trainerDao.create(newTrainer);
//
//        assertFalse(result.isPresent());
//    }
//
////    @Test
////    public void testUpdateTrainer_Success() {
////        trainerDao.getTrainerStorage().getStorage().put(1,trainer);
////
////        Trainer updatedTrainer = new Trainer();
////        updatedTrainer.setUserId(1);
////        updatedTrainer.setFirstName("Jane");
////        Optional<Trainer> result = trainerDao.update(updatedTrainer);
////
////        assertTrue(result.isPresent());
////        assertEquals("Jane", trainerDao.getTrainerStorage().getStorage().get(1).getFirstName());
////    }
//
//    @Test
//    public void testUpdateTrainer_Fail_NotExists() {
//        Trainer updatedTrainer = new Trainer();
//        updatedTrainer.setUserId(2L);
//        updatedTrainer.setFirstName("Jane");
//        Optional<Trainer> result = trainerDao.update(updatedTrainer);
//
//        assertFalse(result.isPresent());
//    }
//
////    @Test
////    public void testDeleteTrainer_Success() {
////        trainerDao.getTrainerStorage().getStorage().put(1,trainer);
////
////        Optional<Trainer> result = trainerDao.delete(1);
////
////        assertTrue(result.isPresent());
////        assertNull(trainerDao.getTrainerStorage().getStorage().get(1));
////    }
//
//    @Test
//    public void testDeleteTrainer_Fail_NotExists() {
//        Optional<Trainer> result = trainerDao.delete(2L);
//
//        assertFalse(result.isPresent());
//    }
//
////    @Test
////    public void testSelectTrainer_Success() {
////        trainerDao.getTrainerStorage().getStorage().put(1,trainer);
////
////        Optional<Trainer> result = trainerDao.select(1);
////
////        assertTrue(result.isPresent());
////        assertEquals(trainer, result.get());
////    }
//
//    @Test
//    public void testSelectTrainer_Fail_NotExists() {
//        Optional<Trainer> result = trainerDao.select(2L);
//
//        assertFalse(result.isPresent());
//    }
//}
