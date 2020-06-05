package projet.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RecuperationDonnees {

	public static void getDataFromCSVFile(String csvFilePath) {

		try {
			FileReader file = new FileReader(csvFilePath);
			BufferedReader bufRead = new BufferedReader(file);
	
			String line = bufRead.readLine();
			while ( line != null) {
			   	String[] array = line.split(",");
			   
			  //TO DO 		
			    line = bufRead.readLine();
			}
	
			bufRead.close();
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
