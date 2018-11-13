import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

public class Pong extends Applet implements Runnable,KeyListener{
	
	//Double Buffering
	Image dbImage;
	Graphics dbg;
	
	//Game stages;
	boolean intro = true;
	boolean gameOver = false;
	boolean space = false;
	
	//Reusable Values;
	int rectWidth = 8;
	int rectHeight = 80;
	int player1Score = 0,player1ScoreX,player1ScoreY;
	int player2Score = 0,player2ScoreX,player2ScoreY; 
	int initBallX = 400,initBallY = 324,ballX = 400,ballY = 324,ballHeight=16,ballWidth=16;
	int initialY = 264,leftY = 264, rightY = 264;//Taking only y co-ordinates as it can only move in y-axis
	int speed = 16,vx = 2,vy=2;
	
	//Shape Objects
	Shape rectLeft = new Rectangle2D.Double(8,leftY,rectWidth,rectHeight);
	Shape rectRight = new Rectangle2D.Double(784,rightY,rectWidth,rectHeight);
	Shape circle = new Ellipse2D.Double(ballX,ballY,ballWidth,ballHeight);
	
	//For MultiKeyListener
	Set<Character> pressed = new HashSet<Character>();
	
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
		
		if(intro){//For Start Screen
			g.setColor(Color.GREEN);
			gd.drawString("Pong",392,248);
			gd.drawString("Press 'Space' to Start",328,264); 
			leftY = rightY = initialY;
			if(space){
				intro = false;
				space = false;
			}
		}else if(gameOver){//For end screen
			gd.setColor(Color.RED);
			gd.drawString("Press 'SPACE' to Continue",328,264);
			leftY = rightY = initialY;
			if(space){
				gameOver = false;
				space = false;
			}
		}else{
			
			if(player1Score == 5){
				gd.setColor(Color.GREEN);
				gd.drawString("Player1 Wins",328,248);
				gd.drawString("Press 'SPACE' to Continue",328,264);
				if(space){
					intro = true;
					space = false;
					player1Score = player2Score = 0;
				}
			}else if(player2Score == 5){
				gd.setColor(Color.GREEN);
				gd.drawString("Player2 Wins",328,248);
				gd.drawString("Press 'SPACE' to Continue",328,264);
				if(space){
					intro = true;
					space = false;
					player1Score = player2Score = 0;
				}
			}else{
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
		}
	}
	
	public void update(Graphics g){
		
		//Double Buffering
		dbImage = createImage(800,648);
		dbg = dbImage.getGraphics();
		paint(dbg);
		g.drawImage(dbImage,0,0,this);
		//updating ball values
		if(player1Score!=5 && player2Score!=5){
			ballX += vx;
			if(ballX < 8){
				gameOver = true;
				ballX = initBallX;
				ballY = initBallY;
				player2Score++;
			}else if(ballX > 792){
				gameOver = true;
				ballX = initBallX;
				ballY = initBallY;
				player1Score++;
			}else{
				if((ballX == 8) && ((ballY > leftY - 8) && (ballY < leftY + rectHeight + 8)))
					vx = vx * (-1);
			
				if((ballX == 784 - rectWidth) && ((ballY > rightY - 8) && (ballY < rightY + rectHeight + 8)))
					vx = vx * (-1);
					
			}
			ballY += vy;
			if(ballY < 8 || ballY > 640)
				vy= vy * (-1);
			
			//creating circle with updated values
			circle = new Ellipse2D.Double(ballX,ballY,ballWidth,ballHeight);
		}
	}
	
	@Override
	public void run(){
		
		while(true){
			repaint();
			update(getGraphics());
			
			try{
				Thread.sleep(20);
			}catch(Exception e){
				System.out.println("Error Occured");
			}
		}
	}
	
	@Override
	public synchronized void keyPressed(KeyEvent e){
		pressed.add(e.getKeyChar());

		if(pressed.size()>=1){
			for(Character c : pressed){
				switch(c)
				{
					case 25:
						intro = true;
						break;
					case 32:
						space = true;
						break;
					case 56:
						rightY-=speed;
						if(rightY < 0)
							rightY = 0;
						rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
						break;
					case 53:
						rightY+=speed;
						if(rightY > 648 - rectHeight)
							rightY = 648 - rectHeight;
						rectRight = new Rectangle(784,rightY,rectWidth,rectHeight);
						break;
					case 'w':
						leftY-=speed;
						if(leftY < 0)
							leftY = 0;
						rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
						break;
					case 's':
						leftY+=speed;
						if(leftY > 648 - rectHeight)
							leftY = 648 - rectHeight; 
						rectLeft = new Rectangle(8,leftY,rectWidth,rectHeight);
						break;
				}
			}
		}
		
		repaint();
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e){
		pressed.remove(e.getKeyChar());
	}
	@Override
	public void keyTyped(KeyEvent e){}
}