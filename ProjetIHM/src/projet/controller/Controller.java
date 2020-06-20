package projet.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.animation.AnimationTimer;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import projet.data.DonneesPlanete;
import projet.data.RecuperationDonnees;
import projet.data.Zone;
import projet.vues.Animation;
import projet.vues.CameraManager;
import projet.vues.Coordonnees;
import projet.vues.EchelleCouleur;
import projet.vues.Graphique;
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
	private Label erreurCoor; 
	@FXML
	private Label de; 
	@FXML
	private TextField textAnnee; 
	@FXML
	private RadioButton btnQuadri; 
	@FXML
	private RadioButton btnHisto;
	@FXML
	private RadioButton btnAucun;
	@FXML
	private  Button btnPlay;  
	@FXML
	private  Button btnBack;
	@FXML
	private  Label labelBack;
	@FXML
	private  Label labelFor; 
	@FXML
	private  Button btnFor; 
	@FXML
	private  Button btnStop; 
	@FXML
	private Button btnGraph; 
	@FXML
	private Label titreAnnee; 
	@FXML
	private Slider sliderAnnee; 
	@FXML
	private Pane pane; 
	@FXML
	private AnchorPane echelleQuadri;
	@FXML
	private AnchorPane echelleHisto; 

	@FXML
	private AnchorPane menu; 
	@FXML
	private Button btnMenu; 
	private int annee;
	private boolean selected = false; 
	private DonneesPlanete terre;
	private boolean menuOpen = false; 
	private MeshView selectedQuadri = null; 
	
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
		Group earth = initializeEarth(420, 388, root3D);
		
		Group modeVisualisation = new Group(); 
		earth.getChildren().add(modeVisualisation);
		
		Group ville = new Group(); 
		earth.getChildren().add(ville);
			
		Animation animation = new Animation(sliderAnnee, earth ); 
		
		ModeQuadri.setEchelleCouleur();
		ModeHisto.setEchelleCouleur();
		
		HashMap<String, Zone> zones= terre.getListeZones();
		
		
		Group quadri = ModeQuadri.initQuadri(zones, annee); 
		Group histos = ModeHisto.initHistos(zones, annee);
		
		earth.getChildren().add(quadri);

		labelFor.textProperty().bind(animation.getSpeedForProp().asString());
		labelBack.textProperty().bind(animation.getSpeedBackProp().asString());
		
		btnQuadri.setOnAction(event -> {

				modeVisualisation.getChildren().clear();
				ModeQuadri.updateQuadri(annee);
				echelleQuadri.setVisible(true);
				echelleHisto.setVisible(false);
		
		});
        
		btnHisto.setOnAction(event -> {
				ModeQuadri.hideQuadri();
				modeVisualisation.getChildren().clear();
				modeVisualisation.getChildren().add(histos);
				echelleQuadri.setVisible(false);
				echelleHisto.setVisible(true);

		});
		
		btnAucun.setOnAction(event ->{
			ModeQuadri.hideQuadri();
			modeVisualisation.getChildren().clear();
			echelleQuadri.setVisible(false);
			echelleHisto.setVisible(false);
		});
		textAnnee.setOnKeyTyped(event -> {
			try {
				Integer.parseInt(textAnnee.getText());
			}catch(Exception e) {
				textAnnee.clear();
				System.out.println("je passe ici setonkeytyped");
			}
		});
		
		try {
		textAnnee.textProperty().bindBidirectional(sliderAnnee.valueProperty(),new NumberStringConverter());
		}catch(Exception e) {
			textAnnee.clear();
			System.out.println("je passe ici binding");
		}
		
		titreAnnee.textProperty().bind(textAnnee.textProperty());
	
	
		btnPlay.setOnAction(event ->     {  
			if(!animation.getPlay()) {
				animation.setPlay(btnPlay); 
				btnFor.setDisable(false);
				btnBack.setDisable(false);
			}
	        
			else {
				animation.setPause(btnPlay);
				btnFor.setDisable(true);
				btnBack.setDisable(true);
			}
		});
		
		btnFor.setOnAction(event ->{
			animation.plusVite();
		});
		
		btnBack.setOnAction(event ->{
			animation.moinsVite();
		
		});
		
		btnStop.setOnAction(event -> {
			animation.setPause(btnPlay);
			animation.reset();
			sliderAnnee.setValue(1880);
			btnFor.setDisable(true);
			btnBack.setDisable(true);
		
		});
		

		sliderAnnee.valueProperty().addListener(new ChangeListener<Number>() 
		{
	        @Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
	        {
	        	modeVisualisation.getChildren().clear();
	        	ModeQuadri.hideQuadri();
	        	annee = newValue.intValue();
	        	
				 
				if(btnQuadri.isSelected()) {
					ModeQuadri.updateQuadri(annee);
				}
				
				if(btnHisto.isSelected()) {
					ModeHisto.updateHistos(annee);
					modeVisualisation.getChildren().add(histos);		
				}
				sliderAnnee.setValue(annee);
			}
		});
		
		earth.setOnMouseMoved(event -> {
			if(!selected) {
				try {
				selectedQuadri = (MeshView) event.getPickResult().getIntersectedNode();
					textLat.setText("" + ModeQuadri.getQuadris().get(selectedQuadri).getLat() );
					textLong.setText(""+ ModeQuadri.getQuadris().get(selectedQuadri).getLon());
				}catch(Exception e) {
					
				}
		   }
		});
		
		earth.setOnMouseClicked(event -> {
			int lat = ModeQuadri.getQuadris().get(selectedQuadri).getLat();
			int lon = ModeQuadri.getQuadris().get(selectedQuadri).getLon();

			if(!selected &&(event.getClickCount() == 2)) {
				ville.getChildren().clear();
				System.out.println(lat + " " + lon);
				Coordonnees.afficheZone(ville, lat, lon); 
				selected = true;
				de.setVisible(true);
			}
			else if (selected && (event.getClickCount() == 2)) {
				ville.getChildren().clear();
				selected = false;
				de.setVisible(false);
			}
		});
	
		btnGraph.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		    	if(selectedQuadri != null) {
		    		
			    	Zone zone = ModeQuadri.getQuadris().get(selectedQuadri);
					Graphique graph = new Graphique(zone); 
					LineChart linechart = graph.getLineChart(); 
					Stage stage = new Stage();
					stage.setTitle("Evolution des anomalies pour la zone "+ zone.toString()+ " entre 1880 et 2020");
					stage.setScene(new Scene(linechart,700,400));
					stage.show();
		    	}	
		    }

		});
		
		btnGraph.setOnMouseReleased(event -> {
			btnGraph.setOpacity(1);
		});
		btnGraph.setOnMousePressed(event -> {
			if(selectedQuadri != null)
			btnGraph.setOpacity(0.5);
		});
		
		textLat.setOnKeyReleased(event -> {
			ville.getChildren().clear();
			selected = true; 
				try {
					int lat = Integer.parseInt(textLat.getText());
					if(lat > 90 || lat < -90 ) {
						erreurCoor.setVisible(true);
					}
					else {
						
						erreurCoor.setVisible(false);
						if(!textLong.getText().isEmpty()) {				
							int lon = Integer.parseInt(textLong.getText());
							Coordonnees.afficheZone(ville, lat, lon);
						}
					}
				}catch(NumberFormatException e) {
					if(textLat.getText() != "-")
					textLat.clear();
					
				}	
		});
		
		textLong.setOnKeyReleased(event -> {
			ville.getChildren().clear();
			selected = true; 
				try {
					int lon= Integer.parseInt(textLong.getText());
					if(lon > 180 || lon < -180 ) {
						erreurCoor.setVisible(true);
					}
					else {
						erreurCoor.setVisible(false);
						if(!textLat.getText().isEmpty()) {				
							int lat = Integer.parseInt(textLat.getText());
							Coordonnees.afficheZone(ville, lat, lon);
						}
					}
				}catch(NumberFormatException e) {
					if(textLong.getText() != "-")
					textLong.clear();
					
				}	
		});
		/*
		textLong.setOnKeyReleased(event -> {
			selected = true; 
			ville.getChildren().clear();
			if(!textLat.getText().isEmpty()) {
				try {
					int lat = Integer.parseInt(textLat.getText());
					int lon = Integer.parseInt(textLong.getText());
					Coordonnees.afficheZone(ville, lat, lon);
				}catch(NumberFormatException e) {
					if(textLong.getText() != "-")
					textLong.clear();
					
				}
			}
		});*/
		
		btnMenu.setOnAction(event -> {
			if(!menuOpen) {
				menuOpen = true; 
				menu.setVisible(true);
			}
			else {
				menuOpen = false; 
				menu.setVisible(false);
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
	
	public void setLight(Group root3D) {

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
        setLight(root3D);
        //add group Camera 
        PerspectiveCamera camera = setCamera(root3D); 
        //create subScene 363 50
        SubScene subscene = new SubScene(root3D, xSubscene, ySubscene, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
	    pane.getChildren().addAll(subscene);
	    return earth; 
	}
	

	
}

