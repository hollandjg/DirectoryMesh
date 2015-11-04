package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.*;
import org.junit.Test;
import de.jgholland.directorymesh.utilities.TestUtilities;
import static org.junit.Assert.assertTrue;

/**
 * Created by john on 2015-10-28.
 */
public class ComparisonRunnerTest extends MeshGeneralTestClass {

    void expectMakeLink(String filename) throws Exception {
        TestUtilities.expectMakeLink(comparisonRunner.pickOperation(filename));
    }

    void expectMakeLinkForDirectory(String filename) throws Exception {
        TestUtilities.expectMakeLinkForDirectory(comparisonRunner.pickOperation(filename));
    }

    void expectNullOperation(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof NullOperation);
    }

    void expectNullOperationIgnoreSubdirectories(String filename) throws Exception {
        assertTrue(comparisonRunner.pickOperation(filename) instanceof NullOperationCorrectBackLinkForDirectory);
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
        expectNullOperation(".DS_Store");
        expectNullOperation(".localized");
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
        expectReportConflict("dataLinkToMaster");
    }

    @Test
    public void testPickOperation_standardRulesForDirectories() throws Exception {
        expectNullOperation("dirMasterNormalDataNormal");
        expectReportConflict("dirMasterNormalDataOtherLink");
        expectNullOperation("dirMasterNormalDataMissing");
        expectNullOperationIgnoreSubdirectories("dirMasterBackLinkDataNormal");
        expectNullOperationIgnoreSubdirectories("dirMasterBackLinkDataOtherLink");
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
