package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.ReportConflict;
import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by john on 2015-10-28.
 */
public class ComparisonRunnerTest {
    TestDirectoryEnvironment testDirectoryEnvironment;
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    ComparisonRunner comparisonRunner;

    @Before
    public void setUp() throws Exception {
        testDirectoryEnvironment = new TestDirectoryEnvironment("generalTest");
        masterDataDirectoryPaths = testDirectoryEnvironment.getMasterDataDirectoryPaths();
        comparisonRunner = new ComparisonRunner(masterDataDirectoryPaths);
    }

    @After
    public void tearDown() throws Exception {
        testDirectoryEnvironment.removeTestDirectories();
    }

    @Test
    public void testPickOperationWithConflictingFiles() throws Exception {
        assertTrue(comparisonRunner.pickOperation("conflictingFile") instanceof ReportConflict);
        assertTrue(comparisonRunner.pickOperation("dir0/conflictingFile") instanceof ReportConflict);
    }

    @Test
    public void testPickOperationWithConflictingDirectoryAndNormalFiles() throws Exception {
        assertTrue(comparisonRunner.pickOperation("dirOrFile") instanceof ReportConflict);
    }

    @Test
    public void testPickOperationWithConflictingLinkAndNormalFiles() throws Exception {
        assertTrue(comparisonRunner.pickOperation("fileLinkedWithinTree") instanceof ReportConflict);
    }




}
