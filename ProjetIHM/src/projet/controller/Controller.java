package projet.controller;

import java.io.File;
import java.net.URL;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.util.converter.NumberStringConverter;
import projet.data.DonneesPlanete;
import projet.data.RecuperationDonnees;
import tutoriel.Earth.*;
import projet.controller.CameraManager;




import java.net.URL;




public class Controller {
	
	@FXML
	private TextField textLat;
	@FXML
	private TextField textLong; 
	@FXML
	private TextField textAnnee; 
	@FXML
	private RadioButton btnQuadri; 
	@FXML
	private RadioButton btnHisto;
	@FXML
	private  Button btnPlay;  
	@FXML
	private  Button btnBack; 
	@FXML
	private  Button btnFor; 
	@FXML
	private  Button btnStop; 
	@FXML
	private ChoiceBox anneeDeb; 
	@FXML
	private ChoiceBox anneeFin; 
	@FXML
	private RadioButton visible; 
	@FXML
	private Label titreMode; 
	@FXML
	private Label titreAnnee; 
	@FXML
	private Slider sliderAnnee; 
	@FXML
	private ChoiceBox uniteTemp;
	@FXML
	private Pane pane; 
	
	private DonneesPlanete terre;
	

	public Controller() {
		terre = new DonneesPlanete("Terre");
		File tempFile = new File("src/projet/data/DonneesTerre.csv");
		if(tempFile.exists())
		{
		RecuperationDonnees.getDataFromCSVFile("src/projet/data/DonneesTerre.csv", terre);
		}
		else {
			System.out.println("[Main] No file " + "src/projet/data/DonneesTerre.csv");
		}
	}
	
	@FXML
	public void initialize()
    {
		/*
		Sphere s = new Sphere(100); 
		Group root = new Group(); 
		root.setLayoutX(100);
		root.setLayoutY(100);
		root.getChildren().add(s);
		subscene.setRoot(root);*/
		
		//Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
       
        // Load geometry
        
        
        ObjModelImporter objImporter = new ObjModelImporter(); 
        try {
        	URL modelUrl = this.getClass().getResource("/tutoriel/Earth/earth.obj");
        	objImporter.read(modelUrl);
        	
        }catch(ImportException e) {
        	System.out.println(e.getMessage());
        }
        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        
        root3D.getChildren().add(earth);
        
        //Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, pane, root3D); 
        
        /*PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D); 
        root3D.getChildren().add(light);*/
        
        SubScene subscene = new SubScene(root3D, 266, 249, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
	    pane.getChildren().addAll(subscene);


		
		textAnnee.textProperty().bindBidirectional(sliderAnnee.valueProperty(),new NumberStringConverter());
		btnPlay.setOnMouseClicked(event -> {
			System.out.println("play !");
		});
		
		btnPlay.setOnKeyPressed(event -> {
			System.out.println("play !");
		});
		
		sliderAnnee.valueProperty().addListener((obs, oldval, newVal) -> 
	    sliderAnnee.setValue(newVal.intValue()));
		
		

    }
}

