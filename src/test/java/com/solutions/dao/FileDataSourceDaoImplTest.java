package com.solutions.dao;

import com.solutions.model.MemberKey;
import junit.framework.TestCase;

import java.io.File;
import java.util.Set;

public class FileDataSourceDaoImplTest extends TestCase {
    private static String keyFilePath = "/Users/xifengzhang/IdeaProjects/webappexample/src/test/resources/keyDataFile";
    private FileDataSourceDaoImpl daoImpl;

    public void setUp() {
        daoImpl = new FileDataSourceDaoImpl(keyFilePath);
    }
    public void testLoadKeys() {
        Set<MemberKey> keys = daoImpl.loadKeys();
        assertEquals(9, keys.size());
        assertTrue(keys.contains(new MemberKey("10000000000")));
        assertTrue(keys.contains(new MemberKey("90000000000")));
    }

    public void testSaveKeys() {
        daoImpl.loadKeys();
        String savedFileName = keyFilePath + ".save";
        File savedFile = new File(savedFileName);
        if (savedFile.exists()) {
            savedFile.delete();
        }

        daoImpl.saveKeys();
        
        assertTrue(savedFile.exists());
    }
}
