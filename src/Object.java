import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
abstract class Object {
	public Vector Pos;
	public Point Size;
	public Object Parents;
	public boolean Visible=true;
	public Vector Acceleration = new Vector(0,0);
	public Vector Velocity  = new Vector(0,0);
	public Vector MaxVelocity = new Vector(0,0);
	public double Angle = 0.0 ;
	public BufferedImage img;
	public double speed=5.0;
	public boolean isMove =true;
	public int Type =0;
	public boolean isAlive=true;
	public int Health=100;
	public boolean remove = false;
	public boolean isThrow =false;
	public float orlength =300.0f;
	public boolean isMoveFrame = true;
	public String Name="";
	public int ID=-2;
	public void remove()
	{
		remove = true;
	}
	public Object()
	{
		Pos= new Vector(0,0);
		Size = new Point(10,10);	
	}
	public Object(int a,int b)
	{
		Pos= new Vector(a,b);
		Size = new Point(10,10);
	}
	public Object(int a,int b,int c,int d)
	{
		Pos= new Vector(a,b);
		Size = new Point(c,d);
	}
	public Object(Object Parents)
	{
		Pos= new Vector(0,0);
		Size = new Point(10,10);
		this.Parents=Parents;
	}
	
	public Object(Object Parents,int a,int b)
	{
		Pos= new Vector(a,b);
		Size = new Point(10,10);
		this.Parents=Parents;
	}
	public Object(Object Parents,int a,int b,int c,int d)
	{
		Pos= new Vector(a,b);
		Size = new Point(c,d);
		this.Parents=Parents;
	}
	public void setPos(double d,double e)
	{
		this.Pos.x=d;
		this.Pos.y=e;	
	}
	public void setSize(int a,int b)
	{
		this.Size.x=a;
		this.Size.y=b;
	}
	public void setPos(Vector a)
	{
		this.Pos.x=a.x;
		this.Pos.y=a.y;
	}
	public void setSize(Point a)
	{
		this.Size.x=a.x;
		this.Size.y=a.y;
	}
	
	public void addPos(Point m)
	{
		this.Pos.x+=m.x;
		this.Pos.y+=m.y;
	}
	public void addPos(Vector m)
	{
		this.Pos.x+=m.x;
		this.Pos.y+=m.y;
	}
	public Vector getAcceleration(){
		return Acceleration;
	}
	public void setAcceleration(Vector m ){
		this.Acceleration.x = m.x ;
		this.Acceleration.y = m.y ;
	}
	
	public void addAcceleratio(Vector m)
	{
		this.Acceleration.add(m);
	}
	public Vector getVelocity(){
		return Velocity;
	}
	public void setVelocity(Vector m ){
		
		this.Velocity.x = m.x ;
		this.Velocity.y = m.y ;
		
	}
	
	public void addVelocity(Vector m)
	{
		this.Velocity.add(m);
	}
	public void move()
	{
		if(!isMove) return;
		if (this.Pos.x+this.Velocity.x<=10)  this.speed=0;
		if (this.Pos.y+this.Velocity.y<=10)  this.speed=0;
		Vector newvec = new Vector (this.Velocity.x,this.Velocity.y);
		newvec.mul( Math.abs(this.speed));
		newvec.mul( Math.abs(this.speed));
		
		//this.addVelocity(this.Acceleration);
		
		this.addPos(newvec);
		if (!isThrow)
		if (Math.abs(this.speed)>0) this.speed-=0.5*Math.abs(this.speed)/this.speed;
		else this.speed =0;
		if (this.Health<=0)
		{
			this.Health=0;
			this.isAlive=false;
		}
		
	}
	public void draw(Camera cam)
	{
		
	}
	public String toString()
	{
		String text ="Object:   \nPos x:"+Pos.x+";y: "+Pos.y+"\nRotate: "+this.Angle+ "\nVeloc  x:"+this.Velocity.x+", y:"+this.Velocity.y+"\nSpeed: "+this.speed;
				;
		
		return text;
	}
	public Color shadowColor = new Color(0,0,0,200);
	public int shadowSize=3;
	public BufferedImage createDropShadow(BufferedImage image) {
		  if (image != null) {
		    BufferedImage shadow = new BufferedImage(image.getWidth(),
		                                             image.getHeight(),
		                                             BufferedImage.TYPE_INT_ARGB);
		    BufferedImage tmp = new BufferedImage(image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

		    for (int x = 0; x < image.getWidth(); x++) {
		        for (int y = 0; y < image.getHeight(); y++) {
		          int argb = image.getRGB(x, y);
		          Color c = new Color(argb,true);
		          if ( c.getAlpha()>0)
		          {
			          argb = shadowColor.getRGB();
			          
		          }
		          tmp.setRGB(x, y, argb);
		        }
		      }
		    
		    getBlurOp(shadowSize).filter(tmp, shadow);
		    return shadow;
		  }
		  return null;
		}
	public boolean checkTouch(Vector m)
	{
		Vector v2 = new Vector(m.x-this.Pos.x,m.y-this.Pos.y);
		if (v2.length()<this.Size.x) return true;
		return false;
	}
	public void checkTouch(Object m)
	{
		
		Vector v2 = new Vector(m.Pos.x-(this.Pos.x+this.Velocity.x*this.speed),m.Pos.y-(this.Pos.y+this.Velocity.y*this.speed));
		
		if (v2.length()<(this.Size.x+m.Size.x)/2)
			{
			this.touch( m);
			
			}
		
		return ;
	}
	public ConvolveOp getBlurOp(int size) {
        float[] data = new float[size * size];
        float value = 1 / (float) (size * size);
        for (int i = 0; i < data.length; i++) {
            data[i] = value;
        }
        return new ConvolveOp(new Kernel(size, size, data));
    }
	public void touch(Object m)
	{
		
	}
 
	public BufferedImage getIMG()
	{	
		return this.img;
	}
}
