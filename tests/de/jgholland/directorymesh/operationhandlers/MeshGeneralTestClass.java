package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import de.jgholland.directorymesh.utilities.CorrectFileOperationAssertions;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

/**
 * Created by john on 2015-10-28.
 */
public class MeshGeneralTestClass {
    protected TestDirectoryEnvironment testDirectoryEnvironment;
    protected MasterDataDirectoryPaths masterDataDirectoryPaths;
    ComparisonRunner comparisonRunner;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        testDirectoryEnvironment.removeTestDirectories();
    }

    void setupSimpleTest() throws IOException {
        setupTest("simpleTest");
    }

    void setupGeneralTest() throws IOException {
        setupTest("generalTest");
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


    private void setupTest(String testName) throws IOException {
        testDirectoryEnvironment = new TestDirectoryEnvironment(testName);
        initialiseMasterDataDirectoryPaths();
        initialiseComparisonRunner();
    }

    void initialiseMasterDataDirectoryPaths() throws IOException {
        masterDataDirectoryPaths = testDirectoryEnvironment.getMasterDataDirectoryPaths();
    }

    void initialiseComparisonRunner() {
        comparisonRunner = new ComparisonRunner(masterDataDirectoryPaths);
    }

    void missingTest() throws Exception {
        assertTrue(false);
    }

    void expectMakeLink(String filename) throws Exception {
        CorrectFileOperationAssertions.expectMakeLink(comparisonRunner.pickOperation(filename));
    }

    void expectMakeLinkForDirectory(String filename) throws Exception {
        CorrectFileOperationAssertions.expectMakeLinkForDirectory(comparisonRunner.pickOperation(filename));
    }

    void expectNullOperation(String filename) throws Exception {
        CorrectFileOperationAssertions.expectNullOperation(comparisonRunner.pickOperation(filename));
    }

    void expectNullOperationIgnoreSubdirectories(String filename) throws Exception {
        CorrectFileOperationAssertions.expectNullOperationIgnoreSubdirectories(comparisonRunner.pickOperation(filename));
    }

    void expectRemoveExistingLink(String filename) throws Exception {
        CorrectFileOperationAssertions.expectRemoveExistingLink(comparisonRunner.pickOperation(filename));
    }

    void expectReportConflict(String filename) throws Exception {
        CorrectFileOperationAssertions.expectReportConflict(comparisonRunner.pickOperation(filename));
    }

    void expectReportDataFileMissing(String filename) throws Exception {
        CorrectFileOperationAssertions.expectReportDataFileMissing(comparisonRunner.pickOperation(filename));
    }

    void expectReportConflictForDirectory(String filename) throws Exception {
        CorrectFileOperationAssertions.expectReportConflictForDirectory(comparisonRunner.pickOperation(filename));
    }

}
