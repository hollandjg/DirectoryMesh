package de.jgholland.directorymesh.utilities;

import de.jgholland.directorymesh.operations.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by john on 2015-11-04.
 */
public class TestUtilities {

    public static void expectMakeLink(FileOperation operation) throws Exception {
        assertTrue(operation instanceof MakeLink);
    }

    public static void expectMakeLinkForDirectory(FileOperation operation) throws Exception {
        assertTrue(operation instanceof MakeLinkForDirectory);
    }

    public static void expectNullOperation(FileOperation operation) throws Exception {
        assertTrue(operation instanceof NullOperation);
    }

    public static void expectNullOperationIgnoreSubdirectories(FileOperation operation) throws Exception {
        assertTrue(operation instanceof NullOperationCorrectBackLinkForDirectory);
    }

    public static void expectRemoveExistingLink(FileOperation operation) throws Exception {
        assertTrue(operation instanceof RemoveExistingLink);
    }

    public static void expectReplaceExistingLink(FileOperation operation) throws Exception {
        assertTrue(operation instanceof ReplaceExistingLink);
    }

    public static void expectReportConflict(FileOperation operation) throws Exception {
        assertTrue(operation instanceof ReportConflict);
    }

    public static void expectReportDataFileMissing(FileOperation operation) throws Exception {
        assertTrue(operation instanceof ReportDataFileMissing);
    }

}
