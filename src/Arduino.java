import processing.core.PApplet;
import processing.serial.*;

public class Arduino {
	
	Serial stream;
	PApplet p;	
	int baudRate = 115200;
	String ard = "/dev/tty.usbmodemFA131";
	
	
	int maxArduinoValue = 255, minArduinoValue = 0;
	float multiplyValue;
	
	int previousA, previousB; 
	
	
	
	Arduino(PApplet parent){
		this.p= parent;
	    if (Serial.list().length>=1){
	    	boolean hasArd = false;
	    	for(int i=0; i<Serial.list().length; i++){
	    		System.out.println(Serial.list()[i]);
	    		if(Serial.list()[i].contains(ard)){
	    			hasArd = true;	
	    		};
	    	}
	    	
	    	if(hasArd){
	    	stream = new Serial(p, ard,baudRate);
	    	System.out.println("Arduino on: " + ard);
	    	} else {
	    		System.out.println(ard + " is not pressent, please connect.");
	    		p.exit();
	    	}
	        }
	    
	    multiplyValue = (float)maxArduinoValue;
	    System.out.println(multiplyValue);
	}
	
	public void setA(float a){
		float tempF = (float)a*multiplyValue;
		int tempI = (int) tempF;	
		if (previousA != tempI ){		
			String send = "a"+tempI+"=";
			stream.write(send);
			previousA = tempI;
		}		
	}
	
	public void setB(float b){
		float tempF = (float)b*multiplyValue;
		int tempI = (int) tempF;		
		if (previousB != b){
			String send = "b"+tempI+"=";
			stream.write(send);
			previousB = tempI;
		}	
	}
		
	public void setAB(float a, float b){		
		float tempF = (float)a*multiplyValue;
		int tempI = (int) tempF;
		float tempF2 = (float)b*multiplyValue;
		int tempI2 = (int) tempF2;
		if (previousA != tempI || previousB != tempI2 ){
			String send = "a"+tempI+"b"+tempI2+"=";
			stream.write(send);
			previousA = tempI;
			previousB = tempI2;
		}	
	}	
	
	public void setAB(float c[]){		
		float a = c[0];
		float b = c[1];
		float tempF = (float)a*multiplyValue;
		int tempI = (int) tempF;
		float tempF2 = (float)b*multiplyValue;
		int tempI2 = (int) tempF2;
		if (previousA != tempI || previousB != tempI2 ){
			String send = "a"+tempI+"b"+tempI2+"=";
			System.out.println("a"+tempI+"b"+tempI2+"= ");
			stream.write(send);
			previousA = tempI;
			previousB = tempI2;
		}	
	}	
		

}
