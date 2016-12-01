import com.leapmotion.leap.Vector;

import processing.core.PApplet;

public class SingleFingerJNDTesterScreen extends PApplet  {

	Leap myLeap;
	Arduino arduino;
	Vector index;
	Cursors iCursor;	
	TouchObject left, right, middle; 
	TouchObject[] objects;
	Haptic scene; 
	
	int[][] averagesForJNDTest;

	
	SingleFingerJNDTesterScreen(int[][] b, Arduino ard){
		this.averagesForJNDTest = b;	
		String[] a = {""};
		PApplet.runSketch(a, this);
		this.arduino = ard;
	}
	

	
		  public void settings() {
				smooth(8);
				fullScreen(P3D,1);
			  }
		  
		  
		  public void setup(){
			  
			  
			  myLeap = new Leap(width,height,100);
			  Vector[] temp = myLeap.getIndexThumbPos();
			  index = temp[0];	  
			  iCursor = new  Cursors (232,123,234,this);
			  left = new TouchObject(width/4,height/3, 119, 190, 119, this, "A");
			  right = new TouchObject(3*(width/4),height/3, 119, 190, 119, this, "B");
			  middle = new TouchObject(width/2,2*(height/3), 119, 190, 119, this, "?");
			  objects = new TouchObject[3];
			  objects[0] = left;
			  objects[1] = right;
			  objects[2] = middle;
			  
			  scene = new Haptic(objects, arduino);
			  
		  }

		  
		  public void draw() {
			  
			  if (myLeap.leap.isConnected()){
			    myLeap.update();
			    index = myLeap.indexCorrected;
			  }
			  
			  if (myLeap.inIdealVolume()){
				  background(255);	   
			  } else {
					// background(150); 
					 background(255);
			  }
			    
			  left.update();
			  right.update();
			  middle.update();
			  
			  Vector[] temp = {index};
			  scene.collsion(temp);		
			  
			  iCursor.update(index);	  
			  }
		  
		  public void setText (String t){ 
		  }
		  
		  public void setHapticResponce(int a, int b, int m){
			  left.setHapticResponce(a);
			  right.setHapticResponce(b);
			  middle.setHapticResponce(m);
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
		  
	}


