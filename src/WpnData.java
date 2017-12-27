import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WpnData {
	public class  Wpn
	{
		public int id ;
		public WpnType Type;
		public int Ammo;
		public int Clip;
		public int DelayReload;
		public float Dmg;
		public int Delay;
		public float Weight;
		public BufferedImage Img;
		public String Name;
		public float SizeDmg;
	}
	public static ArrayList<Wpn> Weapons = new ArrayList<Wpn> ();
	public  void initialize()
	{

		Wpn Knife =new Wpn();
		Knife.id = Weapons.size()+1;
		Knife.Ammo = 0;
		Knife.Clip = 0;
		Knife.DelayReload =0;
		Knife.Dmg=15;
		Knife.Delay=200;
		Knife.Weight=3.2f;
		Knife.Type=WpnType.RightHand;
		Knife.Img = Resource.KnifeImg;
		Knife.Name = "Knife";
		Weapons.add(Knife);
		
		
		Wpn AK47 =new Wpn();
		AK47.id = Weapons.size()+1;
		AK47.Ammo = 30;
		AK47.Clip = 90;
		AK47.DelayReload =1000;
		AK47.Dmg=20;
		AK47.Delay=170;
		AK47.Weight=4.1f;
		AK47.Type=WpnType.LeftHand;
		AK47.Img = Resource.AK47Img;
		AK47.Name = "AK47";
		Weapons.add(AK47);
		
		Wpn M4A1 =new Wpn();
		M4A1.id = Weapons.size()+1;
		M4A1.Ammo = 30;
		M4A1.Clip = 90;
		M4A1.DelayReload =1000;
		M4A1.Dmg=15;
		M4A1.Delay=130;
		M4A1.Weight=3.2f;
		M4A1.Type=WpnType.LeftHand;
		M4A1.Img = Resource.M4A1Img;
		M4A1.Name = "M4A1";
		Weapons.add(M4A1);
		
		
		Wpn Deagle =new Wpn();
		Deagle.id = Weapons.size()+1;
		Deagle.Ammo = 7;
		Deagle.Clip = 21;
		Deagle.DelayReload =1300;
		Deagle.Dmg=32;
		Deagle.Delay=500;
		Deagle.Weight=2.2f;
		Deagle.Type=WpnType.DualHand2;
		Deagle.Img = Resource.DeagleImg;
		Deagle.Name = "Deagle";
		Weapons.add(Deagle);
		
		Wpn C4 =new Wpn();
		C4.id = Weapons.size()+1;
		C4.Ammo = 0;
		C4.Clip = 0;
		C4.DelayReload =1300;
		C4.Dmg=200;
		C4.Delay=5000;
		C4.Weight=2.2f;
		C4.Type=WpnType.LeftHand2;
		C4.Img = Resource.C4Img;
		C4.Name = "C4";
		C4.SizeDmg=400;
		Weapons.add(C4);
		
	}
	public static void DropEntWpn(int id,int x,int y)
	{
		Wpn wpn = Weapons.get(id-1);
		Weapon mWeapon = new Weapon(null);
		mWeapon.Clip=wpn.Clip;
		mWeapon.Ammo=wpn.Ammo;
		mWeapon.idWeapon=id;
		mWeapon.img=wpn.Img;
		mWeapon.delay_reload=wpn.DelayReload;
		mWeapon.mGunDelay=wpn.Delay;
		mWeapon.dmg = wpn.Dmg;
		mWeapon.MaxAmmo = wpn.Ammo;
		mWeapon.setPos(x,y);
		mWeapon.img = wpn.Img;
		mWeapon.name = wpn.Name;
		mWeapon.mWpnType = wpn.Type;
		Process.GameObject.add(mWeapon);
	}
}
