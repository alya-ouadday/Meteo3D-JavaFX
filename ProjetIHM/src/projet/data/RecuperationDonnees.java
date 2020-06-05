package projet.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class RecuperationDonnees {

	public static void getDataFromCSVFile(String csvFilePath, DonneesPlanete earth) {
		
		try {
			FileReader file = new FileReader(csvFilePath);
			BufferedReader bufRead = new BufferedReader(file);
	
			String line = bufRead.readLine();
			String[] firstLine = line.replace("\"", "").split(",");
			firstLine = Arrays.copyOfRange(firstLine, 2, firstLine.length); 
			int[] annees =  Arrays.asList(firstLine).stream().mapToInt(Integer::parseInt).toArray();
			System.out.println(annees.length);
		    line = bufRead.readLine(); 
			while ( line != null) {
				String[] data =line.split(",");
				int lat = Integer.parseInt(data[0]); 
				int lon = Integer.parseInt(data[1]);
				Zone zone = new Zone(lat, lon);
				earth.getListeZones().put(data[0]+","+data[1],zone ); 
				
				for(int i =2; i<data.length; i++) {
					float anomalie = 0; 
					try {
						 anomalie = Float.parseFloat(data[i]); 
						 if(earth.getAnomalieMax() < anomalie) {
							 earth.setAnomalieMax(anomalie);
						 }
						 if(earth.getAnomalieMin() > anomalie) {
							 earth.setAnomalieMin(anomalie);
						 }
					}
					catch(NumberFormatException e) {
						anomalie = Float.NaN;
					}
					finally {
						zone.getAnomalies().put(annees[i-2], anomalie); 
					}	
				}	 
			   	
			    line = bufRead.readLine();
			}
		
	
			bufRead.close();
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
