//package com.mygym.crm.backstages.core.services;
//
//import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
//import com.mygym.crm.backstages.core.services.configs.ServiceTestConfig;
//import com.mygym.crm.backstages.domain.models.Training;
//import com.mygym.crm.backstages.persistence.daos.TrainingDaoImpl;
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
//public class TrainingServiceImplTest {
//    @Autowired
//    private TrainingServiceImpl trainingService;
//    @Autowired
//    private TrainingDaoImpl trainingDao;
//    private TrainingDto trainingDto;
//    private Long trainingKey;
//
////    @Before
////    public void clear(){
////        trainingDao.getTrainingStorage().getStorage().clear();
////    }
//
//    @Before
//    public void setUp() {
//        trainingDto = new TrainingDto();
//
//        trainingDto.setTrainingName("Session1");
//        trainingDto.setTrainingDuration(5);
//    }
//
////    @Test
////    public void testCreateTraining_Success() {
////        trainingService.create(trainingDto);
////
////        Optional<Training> createdTraining = trainingDao.select(trainingDto.getTrainingKey());
////
////        assertTrue(createdTraining.isPresent());
////        assertEquals(trainingKey, createdTraining.get().getTrainingKey());
////        assertEquals("Session1", createdTraining.get().getTrainingName());
////        assertEquals(5, createdTraining.get().getTrainingDuration().intValue());
////    }
//
//    @Test
//    public void testGetById_Success() {
//        trainingService.create(trainingDto);
//
//        Optional<Training> foundTraining = trainingService.getById(trainingKey);
//
//        assertTrue(foundTraining.isPresent());
////        assertEquals(trainingKey, foundTraining.get().getTrainingKey());
//    }
//
//    @Test
//    public void testGetById_NotFound() {
////        Optional<Training> foundTraining = trainingService.getById(new TrainingKey(2,2));
//
////        assertFalse(foundTraining.isPresent());
//    }
//
//    @Test
//    public void test_AutowiringDao_NotNull() {
//        assertNotNull("Storage should have been autowired", trainingDao);
//    }
//}
