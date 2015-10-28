package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.FileOperation;
import de.jgholland.directorymesh.operations.ReportConflict;
import de.jgholland.directorymesh.utilities.FileUtilities;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by john on 2015-10-28.
 */
public class ComparisonRunnerTest {
    TestDirectoryEnvironment testDirectoryEnvironment;
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    FileUtilities fileUtilities;
    ComparisonRunner comparisonRunner;

    @Before
    public void setUp() throws Exception {
        testDirectoryEnvironment = new TestDirectoryEnvironment();
        masterDataDirectoryPaths = testDirectoryEnvironment.getMasterDataDirectoryPaths();
        fileUtilities = new FileUtilities(masterDataDirectoryPaths);
        comparisonRunner = new ComparisonRunner(masterDataDirectoryPaths);
    }

    @After
    public void tearDown() throws Exception {
        testDirectoryEnvironment.removeTestDirectories();
    }

    @Test
    public void testPickOperationWithConflictingFiles() throws Exception {
        String regularFileInRoot = "regularFile";
        fileUtilities.touchFilesInMasterAndData(regularFileInRoot);
        assertTrue(comparisonRunner.pickOperation(regularFileInRoot) instanceof ReportConflict);

        String regularFileInDirectory = "dir/regularFile";
        fileUtilities.touchFilesInMasterAndData(regularFileInDirectory);
        assertTrue(comparisonRunner.pickOperation(regularFileInDirectory) instanceof ReportConflict);

        assertTrue(comparisonRunner.pickOperation("dir/") instanceof ReportConflict);

    }

    @Test
    public void testPickOperationWithConflictingDirectoryAndNormalFiles() throws Exception {
        String filename = "dirOrFile";
        fileUtilities.touchFileInMaster(filename);
        fileUtilities.createDirectoryInMaster(filename);
        assertTrue(comparisonRunner.pickOperation(filename) instanceof ReportConflict);
    }

    @Test
    public void testPickOperationWithConflictingLinkAndNormalFiles() throws Exception {
        String regularFileInRoot = "filename";
        String secondFileInRoot = "anotherFilename";
        fileUtilities.touchFileInMaster(regularFileInRoot);
        fileUtilities.touchWithLink(
                masterDataDirectoryPaths.pathInDataDirectory(regularFileInRoot),
                masterDataDirectoryPaths.pathInDataDirectory(secondFileInRoot));
        assertTrue(comparisonRunner.pickOperation(regularFileInRoot) instanceof ReportConflict);

    }




}
