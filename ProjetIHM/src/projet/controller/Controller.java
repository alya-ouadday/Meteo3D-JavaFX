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
	
	Image imagePause = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/pauseBtn.PNG"), 30, 30, false, false);
	Image imagePlay = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/playBtn.PNG"),30, 30, false, false);

	private int annee;
	private boolean selected = false; 
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
		Group ville = new Group(); 
		earth.getChildren().add(ville); 
			
		Animation animation = new Animation(sliderAnnee, earth ); 
		Graphique graph = new Graphique(); 
		
		ModeQuadri.setEchelleCouleur();
		ModeHisto.setEchelleCouleur();
		HashMap<String, Zone> zones= terre.getListeZones();
		
		Group quadri = ModeQuadri.initQuadri(zones, annee); 
		//Group histos = ModeHisto.initHistos(zones, annee);//
		
		
		
		labelFor.textProperty().bind(animation.getSpeedForProp().asString());
		labelBack.textProperty().bind(animation.getSpeedBackProp().asString());
		
		btnQuadri.setOnAction(event -> {
			long startTime = System.nanoTime(); 
			modeVisualisation.getChildren().clear();
			modeVisualisation.getChildren().add(quadri); 

			long endTime   = System.nanoTime();
			double totalTime = (endTime - startTime) / 1000000000.0;
			System.out.println("temps d'execution : " + totalTime);
		});
        
		btnHisto.setOnAction(event -> {
			modeVisualisation.getChildren().clear();
			//modeVisualisation.getChildren().add(histos);//
			//ModeHisto.setModeHisto(terre, annee, modeVisualisation);
			ModeHisto.initHist(zones, annee, modeVisualisation);
		});
		textAnnee.textProperty().bindBidirectional(sliderAnnee.valueProperty(),new NumberStringConverter());

	
		
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
		/*
		sliderAnnee.setOnMouseDragged(event -> {
			modeVisualisation.getChildren().clear();
        	Double anneeDouble = sliderAnnee.getValue();
        	annee = anneeDouble.intValue();
        	sliderAnnee.setValue(annee);
			 
			if(btnQuadri.isSelected()) {
				ModeQuadri.updateQuadri(annee);
				modeVisualisation.getChildren().add(quadri); 
				//modeVisualisation.getChildren().clear();

			}
			
			if(btnHisto.isSelected()) {
				//ModeHisto.initHist(zones, annee, modeVisualisation);
				//(terre, annee, modeVisualisation);
				
				ModeHisto.updateHistos(annee);//
				System.out.println("je fais une update");
				modeVisualisation.getChildren().add(histos);
				System.out.println("je add mes histos au mode de visu");//
				//modeVisualisation.getChildren().clear();
				//System.out.println("je clear");
			}
		
		});*/
		
		sliderAnnee.valueProperty().addListener(new ChangeListener<Number>() 
		{
	        @Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
	        {
	        	modeVisualisation.getChildren().clear();
	        	annee = newValue.intValue();
	        	//sliderAnnee.setValue(annee);
				 
				if(btnQuadri.isSelected()) {
					selected = false; 
					ModeQuadri.updateQuadri(annee);
					modeVisualisation.getChildren().add(quadri); 
					//modeVisualisation.getChildren().clear();

				}
				
				if(btnHisto.isSelected()) {
					selected = false;
					ModeHisto.initHist(zones, annee, modeVisualisation);

					//(terre, annee, modeVisualisation);
					
					//ModeHisto.updateHistos(annee);//
					System.out.println("je fais une update pour l'annne:" + annee);
					//modeVisualisation.getChildren().add(histos);
	
					//modeVisualisation.getChildren().clear();
					
				}
			}
		});
		
		earth.setOnMouseMoved(event -> {
			if(!selected) {
			double lat  = (Math.toDegrees(Math.asin(-event.getY())) + 0.2); 
			textLat.setText("" + Math.round(lat) );
			double lon; 
			double x = event.getX(); 
			double y = event.getY(); 
			System.out.println(x + " " + y);
			System.out.println(event.getSceneX() + "," + event.getSceneY());
			lon = Math.toDegrees((Math.asin(-x))/(Math.cos(Math.toRadians(lat - 0.2)))) - 2.8; 
		
			 if(x <= 0 )
		        {
		            lon = Math.toDegrees((Math.asin(-x))/(Math.cos(Math.toRadians(lat - 0.2)))) - 2.8; 
		            		//(Math.atan(y/x) )* (180.0/Math.PI) ;
		        }
		        else if(x > 0 && y <= 0)
		        {
		            //lon = (Math.atan(y/x)  )* (180.0/Math.PI) ;
		        	lon = Math.toDegrees((Math.asin(-x))/(Math.cos(Math.toRadians(lat - 0.2)))+ Math.PI) - 2.8;
		        }
		        else
		        {
		            //lon =( Math.atan(y/x) ) * (180.0/Math.PI) ;
		        	lon = Math.toDegrees((Math.asin(-x))/(Math.cos(Math.toRadians(lat - 0.2)))) - 2.8;
		        }
			 textLong.setText("" + Math.round(lon));
			 
			}
		});
		
		earth.setOnMouseClicked(event -> {
			if(!selected) {
			ville.getChildren().clear();
			double x = event.getX(); 
			double y = event.getY();
			 Coordonnees.afficheZone(ville, (float)x, (float)y, (float)event.getZ());
			 double lat  = (Math.toDegrees(Math.asin(-event.getY())) + 0.2); 
				textLat.setText("" + Math.round(lat) );
				selected = true; }
			else {
				ville.getChildren().clear();
				selected = false;
			}
		});
		visible.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		        Parent root;
		        try {
		        	//if(zones.containsKey(textLat+","+textLong)) {
		            root = FXMLLoader.load(getClass().getResource("/projet/vues/Vues.fxml"));
		           // LineChart linechart = graph.getLineChart();
		       	 final NumberAxis xAxis = new NumberAxis(1880, 2020, 25);
			     final NumberAxis yAxis = new NumberAxis();
			     LineChart linechart = new LineChart(xAxis,yAxis);
			     xAxis.setLabel("Année");
			     yAxis.setLabel("Température");
			     linechart.setTitle("Anomalies de température");
		            linechart.getData().clear();
					 //XYChart.Series series= new XYChart.Series();
					 //series.setName("anomalies");
		            XYChart.Series serie= new XYChart.Series();
		            serie.setName("anomalie");
					 HashMap<Integer, Float> anomalies = terre.getZone(Integer.parseInt(textLat.getText()), Integer.parseInt(textLong.getText())).getAnomalies();
					 for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
						 if(entry.getValue() < Float.MAX_VALUE) {
							 serie.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
						 System.out.println(entry.getKey()+ ",test" + entry.getValue());
						 }
						 
						 else {
							 serie.getData().add(new XYChart.Data(entry.getKey(), 0));
						 }
					 }
					 System.out.println(serie.getData());
					linechart.getData().add(serie);
				
			
					 System.out.println(linechart.getData());
			 
		            Stage stage = new Stage();
		            stage.setTitle("My New Stage Title");
		            //stage.setScene(new Scene(root, 450, 450));
		          
		          //  graph.updateValues(terre.getZone(Integer.parseInt(textLat.getText()), Integer.parseInt(textLong.getText())).getAnomalies());
		           //Scene scene =  new Scene(linechart,400,400);
		            stage.setScene(new Scene(linechart,400,400));
		            stage.show();
		
		            // Hide this current window (if this is what you want)
		            //((Node)(event.getSource())).getScene().getWindow().hide();
		        	//}
		        }
		        catch (IOException e) {
		            e.printStackTrace();
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
	
	public RadioButton visibleButton() {
		return visible; 
	}
	
}

