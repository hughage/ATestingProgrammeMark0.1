import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchObject {

	PApplet p;
	int posX, posY, r, g, b;
	Vector index, thumb;
	float pinchDistance;
	int xMag, yMag;
	int xSize, ySize;
	float rotation;
	float pMax; //value of the maximum hand pinch size from the grasp size class
	String label = "";
	int screenLimitL, screenLimitR; //values to draw pinch object if cursors are in the correct part of the screen
	float pinchUpperThreshold, pinchLowerThreshold; //the threshold to start squeezing the pinch object
	float pinchThresholdScaler = 0.75f; //starts pinching object at 0.75 of pMaxValue
	int maximumHapticResult;
	int minimumHapticResult;
	float ramp; //ramp is a scaler used scale the speed at which force is applied based on object penetration, it is a ratio of the screen corrected pMax value.
	boolean onSide;
	
	
	PinchObject(int r, int g, int b, PApplet parent){
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;		
	}
	
	PinchObject(int r, int g, int b, int p1, int p2,int maxHR, int minHR,float ram, float pinchMaxAverage, PApplet parent){
		this.r=r;
		this.g=g;
		this.b=b;
		this.screenLimitL = p1;
		this.screenLimitR = p2;
		this.maximumHapticResult = maxHR;
		this.minimumHapticResult = minHR;
		this.ramp = ram;
		this.pMax = pinchMaxAverage;
		this.p = parent;			
		pinchLowerThreshold = (pinchThresholdScaler*pMax)-(pMax*ramp);
		pinchUpperThreshold = (pinchThresholdScaler*pMax); //start pinching object at 3/4 pinch max value
		xMag= (int) pinchUpperThreshold;
	}
	
	PinchObject(int r, int g, int b, PApplet parent, String l){
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;
		this.label= l;
	}
	

	public void update(Vector i, Vector t){
		
		this.index = i;
		this.thumb = t;
		this.pinchDistance = index.distanceTo(thumb);
		
		if (onSide(i)&&onSide(t)){
			getMidPoint(i,t);
			p.pushMatrix();
			p.translate(posX, posY);
			p.pushMatrix();
			p.fill(r,g,b);
			p.noStroke();
			p.rotate(rotation);
			p.ellipse(0,0,yMag,xMag);
			p.popMatrix(); 
			p.popMatrix();
			p.noFill();
		}
	}
	


	@SuppressWarnings("static-access")
	private void getMidPoint(Vector i, Vector t){
		posX = (int)((i.getX()+t.getX())/2.0f);
		posY = (int)((i.getY()+t.getY())/2.0f);	
		if(pinchDistance<pinchUpperThreshold){
			rotation = p.atan2(i.getY() - t.getY(), i.getX() - t.getX()); //i.angleTo(t);
			if (pinchDistance<pinchLowerThreshold){
				yMag = (int)pinchLowerThreshold;
				} else{
					yMag = (int)(pinchDistance);	
				}
		} else {
			yMag = (int)pinchUpperThreshold;
		}
	}
	
	private boolean onSide(Vector h){
		 onSide = false;
		if(h.getX()>screenLimitL && h.getX()<screenLimitR && h.getY()>0 && h.getY()<p.height){
			onSide = true;
		}
		return onSide;
	}
	
	public void setHapticResultBounds(int maxHR, int minHR){
		this.maximumHapticResult = maxHR;
		this.minimumHapticResult = minHR;
	}
	
	public void setRamp(float r){
		this.ramp = r;
		pinchLowerThreshold = (pinchThresholdScaler*pMax)-(pMax*ramp);
	}
	
	public int[] getHapticResult(){
		int[] temp = { minimumHapticResult,minimumHapticResult};
		if(pinchDistance<pinchUpperThreshold){
			@SuppressWarnings("static-access")
			float v = p.map(pinchDistance,pinchUpperThreshold,pinchLowerThreshold,minimumHapticResult,maximumHapticResult);
			int t = (int) v;
			if (t>maximumHapticResult){
				t = maximumHapticResult;
			}
			temp[0] = t;
			temp[1] = t;
		}
		return temp;
	}
	
}
