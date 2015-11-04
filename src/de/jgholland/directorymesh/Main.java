package de.jgholland.directorymesh;

import de.jgholland.directorymesh.operationhandlers.DirectoryMesh;
import org.apache.commons.cli.*;

public class Main {

    static Options options;
    static CommandLine commandLine;
    static DirectoryMesh directoryMesh;
    static String ApplicationName = "DirectoryMesh";
    static String descriptionOfTheTool = "Recursively symlink the data directory in the master directory.\n\n";
    static String contactDataForIssueReporting = "\nPlease report issues at https://github.com/hollandjg/DirectoryMesh";
    static String masterPathOptionName = "masterPath";
    static String dataPathOptionName = "dataPath";
    static String dryRunOptionName = "dryRun";
    static String quietOptionName = "quiet";
    static String pruneOptionName = "prune";
    static String helpOptionName = "help";

    public static void main(String[] args) throws Exception {
        declareOptions();
        parseCommandLineArguments(args, options);
        initialiseDirectoryMeshObjectWithOptions();
        runDirectoryMesh();
    }

    private static void declareOptions() {
        options = new Options();

        Option masterPath = Option
                .builder("m")
                .longOpt(masterPathOptionName)
                .argName(masterPathOptionName)
                .required(true)
                .type(String.class)
                .hasArg()
                .desc("path where the data are linked")
                .build();

        Option dataPath = Option
                .builder("d")
                .longOpt(dataPathOptionName)
                .argName(dataPathOptionName)
                .required(true)
                .type(String.class)
                .hasArg()
                .desc("path with external data")
                .build();

        options.addOption(masterPath);
        options.addOption(dataPath);
        options.addOption("h", helpOptionName, false, "display help and quit");
        options.addOption("n", dryRunOptionName, false, "dry run");
        options.addOption("q", quietOptionName, false, "quiet");
        options.addOption("p", pruneOptionName, false, "prune incorrect links");

    }

    private static void parseCommandLineArguments(String[] args, Options options) {
        printHelpIfReqired(options);
        initialiseCommandLineParser(args, options);
    }

    private static void initialiseCommandLineParser(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            System.exit(0);
        }
    }

    private static void printHelpIfReqired(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        makeOptionsAppearInTheOrderInWhichTheyWereDeclared(formatter);
        boolean autoUsageOn = true;
        formatter.printHelp(ApplicationName, descriptionOfTheTool, options, contactDataForIssueReporting, autoUsageOn);

    }

    private static void makeOptionsAppearInTheOrderInWhichTheyWereDeclared(HelpFormatter formatter) {
        formatter.setOptionComparator(null);
    }

    private static void initialiseDirectoryMeshObjectWithOptions() {

        String masterPath = commandLine.getOptionValue(masterPathOptionName);
        String dataPath = commandLine.getOptionValue(dataPathOptionName);
        boolean quiet = commandLine.hasOption(quietOptionName);
        boolean dryRun = commandLine.hasOption(dryRunOptionName);
        directoryMesh = new DirectoryMesh(masterPath, dataPath, quiet, dryRun);
    }


    private static void runDirectoryMesh() {
        boolean prune = commandLine.hasOption(pruneOptionName);
        System.out.println("Linking files into place");
        directoryMesh.linkDataFilesIntoMaster();
        if (prune) {
            System.out.println("Checking back links in the master directory");
            directoryMesh.pruneMasterDirectoryBackLinks();
        }
    }
}
