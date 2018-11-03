import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Pong extends Applet implements Runnable,KeyListener{
	
	//Double Buffering
	Image dbImage;
	Graphics dbg;
	
	//Reusable Values;
	int rectWidth = 8;
	int rectHeight = 80;
	int player1Score = 0,player1ScoreX,player1ScoreY;
	int player2Score = 0,player2ScoreX,player2ScoreY; 
	int ballWidth = 16,ballHeight = 16,ballX = 400,ballY = 81;
	int leftY = 264, rightY = 264;//Taking only y co-ordinates as it can only move in y-axis
	int speed = 16;
	
	//Shape Objects
	Shape rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
	Shape rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
	Shape circle = new Ellipse2D.Double(ballX,ballY,ballWidth,ballHeight);
	
	@Override
	public void init(){
		setBackground(Color.BLACK);
		setSize(800,648);
		addKeyListener(this);
		setFocusable(true);
		Frame frame = (Frame)this.getParent().getParent();
		frame.setTitle("Pong");
		super.init();
	}
	
	@Override
	public void start(){
		Thread thread = new Thread(this);
		thread.start();
		super.start();
	}
	
	@Override
	public void destroy(){
		super.destroy();
	}
	
	@Override
	public void stop(){
		super.stop();
	}
	
	
	public void paint(Graphics g){
		Graphics2D gd = (Graphics2D)g;
		
		//Border white rect as apple window size is changing
		gd.setColor(Color.WHITE);
		gd.drawRect(0,0,800,648);
		
		//Two rectangles:
		gd.setColor(Color.BLUE);
		gd.fill(rectLeft);
		gd.fill(rectRight);
		
		//Score:
		gd.setColor(Color.WHITE);
		gd.drawString(String.valueOf(player1Score),8,16);
		gd.drawString(String.valueOf(player2Score),784,16);
		
		//Ball:
		gd.setColor(Color.YELLOW);
		gd.fill(circle);
	}
	
	public void update(Graphics g){
		
		//Double Buffering
		dbImage = createImage(800,648);
		dbg = dbImage.getGraphics();
		paint(dbg);
		g.drawImage(dbImage,0,0,this);
		
	}
	
	@Override
	public void run(){
		
		while(true){
			repaint();
			update(getGraphics());
			
			try{
				Thread.sleep(15);
			}catch(Exception e){
				System.out.println("Error Occured");
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		/*
		switch(key)
		{
			case KeyEvent.VK_UP:
				rightY-=8;
				rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
				break;
			case KeyEvent.VK_DOWN:
				rightY+=8;
				rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
				break;
			case KeyEvent.VK_W:
				leftY-=8;
				rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
				break;
			case KeyEvent.VK_S:
				leftY+=8;
				rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
				break;
		}*/
		
		if(key == KeyEvent.VK_UP){
			rightY-=speed;
			rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
		}
		
		if(key == KeyEvent.VK_DOWN){
			rightY+=speed;
			rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
		}
		
		if(key == KeyEvent.VK_W){
			leftY-=speed;
			rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
		}
		
		if(key == KeyEvent.VK_S){
			leftY+=speed;
			rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
		}
		repaint();
	}
	
	//We won't use this functions
	@Override
	public void keyReleased(KeyEvent e){}
	@Override
	public void keyTyped(KeyEvent e){}
}