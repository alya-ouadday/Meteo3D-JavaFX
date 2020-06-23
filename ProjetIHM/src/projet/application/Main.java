package projet.application;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet.controller.Controller;
import projet.data.DonneesPlanete;
import projet.data.RecuperationDonnees;

/**
 * 
 * Main dans lequel on lance l'application
 * @author BEN OUADDAY 
 *
 */
public class Main extends Application{
	


	@Override
	public void start(Stage stage) throws Exception {
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("/projet/vues/Vue.fxml"));
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

	}
}
