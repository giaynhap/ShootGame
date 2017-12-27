import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Bullet extends Object{
	Graphics2D hGrap;
	public float dmg=20.0f;
	public Bullet(Object Parents)
	{
		
		super(Parents);
		  hGrap = MainDisplay.DisplayScr.hGrap;
		this.isThrow=true;
		this.Angle = Parents.Angle;
		this.speed=5;
		this.setVelocity(new Vector(Math.sin(this.Angle),-Math.cos(this.Angle)));
		this.Type=2;
		Point a = new Point ((int)(Math.cos(this.Angle-Math.PI/2)*(20 )),(int)(Math.sin(this.Angle-Math.PI/2)*(20)));
		 this.setPos(Parents.Pos.x+a.x, Parents.Pos.y+a.y);
	}
	@Override
	public void move()
	{
		super.move();
		if (this.Parents==null) return;
		Vector abs= new Vector(this.Pos.x-this.Parents.Pos.x,this.Pos.y-this.Parents.Pos.y);
		if (abs.length() > this.Parents.orlength) this.remove();
		
	}
	@Override
	public void draw(Camera cam)
	{
		
		if (!this.Visible) return;
	
		Vector Pos = cam.toScreenPos();
		Pos.x+=this.Pos.x;
		Pos.y+=this.Pos.y;
		hGrap.setColor(Color.YELLOW);
		Point a = new Point ((int)(Math.cos(this.Angle-Math.PI/2)*(10 )),(int)(Math.sin(this.Angle-Math.PI/2)*(10)));
		hGrap.drawLine((int)Pos.x, (int)Pos.y,(int)Pos.x+a.x,(int)Pos.y+a.y);
		if (this.speed==0) this.remove();
	}
	@Override
	public void touch(Object obj)
	{
		if (obj.Type==1)
		{
			this.remove();
		}
	}

}
