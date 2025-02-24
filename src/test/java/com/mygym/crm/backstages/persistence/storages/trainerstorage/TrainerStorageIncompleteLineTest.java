package com.mygym.crm.backstages.persistence.storages.trainerstorage;

import com.mygym.crm.backstages.domain.models.Trainer;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StorageTestConfig.class})
public class TrainerStorageIncompleteLineTest {
    @Autowired
    private TrainerStorage storage;
    private File tempFile;

    @Before
    public void setUp() throws Exception {
        tempFile = File.createTempFile("trainer", ".csv");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("id;firstName;lastName;username;password;isActive;trainingType\n");

        writer.write("2;Jane;Doe;Jane.Doe\n");
        writer.close();

        ReflectionTestUtils.setField(storage, "trainerDataPath", tempFile.getAbsolutePath());
    }

    @After
    public void tearDown() throws Exception {
        tempFile.delete();
    }

    @Test
    public void testInit_withIncompleteData_skipsRecord() {
        storage.init();
        Map<Integer, Trainer> map = storage.getStorage();
        assertTrue("Storage should be empty when data is incomplete", map.isEmpty());
    }

    @Test
    public void test_AutowiringStorage_NotNull() {
        assertNotNull("Storage should have been autowired", storage);
    }
}
