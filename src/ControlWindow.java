import processing.core.PApplet;

public class ControlWindow extends PApplet {
	
	String[] windows = {"Grasp Size Test", "JND", "JNDTester", "Pinch Tests", "Pinch Test Tester", "Print Results", "Quit"};
	GraspTestController graspTestController;
	PinchMaxTest pinchMaxTest;
	
	
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
		  int selector =0;;
		  for (int i =0; i<buttons.length; i++){
			  buttons[i].click();
			  if(buttons[i].isSelected)	{
				  selector =i;
			  }
		  }
		  
		 switch (selector){
		 case 0: graspTestController = new GraspTestController();
		 break;
		 case 6: exit();
		 break;
		 
		 }
		  
		}
				
	  }



