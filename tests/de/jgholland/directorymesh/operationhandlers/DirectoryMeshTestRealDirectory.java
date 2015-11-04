package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.utilities.TestUtilities;
import org.junit.Test;

/**
 * Created by john on 2015-11-04.
 */
public class DirectoryMeshTestRealDirectory {

    @Test
    public void testRunDirectoryMeshRealData() throws Exception {
        boolean notQuiet = false;
        boolean dryRun = true;
        boolean prune = true;
        DirectoryMesh directoryMesh = new DirectoryMesh(
                "/Users/john",
                "/Volumes/data/john",
                notQuiet,
                dryRun);
        directoryMesh.comparisonRunner.pickOperation("Pictures/BRGS").reportOperation();
        TestUtilities.expectRemoveExistingLink(directoryMesh.comparisonRunner.pickOperation("Pictures/BRGS"));
    }
}
