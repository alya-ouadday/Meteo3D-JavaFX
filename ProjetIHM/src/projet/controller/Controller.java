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
import javafx.scene.input.KeyEvent;
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


/**
 * 
 * Controller fait le lien entre le Mod�le DonneesPlanete et le fichier FXML qui est la Vue
 * @author BEN OUADDAY 
 *
 */

public class Controller {
	/**
	 * TextFields affichant les latitude/longitude d'une zone
	 */
	@FXML
	private TextField textLat;
	@FXML
	private TextField textLong;
	
	/**
	 * Message d'erreur si latitude/longitudes saisies incorrectes
	 */
	@FXML
	private Label erreurCoor; 
	/**
	 * label d'indication pour selectionner une zone du globe 
	 */
	@FXML
	private Label de; 
	/**
	 * TextField affichant l'ann�e courante et permettant � l'utilisateur de la saisir 
	 */
	@FXML
	private TextField textAnnee; 
	/**
	 * RadioButton pour choisir son mode de Visualisation 
	 */
	@FXML
	private RadioButton btnQuadri; 
	@FXML
	private RadioButton btnHisto;
	@FXML
	private RadioButton btnAucun;
	
	/**
	 * Button pour r�gler l'animation du globe
	 */
	@FXML
	private  Button btnPlay; 	
	@FXML
	private  Button btnStop;  
	@FXML
	private  Button btnBack;
	@FXML
	private  Button btnFor;
	
	/**
	 * Label affichant la vitesse de l'animation 
	 */
	@FXML
	private  Label labelBack;
	@FXML
	private  Label labelFor; 
	
	/**
	 * Button pour afficher un graphique 
	 */

	@FXML
	private Button btnGraph; 
	/**
	 * Label affichant l'ann�e courante dans le titre de l'application 
	 */
	@FXML
	private Label titreAnnee; 
	/**
	 * Slider permettant de changer les ann�es 
	 */
	@FXML
	private Slider sliderAnnee; 
	/**
	 * Pane contenant le globe terrestre 3D 
	 */
	@FXML
	private Pane pane; 
	/**
	 * AnchorPane contenant l'�chelle de couleur des modes de visualisation 
	 */
	@FXML
	private AnchorPane echelleQuadri;
	@FXML
	private AnchorPane echelleHisto; 
	/**
	 * AnchorPane contenant le menu 
	 */
	@FXML
	private AnchorPane menu; 
	/**
	 * Button pour afficher le menu 
	 */
	@FXML
	private Button btnMenu; 
	/**
	 * l'ann�e courante
	 */
	private int annee;
	/**
	 * true si une zone est selectionn�e, false sinon 
	 */
	private boolean selected = false; 
	/**
	 * Le Mod�le contenant les donn�es � utiliser 
	 */
	private DonneesPlanete terre;
	/**
	 * true si le menu est ouvert, false sinon 
	 */
	private boolean menuOpen = false; 
	/**
	 * MeeshView selectionn� sur le globe 
	 */
	private MeshView selectedQuadri = null; 
	
	/**
	 * Constructeur
	 * fait appel � la focntion RecuperationDonnees 
	 */
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
		
		Group root3D = new Group();//Group qui contient la Terre 
		Group earth = initializeEarth(420, 388, root3D); //On ajoute la terre au root3D 
		
		Group modeVisualisation = new Group(); //Group qui contient les histogrammes 
		earth.getChildren().add(modeVisualisation);//on ajoute le group au group qui contient la etrre 
		
		Group ville = new Group(); //Group qui contient la ville � afficher 
		earth.getChildren().add(ville);
			
		Animation animation = new Animation(sliderAnnee, earth ); //On instancie l'Animation qui
		//contient AnimationTimer pour animer la Terre 
		
		ModeQuadri.setEchelleCouleur(); //on instancie l'�chelle de couleurs des quadrilat�res 
		ModeHisto.setEchelleCouleur();  // celle des histogrammes 
		
		HashMap<String, Zone> zones= terre.getListeZones(); //on r�cup�re l'ensembles 
		//des zones connues du fichier 
		
		
		Group quadri = ModeQuadri.initQuadri(zones, annee); // on instancie les groupes qui
		//contiennent les histogrammes et les quadrilat�res 
		Group histos = ModeHisto.initHistos(zones, annee);
		
		earth.getChildren().add(quadri);
		
//-------------------MENU-----------------------
		
		btnMenu.setOnAction(event -> {
			if(!menuOpen) {
				menuOpen = true; 
				menu.setVisible(true); //on rend le menu visible 
			}
			else {
				menuOpen = false; 
				menu.setVisible(false); 
			}
		});
		
// ------------MODE VISUALISATION----------------
		
