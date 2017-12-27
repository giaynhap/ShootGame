import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.Timer;

public class Process implements Runnable {

	private Thread hThread;
	private boolean isRunning=false;
	private BufferedImage canvas;
	private Graphics2D hGrap;
	static public int Width,Height; 
	public static Camera cam = new Camera();
	
	static public ArrayList<Object> GameObject = new ArrayList<Object> ();
	static public ArrayList<Object> GameEffect= new ArrayList<Object> ();
	static public ArrayList<Object> PlayerList= new ArrayList<Object> ();
	public int CurrenID = -1;
	public static Player CurrenPlayer = new Player(false);
	public static Set<Integer> pressed = new HashSet<Integer>();
	 
	static public Graphics2D backScreenGrap ;
	public Process()
	{
		GameObject.clear();
		canvas = MainDisplay.DisplayScr.Canvas;
		hGrap = MainDisplay.DisplayScr.hGrap;
		Width = canvas.getWidth();
		Height = canvas.getHeight();
		hGrap.setBackground(Color.GRAY);
		
	}
 
	@Override
	public void run() {

		 Timer t = new Timer(30, new ActionListener() {
				@Override
				
				public void actionPerformed(ActionEvent arg0) {
					hGrap.clearRect(0, 0, Width,Height);
					process();
					draw();
					
					//hGrap.drawImage(blackScr, 0,0,Width,Height,null);
					HUD();
					
					
					MainDisplay.display.repaint();
					
				}
	         });
		 t.start();
		 /*
		while(isRunning)
		{
			
			
			
			try {
				hGrap.clearRect(0, 0, this.Width,this.Height);
				process();
				draw();
				
				//hGrap.drawImage(blackScr, 0,0,Width,Height,null);
				HUD();
				
				
				MainDisplay.display.repaint();
				hThread.sleep(10);
				
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}
			
		}
		*/
	}
	public void CreateListPlayer()
	{
		
	}
	public void CreateUPlayer()
	{
		CurrenID =0;
		CurrenPlayer.setPos(200,200);
		cam.Parents= CurrenPlayer;
		GameObject.add(CurrenPlayer);
		CurrenPlayer.Name="GiayNhap";
		CurrenPlayer.team=1;
		CurrenPlayer.ID=0;
		PlayerList.add(CurrenPlayer);
		for (int i=0;i<10;i++)
		{
			MapEnt hp = new MapEnt();
			GameObject.add(hp);
			hp.setPos(500+i/3*200,300+i%3*200);
		//	Player hp2 = new Player();
		//	hp2.Name="Bot "+i;
		//	GameObject.add(hp2);
		///hp2.setPos(400+i/3*200,300+i%3*200);
		}
	
		//Grenade gn = new Grenade(CurrenPlayer,WpnData.Weapons.get(4));
		//gn.img = Resource.C4Img;
		//GameObject.add(gn);
		 
			
			WpnData.DropEntWpn(5,330, 100);
			WpnData.DropEntWpn(4,300, 100);
			WpnData.DropEntWpn(1,200, 100);
			WpnData.DropEntWpn(2,230, 100);
			WpnData.DropEntWpn(3,260, 100);
	 
		 
	}
	public void start()
	{
		if (hThread==null) hThread =new Thread(this);
		if(isRunning) return;
		isRunning=true;
		hThread.start();	
		CreateUPlayer();
	}
	public void stop()
	{
		isRunning=false;
	}
	private void process()
	{
		//key
		for (int key : pressed) {
		    
			if (KeyEvent.VK_UP==key||KeyEvent.VK_W==key)
			
				CurrenPlayer.setMove(0);
			 if (KeyEvent.VK_DOWN==key||KeyEvent.VK_S==key)
				CurrenPlayer.setMove(1);
			 if (KeyEvent.VK_A==key)
				CurrenPlayer.setMove(2);
			 if (KeyEvent.VK_D==key)
				CurrenPlayer.setMove(3);	
			 if (KeyEvent.VK_RIGHT==key)
				 CurrenPlayer.Angle+=0.1;
			 if (KeyEvent.VK_LEFT==key)
				 CurrenPlayer.Angle-=0.1;
			 if (KeyEvent.VK_R==key)
			 {
				 CurrenPlayer.doReload();
			 }
			 if (KeyEvent.VK_G==key)
			 {
				 CurrenPlayer.Drop();
			 
			 }	 
			 if (KeyEvent.VK_1==key)
			 {
				 Weapon obj = CurrenPlayer.setWeapon(WpnType.LeftHand);
				 if (obj!=null)
				 {
				 CurrenPlayer.mWeapon = obj; 
				 
				 }
				 
			 }
			 if (KeyEvent.VK_2==key)
			 {
				 Weapon obj = CurrenPlayer.setWeapon(WpnType.DualHand2);
				 if (obj!=null)
				 {
				 CurrenPlayer.mWeapon = obj; 
				 
				 }
			 }
			 if (KeyEvent.VK_3==key)
			 {
				 Weapon obj = CurrenPlayer.setWeapon(WpnType.RightHand);
				 if (obj!=null)
				 {
				 CurrenPlayer.mWeapon = obj; 
				 
				 }
			 }
			 if (KeyEvent.VK_5==key)
			 {
				 Weapon obj = CurrenPlayer.setWeapon(WpnType.LeftHand2);
				 if (obj!=null)
				 {
				 CurrenPlayer.mWeapon = obj; 
				 
				 }
			 }
			}
		//
		cam.move();
		
		for (int  i=0 ;i<GameObject.size();i++)
		{
			Object obj = GameObject.get(i);
			if (obj.Type!=1)
			{
				if (!obj.isAlive) continue;
				if (obj.remove) 
				{
					GameObject.remove(i);
					i--;
					continue;
				}
				
					for (int j=0;j< GameObject.size();j++ )
					{
						Object obj2 = GameObject.get(j);
						if (obj2.Parents==obj||obj.Parents==obj2||obj==obj2) continue;
						if (obj2.isAlive)
						obj.checkTouch(obj2);
						
					}
					obj.move();
				
			}
		}
		
		Vector Pos = cam.toScreenPos();
		Pos.x+=CurrenPlayer.Pos.x;
		Pos.y+=CurrenPlayer.Pos.y;
		if(	CurrenPlayer.isPressfire) 	CurrenPlayer.Fire();
		
	}
	private void draw()
	{
		for (int  i=0;i<GameEffect.size();i++)
		{
			Object obj = GameEffect.get(i);
			if (obj.remove) 
			{
				GameEffect.remove(i);
				i--;
				continue;
			}
			
			obj.draw(cam);
		}
		for (int  i=0;i<GameObject.size();i++)
		{
			Object obj = GameObject.get(i);
			
			Vector dis = new Vector(obj.Pos.x - CurrenPlayer.Pos.x ,obj.Pos.y-CurrenPlayer.Pos.y);
			/*double aln = CurrenPlayer.pointToRotate(obj.Pos);
			int add =0;
			
			int d1 = (int) Math.toDegrees( CurrenPlayer.Angle-Math.PI/3);
			int d2=d1+120;
			int cc1 =0;
		
			int f1 = (int) Math.toDegrees(aln) ;
	*/
			
		
		//if (obj!=CurrenPlayer)
		//	System.out.println("a:"+d1+"a2: "+d2+" al: "+f1);
			double Length =dis.length()  ;
			if ((obj==CurrenPlayer)||(Length<CurrenPlayer.orlength )||obj.Type==1 )
			{
				 if (obj.isAlive)
				obj.draw(cam);
			//	Vector abs  = cam.toScreenPos();
				//hGrap.drawLine((int)(abs.x+obj.Pos.x), (int)(abs.y+obj.Pos.y), (int)( abs.x+CurrenPlayer.Pos.x), (int)(abs.y+CurrenPlayer.Pos.y));
			}
		}
		
	}
	
