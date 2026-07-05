package CardboardCity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Building extends GridElement
{
	protected int progress;
	private int type;
	private int progressBuilding;
	private int maxProgress;
	private BufferedImage progressImage;
	private Citizen[] residents;
	private int comfort;
	private BufferedImage appearance;
	private Citizen[] builders;
	private boolean buildIsSet = false;

	public Building()
	{
		progress = -1;
		type = -1;
		builders = new Citizen[3];
		
		
	}
	
	public Building(int type, int p)
	{
		progress = p;
		
		this.type = type;
		
		switch(type)
		{
		case 0:
			progressBuilding = 0;
			maxProgress = 30;
			
			switch(p)
			{
			case 0: 
				comfort = 0;
				progress = 0;
				residents = new Citizen[1];
				break;
			}
			break;
		}
		
	}
	
	public void setProgress(int p)
	{
		progress = p;
	}
	public int getProgress()
	{
		return progress;
	}
	public Citizen[] getResidents()
	{
		return residents;
	}
	public Citizen[] getBuilders()
	{
		return builders;
	}
	
	public int getType() { return type; }
	public void setType(int t) 
	{ 
		type = t; 
		switch(type)
		{
		case 0: 
			progressBuilding = 30;
			maxProgress = 30;
			break;
		}
	}
	
	public void progress(int i, int p)
	{
		progressBuilding -= p;
		if(progressBuilding <= 0) 
		{
			comfort = 0;
			progress = 0;
			residents = new Citizen[1];
		}
	}
	
	public int getProgessPercent(int i, int j)
	{
		return (int)((double)(j) * ((double)(maxProgress - progressBuilding) / (double)maxProgress));
	
	}
	
	public boolean isBuilt()
	{
		if(progressBuilding <= 0) return true;
		else return false;
	}
	
	public void setBuildIsSet(boolean b) { buildIsSet = b; }
	public boolean buildIsSet() { return buildIsSet; }
	
}

