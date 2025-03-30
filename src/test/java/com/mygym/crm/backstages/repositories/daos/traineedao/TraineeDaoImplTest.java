//package com.mygym.crm.backstages.persistence.daos.traineedao;
//
//import com.mygym.crm.backstages.domain.models.Trainee;
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
//public class TraineeDaoImplTest {
//    @Autowired
//    private TraineeDaoImpl traineeDao;
//    private Trainee trainee;
//
////    @Before
////    public void clearStorage(){
////        traineeDao.getTraineeStorage().getStorage().clear();
////    }
//
//    @Before
//    public void setUpTrainee() throws Exception {
//        trainee = new Trainee();
//        trainee.setUserId(1L);
//        trainee.setFirstName("John");
//        trainee.setLastName("Doe");
//        trainee.setIsActive(true);
//    }
//
//    @Test
//    public void test_AutowiringDao_NotNull() {
//        assertNotNull("Storage should have been autowired", traineeDao);
//    }
//
//    @Test
//    public void testCreateTrainee_Success() {
//        Optional<Trainee> result = traineeDao.create(trainee);
//
//        assertTrue(result.isPresent());
//        assertEquals(trainee, result.get());
//    }
//
//    @Test
//    public void testCreateTrainee_Fail_AlreadyExists() {
//        traineeDao.create(trainee);
//        Trainee newTrainee = new Trainee();
//        newTrainee.setUserId(1L);
//        Optional<Trainee> result = traineeDao.create(newTrainee);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void testUpdateTrainee_Success() {
////        traineeDao.getTraineeStorage().getStorage().put(1,trainee);
//
//        Trainee updatedTrainee = new Trainee();
//        updatedTrainee.setUserId(1L);
//        updatedTrainee.setFirstName("Jane");
//        Optional<Trainee> result = traineeDao.update(updatedTrainee);
//
//        assertTrue(result.isPresent());
////        assertEquals("Jane", traineeDao.getTraineeStorage().getStorage().get(1).getFirstName());
//    }
//
//    @Test
//    public void testUpdateTrainee_Fail_NotExists() {
//        Trainee updatedTrainee = new Trainee();
//        updatedTrainee.setUserId(2L);
//        updatedTrainee.setFirstName("Jane");
//        Optional<Trainee> result = traineeDao.update(updatedTrainee);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void testDeleteTrainee_Success() {
////        traineeDao.getTraineeStorage().getStorage().put(1,trainee);
//
//        Optional<Trainee> result = traineeDao.delete(1L);
//
//        assertTrue(result.isPresent());
////        assertNull(traineeDao.getTraineeStorage().getStorage().get(1));
//    }
//
//    @Test
//    public void testDeleteTrainee_Fail_NotExists() {
//        Optional<Trainee> result = traineeDao.delete(2L);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void testSelectTrainee_Success() {
////        traineeDao.getTraineeStorage().getStorage().put(1,trainee);
//
//        Optional<Trainee> result = traineeDao.select(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(trainee, result.get());
//    }
//
//    @Test
//    public void testSelectTrainee_Fail_NotExists() {
//        Optional<Trainee> result = traineeDao.select(2L);
//
//        assertFalse(result.isPresent());
//    }
//
//}
