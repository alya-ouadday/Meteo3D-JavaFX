package projet.application;

import java.io.File;

import projet.data.DonneesPlanete;
import projet.data.RecuperationDonnees;

public class Main {

	public static void main(String[] args) {
	
	DonneesPlanete terre = new DonneesPlanete("Terre"); 
	
	if(args.length > 0)
	{
		File tempFile = new File(args[0]);
		
		if(tempFile.exists())
		{
			System.out.println("[Main] Reading the file " + args[0] + " ...");
					
			//We start by reading the CSV file
			RecuperationDonnees.getDataFromCSVFile(args[0], terre);
			
			System.out.println("[Main] End of the fi le " + args[0] + ".");
		}
		else
		{
			System.out.println("[Main] No file " + args[0]);
		}
	}
	else
	{
		System.out.println("[Main] You should enter the CSV file path as a parameter.");
	}
	

	terre.getAnomalieZoneAnnee(12, 154, 1881); 
	System.out.println(terre.getAnomalieMax());
	System.out.println(terre.getAnomalieMin()); 
	terre.getAnomaliesZone(12, 154);
	terre.getAnomaliesAnnee(1952); 
	
	}
}
