package individualProject;
import java.awt.*;

import java.util.LinkedList;
import java.util.Random;

public class SetImages {
	
	static LinkedList<Image> Images = new LinkedList<Image>();
	 
	
	
	static  Image[] usedImages = new Image[12];	
	static Image loadImage;	
	
	static boolean[] filled = new boolean[usedImages.length];	
	
	public static void setImages()
	{		
		for(int i = 0; i < filled.length; i++)
			filled[i]=false;
		
		for(int i = 0 ; i < 6; i++)
		{
			loadImage = Toolkit.getDefaultToolkit().getImage("c"+i+".gif");
			Images.add(i, loadImage);			
		}
		
		Random rand = new Random();		
		
		int random;
		int r;		
		int nextEmpty;	
	
		for(int i = 0; i < 6; i++)
		{		
			nextEmpty = searchForNextEmpty(i);
			r = rand.nextInt(Images.size());
			if(filled[i] == false)
			{
				usedImages[i] = Images.get(r);
				filled[i] = true;
			}
			else
			{
				usedImages[nextEmpty] = Images.get(r);
				filled[nextEmpty] = true;
			}		
			
			nextEmpty = searchForNextEmpty(i+1);				
				
			random = rand.nextInt(usedImages.length );
								
				
			if(filled[random] == false)
			{
				usedImages[random] = Images.get(r);
				filled[random] = true;
			}
			else
			{
				random = rand.nextInt(usedImages.length );
				if(filled[random] == false)
				{
					usedImages[random] = Images.get(r);
					filled[random] = true;
				}
				else
				{
					usedImages[nextEmpty] = Images.get(r);
					filled[nextEmpty] = true;
				}
			}
				
			Images.remove(r);			 
		}	
			
	}

	public static int searchForNextEmpty(int i)
	{
		for(int j = i + 1; j != i; j = (j +1)%(filled.length) )
		{
			if(filled[j] == false)
				return j;
			else
				continue;
				
		}
		return 0;
	}

	
	
}
