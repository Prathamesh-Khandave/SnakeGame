import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import java.util.Random;
import java.util.random.*;

import javax.swing.JPanel;

public class Gamepanel extends JPanel implements ActionListener{
   static final int SCREEN_WINDTH =600;
   static final int SCREEN_HEIGHT =600;
   static final int UNIT_SIZE = 25;
   static final int GAME_UNIT = (SCREEN_WINDTH*SCREEN_HEIGHT)/UNIT_SIZE;
   static final int DELAY = 150;
   final int x[]=new int[GAME_UNIT];
   final int y[]=new int[GAME_UNIT];
   int bodyParts = 6;
   int applesEaten;
   int appleX;
   int appleY;
   char direction = 'R';
   boolean running = false;
   Timer timer;
   Random random;
   
	Gamepanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WINDTH,SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MykeyAdapter());
		startgame();
	}
  public void startgame() {
	  newapple();
	  running = true;
	  timer = new Timer(DELAY,this);
	  timer.start();
  }
  public void paintComponent(Graphics g) {
	  super.paintComponent(g);
	  draw(g);
  }
  public void draw(Graphics g) { 
	  if(running) {
	 /* for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
		  g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
		  g.drawLine(0,  i*UNIT_SIZE,SCREEN_WINDTH, i*UNIT_SIZE);
	  }
	  */
	  g.setColor(Color.RED);
	  g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
	  for(int i =0; i<bodyParts;i++) {
		  if ( i==0) {
			  g.setColor(Color.GREEN);
			  g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		  }
		  else {
			  g.setColor(new Color(45,180,0));
			  g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		  }
			  
	  }
	  g.setColor(Color.RED);
	  g.setFont(new Font("Ink Free",Font.BOLD,40));
	  FontMetrics metrics = getFontMetrics(g.getFont());
	  g.drawString("Score: "+applesEaten,(SCREEN_WINDTH -metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
  }
	  else {
		  gameover(g);
	  }
  }
  public void newapple() {
	  appleX   = random.nextInt((int)(SCREEN_WINDTH/UNIT_SIZE))*UNIT_SIZE;
	  appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
  }
  public void move() {
	  for(int i = bodyParts;i>0;i--) {
		  x[i]=x[i-1];
		  y[i]=y[i-1];
	  }
	  switch(direction) {
	  case 'U' :
		  y[0] = y [0] - UNIT_SIZE;
		  break;
	  case 'D' :
		  y[0]=y[0]+UNIT_SIZE;
		  break;
	  case 'L' :
		  x[0]=x[0] - UNIT_SIZE;
		  break;
	  case'R':
		  x[0]= x[0]+UNIT_SIZE;
		  break;
		  
	  }
  }
  public void checkapple() {
	  if((x[0]==appleX)&&y[0]==appleY) {
		  bodyParts++;
		  applesEaten++;
		  newapple();
	  }
  }
  public void checkcolliion() {
	  //checks if head collides with body
	  for(int i=bodyParts;i>0;i--) {
		  if((x[0]==x[i])&&(y[0]==y[i])) {
			  running = false;
		  }
		  //if head touches left border
		  if(x[0]<0) {
			  running = false;
		  }
		  //if head touches right border
		  if(x[0]>SCREEN_WINDTH) {
			  running = false;
		  }
		  //if head touches top border
		  if (y[0]<0) {
			  running = false;
		  }
		  //if head touches the bottom border
		  if (y[0]>SCREEN_HEIGHT) {
			  running = false;
		  }
		  if(!running) {
			  timer.stop();
		  }
	  }
  }
  public void gameover(Graphics g) {
	  g.setColor(Color.RED);
	  g.setFont(new Font("Ink Free",Font.BOLD,75));
	  FontMetrics metrics = getFontMetrics(g.getFont());
	  g.drawString("Game Over",(SCREEN_WINDTH -metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
	  g.setColor(Color.RED);
	  g.setFont(new Font("Ink Free",Font.BOLD,40));
	  FontMetrics metrics2 = getFontMetrics(g.getFont());
	  g.drawString("Score: "+applesEaten,(SCREEN_WINDTH -metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
  }
 
  
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkapple();
			checkcolliion();
		}
		repaint();
	}
	
	
public class MykeyAdapter extends KeyAdapter{
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(direction !='R') {
				direction = 'L';
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(direction !='L') {
				direction = 'R';
			}
			break;
		
	case KeyEvent.VK_UP:
		if(direction !='D') {
			direction = 'U';
		}
		break;
	
case KeyEvent.VK_DOWN:
	if(direction !='U') {
		direction = 'D';
	}
	break;
}
	}
}
}
