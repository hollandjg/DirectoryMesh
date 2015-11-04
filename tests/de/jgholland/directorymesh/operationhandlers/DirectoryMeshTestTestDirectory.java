package de.jgholland.directorymesh.operationhandlers;

import org.junit.Test;

/**
 * Created by john on 2015-10-30.
 */
public class DirectoryMeshTestTestDirectory extends MeshGeneralTestClass {

    @Test
    public void testRunDirectoryMesh() throws Exception {
        boolean notQuiet = false;
        boolean dryRun = true;
        DirectoryMesh directoryMesh = new DirectoryMesh(
                masterDataDirectoryPaths.masterDirectoryPathString,
                masterDataDirectoryPaths.dataDirectoryPathString,
                notQuiet,
                dryRun);
        directoryMesh.linkDataFilesIntoMaster();
    }
}