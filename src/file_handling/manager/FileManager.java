package file_handling.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;



public class FileManager {

	private String currentPath;
	private StringBuilder sb;

	public FileManager() {
		currentPath = "D:\\Documents\\CDA\\";
		this.sb = new StringBuilder();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public List<File> listFiles() {
		File currentFolder = new File(currentPath);

		File[] files = currentFolder.listFiles();

		return files != null ? Arrays.asList(files) : new LinkedList<>();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public void deleteFileFromList(int index) {
		File currentFolder = new File(currentPath);

		currentFolder.listFiles()[index].delete();
	}

	/**
	 * Create a .txt file
	 * 
	 * @param name the name of the file to create (with no extension)
	 * @return
	 */
	public File createTxtFile(String name) {
		File file = new File(currentPath + name + ".txt");

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}


	/**
	 * Move the path to a folder
	 * 
	 * @param index of the folder to move into
	 */
	public void enterFolder(int index) {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			if (!file.isFile()) {
				folders.add(file);
			}
		}

		currentPath += folders.get(index).getName() + "\\";
	}

	/**
	 * Go back one folder from the current path
	 */
	public void backOneFolder() {


		List<String> paths = Arrays.asList(currentPath.split(Pattern.quote("\\")));

		
		if(paths.size() > 0) {

			paths = paths.subList(0, paths.size() - 1);
	
			currentPath = String.join("\\", paths);
	
			currentPath += "\\";
		}
		
		if (currentPath.isEmpty() || currentPath.equals("D:")) {
			currentPath = "D:\\";
		}
	}

	public void createFolder(String name) {
		File file = new File(currentPath + name);

		file.mkdir();
	}


	/**
	 * Get the current path
	 *
	 * @return
	 */
	public String getCurrentPath() {
		return currentPath;
	}
	
	public void readTxtFile(String name) {
		File fileToRead = new File(currentPath + name);
		
		try(FileInputStream fis = new FileInputStream(fileToRead)){
			int data;
			while((data = fis.read()) >= 0) {
				ConsoleManager.getInstance().printToConsole(Character.toString(data), false);
			}
			ConsoleManager.getInstance().consoleLineBreak();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void copyTxtFile(String name) {
		File fileToRead = new File(currentPath + name);
		
		List<String> fullName = new ArrayList<>(Arrays.asList(name.split(Pattern.quote("."))));
		
		 String newfullName = fullName.get(0).concat("_bis");
		 fullName.remove(0);
		 fullName.add(0, newfullName);
		
		String newName = String.join(".", fullName);
		
		
		
		File file = new File(currentPath + newName);
		
		try(FileInputStream fis = new FileInputStream(fileToRead); 
				FileOutputStream fos = new FileOutputStream(file);
			){
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			
			int data = 0;
			
			Date start = new Date();
            while((data = fis.read()) >= 0) {
                fos.write(data);
            }

            fos.flush();
            
            Date end = new Date();
			
            
            
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
