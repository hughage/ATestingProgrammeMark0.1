import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class SingleFingerJNDUserScreen extends PApplet {

	Leap myLeap;
	Arduino arduino;
	Vector index;
	Cursors iCursor, tCursor;	
	TouchObject left, right; 
	TouchObject[] objects;
	Haptic scene; 
	
	
	SingleFingerJNDUserScreen (Arduino ard){
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
		  iCursor = new  Cursors (232,123,234,this);
		  left = new TouchObject(width/4,height/2, 119, 190, 119, this);
		  right = new TouchObject(3*(width/4),height/2, 119, 190, 119, this);
		  objects = new TouchObject[2];
		  objects[0] = left;
		  objects[1] = right;		  
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
			 background(150); 
		  }
		    
		  left.update();
		  right.update();		  
		  Vector[] temp = {index};
		  scene.collsion(temp);				  
		  iCursor.update(index);	  

		  }
	  
	  public void setText (String t){
	  }
	  
	  public void setHapticResponce(int l, int r){
		  left.setHapticResponce(l);
		  right.setHapticResponce(r);
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


