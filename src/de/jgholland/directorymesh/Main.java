package de.jgholland.directorymesh;

import de.jgholland.directorymesh.operationhandlers.DirectoryMesh;
import org.apache.commons.cli.*;

public class Main {

    static Options optionDeclarations;
    static CommandLine commandLine;
    static DirectoryMesh directoryMesh;

    public static void main(String[] args) {
        declareOptions();
        parseCommandLineArguments(args, optionDeclarations);
        initialiseDirectoryMeshObjectWithOptions();
        runDirectoryMesh();
    }

    static String masterPathOptionName = "masterPath";
    static String dataPathOptionName = "dataPath";
    static String dryRunOptionName = "dryRun";
    static String quietOptionName = "quiet";

    private static void declareOptions() {
        optionDeclarations = new Options();
        Option masterPath = Option.builder(masterPathOptionName).hasArg().desc("path where the user wants easy access to the external data").build();
        optionDeclarations.addOption(masterPath);
        Option dataPath = Option.builder(dataPathOptionName).hasArg().desc("path containing the external data").build();
        optionDeclarations.addOption(dataPath);
        optionDeclarations.addOption("n", dryRunOptionName, false, "dry run");
        optionDeclarations.addOption("q", quietOptionName, false, "quiet");
    }

    private static void parseCommandLineArguments(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    private static void initialiseDirectoryMeshObjectWithOptions() {

        String masterPath = commandLine.getOptionValue(masterPathOptionName);
        String dataPath = commandLine.getOptionValue(dataPathOptionName);
        boolean quiet = commandLine.hasOption(quietOptionName);
        boolean dryRun = commandLine.hasOption(dryRunOptionName);
        directoryMesh = new DirectoryMesh(masterPath, dataPath, quiet, dryRun);
    }


    private static void runDirectoryMesh() {
        directoryMesh.runDirectoryMesh();
    }
}
