package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FileCreationUtility;
import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */
public class MakeLink extends OperationOnFilePair {

    public MakeLink(FilePair filePair) {
        super(filePair);
    }

    @Override
    public void runOperation() {
        FileCreationUtility.createSymlink(filePair.dataPath, filePair.masterPath);
    }

    @Override
    public void reportOperation() {
        FileCreationUtility.reportNewSymink(filePair.abbreviatedDataPath(), filePair.abbreviatedMasterPath());
    }

}
