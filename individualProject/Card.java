package individualProject;
import java.awt.*;

public class Card {		
	
	int x;
	int y;
	
	int w;
	int h;
	
	int visited = 0;
	
	boolean flip = false;	
	boolean matched = false;
	
	Image back = Toolkit.getDefaultToolkit().getImage("b0.png");	
	Image face;	
	
	public Card (int x, int y, int w, int h, int cardImageIndex)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.face = SetImages.usedImages[cardImageIndex];
	}

	public void draw(Graphics g)
	{
		if(flip == true && matched == false)
		{		
			g.drawImage(face, x, y, w, h, null);						
		}
		else if (flip == false && matched == false)
		{
			g.drawImage(back, x, y, w, h, null);			
		}	

	}
	
	public void flip()
	{
		if(flip == true)
		{
			flip = false;
			if(matched==false)
			Drawing.flipSound.playFile("flipSound.wav");
		}
		else if(flip == false)
		{
			flip = true;
			if(matched == false)
			Drawing.flipSound.playFile("flipSound.wav");
		}
	}
	
	public boolean contains(int mx, int my)
	{
		if((mx > x) && (mx < x + w) && (my > y) && (my < y + h))
		{
			visited += 1;			
			return true;
		}
		
		else
			return false;
		
	}
	
	public boolean matches(Card card)
	{
		if(face == card.face)
		{
			Drawing.cardsMatched++;
			if(Drawing.cardsMatched <= 6 && matched == false)
				Drawing.coinFlip.playFile("coinFlip.wav");				
		}
		return face == card.face;
	
			
	}

	
}
