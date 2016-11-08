import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchMaxTest extends PApplet { 
	
	String cock = "fbdkfvbdfvbkdfz";
	Leap myLeap;
	Arduino arduino;
	Vector index, thumb;
	Cursors iCursor, tCursor;	
	TouchObject left, right; 
	TouchObject[] objects;
	Haptic scene; 
	
	
	PinchMaxTest (){
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	
	  public void settings() {
			smooth(8);
			fullScreen(P3D);
		  }
	  
	  
	  public void setup(){
		  
		  arduino = new Arduino(this);
		  myLeap = new Leap(width,height,100);
		  Vector[] temp = myLeap.getIndexThumbPos();
		  index = temp[0];
		  thumb = temp[1];		  
		  iCursor = new  Cursors (232,123,234,this);
		  tCursor = new  Cursors (123,123,234,this);
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
		    thumb = myLeap.thumbCorrected;
		  }
		  
		  if (myLeap.inIdealVolume()){
			  background(255);	   
		  } else {
			 background(150); 
		  }
		    
		  left.update();
		  right.update();
		  
		  Vector[] temp = {index,thumb};
		  scene.collsion(temp);		
		  
		  iCursor.update(index);	  
		  tCursor.update(thumb);
		  iCursor.drawDistanceLine(thumb, myLeap.index.distanceTo(myLeap.thumb));
		  
		  

		    textAlign(CENTER,CENTER);
		    text(cock,width/2,height/2);
		  }
	  
	  public void setText (String t){
		  
		  this.cock = t; 
		  
	  }
	  
}

