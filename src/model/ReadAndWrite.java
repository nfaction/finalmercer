package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

public class ReadAndWrite {
	
	/**
	 * @param list
	 * @param fileName
	 *            This writes the accounts to disk
	 */
	public void write(Model list, String fileName) {
		try {
			FileOutputStream outFile = new FileOutputStream(fileName);
			ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
			outputStream.writeObject(list);
			// Do NOT forget to close the output stream!
			outputStream.close();
		} catch (IOException ioe) {
			String message = "Error writing objects to disk to file: "
					+ fileName + "\n" + ioe
					+ "\nHope you had data backed up...";
			JOptionPane.showMessageDialog(null, message);
		}
	}

	/**
	 * @param fileName
	 * @return This reads the accounts from disk
	 */
	public Model read(String fileName) {
		Model list = null;
		try {
			FileInputStream inFile = new FileInputStream(fileName);
			ObjectInputStream inputStream = new ObjectInputStream(inFile);
			Object o = inputStream.readObject();
			list = (Model) o;
			inputStream.close();
		} catch (Exception e) {
			String message = "Error reading objects from disk: " + "\n" + e
					+ "\nProgram shutting down";
			System.out.println(message);
		}
		return list;
	}


}
