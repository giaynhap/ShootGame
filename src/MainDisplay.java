import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;


public class MainDisplay {

	private JFrame frmgiaynhapgame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDisplay window = new MainDisplay();
					window.frmgiaynhapgame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainDisplay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	static DisplayScr display;
	static Process hProcess;
	public static boolean SERVER = false;
	static int MapSize =1000;
	public static MsgGame HUDGAME = new MsgGame();
 
	private void initialize() {
		new Resource().initialize();
		new WpnData().initialize();
		frmgiaynhapgame = new JFrame();
		//frmgiaynhapgame.setType(Type.UTILITY);
		frmgiaynhapgame.setBackground(Color.WHITE);
		frmgiaynhapgame.setResizable(false);
		
		frmgiaynhapgame.setBounds(100, 100, 731, 411);
		frmgiaynhapgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	 	SERVER = true;
	 	 
		display = new DisplayScr( 731, 411);
		frmgiaynhapgame.getContentPane().add(display);
		hProcess=new Process();
		frmgiaynhapgame.addKeyListener(new Process.KeyProc() );
		frmgiaynhapgame.addMouseListener(new Process.MouseProc());
		frmgiaynhapgame.addMouseMotionListener(new Process.MouseMotionProc());
		hProcess.start();
		 
	}
	static class DisplayScr extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public static BufferedImage Canvas;
		public static Graphics2D hGrap;
		public DisplayScr(int w,int h)
		{
			DisplayScr.Canvas = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			this.setBounds(0,0,w, h);
			JLabel label = new JLabel(new ImageIcon(Canvas) );
			this.add(label);
			DisplayScr.hGrap = DisplayScr.Canvas.createGraphics();
			applyQualityRenderingHints(DisplayScr.hGrap);
			hGrap.setFont(Resource.font);
		}
		  public static void applyQualityRenderingHints(Graphics2D g2d) {
		    //    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		     //   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		    }
	}
}
