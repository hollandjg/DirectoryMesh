package de.jgholland.directorymesh.operationhandlers;

import org.junit.Test;

/**
 * Created by john on 2015-10-26.
 */
public class MainTest extends MeshGeneralTestClass {

    @Test
    public void testMain() throws Exception {
        setupGeneralTest();
        de.jgholland.directorymesh.Main.main(new String[] {"-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString});

    }
}
