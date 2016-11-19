import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchUserScreen extends PApplet{
	
	Leap myLeap;
	Arduino arduino;
	Vector index, thumb;
	Cursors iCursor, tCursor;
	PinchObject pinchObject;

	PinchUserScreen(Arduino ard) {
		this.arduino = ard;
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	public void settings() {
		smooth(8);
		fullScreen(P3D,1);
	}
	
	public void setup(){
		  myLeap = new Leap(width,height,100);
		  Vector[] temp = myLeap.getIndexThumbPos();
		  index = temp[0];
		  thumb = temp[1];
		  iCursor = new  Cursors (232,123,234,this);
		  tCursor = new  Cursors (123,123,234,this);
		  iCursor.drawMag3D(false);
		  pinchObject = new PinchObject (23,233,189, this);
		  
	}
	
	public void draw() {
		  
		  if (myLeap.leap.isConnected()){
		    myLeap.update();
		    index = myLeap.indexCorrected;
		    thumb = myLeap.thumbCorrected;
		  }
		  
		  if (myLeap.inIdealVolume()){
			  background(255);	   
		  } else {
			 background(150); 
		  }
		  
		  iCursor.update(index);	  
		  tCursor.update(thumb);
		  iCursor.drawDistanceLine(thumb, myLeap.index.distanceTo(myLeap.thumb));
		  pinchObject.update(index, thumb);
		  drawSeperatorDots();
		
	}
	
	public void running(boolean g){
		  if(!g){
		  this.noLoop();
		  surface.setVisible(false);
		  } else {
			  this.loop();
			  surface.setVisible(true);
		  }
	  }
	
	public void drawSeperatorDots(){
		int seperatorDots = 30; 
		  for (int i=0; i<seperatorDots+1; i++){
			  noStroke();
			  fill(24,230,178);
			  ellipse(width/2,i*(height/seperatorDots),20,20);
			  noFill();
		  }
	}
}
