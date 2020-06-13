package projet.controller;

import java.io.File;
import java.net.URL;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
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
import javafx.scene.image.Image;
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
import projet.vues.Animation;
import projet.vues.CameraManager;
import projet.vues.EchelleCouleur;
import projet.vues.ModeHisto;
import projet.vues.ModeQuadri;
import tutoriel.Earth.*;

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
	
	Image imagePause = new Image(getClass().getResourceAsStream("/src/projet/vues.vuesBoutons/pauseBtn.PNG"));

	private int annee;
	
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
		
		annee = 1880;
		Group root3D = new Group();
		Group earth = initializeEarth(363, 350, root3D);
		Group modeVisualisation = new Group(); 
		earth.getChildren().add(modeVisualisation);
		
		Animation animation = new Animation(sliderAnnee, earth ); 
		//final long startNanoTime = System.nanoTime();
		
		
	     /*  AnimationTimer timer = new AnimationTimer() {
	    	   @Override
		        public void handle(long currentNanoTime) {
		        	double t = (currentNanoTime - startNanoTime)/ 1000000000.0;
		        	double rotationSpeed = 100; 
		        	earth.setRotationAxis(new Point3D(0,1,0));
		        	earth.setRotate(rotationSpeed*t); 
		        	if(rotationSpeed*t / (360*angle) >= 1) {
		            if(annee < 2020) {
		        	sliderAnnee.setValue(sliderAnnee.getValue()+1);
		        	angle ++; 
		            } 
		            else {
		            	
		            }
		        	}
		        }
	        };*/
	        
		EchelleCouleur.setEchelleQuadri();
		Group quadri = ModeQuadri.initQuadri(terre.getListeZones(), annee); 
		//modeVisualisation.getChildren().add(quadri); 
		
		
		btnQuadri.setOnAction(event -> {
			long startTime = System.nanoTime(); 
			modeVisualisation.getChildren().clear();
			modeVisualisation.getChildren().add(quadri); 
			//ModeQuadri.setModeQuadri(terre, annee, modeVisualisation);
			long endTime   = System.nanoTime();
			double totalTime = (endTime - startTime) / 1000000000.0;
			System.out.println("temps d'execution : " + totalTime);
		});
        
		btnHisto.setOnAction(event -> {
			modeVisualisation.getChildren().clear();
			ModeHisto.setModeHisto(terre, annee, modeVisualisation);
		});
		textAnnee.textProperty().bindBidirectional(sliderAnnee.valueProperty(),new NumberStringConverter());
		/*
		sliderAnnee.setOnMouseReleased(event -> {
			Double anneeDouble = sliderAnnee.getValue(); 
			annee= anneeDouble.intValue(); 
			modeVisualisation.getChildren().clear();
			
			if(btnQuadri.isSelected()) {
				ModeQuadri.setModeQuadri(terre, annee, modeVisualisation);
			}
			if(btnHisto.isSelected()) {
				ModeHisto.setModeHisto(terre, annee, modeVisualisation);
			}
			
		});*/
		
	
		
		btnPlay.setOnAction(event ->     {  
		if(!animation.getPlay()) {
			animation.getTimer().start();
			animation.setPlay(); 
		}
        
		else {
			animation.getTimer().stop();
			animation.setPause();
		}
		});
		
		sliderAnnee.valueProperty().addListener((obs, oldval, newVal) -> 
	    sliderAnnee.setValue(newVal.intValue())
	    
				);
		
		sliderAnnee.valueProperty().addListener(new ChangeListener<Number>() 
		{
	        @Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
	        {
	        	modeVisualisation.getChildren().clear();
				annee = newValue.intValue(); 
				if(btnQuadri.isSelected()) {
					ModeQuadri.updateQuadri(annee);
					modeVisualisation.getChildren().add(quadri); 
					//ModeQuadri.setModeQuadri(terre, annee, modeVisualisation);
				}
				
				if(btnHisto.isSelected()) {
					ModeHisto.setModeHisto(terre, annee, modeVisualisation);
				}
			}
		});

    }
	
	public Group loadEarth(Group root3D) {
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
	        return earth;
	        
	        
	}
	
	public void setLight(Group root3D, int x, int y, int z) {
		/*
		PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(x); //-180
        light.setTranslateY(y); //-75
        light.setTranslateZ(z); //-120
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);*/        
        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);
	}
	
	public PerspectiveCamera setCamera(Group root3D) {
	    PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, pane, root3D); 
        return camera; 
	}
	
	public Group initializeEarth(int xSubscene, int ySubscene, Group root3D) {
		//Create a graph scene root for the 3D content
        // Load geometry 
        Group earth = loadEarth(root3D); 
        // Add point light
        setLight(root3D, -180, -90, -120);
        //add group Camera 
        PerspectiveCamera camera = setCamera(root3D); 
        //create subScene 363 50
        SubScene subscene = new SubScene(root3D, xSubscene, ySubscene, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
	    pane.getChildren().addAll(subscene);
	    return earth; 
	}
	
	
}

