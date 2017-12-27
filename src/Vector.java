
public class Vector {
	public double x;
	public double y;
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public void add(double x,double y)
	{
		this.x += x;
		this.y += y;
	}
	public void add(Vector a)
	{
		this.x += a.x;
		this.y += a.y;
	}
	public void mul(double s)
	{
		this.x*=s;
		this.y*=s;
	}
	public double  length()
	{
		return (double) Math.sqrt(this.x*this.x+this.y*this.y);
	}
	@Override
	public String toString()
	{
		return this.x+","+this.y;
	}
}