		btnQuadri.setOnAction(event -> {

			modeVisualisation.getChildren().clear(); //on enl�ve les histogrames 
			echelleHisto.setVisible(false);//on cache l'�chelle des histogrammes 
			
			ModeQuadri.updateQuadri(annee); //on change le material des quadrilateres 
			//en fonction de leur anommalie l'annee courante 
			echelleQuadri.setVisible(true); //on montre l'echelle des quadrilat�re 
			
		
		});
        
		btnHisto.setOnAction(event -> {
			
			ModeQuadri.hideQuadri(); //on cache les quadrilat�res et leur echelle 
			echelleQuadri.setVisible(false);
			
			ModeHisto.updateHistos(annee);//on met � jour les histogrammes et les ajoute 
			//au mode de visualisation 
			modeVisualisation.getChildren().add(histos);

			echelleHisto.setVisible(true);//on montre l'�chelle des histogrammes 

		});
		
		btnAucun.setOnAction(event ->{
			ModeQuadri.hideQuadri(); //on cache les quadrilat�res
			modeVisualisation.getChildren().clear(); //on efface les histogrammes 
			//on cache les �chelles de couleur 
			echelleQuadri.setVisible(false);
			echelleHisto.setVisible(false);
		});
		
//----------------------------ANNEE-----------------------
		sliderAnnee.valueProperty().addListener(new ChangeListener<Number>() 
		{
	        @Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
	        {
	        	annee = newValue.intValue();
	        	sliderAnnee.setValue(annee); //on affecte au slider une valeur enti�re 
	        	
	        	
				if(btnQuadri.isSelected()) { //si on est en mode quadrilat�re 
					ModeQuadri.updateQuadri(annee);// on met � jour les material chaque ann�e 
				}
				
				if(btnHisto.isSelected()) {
					ModeHisto.updateHistos(annee);	//on met � jour taille et material des histogrammes
				}
				
			}
		});
		
		//on fait en sorte de ne prendre que les valeurs num�riques entr�es par l'utilisteur 
		textAnnee.addEventFilter(KeyEvent.KEY_TYPED , isNumeric());
		
		//on relie de fa�on bidirectionnelle le textField ann�e au slider 
		textAnnee.textProperty().bindBidirectional(sliderAnnee.valueProperty(),new NumberStringConverter());
	
		//on relie le titre de l'application � la valeur du slider 
		titreAnnee.textProperty().bind(textAnnee.textProperty());	
				
	
//---------------ANIMATION--------------------------
	
		btnPlay.setOnAction(event ->     {  
			if(!animation.getPlay()) { // si on est pas deja en mode play 
				animation.setPlay(btnPlay); //on met en marche l'application 
			}
	        
			else {
				animation.setPause(btnPlay); // sinon on l'a met en pause 
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
			animation.reset(); // on r�initialise les param�tres de l'animation
			sliderAnnee.setValue(1880);// on remet le slider au d�but
		
		});
		
		//on relie les labels qui indique les vitesses aux valeurs de vitesse de l'animation 
		labelFor.textProperty().bind(animation.getSpeedForProp().asString()); 
		labelBack.textProperty().bind(animation.getSpeedBackProp().asString());


	
//-----------------LATITUDE - LONGITUDE ---------------------------
		
		earth.setOnMouseMoved(event -> {// si on passe la souris sur le globe 
			if(!selected) {// si on a pas selectionn� un Zone 
				try {
					// on r�cup�re le quadrilat�re sur lequel on pointe 
					selectedQuadri = (MeshView) event.getPickResult().getIntersectedNode();
					
					//on modifie les champs latitude/longitude avec celles du quadrilat�re selectionn� 
					textLat.setText("" + ModeQuadri.getQuadris().get(selectedQuadri).getLat() );
					textLong.setText(""+ ModeQuadri.getQuadris().get(selectedQuadri).getLon());
					
				}catch(Exception e) {
					
				}
		   }
		});
		
		earth.setOnMouseClicked(event -> { // si on clique sur le globe 
			if(selectedQuadri != null) { // si on a bien cibl� un quadrilat�re 
				
				//on r�cup�re ses latitude longitude 
				int lat = ModeQuadri.getQuadris().get(selectedQuadri).getLat();
				int lon = ModeQuadri.getQuadris().get(selectedQuadri).getLon();
				
				//si on a pas d�j� selectionn� une zone et on a double-cliqu� 
				if(!selected &&(event.getClickCount() == 2)) {
					
					//on affiche la zone selectionn�e par une sph�re 
					ville.getChildren().clear(); 
					Coordonnees.afficheZone(ville, lat, lon); 
					selected = true; // on a selectionn� 
					// on notifie l'utilisateur qu'il faut double-cliquer pour DEselectionner 
					de.setVisible(true); 
				}
				else if (selected && (event.getClickCount() == 2)) {
					//si on a d�j� selectionn� une zone et on double clique 
					ville.getChildren().clear();// on efface la sph�re 
					selected = false;
					de.setVisible(false);
				}
			}
		});
		
		textLat.setOnKeyTyped(event -> {// si on tape dans la zone latitude 
			ville.getChildren().clear();
			selected = true; // on est en mode select 
				try {
					//on r�cup�re la valeur de latitude 
					int lat = Integer.parseInt(textLat.getText());
					if(lat > 90 || lat < -90 ) { // si elle ne correspond pas aux bonnes valeurs 
						//on affiche une erreur 
						erreurCoor.setVisible(true);
					}
					else {
						
						erreurCoor.setVisible(false);
						if(!textLong.getText().isEmpty()) {	// si le text Longitude n'est pas vide 			
							int lon = Integer.parseInt(textLong.getText());
							Coordonnees.afficheZone(ville, lat, lon);// on affiche la zone qui lui correspond 
						}
					}
				}catch(NumberFormatException e) {
					//si la latitude n'est pas un chiffre mais un "-" n�gatif 
					if(!textLat.getText().equals("-")) {
					textLat.clear();// on efface le texte
					}
					
				}	
		});
		
		textLong.setOnKeyTyped(event -> {
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
					if(!textLong.getText().equals("-"))
					textLong.clear();
					
				}	
		});
		
