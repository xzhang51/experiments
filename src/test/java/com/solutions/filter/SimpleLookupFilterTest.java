package com.solutions.filter;

import com.solutions.dao.FileDataSourceDaoImpl;
import com.solutions.model.MemberKey;
import junit.framework.TestCase;

public class SimpleLookupFilterTest extends TestCase {
    private static String keyFilePath = "/Users/xifengzhang/IdeaProjects/webappexample/src/test/resources/keyDataFile";
    private SimpleLookupFilter filter;

    public void setUp() {
        filter = new SimpleLookupFilter();
        filter.setKeySourceDao(new FileDataSourceDaoImpl(keyFilePath));
    }

    public void testIsFound() {
        assertTrue(filter.isFound(new MemberKey("10000000000")));
    }

    public void testSize() {
        filter.load();
        assertEquals(9, filter.size());
    }

}
