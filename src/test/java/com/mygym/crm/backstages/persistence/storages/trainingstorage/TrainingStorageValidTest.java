package com.mygym.crm.backstages.persistence.storages.trainingstorage;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.persistence.storages.TrainingStorage;
import com.mygym.crm.backstages.persistence.storages.configs.StorageTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StorageTestConfig.class})
public class TrainingStorageValidTest {
    @Autowired
    private TrainingStorage storage;
    private File tempFile;

    @Before
    public void setUp() throws Exception {
        tempFile = File.createTempFile("training", ".csv");
        FileWriter writer = new FileWriter(tempFile);

        writer.write("traineeId;trainerId;trainingName;trainingType;trainingDate;trainingDuration\n");
        writer.write("1;2;Session1;WEIGHT;2023-01-09;5\n");

        writer.close();

        ReflectionTestUtils.setField(storage, "trainingDataPath", tempFile.getAbsolutePath());
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void testInit_withValidData_loadsTraineeCorrectly() {
        storage.init();
        Map<TrainingKey, Training> map = storage.getStorage();
        assertEquals("Storage should contain one training", 1, map.size());

        Training training = map.get(new TrainingKey(1,2));
        assertNotNull("Training with id=(1,2) should be present", training);
        assertEquals("Session1", training.getTrainingName());
        assertEquals("WEIGHT", training.getTrainingType().getTrainingTypeName());
        assertEquals(LocalDate.parse("2023-01-09"), training.getTrainingDate());
        assertEquals(5, training.getTrainingDuration().intValue());
    }
    @Test
    public void test_AutowiringStorage_NotNull() {
        assertNotNull("Storage should have been autowired", storage);
    }
}
