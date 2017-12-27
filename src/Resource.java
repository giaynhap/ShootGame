
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {
	static public int Port = 2402;
	static public  BufferedImage PlayerImg;
	static public  BufferedImage Box1Img;
	static public BufferedImage BloodImg;
	static public BufferedImage AK47Img;
	static public BufferedImage M4A1Img;
	static public BufferedImage KnifeImg;
	static public BufferedImage DeagleImg;
	static public BufferedImage ExpImg;
	static public BufferedImage C4Img;
	static public BufferedImage UIAMOImg;
	static public BufferedImage UI_HPImg;
	static public BufferedImage UI_PlayerImg;
	static public Font font;
	public boolean initialize()
	{
		
		try {
			font = new Font ("Tahoma",1,12);
			Resource.PlayerImg = ImageIO.read(new File( "player.png"));
			Resource.Box1Img = ImageIO.read(new File( "Box1.jpg"));
			Resource.BloodImg= ImageIO.read(new File( "blood.png"));
			Resource.AK47Img= ImageIO.read(new File( "AK47Img.png"));
			Resource.KnifeImg= ImageIO.read(new File( "KnifeImg.png"));
			Resource.M4A1Img= ImageIO.read(new File( "M4A1Img.png"));
			Resource.DeagleImg= ImageIO.read(new File( "DeagleImg.png"));
			Resource.ExpImg= ImageIO.read(new File( "exp2.png"));
			Resource.C4Img = ImageIO.read(new File( "C4Img.png"));
			Resource.UIAMOImg = ImageIO.read(new File( "UI_Amo.png"));
			Resource.UI_HPImg = ImageIO.read(new File( "UI_HPImg.png"));
			Resource.UI_PlayerImg = ImageIO.read(new File( "UI_PlayerImg.png"));
		
		} catch (IOException e) {
			
			System.out.println(e);
			 return false;
		}
		return false;
	}
}
