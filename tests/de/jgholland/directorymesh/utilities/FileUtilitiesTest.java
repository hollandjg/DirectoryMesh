package de.jgholland.directorymesh.utilities;

import de.jgholland.directorymesh.operationhandlers.ComparisonRunner;
import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by john on 2015-10-28.
 */
public class FileUtilitiesTest {

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

    @Test(expected = java.nio.file.FileAlreadyExistsException.class)
    public void testCantRemakeExistingFile() throws Exception {
        fileUtilities.touchFileInMaster("file");
        fileUtilities.touchFileInMaster("file");

    }
}
