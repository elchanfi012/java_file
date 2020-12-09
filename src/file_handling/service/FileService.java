package file_handling.service;

import file_handling.manager.ConsoleManager;
import file_handling.manager.FileManager;

import java.io.File;

public class FileService {

    private boolean done;
    private FileManager fileManager;

    public FileService() {
        done = false;
        fileManager = new FileManager();
    }

    public void run() {
        // print the beautiful app title
        printApplicationTitle();

        String answer;

        do {
            // print the menu & get the user's answer back in return
            answer = printMenu();

            // handle the user action
            handleAction(answer);
        } while (!answer.equalsIgnoreCase(UserActions.EXIT.getValue())); // loop while until user wants to exit

        done = true;
    }

    private void handleAction(String action) {
        if (action.equalsIgnoreCase(UserActions.LIST_FILES.getValue())) {
            listFiles();
        }

        if (action.equalsIgnoreCase(UserActions.CREATE_FILE.getValue())) {
            createFile();
        }

        if (action.equalsIgnoreCase(UserActions.CREATE_FOLDER.getValue())) {
            createFolder();
        }

        if (action.equalsIgnoreCase(UserActions.EDIT_FILE.getValue())) {
        	editFile();
        }

        if (action.equalsIgnoreCase(UserActions.DELETE_FILE.getValue())) {
        	deleteAFile();
        }

        if (action.equalsIgnoreCase(UserActions.GO_IN_FOLDER.getValue())) {
        	moveInto();
        }

        if (action.equalsIgnoreCase(UserActions.BACK_FOLDER.getValue())) {
        	back();
        }
        
        if (action.equalsIgnoreCase(UserActions.READ_TXT_FILE.getValue())) {
        	readFile();
        }
    }

    /**
     * Print the menu
     * @return the action answer number
     */
    private String printMenu() {
        boolean rightAnswer = false;
        String answer = "";

        do {
            // print the option menu
            ConsoleManager.getInstance().printLine();
            ConsoleManager.getInstance().printToConsole("Current path - "+fileManager.getCurrentPath(), true);
            ConsoleManager.getInstance().printLine();
            ConsoleManager.getInstance().consoleLineBreak();
            ConsoleManager.getInstance().printToConsole("What do you want to do ? ", true);
            ConsoleManager.getInstance().printToConsole("1 - List files ", true);
            ConsoleManager.getInstance().printToConsole(UserActions.CREATE_FILE.getValue()+" - Create a file", true);
            ConsoleManager.getInstance().printToConsole("3 - Create a folder", true);
            ConsoleManager.getInstance().printToConsole("4 - Edit a file", true);
            ConsoleManager.getInstance().printToConsole("5 - Delete a file", true);
            ConsoleManager.getInstance().printToConsole("6 - Go into folder", true);
            ConsoleManager.getInstance().printToConsole("7 - Move back one folder", true);
            ConsoleManager.getInstance().printToConsole("8 - Read a txt file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.EXIT.getValue() + " - Exit", true);

            // ask user answer
            answer = ConsoleManager.getInstance().readUserInput();

            if (UserActions.containsAction(answer)) {
                rightAnswer = true;
            }
        } while (!rightAnswer);

        return answer;
    }

    private Integer listFiles() {
        printActionTitle("Files listing");

        int counter = 0;

        for (File file : fileManager.listFiles()) {
            ConsoleManager.getInstance().printToConsole(counter + " - " + file.getName(), true);

            counter++;
        }

        if(counter == 0) {
            ConsoleManager.getInstance().printToConsole("There's no file here", true);
        }

        return counter;
    }

    private Integer listFolders() {
        int counter = 0;

        for (File file : fileManager.listFiles()) {
            // test if its a folder
            if(!file.isFile()) {
                ConsoleManager.getInstance().printToConsole(counter + " - " + file.getName(), true);

                counter++;
            }
        }

        if(counter == 0) {
            ConsoleManager.getInstance().printToConsole("There's no folder here", true);
        }

        return counter;
    }

    private void createFile() {
        printActionTitle("File creation");

        ConsoleManager.getInstance().printToConsole("Type file name to create : ", true);

        ConsoleManager.getInstance().consoleLineBreak();

        String name = ConsoleManager.getInstance().readUserInput();
        File file = fileManager.createTxtFile(name);

        if (file == null) {
            ConsoleManager.getInstance().printToConsole("Une erreur est survenue lors de la création...", true);
        } else {
            ConsoleManager.getInstance().printToConsole("Fichier créé", true);
        }
    }

    private void createFolder() {
        printActionTitle("Folder creation");

        ConsoleManager.getInstance().printToConsole("Type folder name to create : ", true);

        String name = ConsoleManager.getInstance().readUserInput();
        fileManager.createFolder(name);

        ConsoleManager.getInstance().printToConsole("Dossier créé", true);
    }

    private void deleteAFile() {
        printActionTitle("File deletion");

        // list files for user to choose
        int nbFiles = listFiles();

        int answer;

        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to delete ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFiles);

        fileManager.deleteFileFromList(answer);
    }

    private void back() {
		fileManager.backOneFolder();
	}

	private void moveInto() {
		printActionTitle("List Folders");

        // list folders for user to choose
        int nbFolders = listFolders();

        int answer;

        do {
            ConsoleManager.getInstance().printToConsole("Which folder do you want to enter ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFolders);

        fileManager.enterFolder(answer);
	}

	private void editFile() {
		// TODO Auto-generated method stub

	}
	
	private void readFile() {
		printActionTitle("File read");

        // list files for user to choose
        int nbFiles = listFiles();

        int answer;

        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to read ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFiles);

        fileManager.readTxtFile(fileManager.listFiles().get(answer).getName());
	}

    private void printApplicationTitle() {
        ConsoleManager.getInstance().consoleLineBreak();
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().printToConsole("File System Manager", true);
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().consoleLineBreak();
    }

    private void printActionTitle(String title) {
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().printToConsole(title, true);
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().consoleLineBreak();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
