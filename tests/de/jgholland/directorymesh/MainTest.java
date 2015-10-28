package de.jgholland.directorymesh;

import de.jgholland.directorymesh.utilities.FileUtilities;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import de.jgholland.directorymesh.testutilities.TestDirectoryEnvironment;
import org.junit.*;

/**
 * Created by john on 2015-10-26.
 */
public class MainTest {

    MasterDataDirectoryPaths masterDataDirectoryPaths;
    TestDirectoryEnvironment testDirectoryEnvironment;
    FileUtilities fileUtilities;

    @org.junit.Before
    public void setUp() throws Exception {
        testDirectoryEnvironment = new TestDirectoryEnvironment();
        masterDataDirectoryPaths = testDirectoryEnvironment.getMasterDataDirectoryPaths();
        fileUtilities = new FileUtilities(masterDataDirectoryPaths);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        testDirectoryEnvironment.removeTestDirectories();
    }

    @Test
    public void testFilesystemCreation() throws Exception {
        fileUtilities.touchFile(masterDataDirectoryPaths.pathInDataDirectory("datafile0"));
        fileUtilities.touchFile(masterDataDirectoryPaths.pathInDataDirectory("datafile1"));
        fileUtilities.touchFile(masterDataDirectoryPaths.pathInDataDirectory("commonDir/datafile2"));
        fileUtilities.createDirectory(masterDataDirectoryPaths.pathInMasterDirectory("commonDir/"));
        fileUtilities.createDirectory(masterDataDirectoryPaths.pathInMasterDirectory("dirOnlyInMaster/datafile3"));
        fileUtilities.touchWithLink(masterDataDirectoryPaths.pathInDataDirectory("datafile4"), masterDataDirectoryPaths.pathInMasterDirectory("datafile4"));
    }

    @Test
    public void testMain() throws Exception {
        Main mainNQ = new de.jgholland.directorymesh.Main();
        mainNQ.main(new String[] { "-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString, "-nq"});
        de.jgholland.directorymesh.Main.main(new String[] {"-q",  "-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString});
        de.jgholland.directorymesh.Main.main(new String[] {"-m", masterDataDirectoryPaths.masterDirectoryPathString, "-d", masterDataDirectoryPaths.dataDirectoryPathString, "-n"});

    }
}
