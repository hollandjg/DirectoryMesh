package de.jgholland.directorymesh;

import de.jgholland.directorymesh.operations.FileUtilities;
import org.junit.*;

import java.io.File;
import java.nio.file.*;

/**
 * Created by john on 2015-10-26.
 */
public class MainTest {

    Path temporaryDirectoryPath;
    String temporaryDirectoryPathString;
    FileUtilities fileUtilities;

    @org.junit.Before
    public void setUp() throws Exception {
        temporaryDirectoryPath = java.nio.file.Files.createTempDirectory("directoryMesh");
        Path masterDirectoryPath = Paths.get(temporaryDirectoryPath.toString(), "master/");
        Path dataDirectoryPath = Paths.get(temporaryDirectoryPath.toString(), "data/");
        fileUtilities = new FileUtilities(masterDirectoryPath, dataDirectoryPath);
        temporaryDirectoryPathString = temporaryDirectoryPath.toString();
        System.out.println("created " + masterDirectoryPath + "\n" + dataDirectoryPath);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        org.apache.commons.io.FileUtils.deleteDirectory(new File(temporaryDirectoryPathString));
        System.out.println("deleted " + temporaryDirectoryPathString);
    }

    @Test
    public void testFilesystemCreation() throws Exception {
        fileUtilities.touchFile(fileUtilities.pathInDataDirectory("datafile0"));
        fileUtilities.touchFile(fileUtilities.pathInDataDirectory("datafile1"));
        fileUtilities.touchFile(fileUtilities.pathInDataDirectory("commonDir/datafile2"));
        fileUtilities.createDirectory(fileUtilities.pathInMasterDirectory("commonDir/"));
        fileUtilities.createDirectory(fileUtilities.pathInMasterDirectory("dirOnlyInMaster/datafile3"));
        fileUtilities.touchWithLink(fileUtilities.pathInDataDirectory("datafile4"), fileUtilities.pathInMasterDirectory("datafile4"));
    }

    @Test
    public void testMain() throws Exception {
        Main mainNQ = new de.jgholland.directorymesh.Main();
        mainNQ.main(new String[] { "-m", fileUtilities.masterDirectoryPathString, "-d", fileUtilities.dataDirectoryPathString, "-nq"});
        de.jgholland.directorymesh.Main.main(new String[] {"-q",  "-m", fileUtilities.masterDirectoryPathString, "-d", fileUtilities.dataDirectoryPathString});
        de.jgholland.directorymesh.Main.main(new String[] {"-m", fileUtilities.masterDirectoryPathString, "-d", fileUtilities.dataDirectoryPathString, "-n"});

    }
}
