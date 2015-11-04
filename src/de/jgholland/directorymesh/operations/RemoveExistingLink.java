package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FileCreationUtility;
import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */

public class RemoveExistingLink extends OperationOnFilePair {

    public RemoveExistingLink(FilePair filePair) {
        super(filePair);
    }

    @Override
    public void reportOperation() {
        FileCreationUtility.reportRemoveSymink(filePair.abbreviatedMasterPath());
    }

    @Override
    public void runOperation() {
        FileCreationUtility.removeSymlink(filePair.masterPath);
    }
}
