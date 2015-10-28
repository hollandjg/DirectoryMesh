package de.jgholland.directorymesh;

import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import org.junit.*;

/**
 * Created by john on 2015-10-26.
 */
public class MainTest {

    MasterDataDirectoryPaths masterDataDirectoryPaths;
    TestDirectoryEnvironment testDirectoryEnvironment;


    @org.junit.Before
    public void setUp() throws Exception {
        testDirectoryEnvironment = new TestDirectoryEnvironment("testGeneral");
        masterDataDirectoryPaths = testDirectoryEnvironment.getMasterDataDirectoryPaths();

    }

    @org.junit.After
    public void tearDown() throws Exception {
        testDirectoryEnvironment.removeTestDirectories();
    }

    @Test
    public void testMain() throws Exception {
        Main mainNQ = new de.jgholland.directorymesh.Main();
        mainNQ.main(new String[] { "-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString, "-nq"});
        de.jgholland.directorymesh.Main.main(new String[] {"-q",  "-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString});
        de.jgholland.directorymesh.Main.main(new String[] {"-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString, "-n"});

    }
}
