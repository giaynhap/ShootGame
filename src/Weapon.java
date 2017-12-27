import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

public class Weapon extends Object{
public int mGunDelay=200;
public boolean isCanFire=true;
public float dmg=15.0f;
public int Ammo=30,MaxAmmo=30;
public int Clip=90;
public int delay_reload=1000;
public int idWeapon ;
public WpnType mWpnType;
public String name;
public Player oldPar=null;
private Timer timeDel  ;
public Weapon(Object parents)
{
	super(parents);
	this.Type=3;
	this.setSize(32,32);
	Weapon current = this;
	timeDel = new Timer(3000, new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent arg0) {
				

				current.oldPar = null;
					timeDel.stop();
			}
    });
	 
	 timeDel.start();
}
@Override
public void move()
{
	 super.move();
	if (Parents==null) return;
	this.setPos(this.Parents.Pos);
	this.Angle=Parents.Angle;
	
}
@Override
public void draw(Camera cam)
{
	if (Parents==null)	
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
		
		return ;
	}
	if (!this.Visible) return;
	Graphics2D hGrap = MainDisplay.DisplayScr.hGrap;
	Vector Pos = cam.toScreenPos();
	Pos.x+=this.Pos.x;
	Pos.y+=this.Pos.y;
	AffineTransform aff2 = new AffineTransform();
	aff2.translate(Pos.x-this.Size.x/2  , Pos.y-this.Size.y/2-this.Size.x/2+4 );
	aff2.rotate(this.Angle,this.Size.x/2,+this.Size.x-4);
	hGrap.drawImage( this.img.getSubimage(0, 0, 32, 32) ,aff2,null);
}
@Override
public BufferedImage getIMG()
{
	return this.img.getSubimage(32, 0, 32, 32);
}
Timer t;
public void Fire()
{
	if (!isCanFire) return;

	if (WpnType.RightHand == this.mWpnType)
	{
		isCanFire = false;
		Weapon current = this;
		 t = new Timer(mGunDelay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					isCanFire = true;
					t.stop();
						if (current.Parents!=null)
						{
						
							if (((Player)current.Parents).objcurrent_t!=null)
							{
								if (((Player)current.Parents).objcurrent_t.Type==0)
								{
									Player obj2 = (Player)((Player)current.Parents).objcurrent_t;
									Player obj3 = (Player)current.Parents;
									Vector vec = new Vector(obj2.Pos.x- obj3.Pos.x,obj2.Pos.y-obj3.Pos.y);
									if (vec.length()<64 &&obj2.isAlive)
									{ 
										/*
										obj2.CreateBlood(obj2);
										obj2.Health -= current.dmg;
										if (obj2.Health<=0)
										{
											MainDisplay.HUDGAME.MsgDeathAdd((Player)(current.Parents), ((Player)current.Parents).mWeapon, obj2);
											obj2.Drop();
										}
										*/
										obj2.TakeDamge(current.dmg,(Player)current.Parents, ((Player)current.Parents).mWeapon.getIMG());
									}
								}
								
							}
						}
				
				}
	         });
	         t.start();
	return;
	}
	if (WpnType.LeftHand2 == this.mWpnType)
	{
		Player par = (Player)this.Parents;

		Grenade gn = new Grenade(this.Parents,WpnData.Weapons.get(par.mWeapon.idWeapon-1));
		gn.img = par.mWeapon.img;
		Process.GameObject.add(gn);
		par.WeaponData.remove(par.mWeapon);
		par.mWeapon = par.WeaponData.get(0);
	}
	if (Ammo<=0) return;
	
	isCanFire=false;

	Bullet bul = new Bullet(this.Parents);
	bul.dmg = this.dmg;
	this.Ammo-=1;
	Process.GameObject.add(bul);
	 t = new Timer(mGunDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isCanFire = true;
			
				t.stop();
			}
         });
         t.start();
}
@Override
public String toString()
{
	String re = "";
	  int wpnid = this.idWeapon;
	  int ammo = this.Ammo;
	  int clip = this.Clip;
	  double x =this.Pos.x;
	  double y = this.Pos.y;
	  double rotate = this.Angle;
	  re+=""+wpnid;
	  re+=","+ammo;
	  re+=","+clip;
	  re+=","+x;
	  re+=","+y;
	  re+=","+rotate;
	  return re;
}
}
