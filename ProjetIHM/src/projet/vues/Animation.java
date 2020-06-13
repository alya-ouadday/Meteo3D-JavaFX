package projet.vues;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Slider;

public class Animation  {

	private  int rotationSpeed ; 
	private float multipleSpeed;
	private static final int SPEEDMAX = 500;
	private static final int SPEEDMIN = 10; 
	private int angle ; 
	private AnimationTimer timer; 
	private boolean play; 
	
	public Animation(Slider sliderAnnee, Group earth) {
		  rotationSpeed = 50; 
	      multipleSpeed = 1; 
	      angle = 1; 
	      play = false; 
	      final long startNanoTime = System.nanoTime();; 
		
	       timer = new AnimationTimer() {
	    	   @Override
		        public void handle(long currentNanoTime) {
		        	double t = (currentNanoTime - startNanoTime)/ 1000000000.0;
		        	double rotationSpeed = 100; 
		        	earth.setRotationAxis(new Point3D(0,1,0));
		        	earth.setRotate(rotationSpeed*t); 
		        	if(rotationSpeed*t / (360*angle) >= 1) {
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
		if(rotationSpeed < SPEEDMAX) {
		rotationSpeed += 25; 
		multipleSpeed += 0.5; 
		}
	}
	
	public void MoinsVite() {
		if(rotationSpeed > SPEEDMIN) {
		rotationSpeed -= 25; 
		multipleSpeed -= 0.5; 
		}
	}
	
	public int getRotationSpeed() {
		return rotationSpeed;
	}
	
	public float getMultipleSpeed() {
		return multipleSpeed; 
	}
	
	public AnimationTimer getTimer() {
		return timer; 
	}
	
	public boolean getPlay() {
		return play; 
	}
	public void setPlay() {
		play = true;	
	}
	
	public void setPause() {
		play = false; 
	}
}
