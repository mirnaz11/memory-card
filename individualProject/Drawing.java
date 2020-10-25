package individualProject;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;



@SuppressWarnings("serial")
public class Drawing extends Applet implements MouseListener, Runnable {

	Font currentFont= new Font("TimesRoman", Font.PLAIN, 40);
	
	Card[] card= new Card[12];	
	int[] visited = new int[2];
	
	Graphics offScreen_g;
	
	Image offScreen;
	Image background;
	Image welcome;
	Image playButton;
	Image playAgainButton;
	Image scoreImage;	
	
	int mouse_x;
	int mouse_y;	
	int score;	
	int clicks ;
	static int cardsMatched;
		
	boolean pressedStart;
	boolean justFinished ;
	boolean stillPlaying;
	static boolean over ;
	
	JavaSound click;
	static JavaSound finish;
	static JavaSound flipSound ;
	static JavaSound coinFlip;	 
	 
	 Thread t;
	
	public void init()
	{	
		offScreen = createImage(1500, 700); 			
		offScreen_g = offScreen.getGraphics();		
		
		background = Toolkit.getDefaultToolkit().getImage("background7.png");
		welcome = Toolkit.getDefaultToolkit().getImage("Memory_Card_Game_Logo.png");
		playButton = Toolkit.getDefaultToolkit().getImage("PlayButton.png");
		playAgainButton = Toolkit.getDefaultToolkit().getImage("PlayAgainButton.png");		
		scoreImage = Toolkit.getDefaultToolkit().getImage("score.png");
		
		 click = new JavaSound();
		 finish = new JavaSound();
		 flipSound = new JavaSound();
		 coinFlip = new JavaSound();		 
		
		requestFocus();
		addMouseListener(this);	
		
		initialize();
		
		t = new Thread();		
	}
	public void initialize()
	{
		
		score = 0;
		clicks = 0;
		pressedStart = false;		
		over = false;
		cardsMatched = 0;
		justFinished = true;
		stillPlaying = true;		 
		
		SetImages.setImages();
		
		int x_0 = 300;
		int y_0 = 50;
		
		for(int i = 0; i < 12; i++)					
			card[i] = new Card(x_0 + i%4 + 2*(i%4)*100, y_0 + i/4 + 2*(i/4)*100, 140, 180, i);		
		
		for(int i = 0; i < 2; i++)
			visited[i] = -1;	
		
	}	
	
	@Override
	public void run() {
		
		while(stillPlaying == true)
		{
			t.start();
			while(!pressedStart)
			{
				repaint();
			}
			while(!over)
			{			
				inGameLoop();
				repaint();	
				
				try{
					Thread.sleep(16);
					}
				catch(Exception x){};			
			}
			while(over)
			{		
				postGameLoop();		
				
				repaint();
				
				try{
					Thread.sleep(16);
				}
				catch(Exception x){};
			}
		}		
	}
	
	public void inGameLoop(){}
	
	public void postGameLoop(){}	
	
	public void paint(Graphics g)
	{			
		if(cardsMatched == 6)
		{
			if(justFinished == true)
			{			
				finish.playFile("TaDa.wav");				
			}
			over = true;			
		}
		else 
			over = false;
		
		if(!pressedStart && over == false)
		{	
			g.drawImage(background, 0, 0, 1370, 650, null);
			g.drawImage(welcome, 300, 200, null);
			g.drawImage(playButton, 550,450, null);
		}
		else if(pressedStart && over == false)
		{
			g.drawImage(background, 0, 0, 1370, 650, null);
			g.drawImage(scoreImage, 1120, 30, 30, 60, null);
			
			g.setColor(Color.YELLOW);
			g.setFont(currentFont);
			
			g.drawString("Clicks", 1120, 150);
			g.drawString(""+clicks, 1150, 200 );
			g.drawString(""+score, 1170, 75);	
		
			for(int i = 0; i < 12 ; i++)			
				card[i].draw(g);			
		}
		else if (over)
		{
			if(justFinished == true)
			{
				justFinished = false;
				try{
					Thread.sleep(1000);
				}
				catch(Exception x){};
			}
			
			g.drawImage(background, 0, 0, 1370, 650, null);
			g.setColor(Color.YELLOW);
			g.drawString("Clicks", 1120, 150);
			g.drawString(""+clicks, 1150, 200 );
			g.drawImage(scoreImage, 1120, 30, 30, 60, null);
			g.drawString(""+score, 1170, 75);
			g.drawImage(playAgainButton, 550, 450, null);
			
		}
	}

	
	public void repaint() 
	{
		Graphics g;
		g = this.getGraphics();
		if(g == null); 
		else	
		{	update(g);
			g.dispose();
		}
	}
	
	public void update(Graphics g)
	{
		offScreen_g.clearRect(0,  0,  1500, 700);
		paint(offScreen_g); 
		g.drawImage(offScreen, 0, 0, null);		
	}
	
	int count = 0;
	
	public void mousePressed(MouseEvent e) {
		
		mouse_x = e.getX();
		mouse_y = e.getY();
		
		if(pressedStart == false && mouse_x > 550 && mouse_y > 450 && mouse_x < 691 && mouse_y <511)
			click.playFile("MouseClick.wav");
		
		if(over == true && mouse_x > 550 && mouse_y > 450 && mouse_x < 691 && mouse_y <511)
		{
			initialize();
			pressedStart = false;
			over = false;
			stillPlaying = true;
			
			click.playFile("MouseClick.wav");
		}
		
		else if(pressedStart == true)
		{
			clicks += 1;		
		
			for(int i = 0; i < 12; i++)
			{
				if(card[i].contains(mouse_x, mouse_y))
				{
					if(card[i].matched == true)
						continue;		
				
					if(card[i].visited == 1)
					{
						card[i].flip();
						
						if(count == 0  )
						{
							visited[0] = i;
							count+=1;
						}
						else if( count == 1)
						{
							visited[1] = i;
							count+=1;
						}
						repaint();
				
						if(count == 2)
						{
							if(card[visited[0]].matches(card[visited[1]]))
							{
								score += 50;
								repaint();
							
								try{
									Thread.sleep(500);
								}catch(Exception x){};
						
								card[visited[0]].matched = true;
								card[visited[1]].matched = true;					
							}
							else 
							{
								score -= 10;
								repaint();
							
							
								try{
									Thread.sleep(500);
								}catch(Exception x){};
						
								card[visited[0]].flip();
								card[visited[1]].flip ();						
							}					
							count = 0;
							card[visited[0]].visited = 0;
							card[visited[1]].visited = 0;
						}
					
					
					}				
					else if(card[i].visited == 2)
					{			
						card[i].flip();
						card[i].visited = 0;
						count = 0;
						break;	
					
					}				
				
				}			
				
			}
				
		}	
	
		
	}
	public void starting()
	{
		pressedStart = true;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mouse_x = e.getX();
		mouse_y = e.getY();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		mouse_x = e.getX();
		mouse_y = e.getY();
		
		if(pressedStart == false && mouse_x > 550 && mouse_y > 450 && mouse_x < 691 && mouse_y <511)
		{
			starting();
			
		}
		
		repaint();
		
	}

}
