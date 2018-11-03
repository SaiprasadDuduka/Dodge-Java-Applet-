import java.applet.*;
import java.awt.*;

public class Pong extends Applet implements Runnable{
	boolean running = true;
	Thread thread = new Thread(this);
	int rectWidth = 8;
	int rectHeight = 80;
	int player1Score = 0,player1ScoreX,player1ScoreY;
	int player2Score = 0,player2ScoreX,player2ScoreY; 
	int ballWidth = 16,ballHeight = 16,ballX = 400,ballY = 81;
	
	public void init(){
		setBackground(Color.BLACK);
		setSize(800,648);
	}
	
	public void start(){thread.start();}
	public void destroy(){running = false;}
	
	public void stop(){running = false;}
	
	public void paint(Graphics g){
		//Border white rect as apple window size is changing
		g.setColor(Color.WHITE);
		g.drawRect(0,0,800,648);
		
		//Two rectangles:
		g.setColor(Color.BLUE);
		g.fillRect(8,264,rectWidth,rectHeight);
		g.fillRect(784,264,rectWidth,rectHeight);
		
		//Score:
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(player1Score),8,16);
		g.drawString(String.valueOf(player2Score),784,16);
		
		//Ball:
		g.setColor(Color.YELLOW);
		g.fillOval(ballX,ballY,ballWidth,ballHeight);
	}
	
	public void update(){
		//update the values of the ball
	}
	public void run(){
		init();
		while(running){
			repaint();
			update();
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){
				System.out.println("Error Occured");
			}
		}
	}
	
}