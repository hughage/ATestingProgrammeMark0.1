import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchUserScreen extends PApplet{
	
	Leap myLeap;
	Arduino arduino;
	Vector index, thumb;
	Cursors iCursor, tCursor;
	PinchObject pinchObjectR;
	PinchObject pinchObjectL;
	
	String leftText = "Changer";
	String rightText = "Reference";
	int textHeight = 100;
	int textsize = 60;
	int []textColour = {0,0,0};
	
	float pMax; //value of the maximum hand pinch size from the grasp size class

	PinchUserScreen(Arduino ard, float pinchMaxValue) {
		this.arduino = ard;
		String[] a = {""};
		PApplet.runSketch(a, this);
		this.pMax = pinchMaxValue;
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
		  iCursor.drawMag3D(true);
		  pinchObjectL = new PinchObject (23,233,189,0,width/2,255,0, 0.5f,myLeap.pMaxscreenCorrected(pMax), this);
		  pinchObjectR = new PinchObject (23,233,189,width/2,width,150,0,0.5f, myLeap.pMaxscreenCorrected(pMax), this);
		  
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
				// background(150); 
				 background(255); 
		  }
		  
		  fill(textColour[0],textColour[1],textColour[2]);
		  textSize(textsize);
		  text(leftText,width/4,height-textHeight);
		  text(rightText,3*(width/4),height-textHeight);
		  noFill();
		  
		  pinchObjectL.update(index, thumb);
		  pinchObjectR.update(index, thumb);
		  haptics();
		  iCursor.update(index);	  
		  tCursor.update(thumb);
		  iCursor.drawDistanceLine(thumb, myLeap.index.distanceTo(myLeap.thumb));	  
		  drawSeperatorDots();
		
	}
	
	public void running(boolean g){
		  if(!g){
		  this.noLoop();
		  surface.setVisible(false);
		  arduino.off();
		  } else {
			  this.loop();
			  surface.setVisible(true);
		  }
	  }
	
	public void haptics(){
		if (pinchObjectL.onSide){	
			arduino.setAB(pinchObjectL.getHapticResult()); 
		} 
		else if (pinchObjectR.onSide){	
			arduino.setAB(pinchObjectR.getHapticResult()); 
		} else {
			arduino.off();
		}	
	}
	
	public void setHapticResponce(int changeTop, int changeBottom, int reffTop, int reffBottom){
		pinchObjectL.setHapticResultBounds(changeTop, changeBottom);
		pinchObjectR.setHapticResultBounds(reffTop,reffBottom);
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
