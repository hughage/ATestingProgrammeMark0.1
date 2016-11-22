import processing.core.PApplet;

public class ControlWindow extends PApplet {
	
	Arduino arduino;
	
	String[] windows = {"Single Finger JND","Grasp Size Test", "JND", "JNDTester", "Pinch Test", "Deluxe Curve Ball", "Print Results", "Quit"};
	boolean initiated[];
	
	SingleFingerJNDController singleFingerJNDController;
	GraspTestController graspTestController;
	PinchMaxTest pinchMaxTest;
	JNDController jndController;
	JNDTestController jndTestController;
	PinchController pinchController;

	
	int butWidth;
	int butHeight;
	
	Button[] buttons;
	
	ControlWindow(){
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	  public void settings() {
			smooth(8);
			size(500,500);
			//fullScreen(P3D);
		  }
	  
	  public void setup(){
		  
		  arduino = new Arduino(this);
		  
		  initiated = new boolean[windows.length-1];
		  for (int i =0; i<initiated.length; i++){
			  initiated[i]=false;
		  }
		  
		  buttons = new Button[windows.length];
		  for (int i =0; i<windows.length; i++){
			  buttons[i] = new Button (this,i,windows.length,windows[i]);
			  //Button(PApplet parent, int n, int total, String name)
		  }
		  
	  }
	  public void draw() {
		  //background(255);
		  for (int i =0; i<buttons.length; i++){
			  buttons[i].drawButton();
		  }
		  
		  
	  }
	  
	  public void mouseReleased(){
		  
		  int selector =7;
		  for (int i =0; i<buttons.length; i++){
			  buttons[i].click();
			  if(buttons[i].isSelected)	{
				  selector =i +1;
			  } 
			  buttons[i].click();
		  }
		  
		 switch (selector){
		 
		 case 0:
			 break;
			 
		 case 1: 
			 if (!initiated[selector]){
				 singleFingerJNDController = new SingleFingerJNDController(arduino);
				 initiated[selector] = true;
			 } if(initiated[selector]){
				 singleFingerJNDController.running(true);
			 }
		 break;
		 
		 case 2: 
			 if (!initiated[selector]){
				 graspTestController = new GraspTestController();
				 initiated[selector] = true;
			 } if(initiated[selector]){
				 graspTestController.running(true);
			 }
		 break;
		 
		 case 3: 
			 if (!initiated[selector]){
				 jndController = new  JNDController(arduino);
				 initiated[selector] = true;
			 } if(initiated[selector]) {
				 jndController.running(true);
			 }
		 break;
		 
		 case 4: 
			 if (!initiated[selector] && initiated[selector-1]){		 
				 jndTestController = new JNDTestController(jndController.getAveragesForJNDTest(), arduino);
				 initiated[selector] = true;
			 } if(initiated[selector]) {
				 jndTestController.running(true);
			 }
		 break;
		 
		 case 5: 
			 if (!initiated[selector] && initiated[1]){	
				 pinchController = new PinchController (graspTestController.getAverage(), arduino);
				 initiated[selector] = true;
			 } if(initiated[selector]) {
				 pinchController.running(true);
			 }
		 break;
		 
		
		 case 8: exit();
		 break;

		 
		 }
		  
		}
				
	  }



