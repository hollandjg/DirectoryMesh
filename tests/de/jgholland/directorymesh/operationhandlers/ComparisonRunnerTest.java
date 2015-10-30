package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.*;
import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

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

    void expectMakeLink(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof MakeLink);
    }

    void expectMakeLinkForDirectory(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof MakeLinkForDirectory);
    }

    void expectNullOperation(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof NullOperation);
    }

    void expectRemoveExistingLink(String filename) throws Exception {
        FileOperation operation = comparisonRunner.pickOperation(filename);
        assertTrue(operation instanceof RemoveExistingLink);
    }

    void expectReplaceExistingLink(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof ReplaceExistingLink);
    }

    void expectReportConflict(String filename) throws Exception {
        FileOperation operation = comparisonRunner.pickOperation(filename);
        assertTrue(operation instanceof ReportConflict);
    }

    void expectReportDataFileMissing(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof ReportDataFileMissing);
    }

    @Test
    public void testPickOperation_standardRulesForFiles() throws Exception {
        expectReportConflict("MasterNormalDataNormal");
        expectReportConflict("MasterNormalDataOtherLink");
        expectNullOperation("MasterNormalDataMissing");
        expectNullOperation("MasterBackLinkDataNormal");
        expectNullOperation("MasterBackLinkDataOtherLink");
        expectRemoveExistingLink("MasterBackLinkDataMissing");
        expectReportConflict("MasterOtherLinkDataNormal");
        expectReportConflict("MasterOtherLinkDataOtherLink");
        expectNullOperation("MasterOtherLinkDataMissing");
        expectMakeLink("MasterMissingDataNormal");
        expectMakeLink("MasterMissingDataOtherLink");
        expectReportDataFileMissing("MasterMissingDataMissing");
    }

    @Test
    public void testPickOperation_standardRulesForDirectories() throws Exception {
        expectNullOperation("dirMasterNormalDataNormal");
        expectReportConflict("dirMasterNormalDataOtherLink");
        expectNullOperation("dirMasterNormalDataMissing");
        expectNullOperation("dirMasterBackLinkDataNormal");
        expectNullOperation("dirMasterBackLinkDataOtherLink");
        expectRemoveExistingLink("dirMasterBackLinkDataMissing");
        expectReportConflict("dirMasterOtherLinkDataNormal");
        expectReportConflict("dirMasterOtherLinkDataOtherLink");
        expectNullOperation("dirMasterOtherLinkDataMissing");
        expectMakeLinkForDirectory("dirMasterMissingDataNormal");
        expectMakeLinkForDirectory("dirMasterMissingDataOtherLink");
        expectReportDataFileMissing("dirMasterMissingDataMissing");
    }

    @Test
    public void testPickOperation_specialRules() throws Exception {
        expectReportConflict("dirMasterFileData");
        expectReportConflict("dirDataFileMaster");
    }

}
