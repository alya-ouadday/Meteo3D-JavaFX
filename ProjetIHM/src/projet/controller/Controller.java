package projet.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
	
	
	Image imagePause = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/pauseBtn.PNG"), 30, 30, false, false);
	Image imagePlay = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/playBtn.PNG"),30, 30, false, false);

	private int annee;
	private boolean selected = false; 
	private DonneesPlanete terre;
	private boolean menuOpen = false; //
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

		menu.setVisible(false); 
		annee = 1880;
		Group root3D = new Group();
		Group earth = initializeEarth(420, 388, root3D);
		
		Group modeVisualisation = new Group(); 
		earth.getChildren().add(modeVisualisation);
		
		Group ville = new Group(); 
		earth.getChildren().add(ville);
	
		
		//mnemonicParsing="false" 
			
		Animation animation = new Animation(sliderAnnee, earth ); 
		//Graphique graph = new Graphique(); 
		
		ModeQuadri.setEchelleCouleur();
		ModeHisto.setEchelleCouleur();
		HashMap<String, Zone> zones= terre.getListeZones();
		
		
		Group quadri = ModeQuadri.initQuadri(zones, annee); 
		Group histos = ModeHisto.initHistos(zones, annee);//
		earth.getChildren().add(quadri);
		
		
		
	
		
		
		
		labelFor.textProperty().bind(animation.getSpeedForProp().asString());
		labelBack.textProperty().bind(animation.getSpeedBackProp().asString());
		
		btnQuadri.setOnAction(event -> {
			if(btnQuadri.isSelected()) {
			btnHisto.setSelected(false);
			modeVisualisation.getChildren().clear();
			ModeQuadri.updateQuadri(annee);
			echelleQuadri.setVisible(true);
			echelleHisto.setVisible(false);}
			else {
				ModeQuadri.hideQuadri();
				echelleQuadri.setVisible(false);

			}
		});
        
		btnHisto.setOnAction(event -> {
			if(btnHisto.isSelected()) {
			ModeQuadri.hideQuadri();
			btnQuadri.setSelected(false);
			modeVisualisation.getChildren().clear();
			modeVisualisation.getChildren().add(histos);
			echelleQuadri.setVisible(false);
			echelleHisto.setVisible(true);}//
			//ModeHisto.initHist(zones, annee, modeVisualisation);
			else {
				modeVisualisation.getChildren().clear();
				echelleHisto.setVisible(false);
			}
		});
		textAnnee.textProperty().bindBidirectional(sliderAnnee.valueProperty(),new NumberStringConverter());
		titreAnnee.textProperty().bind(sliderAnnee.valueProperty().asString());
	
		
		btnPlay.setOnAction(event ->     {  
		if(!animation.getPlay()) {
			//animation.getTimer().start();
			animation.setPlay(btnPlay); 
			//btnPlay.setGraphic(new ImageView(imagePause));
		}
        
		else {
			//animation.getTimer().stop();
			animation.setPause(btnPlay);
			//btnPlay.setGraphic(new ImageView(imagePlay));
		}
		});
		
		btnFor.setOnAction(event ->{
			animation.plusVite();
		});
		
		btnBack.setOnAction(event ->{
			animation.moinsVite();
		
		});
		
		btnStop.setOnAction(event -> {
			//animation.getTimer().stop();
			animation.setPause(btnPlay);
			animation.reset();
			sliderAnnee.setValue(1880);
			
			
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
					//selected = false; 
					ModeQuadri.updateQuadri(annee);
				}
				
				if(btnHisto.isSelected()) {
					//selected = false;
					//ModeHisto.initHist(zones, annee, modeVisualisation)
					ModeHisto.updateHistos(annee);//
					modeVisualisation.getChildren().add(histos);//
	
					
				}
			}
		});
		
		earth.setOnMouseMoved(event -> {
			if(!selected) {
				try {
				System.out.println(event.getPickResult().getIntersectedNode());
				selectedQuadri = (MeshView) event.getPickResult().getIntersectedNode();
					textLat.setText("" + ModeQuadri.getQuadris().get(selectedQuadri).getLat() );
					textLong.setText(""+ ModeQuadri.getQuadris().get(selectedQuadri).getLon());
				}catch(Exception e) {
					
				}
			}
		});
		
		earth.setOnMouseClicked(event -> {
			System.out.println(event.getPickResult().getIntersectedNode());
			//selectedQuadri (MeshView) event.getPickResult().getIntersectedNode();
			int lat = ModeQuadri.getQuadris().get(selectedQuadri).getLat();
			int lon = ModeQuadri.getQuadris().get(selectedQuadri).getLon();
			//textLat.setText("" + lat);
			//textLong.setText(""+ ModeQuadri.getQuadris().get(quadriSelect).getLon());
			
			if((!selected)&& (event.getClickCount() == 2)) {
			ville.getChildren().clear();
			System.out.println(lat + " " + lon);
			//ville.toBack();
			 Coordonnees.afficheZone(ville, lat, lon); 
			 //ville.toBack(); 
			 
			 selected = true; }
			else if (selected && (event.getClickCount() == 2)) {
				ville.getChildren().clear();
				selected = false;
			}
		});
	
		btnGraph.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		    	if(selectedQuadri != null) {
		    	Zone zone = ModeQuadri.getQuadris().get(selectedQuadri);
		        HashMap<Integer, Float> anomalies = zone.getAnomalies();
				Graphique graph = new Graphique(anomalies); 
				LineChart linechart = graph.getLineChart(); 
				Stage stage = new Stage();
				stage.setTitle("Evolution des anomalies pour la zone "+ zone.toString()+ " entre 1880 et 2020");

				stage.setScene(new Scene(linechart,700,400));
				stage.show();
		    	}	
		    }

		});
		
		textLat.setOnKeyReleased(event -> {
			ville.getChildren().clear();
			selected = true; 
			if(!textLong.getText().isEmpty()) {
				try {
					int lat = Integer.parseInt(textLat.getText());
					int lon = Integer.parseInt(textLong.getText());
					Coordonnees.afficheZone(ville, lat, lon);
				}catch(NumberFormatException e) {
					textLat.clear();
					
				}
			}
		});
		
		textLong.setOnKeyReleased(event -> {
			selected = true; 
			ville.getChildren().clear();
			if(!textLat.getText().isEmpty()) {
				try {
					int lat = Integer.parseInt(textLat.getText());
					int lon = Integer.parseInt(textLong.getText());
					Coordonnees.afficheZone(ville, lat, lon);
				}catch(NumberFormatException e) {
					textLong.clear();
					
				}
			}
		});
		
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
	
	public void setLight(Group root3D, int x, int y, int z) {
		
		/*PointLight light = new PointLight(Color.WHITE);
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
	
	public boolean getMenu() {
		return menuOpen; 
	}
	public void setSize(Stage stage) {
		stage.setWidth(830);
	}
	
}

