import java.awt.Point;
public class Camera extends Object {
	public Camera()
	{
	
		super();
		isMove = false;
	}
	public Camera(Object obj)
	{
		super();
		isMove = false;
		this.Parents = obj;
	}
	public Vector toScreenPos()
	{
		Vector re= new Vector(0,0);
		re.x =-this.Pos.x;
		re.y = -this.Pos.y;
		return re;
	}
	public Vector screenToPoint(Point p)
	{
		return new Vector(this.Pos.x+p.x,this.Pos.y+p.y);
	}
	@Override
	public void move()
	{
		int mSize = MainDisplay.MapSize;
		int h= Process.Height;
		int w= Process.Width;
		if (this.Parents!=null)
		{
			this.setPos((double)Math.min(mSize-w,Math.max(Parents.Pos.x-Process.Width/2,0)),(double)Math.min(mSize-h,Math.max(Parents.Pos.y-Process.Height/2,0)));
		}
	}
	
}
