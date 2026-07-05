package CardboardCity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Residential extends Building
{
	private Citizen[] residents;
	private int comfort;
	private BufferedImage appearance;
	
	public Residential()
	{
		progress = 0;
		
		try { image = ImageIO.read(new File("ResidentialSet")); } 
		catch (IOException e) { e.printStackTrace(); }
		
		residents = new Citizen[1];
		
		comfort = -1;
		
		
	}
	
	public Residential(int type, int p)
	{
		progress = p;
		
		try { image = ImageIO.read(new File("ResidentialSet")); } 
		catch (IOException e) { e.printStackTrace(); }
		
		switch(p)
		{
		case 0: 
			appearance = image.getSubimage(0, 0, 200, 200);
			comfort = 0;
			progress = 0;
			residents = new Citizen[1];
			break;
		}
	}
}



