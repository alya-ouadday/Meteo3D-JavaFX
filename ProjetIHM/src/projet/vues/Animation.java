package projet.vues;

import javafx.animation.AnimationTimer;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animation  {

	private Image imagePause = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/btnPause.PNG"), 41, 38, false, false);
	private Image imagePlay = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/btnPlay.PNG"), 41, 38, false, false);
	private  int rotationSpeed ; 
	private float multipleSpeed;
	private FloatProperty speedForProp;
	private FloatProperty speedBackProp;
	private static final int SPEEDMAX = 500;
	private static final int SPEEDMIN = 10; 
	private static final int INITIAL_SPEED = 100; 
	private float angle ; 
	private AnimationTimer timer; 
	private boolean play; 
	
	public Animation(Slider sliderAnnee, Group earth) {
		  rotationSpeed = INITIAL_SPEED ; 
	      multipleSpeed = 1; 
	      speedForProp = new SimpleFloatProperty(multipleSpeed + 0.25f); 
	      speedBackProp = new SimpleFloatProperty(multipleSpeed - 0.25f); 
	      angle = 1; 
	      play = false; 
	      final long startNanoTime = System.nanoTime();; 
		
	       timer = new AnimationTimer() {
	    	   @Override
		        public void handle(long currentNanoTime) {
		        	double t = (currentNanoTime - startNanoTime)/ 1000000000.0; 
		        	earth.setRotationAxis(new Point3D(0,1,0));
		        	earth.setRotate(rotationSpeed*t); 
		        	if(rotationSpeed*t / (360*angle) >= 1 ) {
			            if(sliderAnnee.getValue() < 2020) {
			        	sliderAnnee.setValue(sliderAnnee.getValue()+1);
			        	angle ++; 
			            }
			            else {
			            	timer.stop();
			            }
		        	}
		        }
	        };   
	}



	public void setRotationSpeed(int newSpeed) {
		rotationSpeed = newSpeed; 
	}
	
	public void plusVite() {
		if(rotationSpeed < SPEEDMAX-25) {
		rotationSpeed += 25; 
		multipleSpeed += 0.25; 
		speedForProp.setValue(multipleSpeed + 0.25);
		speedBackProp.setValue(multipleSpeed - 0.25);
		angle = angle + (25*angle)/rotationSpeed;
		}

	}
	
	public void moinsVite() {
		if(rotationSpeed > SPEEDMIN+25) {
		rotationSpeed -= 25; 
		multipleSpeed -= 0.25; 
		speedForProp.setValue(multipleSpeed + 0.25);
		speedBackProp.setValue(multipleSpeed - 0.25);
		}
	}
	
	public void reset() {
		multipleSpeed = 1;
		rotationSpeed = INITIAL_SPEED; 
		speedForProp.setValue(multipleSpeed + 0.25);
		speedBackProp.setValue(multipleSpeed - 0.25);
	}
	
	public int getRotationSpeed() {
		return rotationSpeed;
	}
	
	public float getMultipleSpeed() {
		return multipleSpeed; 
	}
	public FloatProperty getSpeedForProp() {
		return speedForProp; 
	}
	
	public FloatProperty getSpeedBackProp() {
		return speedBackProp; 
	}

	
	public AnimationTimer getTimer() {
		return timer; 
	}
	
	public boolean getPlay() {
		return play; 
	}
	public void setPlay(Button button) {
		timer.start();
		play = true;
		button.setGraphic(new ImageView(imagePause));
	}
	
	public void setPause(Button button) {
		timer.stop();
		play = false; 
		button.setGraphic(new ImageView(imagePlay));
	}
	

}
