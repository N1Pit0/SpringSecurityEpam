//package com.mygym.crm.backstages.persistence.daos.trainingdao;
//
//import com.mygym.crm.backstages.domain.models.Training;
//import com.mygym.crm.backstages.persistence.daos.configs.DaoTestConfig;
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
//public class TrainingDaoImplTest {
//    @Autowired
//    private TrainingDaoImpl trainingDao;
//    private Training training;
//    private Long trainingKey;
//
////    @Before
////    public void clearStorage(){
////        trainingDao.getTrainingStorage().getStorage().clear();
////    }
//
////    @Before
////    public void setUpTraining() throws Exception {
////        training = new Training();
////        trainingKey = new TrainingKey(1,2);
////
////        training.setTrainingKey(trainingKey);
////        training.setTrainingName("Session1");
////        training.setTrainingDuration(5);
////    }
//
//    @Test
//    public void test_AutowiringDao_NotNull() {
//        assertNotNull("Storage should have been autowired", trainingDao);
//    }
//
//    @Test
//    public void testCreateTraining_Success() {
//        Optional<Training> result = trainingDao.create(training);
//
//        assertTrue(result.isPresent());
//        assertEquals(training, result.get());
//    }
//
////    @Test
////    public void testCreateTraining_Fail_AlreadyExists() {
////        trainingDao.create(training);
////        Training newTraining = new Training();
////        TrainingKey newTrainingKey = new TrainingKey(1,2);
////        newTraining.setTrainingKey(newTrainingKey);
////
////        Optional<Training> result = trainingDao.create(newTraining);
////
////        assertFalse(result.isPresent());
////    }
//
////    @Test
////    public void testUpdateTraining_Success() {
////        trainingDao.getTrainingStorage().getStorage().put(trainingKey,training);
////
////        Training updatedTraining = new Training();
////
////        TrainingKey updatedTrainingKey = new TrainingKey(1,2);
////        updatedTraining.setTrainingKey(updatedTrainingKey);
////        updatedTraining.setTrainingName("Session2");
////
////        Optional<Training> result = trainingDao.update(updatedTraining);
////
////        assertTrue(result.isPresent());
////        assertEquals("Session2",
////                trainingDao.getTrainingStorage().getStorage().get(updatedTrainingKey).getTrainingName());
////    }
//
//    @Test
//    public void testUpdateTraining_Fail_NotExists() {
//        Training updatedTraining = new Training();
//
//        updatedTraining.setTrainingName("Session2");
//
//        Optional<Training> result = trainingDao.update(updatedTraining);
//
//        assertFalse(result.isPresent());
//    }
//
////    @Test
////    public void testDeleteTraining_Success() {
////        trainingDao.getTrainingStorage().getStorage().put(trainingKey,training);
////
////        Optional<Training> result = trainingDao.delete(trainingKey);
////
////        assertTrue(result.isPresent());
////        assertNull(trainingDao.getTrainingStorage().getStorage().get(trainingKey));
////    }
//
////    @Test
////    public void testDeleteTraining_Fail_NotExists() {
////        Optional<Training> result = trainingDao.delete(new TrainingKey(2,2));
////
////        assertFalse(result.isPresent());
////    }
//
////    @Test
////    public void testSelectTraining_Success() {
////        trainingDao.getTrainingStorage().getStorage().put(trainingKey,training);
////
////        Optional<Training> result = trainingDao.select(trainingKey);
////
////        assertTrue(result.isPresent());
////        assertEquals(training, result.get());
////    }
//
////    @Test
////    public void testSelectTraining_Fail_NotExists() {
////        Optional<Training> result = trainingDao.select(new TrainingKey(2,2));
////
////        assertFalse(result.isPresent());
////    }
//}
