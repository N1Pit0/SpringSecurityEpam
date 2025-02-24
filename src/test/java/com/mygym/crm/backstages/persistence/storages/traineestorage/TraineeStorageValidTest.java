package com.mygym.crm.backstages.persistence.storages.traineestorage;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.persistence.storages.TraineeStorage;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StorageTestConfig.class})
public class TraineeStorageValidTest  {
    @Autowired
    private TraineeStorage storage;
    private File tempFile;

    @Before
    public void setUp() throws Exception {
        // Create a temporary file with valid CSV content
        tempFile = File.createTempFile("trainee", ".csv");
        FileWriter writer = new FileWriter(tempFile);
        // Header line (will be skipped)
        writer.write("id;firstName;lastName;username;password;isActive;dateOfBirth;address\n");
        // One valid record
        writer.write("1;John;Doe;John.doe;John.doe;true;1990-01-01;123 Main St\n");
        writer.close();

        // Using ReflectionTestUtils from Spring Test to inject the value
        ReflectionTestUtils.setField(storage, "traineeDataPath", tempFile.getAbsolutePath());
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void testInit_withValidData_loadsTraineeCorrectly() {
        storage.init();
        Map<Integer, Trainee> map = storage.getStorage();
        assertEquals("Storage should contain one trainee", 1, map.size());

        Trainee trainee = map.get(1);
        assertNotNull("Trainee with id=1 should be present", trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
        assertEquals("John.doe", trainee.getUserName());
        assertTrue(trainee.isActive());
        assertEquals(LocalDate.parse("1990-01-01"), trainee.getDateOfBirth());
        assertEquals("123 Main St", trainee.getAddress());
    }

    @Test
    public void test_AutowiringStorage_NotNull() {
        assertNotNull("Storage should have been autowired", storage);
    }
}

