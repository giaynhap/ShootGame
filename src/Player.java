import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

public class Player extends Object{
	public Player(boolean bot)
	{
	
		super();
		 
		this.setSize(32,32);
		this.GiveWeapon(1);
		/*mWeapon= new Weapon(this);
		mWeapon.img = Resource.AK47Img;
		mWeapon.setSize(32,32);
		*/
	}
	public boolean isReloading =false;
	public int team=0;
	public boolean isCanFire=true;
	public boolean isPressfire=false;
	float coll =2;
	public int mGunDelay=100;
	public double MaxSpeed=2.0;
	public double currenspeed;
	public Weapon mWeapon;
 
	ArrayList <Weapon> WeaponData = new ArrayList <Weapon>();
	int mv =1;
	Timer timerel;
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
		if (this.speed>0)
		{
			coll+=1*mv;
			if (Math.abs(coll)>=10) mv*=-1;
			shGrap.fillRect(img.getWidth()/2-10, (int)(img.getHeight()/2+coll), 7, 7);
			shGrap.fillRect(img.getWidth()/2+3, (int)(img.getHeight()/2-coll), 7, 7);
		}
		shGrap.drawImage(img, 0,0,null);
		hGrap.drawImage(createDropShadow(img),aff2,null);
		hGrap.drawImage(newImg,aff,null);
	 
		//hGrap.drawLine((int)Pos.x,(int)Pos.y,(int)( Pos.x+Math.cos(this.Angle-Math.PI/2)*200), (int)(Pos.y+Math.sin(this.Angle-Math.PI/2)*200));
		//hGrap.drawLine((int)Pos.x,(int)Pos.y,(int)( Pos.x+Math.cos(this.Angle-Math.PI/2-Math.PI/3)*200), (int)(Pos.y+Math.sin(this.Angle-Math.PI/2-Math.PI/3)*200));
		//hGrap.drawLine((int)Pos.x,(int)Pos.y,(int)( Pos.x+Math.cos(this.Angle-Math.PI/2+Math.PI/3)*200), (int)(Pos.y+Math.sin(this.Angle-Math.PI/2+Math.PI/3)*200));
		
