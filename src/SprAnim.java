
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import javax.swing.Timer;

public class SprAnim extends Object{
	public int iFrame;
	public int hFrame=3;
	public int MaxFrame=9;
	public boolean isPlayAnim=false;
	public boolean Loop = false;
	public SprAnim(Object parents, int width, int height) {
		super(parents);
		this.setSize(width,height);
		this.Type=1;
		this.setPos(parents.Pos);
		this.Angle=Math.PI*2-parents.Angle;
		//96 96
	 Timer t = new Timer(50, new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent arg0) {
				if (isPlayAnim)
				iFrame+=ss1;
				if (iFrame<=0) iFrame=0;
				
			}
         });
	 t.start();
	}
	private int ss1 =1;
	@Override
	public BufferedImage getIMG ()
	{
		if (iFrame>=MaxFrame) 
		{
			 	
				iFrame= MaxFrame-1;
				if (!this.Loop) this.remove();
				 
			 
		}
	
		int x = iFrame%hFrame;
		int y = iFrame/hFrame;
		BufferedImage newImg = null;
		BufferedImage timg = super.getIMG();
		try 
		{
			newImg = timg.getSubimage(x*(this.Size.x),y*this.Size.y, this.Size.x, this.Size.y);
		}
		catch (RasterFormatException e)
		{
			   newImg = timg.getSubimage(0,0, this.Size.x, this.Size.y);
		}
		if (ss1==-1 && iFrame==0) this.remove();
		return newImg;
	}
	public void draw(Camera cam)
	{
		
		if (!this.Visible) return;
		Graphics2D hGrap = MainDisplay.DisplayScr.hGrap;
		Vector Pos = cam.toScreenPos();
		Pos.x+=this.Pos.x;
		Pos.y+=this.Pos.y;
		AffineTransform aff2 = new AffineTransform();
		aff2.translate(Pos.x-this.Size.x/2 , Pos.y-this.Size.y/2 );
		aff2.rotate(this.Angle,this.Size.x/2,this.Size.x/2);
		hGrap.drawImage(getIMG (),aff2,null);
	}
	 Timer t2;
	public void Play ()
	{
		isPlayAnim= true;
		
	        t2 = new Timer(MaxFrame*400, new ActionListener() {
	 			@Override
	 			
	 			public void actionPerformed(ActionEvent arg0) {
	 				ss1=-1;
	 				t2.stop();
	 				
	 			}
	          });
	          t2.start();
	}
	public void Pause()
	{
		isPlayAnim = false;
	}
}
