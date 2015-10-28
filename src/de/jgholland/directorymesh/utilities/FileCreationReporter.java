package de.jgholland.directorymesh.utilities;

/**
 * Created by john on 2015-10-28.
 */
public class FileCreationReporter {
    String masterDirectoryPathString;
    String dataDirectoryPathString;

    public FileCreationReporter(String masterDirectoryPathString, String dataDirectoryPathString) {
        this.masterDirectoryPathString = masterDirectoryPathString;
        this.dataDirectoryPathString = dataDirectoryPathString;
    }

    public FileCreationReporter(MasterDataDirectoryPaths masterDataDirectoryPaths) {
        this(masterDataDirectoryPaths.masterDirectoryPathString, masterDataDirectoryPaths.dataDirectoryPathString);
    }

    public void report(String string) {
        String newstring = string.replaceAll(masterDirectoryPathString, "[master]").replaceAll(dataDirectoryPathString, "[data]");
        System.out.println(newstring);
    }
}
