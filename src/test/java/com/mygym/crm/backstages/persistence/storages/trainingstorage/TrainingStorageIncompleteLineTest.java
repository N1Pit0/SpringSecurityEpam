package com.mygym.crm.backstages.persistence.storages.trainingstorage;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
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
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StorageTestConfig.class})
public class TrainingStorageIncompleteLineTest {
    @Autowired
    private TrainingStorage storage;
    private File tempFile;

    @Before
    public void setUp() throws Exception {
        tempFile = File.createTempFile("training", ".csv");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("traineeId;trainerId;trainingName;trainingType;\n");

        writer.write("1;2;Session1;WEIGHT\n");
        writer.close();

        ReflectionTestUtils.setField(storage, "trainingDataPath", tempFile.getAbsolutePath());
    }

    @After
    public void tearDown() throws Exception {
        tempFile.delete();
    }

    @Test
    public void testInit_withIncompleteData_skipsRecord() {
        storage.init();
        Map<TrainingKey, Training> map = storage.getStorage();
        assertTrue("Storage should be empty when data is incomplete", map.isEmpty());
    }

    @Test
    public void test_AutowiringStorage_NotNull() {
        assertNotNull("Storage should have been autowired", storage);
    }
}
