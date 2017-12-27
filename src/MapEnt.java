import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MapEnt extends Object{
	public MapEnt()
	{
		
		super();
		this.setSize(64,64);
		this.isMove=false;
		this.Type =1;
	}
	@Override
	public void draw(Camera cam)
	{
		if (!this.Visible) return;
		Graphics2D hGrap = MainDisplay.DisplayScr.hGrap;
		BufferedImage img = this.getIMG();
		AffineTransform aff = new AffineTransform();
		Vector Pos = cam.toScreenPos();
		Pos.x+=this.Pos.x;
		Pos.y+=this.Pos.y;
		aff.rotate(this.Angle,Pos.x,Pos.y);
		aff.translate(Pos.x-this.Size.x/2, Pos.y-this.Size.y/2);
		Vector alnp = new Vector(cam.Pos.x-this.Pos.x,cam.Pos.y-this.Pos.y);
		alnp.mul(-1/100.0);
		AffineTransform aff2 = new AffineTransform();
		aff2.translate(Pos.x-this.Size.x/2+alnp.x, Pos.y-this.Size.y/2+alnp.y);
		aff2.rotate(this.Angle,this.Size.x/2,this.Size.x/2);
		BufferedImage newImg=  new BufferedImage(img.getWidth(),
				img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D shGrap = newImg.createGraphics();
		shGrap.setColor(Color.black);
		 
		shGrap.drawImage(img, 0,0,null);
		hGrap.drawImage(createDropShadow(img),aff2,null);
		hGrap.drawImage(newImg,aff,null);
	}
	public BufferedImage getIMG()
	{	
		return Resource.Box1Img.getSubimage(0, 0, 64, 64);
	}
}
