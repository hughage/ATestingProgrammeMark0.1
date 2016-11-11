import processing.core.PApplet;

public class ControlWindow extends PApplet {
	
	String[] windows = {"Grasp Size Test", "JND", "JNDTester", "Pinch Tests", "Pinch Test Tester", "Print Results", "Quit"};
	boolean initiated[];
	GraspTestController graspTestController;
	PinchMaxTest pinchMaxTest;
	JNDController jndController;
	
	
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
			 println ("Bang");
			 if (!initiated[selector]){
				 graspTestController = new GraspTestController();
				 initiated[selector] = true;
			 } if(initiated[selector]){
				 graspTestController.running(true);
			 }
		 break;
		 
		 case 2: 
			 if (!initiated[selector]){
				 jndController = new  JNDController();
				 initiated[selector] = true;
			 } if(initiated[selector]) {
				 jndController.running(true);
			 }
		 break;
		 
		
		 case 7: exit();
		 break;

		 
		 }
		  
		}
				
	  }



