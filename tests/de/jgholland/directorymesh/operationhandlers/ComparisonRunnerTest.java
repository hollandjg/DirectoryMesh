package de.jgholland.directorymesh.operationhandlers;

import org.junit.Test;

/**
 * Created by john on 2015-10-28.
 */
public class ComparisonRunnerTest extends MeshGeneralTestClass {

    @Test
    public void testPickOperation_standardRulesForFiles() throws Exception {
        setupGeneralTest();

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
        setupGeneralTest();

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
        setupGeneralTest();

        expectReportConflictForDirectory("dirMasterFileData");
        expectReportConflictForDirectory("dirDataFileMaster");

    }

}