	//	
		if (mWeapon!=null)
		mWeapon.draw(cam);
		
	}
	public void doReload()
	{
		
		if (this.isReloading|| this.mWeapon.mWpnType==WpnType.RightHand) return;
		if (this.mWeapon.Clip<=0) return;
		 
		this.isReloading= true;
		timerel = new Timer(this.mWeapon.delay_reload, new ActionListener() {
 			@Override
 			
 			public void actionPerformed(ActionEvent arg0) {
 			
 				Reload();
 				 
 				timerel.stop();
 			}
          });
		timerel.start();
	}
	@Override
	public void move()
	{
		super.move();
		if (mWeapon!=null)
		{
			mWeapon.move();
			
			if (!this.isPressfire && this.mWeapon.Ammo<=0)
			{
				doReload();
				
			}
		}
		
	}
	
	
	@Override
	public BufferedImage getIMG()
	{	if (this.mWeapon!=null)
		{
			 int x = (this.mWeapon.mWpnType.getValue()-1)%2;
			 int y = ((this.mWeapon.mWpnType.getValue()-1)/2);
			 
			return Resource.PlayerImg.getSubimage(32*x, 32*3+32*y, 32, 32);
		
		}
		
		return Resource.PlayerImg.getSubimage(0, 3*32, 32, 32);
	}
	public void setMove(int a)
	{
		if(checkTouchCurrent()) return;
			
	 
		this.speed=MaxSpeed;
		double alg = 0.0;
		if (a==1||a==3) alg=Math.PI;
		if (a==1||a==0)
		this.setVelocity(new Vector(Math.sin(this.Angle+alg),-Math.cos( this.Angle-alg)));
		else if (a==2||a==3)
			this.setVelocity(new Vector(-Math.cos(this.Angle+alg),Math.sin(this.Angle-alg )));
		
		
	}
	public void TakeDamge(float dmg,Player Attaker,BufferedImage wpn)
	{
		if (this.Health<=0||!this.isAlive ) return;
		this.Health-= (int)dmg;
		CreateBlood(this);
		if (Health<=0)
		{
			MainDisplay.HUDGAME.MsgDeathAdd(Attaker, wpn, this);
			Drop();
		}
	}
	public void TakeDamge(Bullet bul)
	{
		this.Health-= (int)bul.dmg;
		CreateBlood(this);
		if (Health<=0)
		{
			MainDisplay.HUDGAME.MsgDeathAdd((Player)bul.Parents,((Player)bul.Parents).mWeapon.getIMG(), this);
			Drop();
		}
	}
	public double pointToRotate(Vector p)
	{  
		double angle=0.0;
		
		Vector Vec  = new Vector(this.Pos.x-p.x,this.Pos.y-p.y);
		
		if (this.Pos.length()<=0) return Double.NaN;
		
		double Length = Vec.length();
		Vector Ox = new Vector(this.Pos.x-p.x,0);
		double oxvecmul	 = Vec.x*Ox.x+Vec.y*Ox.y;
		
		angle =  Math.acos(Math.abs(oxvecmul)/(Length*Ox.length()))-Math.PI/2;
		if (Double.isNaN(angle))
		{
			if (p.y<=this.Pos.y ) angle= Math.PI;
		
			angle = 0.0;
			
		}
		if (p.x>= this.Pos.x) angle =Math.PI*2-angle;
		if (p.y>this.Pos.y ) angle =Math.PI  -angle ;
		if (angle<0) angle+=Math.PI*2;
		angle-=Math.PI*2*(int)(angle/(Math.PI*2));
		if (angle<0)angle = Math.PI*2+angle;
		return angle;
	
	}
 
	public Object objcurrent_t=null;

	@Override
	public void touch(Object obj)
	{
		
		if (obj.Type<=1)
		{
		
			objcurrent_t= obj;
			if (this.speed>0)
			{
				Vector nVec=new Vector(-this.Velocity.x*Math.abs(this.MaxSpeed*2),-this.Velocity.y*Math.abs(this.MaxSpeed*2));
				this.addPos(nVec);
			}
			
		
			
		}else if (obj.Type==3)
		{
			if (!this.isAlive||this.Health<=0) return;
			if (((Weapon)obj).oldPar ==this) return;
			
			if (this.mWeapon!=null)
			{
				if (this.mWeapon.idWeapon==((Weapon)obj).idWeapon)
				{
					this.mWeapon.Clip+=((Weapon)obj).Clip;
					obj.remove();
				}
				
			
			}
			
			if (!checkHas(((Weapon)obj).mWpnType))
			if ((this.mWeapon==null||this.mWeapon.mWpnType != ((Weapon)obj).mWpnType))
			{
				
					this.mWeapon=new  Weapon(this);
					WpnData.Wpn wpn = WpnData.Weapons.get(((Weapon)obj).idWeapon-1);
					this.mWeapon.Clip=((Weapon)obj).Clip;
					this.mWeapon.Ammo=((Weapon)obj).Ammo;
					this.mWeapon.idWeapon=((Weapon)obj).idWeapon;
					this.mWeapon.img=((Weapon)obj).img;
					this.mWeapon.delay_reload=wpn.DelayReload;
					this.mWeapon.mGunDelay=wpn.Delay;
					this.mWeapon.dmg = wpn.Dmg;
					this.mWeapon.MaxAmmo = wpn.Ammo;
					this.mWeapon.name = wpn.Name;
					this.mWeapon.mWpnType = wpn.Type;
					obj.remove();
					WeaponData.add(mWeapon);
			}
		}
		else
		{
		
			
			Bullet bul = (Bullet)obj;
			if (bul.remove==true) return;
			bul.remove();
			Vector nVec=new Vector(-this.Velocity.x*Math.abs(this.MaxSpeed),-this.Velocity.y*Math.abs(this.MaxSpeed));
			this.addPos(nVec);
			Player p =(Player)bul.Parents;
			if (p.team!=0&&p.team==this.team) return;
			TakeDamge(bul);
			
		
		}
	
	}
	public void GiveWeapon(int id)
	{
		if (!this.isAlive) return ;
		this.mWeapon=new  Weapon(this);
		WpnData.Wpn wpn = WpnData.Weapons.get( id-1);
		this.mWeapon.Clip=wpn.Clip;
		this.mWeapon.Ammo=wpn.Ammo;
		this.mWeapon.idWeapon=wpn.id;
		this.mWeapon.img=wpn.Img;
		this.mWeapon.delay_reload=wpn.DelayReload;
		this.mWeapon.mGunDelay=wpn.Delay;
		this.mWeapon.dmg = wpn.Dmg;
		this.mWeapon.MaxAmmo = wpn.Ammo;
		this.mWeapon.name = wpn.Name;
		this.mWeapon.mWpnType = wpn.Type;
		WeaponData.add(mWeapon);
	}
	public boolean checkHas(WpnType wpnt)
	{
		for (Weapon obj : this.WeaponData)
		{
		 
			if (obj.mWpnType==wpnt) return true;
		}
		return false;
	}
	public Weapon setWeapon(WpnType type)
	{
		for (Weapon obj : this.WeaponData)
		{
			if (timerel!=null) this.timerel.stop();
			this.isReloading= false;
			if (obj.mWpnType==type) return obj;
		}
		return null;
	}
	public Weapon setWeapon(int type)
	{
		for (Weapon obj : this.WeaponData)
		{
			if (timerel!=null) this.timerel.stop();
			this.isReloading= false;
			if (obj.mWpnType.getValue()==type) return obj;
		}
		return null;
	}
	public void CreateBlood(Object bul)
	{
		SprAnim blood = new SprAnim(this,64,64);
		blood.img = Resource.BloodImg;
		blood.hFrame=3;
		blood.Play();
		blood.Loop=true;
		blood.Angle = bul.Angle;
		Process.GameEffect.add(blood);
	}
	 
	public void Drop()
	{
		
		if(this.mWeapon==null) return;
		
		if (this.mWeapon.mWpnType == WpnType.RightHand) return;
			Weapon drop = new Weapon(null);
			drop.setPos(this.Pos);
			this.isReloading = false;
			if (timerel!=null)
			timerel.stop();
			
			drop.Angle = this.Angle;
			drop.img = mWeapon.img;
			drop.Clip = this.mWeapon.Ammo+this.mWeapon.Clip;
			drop.dmg=this.mWeapon.dmg;
			drop.idWeapon = this.mWeapon.idWeapon;
			drop.oldPar = this;
			drop.Ammo=0;
			drop.mWpnType= this.mWeapon.mWpnType;
			drop.setVelocity( new Vector(Math.sin(this.Angle), -Math.cos(this.Angle)));
			drop.speed=3;
			Process.GameObject.add(drop);
			WeaponData.remove(this.mWeapon);
			this.mWeapon= null;
			if (WeaponData.size()>0)
			this.mWeapon = WeaponData.get(0);
	}
	public boolean checkTouchCurrent()
	{	if(objcurrent_t==null) return false;
		if (objcurrent_t.isAlive ==false) return false;
		Vector v2 = new Vector(objcurrent_t.Pos.x-(this.Pos.x+this.Velocity.x*this.speed),objcurrent_t.Pos.y-(this.Pos.y+this.Velocity.y*this.speed));
		if (v2.length()<(this.Size.x+objcurrent_t.Size.x)/2) return true;
		
		return false;
	}

	public void Reload()
	{
		if (!this.isAlive) return ;
		if (this.mWeapon.Clip<=0) return ;
	 
		int mrel = this.mWeapon.MaxAmmo-this.mWeapon.Ammo;
		mrel = Math.min(mrel, this.mWeapon.Clip);
		this.mWeapon.Clip-= mrel;
		this.mWeapon.Ammo+=mrel;
		this.isReloading=false;
	
	}
	public void Fire()
	{
		if (!this.isAlive) return ;
	 
		this.mWeapon.Fire();
		isPressfire=true;
		
	}
	@Override
	public String toString()
	{
		String rt ="";
		rt = "set_name,"+this.Name;
		rt = ",set_id,"+this.ID;
		rt+=",setpos,"+this.Pos;
		rt+=",setrotate,"+this.Angle;
		if (this.WeaponData.size()>0)
		for (int i=0;i<this.WeaponData.size();i++ )
		{
			Weapon obj = this.WeaponData.get(i);
			rt+=",give_wpn,"+obj.idWeapon;
			rt+=",set_amo,"+obj.Ammo;
			rt+=",set_clip,"+obj.Clip;
		}
		rt+=",set_health,"+this.Health;
		
		return rt;
		
	}
}
