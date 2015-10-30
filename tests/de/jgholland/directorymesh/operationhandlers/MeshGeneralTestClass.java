package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import org.junit.After;
import org.junit.Before;

import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

/**
 * Created by john on 2015-10-28.
 */
public class MeshGeneralTestClass {
    TestDirectoryEnvironment testDirectoryEnvironment;
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    ComparisonRunner comparisonRunner;

    @Before
    public void setUp() throws Exception {
        testDirectoryEnvironment = new TestDirectoryEnvironment("generalTest");
        masterDataDirectoryPaths = testDirectoryEnvironment.getMasterDataDirectoryPaths();
        comparisonRunner = new ComparisonRunner(masterDataDirectoryPaths);

        /*
        Add some links which can't be stored in the version control system because they are broken.
        If they are missing during the compile, they cause the java compiler to crash.
        */
        Files.createSymbolicLink(
                masterDataDirectoryPaths.pathInMasterDirectory("MasterBackLinkDataMissing"),
                masterDataDirectoryPaths.pathInDataDirectory("MasterBackLinkDataMissing") // File should not exist.
        );
        Files.createSymbolicLink(
                masterDataDirectoryPaths.pathInMasterDirectory("dirMasterBackLinkDataMissing"),
                masterDataDirectoryPaths.pathInDataDirectory("dirMasterBackLinkDataMissing") // Directory should not exist.
        );
    }

    @After
    public void tearDown() throws Exception {
        testDirectoryEnvironment.removeTestDirectories();
    }

    void missingTest() throws Exception {
        assertTrue(false);
    }

}
