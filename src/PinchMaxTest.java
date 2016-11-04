import com.leapmotion.leap.Vector;

import processing.core.PApplet;

public class PinchMaxTest extends PApplet { 
	
	String cock = "fbdkfvbdfvbkdfz";
	Leap myLeap;
	
	Vector index, thumb;
	Cursors iCursor, tCursor;
	
	
	PinchMaxTest (){
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	
	  public void settings() {
			smooth(8);
			//size(100,100);
			fullScreen(P3D);
		  }
	  
	  
	  public void setup(){
		  
		  myLeap = new Leap(width,height,100);
		  Vector[] temp = myLeap.getIndexThumbPos();
		  index = temp[0];
		  thumb = temp[1];
		  
		  iCursor = new  Cursors (232,123,234,this);
		  tCursor = new  Cursors (123,123,234,this);
		  
	  }

	  
	  public void draw() {
		  
		  if (myLeap.leap.isConnected()){
		    background(255);
		    myLeap.update();
		    index = myLeap.indexCorrected;
		    thumb = myLeap.thumbCorrected;
		  } else {
			 background(0); 
		  }
		  
		  iCursor.update(index);
		  tCursor.update(thumb);
		  

		    textAlign(CENTER,CENTER);
		    text(cock,width/2,height/2);
		  }
	  
	  public void setText (String t){
		  
		  this.cock = t; 
		  
	  }
	  
}

