import processing.core.PApplet;


public class PinchController  extends PApplet{
	
	PinchUserScreen pinchUserScreen;
	Button quit;
	
	int spaceing = 0;
	int spaces = 8;

	PinchController(float average, Arduino ard) {
		String[] a = {""};
		PApplet.runSketch(a, this);
		pinchUserScreen = new PinchUserScreen(ard);
	}
	
	public void settings() {
		size(500,500);
	}
	
	public void setup(){
		background(255);
		spaceing = height/(spaces);
		quit = new Button(this,1,8,"Quit",height-spaceing);
		delay(3000);
		//setHapticResponce();	
		//recalculate();	
	}
	
	public void draw() {
		background(255);	  	  
		fill(0);
		textSize(20);
		textAlign(CENTER, CENTER);
		
		
		quit.drawButton();
	}
	
	public void mouseReleased(){

		quit.click();	  
		if (quit.isSelected){		
			quit.isSelected = false;
			this.running(false);
		  } else {
			  this.running(true);
		  }
	  }
	
	public void running(boolean g){
		if(!g){
			this.noLoop();
			surface.setVisible(false);
			pinchUserScreen.running(false);
		} else {
			this.loop();
			surface.setVisible(true);
			pinchUserScreen.running(true);
		  }
	  }

}
