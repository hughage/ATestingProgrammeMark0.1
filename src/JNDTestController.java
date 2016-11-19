import processing.core.PApplet;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class JNDTestController extends PApplet { 
	
	JNDTesterScreen jndTesterScreen;
	Button quit;
	
	int spaceing = 0;
	int spaces = 20;
	
	int currentTest = 0;
	
	int[][] averagesForJNDTest;
	int[][] testsValues; //for each average, test if people can identify the threshold value and the reff value [test number][reff,jnd,tester,result(reff or jnd)]
	
	int[] randomSelection;
	
	JNDTestController (int [][] b, Arduino ard){
		String[] a = {""};
		PApplet.runSketch(a, this);
		this.averagesForJNDTest = b;
		System.out.println("dog");
		jndTesterScreen = new JNDTesterScreen(b, ard);		
	}
	
	
	  public void settings() {
			size(500,500);
			//background(255);
		
		  }
	  
	  
	  public void setup(){	 
		
		 spaceing = height/(spaces);
		 quit = new Button(this,1,8,"Quit",height-(int)(spaceing*4));

		 testsValues = new int[averagesForJNDTest.length*2][4]; //for each average, test if people can identify the threshold value and the reff value [test number][reff,jnd,tester,result(reff or jnd)]
		 for(int i =0; i<averagesForJNDTest.length; i++){
			 testsValues[i*2][0]= averagesForJNDTest[i][1];
			 testsValues[i*2][1]= averagesForJNDTest[i][0];
			 testsValues[i*2][2]= averagesForJNDTest[i][0];		
			 testsValues[i*2][3]= 666;	
			 //create the opposite test
			 testsValues[(i*2)+1][0]= averagesForJNDTest[i][1];
			 testsValues[(i*2)+1][1]= averagesForJNDTest[i][0];
			 testsValues[(i*2)+1][2]= averagesForJNDTest[i][1];
			 testsValues[(i*2)+1][3]= 666;
		 }
		 
		 testsValues= shuffleArray(testsValues);	 
		 delay(3000);
		 setHapticResponce();
	  }

	  
	  public void draw() {
		  background(255);	  
		  
		  fill(0);
		  textSize(20);
		  textAlign(CENTER, CENTER);
		  

		  text("Current Test Number: "+(currentTest+1), width/2, spaceing);
		  	  

		  text("Test:", width/6,(3*spaceing));
		  text("A", 2*(width/6),(3*spaceing));
		  text("B", 3*(width/6),(3*spaceing));
		  text("M", 4*(width/6),(3*spaceing));
		  text("-", 5*(width/6),(3*spaceing));
		  
		  for (int i=0; i<testsValues.length; i++){
			  if(i==currentTest){
				  fill(230,130,20);
			  } else{
				  fill(0);
			  }
			  text((i+1), width/6,(5*spaceing)+(i*spaceing));
			  text(testsValues[i][0], 2*(width/6),(5*spaceing)+(i*spaceing));
			  text(testsValues[i][1], 3*(width/6),(5*spaceing)+(i*spaceing));
			  text(testsValues[i][2], 4*(width/6),(5*spaceing)+(i*spaceing));
			  if(testsValues[i][3]==666){
				  text("?", 5*(width/6),(5*spaceing)+(i*spaceing));
			  } else {
				  text(testsValues[i][3], 5*(width/6),(5*spaceing)+(i*spaceing));
			  } 
			  }	  
		  
		  quit.drawButton();	  
	  }

	  
	  public void keyReleased(){

		  
		  if (key == CODED) {
			    if (keyCode == DOWN) {
			    	if (currentTest<testsValues.length-1){
			    	currentTest++;
			    	} else {
			    		currentTest =0;
			    	}  	
			    }
			    if (keyCode == UP) {
			    	if (currentTest>0){
			    	currentTest--;
			    	} else {
			    		currentTest = testsValues.length-1;
			    	}
			    }	   	    
		  }
		 
		  setHapticResponce();
	  }
	  
	  public void keyPressed(){
		  
		  if (key == 'a'|| key == 'A'){
			  testsValues[currentTest][3] = testsValues[currentTest][0];
		  }
		  
		  if (key == 'b'|| key == 'B'){
			  testsValues[currentTest][3] = testsValues[currentTest][1];
		  }
		  
		  if (key == CODED) {
		    if (keyCode == RIGHT) {

		    	}
		    }
		    if (keyCode == LEFT) {

		    }
		  }
		  
		  
	  static int[][] shuffleArray(int[][] ar)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--){
	    	
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a[] = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }
	  
	  private void setHapticResponce(){
		  jndTesterScreen.setHapticResponce(testsValues[currentTest][0],testsValues[currentTest][1],testsValues[currentTest][2]);
	  }
	  

	
	  
	  public int[][] getAveragesForJNDTest(){
		  return averagesForJNDTest;
	  }
	  
	  public void mouseReleased(){

		  quit.click();	  
		  if (quit.isSelected){		
			  quit.isSelected = false;
			  this.running(false);
			 
		  } else {
			  this.running(true);
			  //quit.isSelected = true;
		  }
	  }

	  
	  public void running(boolean g){
		  if(!g){
		  this.noLoop();
		  surface.setVisible(false);
		  jndTesterScreen.running(false);
		  } else {
			  this.loop();
			  surface.setVisible(true);
			  jndTesterScreen.running(true);
		  }
	  }
	  
	  
	  
}



