package com.mygym.crm.backstages.persistence.storages.trainerstorage;

import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.persistence.storages.TrainerStorage;
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
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StorageTestConfig.class})
public class TrainerStorageValidTest {
    @Autowired
    private TrainerStorage storage;
    private File tempFile;

    @Before
    public void setUp() throws Exception {
        tempFile = File.createTempFile("trainer", ".csv");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("id;firstName;lastName;username;password;isActive;trainingType\n");
        // One valid record
        writer.write("1;John;Doe;John.doe;John.doe;true;PLYOMETRIC\n");
        writer.close();

        ReflectionTestUtils.setField(storage, "trainerDataPath", tempFile.getAbsolutePath());
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void testInit_withValidData_loadsTraineeCorrectly() {
        storage.init();
        Map<Integer, Trainer> map = storage.getStorage();
        assertEquals("Storage should contain one trainer", 1, map.size());

        Trainer trainer = map.get(1);
        assertNotNull("Trainer with id=1 should be present", trainer);
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
        assertEquals("John.doe", trainer.getUserName());
        assertTrue(trainer.getIsActive());
        assertEquals("PLYOMETRIC", trainer.getTrainingType().getTrainingTypeName());
    }

    @Test
    public void test_AutowiringStorage_NotNull() {
        assertNotNull("Storage should have been autowired", storage);
    }
}
