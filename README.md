DirectoryMesh
==============

`DirectoryMesh` is a Java utility which can mesh a master directory and a data directory together. 

This is pre-release software
-----------------------------

I did my best, but you'd beta watch out. Please report any issues here: https://github.com/hollandjg/DirectoryMesh

Usage scenario
---------------

A user has several storage drives, of which one is always present and should be considered the "master" drive.
This is usually a drive with insufficient capacity to store all of the data the user needs.
The user also has a second drive with a larger capacity but wants to be able to access data on both drives within the
master directory structure.

Solution
---------

The user defines a sensible directory structure in master (which we assume to be the home directory):

    [master]
    ├─── Documents
    │    ├─── Applications
    │    └─── Letters
    ├─── Projects
    │    ├─── Doctorate
    │    │    ├─── OldFile -> [data]/Projects/Doctorate/OldFile
    │    │    └─── Thesis
    │    └─── MoonlightingAsASecurityConsultant
    └─── Music
         └─── DAWProjects
              └─── CurrentProject
              


The required subset of the structure is implemented on the data drive:

    [data]
    ├─── Projects
    │    └─── Doctorate
    │         └─── LargeDataFile
    └─── Music
         └─── DAWProjects
              └─── ArchivedProject
   
The two directories are meshed together using the shell command:

```bash
java -jar DirectoryMesh.jar --dryRun --prune -m [master] -d [data]
# After checking that the correct operations are going to be performed and dealing with any conflicts reported,
# remove the --dryRun option to actually carry out the meshing operation. 
```

or if you wish to install using homebrew:

```bash
brew tap hollandjg/tap 
brew install directorymesh
directorymesh --dryRun --prune -m [master] -d [data]
```

A concrete example, where `/Users/john` is on an SSD and `/Volumes/data` is a large capacity external drive:

```bash
directorymesh --dryRun --prune -m /Users/john -d /Volumes/data/john
```

After running the mesh, the filestructure looks like this:

        [master]
        ├─── Documents
        │    ├─── Applications
        │    └─── Letters
        ├─── Projects
        │    ├─── Doctorate
        │    │    ├─── LargeDataFile -> [data]/Projects/Doctorate/LargeDataFile
        │    │    └─── Thesis
        │    └─── MoonlightingAsASecurityConsultant
        └─── Music
             └─── DAWProjects
                  ├─── ArchivedProject -> [data]/Music/DAWProjects/ArchivedProject
                  └─── CurrentProject


 - The broken link at `[master]/Projects/Doctorate/OldFile` has been pruned. 
 - If any conflicts arise during the mesh, such as two regular files in the same location, the file reports the conflict and doesn't do anything with those files. `DirectoryMesh` continues regardless.
 - It should not delete any files which aren't symlinks or which are not links back to locations within `[data]`. 
 - Running the mesh on a directory which has already been meshed should cause no problems. New links will be added and (if the --prune option is specified) broken links will be removed. 
   