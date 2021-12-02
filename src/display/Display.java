package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import base.GameThread;
import sound.Music;
import sound.Sound;
import base.Sprite;
import base.Tile;
import game.Game;
import game.GameScene;

public class Display extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3628124518633114387L;
	private static Dimension screenSize;
	private static int windowWidth,windowHeight;
	private static float windowRate;
	private static GameThread gThread;
	private final static JMenuBar windowMenu = new JMenuBar();
	private final static Display display = new Display();
	public static int FramePerSecond = 0;
	public static int FrameThisSecond = 0;
	private static FrameThread fThread;
	
	/*按键判定用开关*/
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	public static boolean select = false;
	public static boolean start = false;
	public static boolean A = false;
	public static boolean B = false;
	public static boolean X = false;
	public static boolean Y = false;
	/*对应按键设置*/
	public static int upButton = KeyEvent.VK_W;
	public static int downButton = KeyEvent.VK_S;
	public static int leftButton = KeyEvent.VK_A;
	public static int rightButton = KeyEvent.VK_D;
	public static int selectButton = KeyEvent.VK_SPACE;
	public static int startButton = KeyEvent.VK_ENTER;
	public static int AButton = KeyEvent.VK_J;
	public static int BButton = KeyEvent.VK_K;
	public static int XButton = KeyEvent.VK_U;
	public static int YButton = KeyEvent.VK_I;
	/*键盘监听*/
	public static KeyListener keyboard = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			int k = e.getKeyCode();
			if(k==upButton) {
				up = true;
			}
			if(k==downButton) {
				down = true;
			}
			if(k==leftButton) {
				left = true;
			}
			if(k==rightButton) {
				right = true;
			}
			if(k==AButton) {
				A = true;
			}
			if(k==BButton) {
				B = true;
			}
			if(k==XButton) {
				X = true;
			}
			if(k==YButton) {
				Y = true;
			}
			if(k==startButton) {
				start = true;
			}
			if(k==selectButton) {
				select = true;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			int k = e.getKeyCode();
			if(k==upButton) {
				up = false;
			}
			if(k==downButton) {
				down = false;
			}
			if(k==leftButton) {
				left = false;
			}
			if(k==rightButton) {
				right = false;
			}
			if(k==AButton) {
				A = false;
			}
			if(k==BButton) {
				B = false;
			}
			if(k==XButton) {
				X = false;
			}
			if(k==YButton) {
				Y = false;
			}
			if(k==startButton) {
				start = false;
			}
			if(k==selectButton) {
				select = false;
			}
		}
	};
	/*鼠标点击*/
	public static boolean mouseClicked = false;
	public static boolean mouseRightClicked = false;
	/*鼠标监听*/
	public static MouseListener mouseClick = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int m = e.getButton();
			if(m==MouseEvent.BUTTON1) {
				mouseClicked = true;
			}
			if(m==MouseEvent.BUTTON3) {
				mouseRightClicked = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			int m = e.getButton();
			if(m==MouseEvent.BUTTON1) {
				mouseClicked = false;
			}
			if(m==MouseEvent.BUTTON3) {
				mouseRightClicked = false;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	};
	/*鼠标坐标*/
	public static int mouseX = -20;
	public static int mouseY = -20;
	/*鼠标位置监听*/
	public static MouseMotionListener mouseLocation = new MouseMotionListener() {
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = (int) (e.getX()/windowRate);
			mouseY = (int) (e.getY()/windowRate);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = (int) (e.getX()/windowRate);
			mouseY = (int) (e.getY()/windowRate);
		}
	};
	private static FocusListener focused = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			GameThread.isRunning = true;
			Music.recover();
			Sound.recoverCurrentSounds();
		}
		@Override
		public void focusLost(FocusEvent e) {
			up = false;
			down = false;
			left = false;
			right = false;
			A = false;
			B = false;
			X = false;
			Y = false;
			start = false;
			select = false;
			GameThread.isRunning = false;
			Music.pauseBGM();
			Sound.pauseCurrentSounds();
		}
	};
	public static class GameWindow extends JFrame {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2507104176820558434L;

		public GameWindow() {
			Toolkit tok = Toolkit.getDefaultToolkit();
			screenSize = tok.getScreenSize();
			this.setMinimumSize(new Dimension(480,270));
			this.setJMenuBar(windowMenu);
			this.setResizable(false);
			this.setTitle("Ahoutsukai Nikki Java");
			windowRate = ((float)screenSize.height-240)/540;
			windowWidth = (int)(960*windowRate);
			windowHeight = (int)(540*windowRate);
			this.getContentPane().setPreferredSize(new Dimension(windowWidth,windowHeight));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addKeyListener(keyboard);
			display.setBackground(Color.black);
			display.addMouseListener(mouseClick);
			display.addMouseMotionListener(mouseLocation);
			this.add(display);
			this.pack();
			this.setLocationRelativeTo(null);
			this.addFocusListener(Display.focused);
			gThread = new GameThread(display);
			fThread = new FrameThread();
			Game.init();
			this.setVisible(true);
			gThread.start();
			fThread.start();
			GameScene.introScene();
		}
	}
	public void paint(Graphics og) {
		super.paint(og);
		/*画面双缓冲初始化*/
		BufferedImage buffer = new BufferedImage(960,540,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffer.createGraphics();
		/*显示*/
		for(int i=0;i<Game.background.size();i++) {
			Tile t = Game.background.get(i);
			if(t.visible) {
				g.drawImage(t.image, t.x-Camera.x, t.y-Camera.y, t.w, t.h, this);
			}
		}
		for(int i=Game.sprites.size()-1;i>=0;i-- ) {
			Sprite sp = Game.sprites.get(i);
			if(sp.getRotate()!=0) {
				float[] rc = sp.getRotateCenter();
				g.rotate(sp.getRotate(), rc[0]-Camera.x, rc[1]-Camera.y);
			}
			
			if(sp.visible) {
				float opa = sp.getOpacity();
				if(opa>=0&&opa<1) {
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opa));
				}
				g.drawImage(sp.show(), sp.x-Camera.x, sp.y-Camera.y, sp.w, sp.h, this);
				if(opa>=0&&opa<1) {
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
				}
				if(sp.mask!=null) {
					opa = sp.mask.opacity;
					if(opa>0&&opa<=1) {
						sp.mask.draw(g);
					}
				}
			}
			
			if(sp.getRotate()!=0) {
				g.setTransform(AffineTransform.getRotateInstance(0));
			}
		}
		for(int i=Game.foreground.size()-1;i>=0;i--) {
			Tile t = Game.foreground.get(i);
			if(t.visible) {
				g.drawImage(t.image, t.x-Camera.x, t.y-Camera.y, t.w, t.h, this);
			}
		}
		g.setColor(screenColor);
		g.fillRect(0, 0, 960, 540);
		/*显示缓冲好的画面*/
		og.drawImage(buffer, 0, 0, windowWidth, windowHeight, this);
		FrameThisSecond++;
		og.setColor(Color.white);
		og.drawString(FramePerSecond+"/60", 0, 30);
		g.dispose();
		buffer.flush();
	}
	public static Color getScreenColor() {
		return screenColor;
	}
	public static void setScreenColor(Color screenColor) {
		Display.screenColor = screenColor;
	}
	private static Color screenColor = new Color(0,0,0,0);
	private static class FrameThread extends Thread {
		public void run() {
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(GameThread.isRunning) {
					FramePerSecond = FrameThisSecond;
					FrameThisSecond = 0;
				}
			}
		}
	}
}
