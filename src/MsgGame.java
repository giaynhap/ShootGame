
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;
public class MsgGame {
	
	
public	class Death
	{
		public int k_team;
		public int v_team;
		public String killer;
		public BufferedImage img;
		public String victim;
		public int nKill;
		public BufferedImage GetImg;
		public void CreateDraw()
		{
			String Color[] = new String []{"#96f2a0","#f47442","#31bcae"};
			BufferedImage buffer = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
			Font gfont = Resource.font;
			Graphics2D hgrap = buffer.createGraphics();
			hgrap.setFont(gfont);
			FontMetrics metrics = hgrap.getFontMetrics(gfont);
		
			String s =this.killer+this.victim;
			
			
			
			BufferedImage buffer2 = new BufferedImage(metrics.stringWidth(s)+32,20,BufferedImage.TYPE_INT_ARGB);
			Graphics2D hgrap2 = buffer2.createGraphics();
			hgrap2.setFont(gfont);
			hgrap2.setColor( java.awt.Color.decode(Color[k_team]));
			hgrap2.drawString(killer, 0,10);
			hgrap2.drawImage(img, metrics.stringWidth(killer), -10,null);
			
			hgrap2.setColor( java.awt.Color.decode(Color[v_team]));
			hgrap2.drawString(victim, metrics.stringWidth(killer)+32,10);
			GetImg= buffer2;
			
		//	hgrap.drawString( s,this.parents.WinWidth/2-metrics.stringWidth(s)/2, 100);
		}
	}

public static ArrayList<Death > DeathMSG = new ArrayList<Death>();
Timer timeDel;
public void MsgDeathAdd(Player killer, BufferedImage wpn,Player victim)
{
	 if (DeathMSG.size()>3) DeathMSG.remove(0);
	 Death pl = new Death();
	 
	 pl.k_team = killer.team;
	 pl.v_team = victim.team;
	 pl.killer = killer.Name;
	 pl.victim = victim.Name;
	 pl.img = wpn;
	 pl.CreateDraw();
	 
	 DeathMSG.add(pl);
	 if (timeDel==null)
	 {
	 timeDel = new Timer(3000, new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent arg0) {
				if (DeathMSG.size()>0)
				 DeathMSG.remove(0);
			 
			}
       });
	 
	 timeDel.start();
	 }
	 timeDel.restart();
	 
}
public static void Draw()
{
	Graphics2D hGrap = MainDisplay.DisplayScr.hGrap;
	for (int i=0;i< DeathMSG.size();i++)
	{
		Death msg  = DeathMSG.get(i);
		hGrap.drawImage(msg.GetImg,Process.Width-msg.GetImg.getWidth()-10,i*20+10,null);
	}
}

}
