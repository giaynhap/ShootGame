import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

public class Grenade extends Object{
	public float DmgSize;
	public float dmg;
	public int Time;
	private Timer TimeExp  ;
 public   Grenade(Object parents,WpnData.Wpn wpn)
 {
	 super(parents);
	 this.Type=3;
	 this.setSize(16,16);
	 this.setPos(parents.Pos);
	 this.Time = wpn.Delay;
	 this.dmg = wpn.Dmg;
	 this.DmgSize = wpn.SizeDmg;
	 TimeExp = new Timer(Time, new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent arg0) {
				
				EXP();
				TimeExp.stop();
			}
		 });
	 
	 TimeExp.start();
 }
	 public void EXP()
	 {
		 for (int i=0;i<Process.GameObject.size();i++)
		 {
			 Object obj = Process.GameObject.get(i);
			 if (obj.Type!=0) continue;
			 Vector vec = new Vector(this.Pos.x-obj.Pos.x,this.Pos.y-obj.Pos.y);
			 if (vec.length()<=this.DmgSize)
			 ((Player)obj).TakeDamge(dmg,(Player)(this.Parents), this.getIMG());
			 	
			 
		 }
		 SprAnim Exp = new SprAnim(this,64,64);
	 	Exp.img = Resource.ExpImg;
	 	Exp.hFrame=8;
	 	Exp.MaxFrame=32;
	 	Exp.Play();
	 	Exp.Loop=false;
	 	Exp.Angle = this.Angle;
		Process.GameEffect.add(Exp);
		 this.remove();
	 }
	 @Override
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
		hGrap.drawImage(this.img.getSubimage(0, 0, 32, 32),aff2,null);
	}
	 @Override
	 public BufferedImage getIMG()
	 {
		 return this.img.getSubimage(0, 0, 32, 32);
	 }
}
