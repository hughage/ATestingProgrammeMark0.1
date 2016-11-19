import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchObject {

	PApplet p;
	int posX, posY, r, g, b;
	int xMag = 300, yMag;
	float rotation;
	String label = "";
	
	PinchObject(int r, int g, int b, PApplet parent){
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;		
	}
	
	PinchObject(int r, int g, int b, PApplet parent, String l){
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;
		
		this.label= l;
	}
	

	public void update(Vector i, Vector t){
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
	


	@SuppressWarnings("static-access")
	private void getMidPoint(Vector i, Vector t){
		posX = (int)((i.getX()+t.getX())/2.0f);
		posY = (int)((i.getY()+t.getY())/2.0f);	
		rotation = p.atan2(i.getY() - t.getY(), i.getX() - t.getX()); //i.angleTo(t);
		yMag = (int)(i.distanceTo(t));
	}
	
}
