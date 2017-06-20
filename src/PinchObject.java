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
	float pinchThresholdScaler = 0.5f; //starts pinching object at 0.75 of grasp max
	int maximumHapticResult;
	int minimumHapticResult;
	float ramp; //ramp is a scaler used scale the speed at which force is applied based on object penetration, it is a ratio of the screen corrected pMax value.
	boolean onSide;
	
	boolean visualLimit = true;
	
	
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
	

	public void update(ThreeDCursor ic, ThreeDCursor tc){
		
		Vector i = new Vector (ic.position);
		Vector t = new Vector (tc.position);
		
		p.stroke(255,134,30);
		p.line(i.getX(),i.getY(), i.getZ(),t.getX(),t.getY(), t.getZ());
		
		//this.pinchDistance = index.distanceTo(thumb);
		
		if (onSide(i)&&onSide(t)){

		float[] temp= returnMidPoint(i,t);//ball.getPos();
		p.pushMatrix();

		p.fill(0,255,100);
		p.translate(temp[0], temp [1], temp[2]); //!!!!!!!!!!!! minus? for x
		
		float tempDistanceLeft = p.sqrt( (p.sq(i.getX()-temp[0])) + (p.sq(i.getY()-temp[1])) + (p.sq(i.getZ()-temp[2])));
		
//		float tempRotZ = p.atan2(i.getY()-temp[1], i.getX()-temp[0]);
//		float tempRotY = p.atan2(i.getZ()-temp[2], i.getX()-temp[0]);
		
		Vector test = new Vector(i.getX()-t.getX(),i.getY()-t.getY(),i.getZ()-t.getZ());
		test = test.normalized();
//		
		float tempRotZ = p.atan2(test.getY(), test.getX());
		float tempRotY = p.atan2(test.getZ(), test.getX());
		float tempRotX = p.atan2(test.getZ(), test.getY());
		
		
		p.rotateZ(tempRotZ);
		p.rotateY(tempRotY);
		p.rotateX(tempRotX);
		
		
		if((pinchUpperThreshold+ic.xSize)/2> tempDistanceLeft){
			float tempScaleL = (pinchUpperThreshold+ic.xSize)/2 - tempDistanceLeft;
			tempScaleL = (tempScaleL/pinchUpperThreshold)*2;
			p.scale(1-tempScaleL,1,1);	
		}
		

	
		//p.noFill();
		p.stroke(255,0,0);
		p.line(0, 0, 0, 1000,0,0);
		p.stroke(0,255,0);
		p.line(0, 0, 0, 0,1000,0);
		p.stroke(0,0,255);
		p.line(0, 0, 0,0 ,0,1000);
		p.stroke(0);
		p.sphere(pinchUpperThreshold/2);
		p.popMatrix();
	
		}
	}
	
	public float[] returnMidPoint(float[]l, float[]r){
		
		float x1 = l[0];
		float  y1 = l[1];
		float  z1 = l[2];
		
		float  x2 = r[0];
		float  y2 = r[1];
		float  z2 = r[2];
		
		float x=(x1+x2)/2f;
		float y=(y1+y2)/2f;
		float z=(z1+z2)/2f;
		
		float[] temp = {x,y,z};
		
		return temp;
	}
	
	public float[] returnMidPoint(Vector l, Vector r){
		
		float x1 = l.getX();
		float  y1 = l.getY();
		float  z1 = l.getZ();
		
		float  x2 = r.getX();
		float  y2 = r.getY();
		float  z2 = r.getZ();
		
		float x=(x1+x2)/2f;
		float y=(y1+y2)/2f;
		float z=(z1+z2)/2f;
		
		float[] temp = {x,y,z};
		
		return temp;
	}
	


	@SuppressWarnings("static-access")
	private void getMidPoint(Vector i, Vector t){
		posX = (int)((i.getX()+t.getX())/2.0f);
		posY = (int)((i.getY()+t.getY())/2.0f);	
		if(pinchDistance<pinchUpperThreshold){
			rotation = p.atan2(i.getY() - t.getY(), i.getX() - t.getX()); //i.angleTo(t);
			if (pinchDistance<pinchLowerThreshold && visualLimit){
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
	
	public float getRamp(){
		return ramp;
	}
	
	public void setRamp(float r){
		this.ramp = r;
		pinchLowerThreshold = (pinchThresholdScaler*pMax)-(pMax*ramp);
	}
	
	public void setVisualLimit(boolean v){
		this.visualLimit = v;
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