		//le mode select s'enl�ve si l'utilisateur efface le texte des champs latitude/longitude 
		textLat.addEventFilter(KeyEvent.KEY_RELEASED, deselect());
		textLong.addEventFilter(KeyEvent.KEY_RELEASED, deselect());
	
//--------------------GRAPHIQUE----------------------------
		
		btnGraph.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		    	
		    		//on r�cup�re les valeurs des latitudes/longitudes 
		    		int lat = Integer.parseInt(textLat.getText());
		    		int lon = Integer.parseInt(textLong.getText()); 
		    		
		    		//on calcule la Zone connue la plus proche 
			    	Zone zone = Coordonnees.zonePlusproche(terre, lat, lon);
			    	//on cr�e le graphique gr�ce aux donn�es de cette zone 
			    	
					Graphique graph = new Graphique(zone); 
					LineChart linechart = graph.getLineChart(); 
					Stage stage = new Stage();
					stage.setTitle("Evolution des anomalies pour la zone "+ zone.toString()+ " entre 1880 et 2020");
					
					//on cr�e une sc�ne et affiche le stage 
					stage.setScene(new Scene(linechart,700,400));
					stage.show();
		    }	
		});
		
		// on notifie l'utilisateur de la prise en compte de son appuie 
		//sur le bouton par changement d'opacit� 
		btnGraph.setOnMouseReleased(event -> {
			btnGraph.setOpacity(1);
		});
		btnGraph.setOnMousePressed(event -> {
			if(selectedQuadri != null)
			btnGraph.setOpacity(0.5);
		});
		
    }
	
	/** 
     * importe la terre et l'ajoute dans un Group 
	 * @param root3D dans lequel sera plac�e la terre 
	 * 
	 */
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
	
	/** 
     * met en place la lumi�re dans un groupe 
	 * @param root3D o� se trouve la terre et o� on ajoute la lumi�re 
	 * 
	 */
	public void setLight(Group root3D) {

        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);
	}
	
	/** 
     * met en place la cam�ra dans un groupe  
	 *@param root3D o� se trouve la terre et o� on ajoute la lumi�re 
	 *@return camera ajout�e � root3D
	 */
	public PerspectiveCamera setCamera(Group root3D) {
	    PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, pane, root3D); 
        return camera; 
	}
	
	/** 
     * met en place l'ensemble des �l�ments (camera, lumi�re, terre ) dans le groupe 
	 * @param xSubscene taille x de la subscene 
	 * @param ySubscene taille y de la subscene 
	 * @param  root3D 
	 * 				  Group dans lequel on ajoute l'ensemble des �l�ments 
	 * @return root3D 
	 * 
	 */
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
	
	//EventHandler pour filter seulement les caract�res num�riques saisis dans le textField Ann�e 
	   public EventHandler<KeyEvent> isNumeric() {
	        return new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent e) {

	                if(!e.getCharacter().matches("[0-9.]")){ 
	                    e.consume();
	                }
	            }
	        };
	    }
	   
	   //EventHandler pour enlever le mode select lorsque l'utilisateur efface les champs
	   //latitude/longitude 
	   public EventHandler<KeyEvent> deselect() {
	        return new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent e) {

	            	if(textLat.getText().isEmpty() && textLong.getText().isEmpty()) {
	    				selected = false; 
	    			}
	            }
	        };
	    }

	
}