	private void HUD()
	{
		if (CurrenPlayer.Health<=0 )
		{
			hGrap.setColor( new Color(255,0,0,100));
			hGrap.fillRect(0, 0, Width, Height);
		}
		hGrap.setColor(Color.WHITE);
	//	hGrap.drawString("Camera: "+cam.Pos.x+" : "+cam.Pos.y,0,10);
		
		
		
		hGrap.drawImage(Resource.UI_PlayerImg,
				10,Process.Height-110,null);
		hGrap.drawImage(Resource.UI_HPImg,
				100,Process.Height-70,null);
		hGrap.drawString( CurrenPlayer.Health+"",130,Process.Height-55);
		if (CurrenPlayer.mWeapon!=null)
		{
			FontMetrics metrics = hGrap.getFontMetrics(Resource.font);
			hGrap.drawString( CurrenPlayer.mWeapon.name,Process.Width-metrics.stringWidth( CurrenPlayer.mWeapon.name)-10,Process.Height-65);	
			if (CurrenPlayer.mWeapon.mWpnType!=WpnType.RightHand&&CurrenPlayer.mWeapon.mWpnType!=WpnType.LeftHand2)
			{
				hGrap.drawImage(Resource.UIAMOImg,
						Process.Width-200,Process.Height-60,null);
				hGrap.drawString(CurrenPlayer.mWeapon.Ammo+"" ,Process.Width-120-metrics.stringWidth(CurrenPlayer.mWeapon.Ammo+""),Process.Height-45);
				hGrap.drawString(CurrenPlayer.mWeapon.Clip +"" ,Process.Width-110,Process.Height-45);
				if (CurrenPlayer.isReloading)
				{
					hGrap.drawString("Reloading",Process.Width-metrics.stringWidth("Reloading")-10,Process.Height-80);
				}
			}
		}
		
		MsgGame.Draw();
	}
	
