package CardboardCity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Scenery extends GridElement
{
	private int[] objects;
	private int[] progress = new int[3];
	private int[] maxProgress = new int[3];
	private BufferedImage boxSet;
	private BufferedImage[] progressImage = new BufferedImage[3];
	private Citizen[][] activeClearers = new Citizen[3][3];
	private int[] xCor;
	private int[] yCor;
	public Scenery()
	{
		objects = new int[3];
		
		for(int i = 0; i < 3; i++)
		{
			objects[i] = (int)(Math.random() * 3);
			switch(objects[i])
			{
			case 0: progress[i] = 12; maxProgress[i] = 12; break;
			case 1: progress[i] = 30; maxProgress[i] = 30; break;
			case 2: progress[i] = 64; maxProgress[i] = 64; break;
			}
		}
		
		try
		{
			boxSet = ImageIO.read(new File("BoxSet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		progressImage[0] = boxSet.getSubimage(1200, 0, 200, 200);
		progressImage[1] = boxSet.getSubimage(1200, 0, 200, 200);
		progressImage[2] = boxSet.getSubimage(1200, 0, 200, 200);
	}
	
	public int getObject(int i) { return objects[i]; }
	public void destroyObject(int i) { objects[i] = -1; }
	public int getAmountOfScenery() { return objects.length; }
	public void addActiveClearer(int index, int whichBox, Citizen c)
	{
		activeClearers[index][whichBox] = c;
	}
	public Citizen[][] getActiveClearers() { return activeClearers; }
	public void progress(int i, int p)
	{
		progress[i] -= p;
		if(progress[i] <= 0) destroyObject(i);
	}
	
	public int getProgessPercent(int i, int j)
	{
		return (int)((double)(j) * ((double)(maxProgress[i] - progress[i]) / (double)maxProgress[i]));
	}
	
	public BufferedImage getProgressImage(int i)
	{
		return progressImage[i];
	}
	
	public boolean isTileCleared()
	{
		if(progress[0] <= 0 && progress[1] <= 0 && progress[2] <= 0) return true;
		else return false;
	}
}







