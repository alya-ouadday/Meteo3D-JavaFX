package projet.application;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet.data.DonneesPlanete;
import projet.data.RecuperationDonnees;


public class Main extends Application{


	@Override
	public void start(Stage stage) throws Exception {
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("/projet/controller/Vues.fxml"));
			//Scene scene = new Scene(root); 
			stage.setScene(new Scene(root));
			stage.setTitle("Global Warming 3D"); 
			stage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	/*
	terre.getAnomalieZoneAnnee(12, 154, 1881); 
	System.out.println(terre.getAnomalieMax());
	System.out.println(terre.getAnomalieMin()); 
	terre.getAnomaliesZone(12, 154);
	terre.getAnomaliesAnnee(1952); */
	
	}
}