	/* public static void Command(ServerTCP.client client, Player Current,StringTokenizer stoken)
	{
		
        while (stoken.hasMoreTokens()) {  
        	 String fargument = stoken.nextToken(",");
        	// System.out.println(fargument);
        	/*
        	  if (fargument.equals("createplayer"))
             {
        		
        		
        
        		Current.Name =  stoken.nextToken(",");
     
        		Current.ID =Process.PlayerList.size();
        		client.send("-1,set_id,"+Current.ID);
        		/*
        		for (int i=0;i<PlayerList.size();i++)
        		{
        			client.send(i+","+((Player)PlayerList.get(i)).toString());
        		}
        		
				Process.GameObject.add(Current);
				
				MainDisplay.TCP.sendAllClient(Current.ID+","+Current.toString(),client);
	        	Process.PlayerList.add(Current);
	        		
        	 }
        	else 
        	 //////////////////
        	if (fargument.equals("setpos"))
        	{
        	
        		 double x = Double.parseDouble(stoken.nextToken(","));
        		 double y = Double.parseDouble(stoken.nextToken(","));
        		 Current.setPos(new Vector(x,y));
        		
        	}
        	else if (fargument.equals("setrotate"))
        	{
        		
        		 double alg = Double.parseDouble(stoken.nextToken(","));
        		 Current.Angle = alg;
        	}
        	else if (fargument.equals("move"))
        	{
        		
        		 int alg = Integer.parseInt(stoken.nextToken(","));
        		 Current.setMove(alg);
        		 
        	}
        	else if (fargument.equals("fire")) {
        		 Current.Fire();;
        	
        	}
        	else if (fargument.equals("drop")) {
        		 Current.Drop();
        	}
        	else if (fargument.equals("give_wpn"))
        	{
        		 
        		 int alg = Integer.parseInt(stoken.nextToken(","));
        		 Current.GiveWeapon(alg);
        	}
        	
        	else if (fargument.equals("set_wpn"))
        	{
        		 
        		 int alg = Integer.parseInt(stoken.nextToken(","));
        		Weapon obj = Current.setWeapon(alg);
        		if (obj!=null)
        		 Current.mWeapon =obj; 
        	}
        	else if (fargument.equals("reload"))
        	{
        		 
        		 
        		 Current.doReload();
        	}
        	else if (fargument.equals("set_name"))
        	{
        		Current.Name = stoken.nextToken(",");
        	}
        	else if (fargument.equals("set_amo"))
        	{
        		 int alg = Integer.parseInt(stoken.nextToken(","));
        		Current.mWeapon.Ammo = alg;
        	}
        	else if (fargument.equals("set_clip"))
        	{
        		 int alg = Integer.parseInt(stoken.nextToken(","));
        		Current.mWeapon.Ammo = alg;
        	}
        	else if (fargument.equals("set_health"))
        	{
        		int alg = Integer.parseInt(stoken.nextToken(","));
        		Current.Health = alg;
        	}
        	else if (fargument.equals("set_id"))
        	{
        	     
        		Current.ID = Integer.parseInt(stoken.nextToken(","));


        	}
        	else if (fargument.equals("reloaded"))
        	{
        		Current.Reload();
        		Current.isReloading=false;
        	}
        	else if (fargument.equals("add_ent_wpn"))
        	{	
        		WpnData.DropEntWpn( Integer.parseInt(stoken.nextToken(",")), Integer.parseInt(stoken.nextToken(",")), Integer.parseInt(stoken.nextToken(",")));
        	}
        	else if (fargument.equals("add_drop_wpn"))
        	{	
        			int id = Integer.parseInt(stoken.nextToken(","));
        		
        			int ammo = Integer.parseInt(stoken.nextToken(","));
        			int clip= Integer.parseInt(stoken.nextToken(","));
        			double x = Double.parseDouble(stoken.nextToken(","));
        			double y = Double.parseDouble(stoken.nextToken(","));       			double rotate= Double.parseDouble(stoken.nextToken(","));
	        		WpnData.Wpn wpn = WpnData.Weapons.get(id-1);
	        		Weapon mWeapon = new Weapon(null);
	        		mWeapon.Angle = rotate;
	        		mWeapon.Clip=clip;
	        		mWeapon.Ammo=ammo;
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
	}*/
	public void onKill(Player killer,Weapon weapon, Player victim)
	{
		
	}
	static public class MouseMotionProc implements MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent arg0) {

			Point Mouse =arg0.getPoint();
			
			double a = CurrenPlayer.pointToRotate( cam.screenToPoint(Mouse));
			CurrenPlayer.Angle = a; 
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			
		 
			Point Mouse =arg0.getPoint();
			
			double a = CurrenPlayer.pointToRotate( cam.screenToPoint(Mouse));
			CurrenPlayer.Angle = a; 
		 
		}
		
	}
	static public class MouseProc implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
		
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		
			CurrenPlayer.isPressfire=true;
		
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
			CurrenPlayer.isPressfire=false;
		}
		
	}
	static public class KeyProc implements KeyListener
	{
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			
			pressed.add( e.getKeyCode());
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			 pressed.remove(e.getKeyCode());
		}

		@Override
		public void keyTyped(KeyEvent e) {
	
			
		}
		
		
	}
	
}
