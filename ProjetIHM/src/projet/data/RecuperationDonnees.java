package projet.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * 
 * RecuperationDonnees permet de lire le fichier .csv et stocker ses données dans les
 * différentes structures du Modèle
 * 
 * @author BEN OUADDAY
 *
 */
public class RecuperationDonnees {
	/**
	 * nombre d'années contenues dans le fichier 
	 */
	public static int sampleNumber = 0; 
	
	/**
	 * récupère les données du fichier .csv
	 * @param csvFilePath
	 * 					le chemin permettant de retrouver le fichier .csv 
	 * @param earth			
	 * 					l'instance dans laquelle sera stockée l'ensemble des données extraites du fichier
	 */
	public static void getDataFromCSVFile(String csvFilePath, DonneesPlanete earth) {
		
		try {
			FileReader file = new FileReader(csvFilePath);
			BufferedReader bufRead = new BufferedReader(file);
	
			String line = bufRead.readLine();
			//on enlève toutes les guillemets et on sépare les années par les virgules 
			String[] firstLine = line.replace("\"", "").split(",");
			//on commence la première ligne au troisième mot (on enlève "lat" et "lon")
			firstLine = Arrays.copyOfRange(firstLine, 2, firstLine.length); 
			//on copie tout le tableau de String qui contient les années en tableau de int 
			int[] annees =  Arrays.asList(firstLine).stream().mapToInt(Integer::parseInt).toArray();
			sampleNumber = annees.length; // nombre d'années contenu dans le tableau d'années 
		    line = bufRead.readLine(); 
			while ( line != null) {
				String[] data =line.split(",");
				// on récupère les valeurs des latitudes/longitudes (deux premieres cases du tableau)
				int lat = Integer.parseInt(data[0]); 
				int lon = Integer.parseInt(data[1]);
				
				//on crée une Zone avec ses latitude/longitude
				Zone zone = new Zone(lat, lon);
				//on l'ajoute au modèle 
				earth.getListeZones().put(data[0]+","+data[1],zone ); 
				
				for(int i =2; i<data.length; i++) {
					float anomalie = 0; 
					try {
						 anomalie = Float.parseFloat(data[i]); //on récupère la valeur de l'anomalie 
						 // on teste pour récupérer anomalieMax et Min 
						 if(earth.getAnomalieMax() < anomalie) {
							 earth.setAnomalieMax(anomalie);
						 }
						 if(earth.getAnomalieMin() > anomalie) {
							 earth.setAnomalieMin(anomalie);
						 }
					}
					catch(NumberFormatException e) {// si c'est "NA"
						anomalie = Float.NaN;
					}
					finally {
						//on ajoute l'anomalie à la liste d'anomalie de la Zone en y associant l'année 
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
