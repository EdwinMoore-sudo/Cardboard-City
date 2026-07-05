package CardboardCity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Game extends JPanel
{
	private ArrayList<Map> locations = new ArrayList<Map>();
	private ArrayList<Citizen> citizens = new ArrayList<Citizen>();
	ArrayList<JLabel> text = new ArrayList<JLabel>();
	
	
	private int[] inventorY = new int[4];
	/*
	 * 0: Money
	 * 1: Meat
	 * 2: Wood
	 * 3: Stone
	 */
	
	private int currentTileX;
	private int currentTileY;
	
	private BufferedImage titleScreenSet;
	private BufferedImage overlaySet;
	private BufferedImage tileSet;
	private BufferedImage personSet;
	private BufferedImage scenerySet;
	private BufferedImage boxSet;
	private BufferedImage aestheticsSet;
	private BufferedImage professionBoxSet;
	private BufferedImage headersSet;
	private BufferedImage buildingsSet;
	private BufferedImage treeBackground;
	private BufferedImage backgroundSet;
		
	private int room = 1;
	private boolean isTileMenuOpen;
	private int isASceneryRemovalMenuOpen = -1;
	private boolean isCharacterMenuOpen = false;
	private int isSpecificCharacterMenuOpen = -1;
	private int isACharacterAttributeMenuOpen = -1;
	private boolean isItANewHour = false;
	private ScrollBar scrollBar_CAM1 = new ScrollBar(375);
	private ScrollBar scrollBar_CAM2 = new ScrollBar(375);
	private ArrayList<Citizen> clearers = new ArrayList<Citizen>();
	private ArrayList<Citizen> builders = new ArrayList<Citizen>();
	private int[] scrollMenu_CAM1 = new int[8];
	private int[] menu_TMC = new int[9];
	private int[] displayX = new int[3];
	private int[] clock = new int[7];
	private int day = 1;
	
	private int[] inventory;
	/*
	 * 0: Food
	 * 1: Leaves
	 * 2: Wood
	 * 3: Stone
	 * 4: 
	 */
	
	private CardboardCity.Input mi;
	
	public Game() 
	{
		locations.add(new Map());
		addCitizen();
		
		currentTileX = 28;
		currentTileY = 28;
		
		try {
			titleScreenSet = ImageIO.read(new File("TitleScreenSet.png"));
			tileSet = ImageIO.read(new File("TileSet.png"));
			personSet = ImageIO.read(new File("PersonSet.png"));
			scenerySet = ImageIO.read(new File("ScenerySet.png"));
			overlaySet = ImageIO.read(new File("OverlaySet.png"));
			boxSet = ImageIO.read(new File("BoxSet.png"));
			aestheticsSet = ImageIO.read(new File("AestheticsSet.png"));
			professionBoxSet = ImageIO.read(new File("ProfessionBoxSet.png"));
			headersSet = ImageIO.read(new File("HeadersSet.png"));
			buildingsSet = ImageIO.read(new File("ResidentialSet.png"));
			treeBackground = ImageIO.read(new File("TreeBackground.png"));
			backgroundSet = ImageIO.read(new File("BackgroundSet.png"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(1600, 900));
		
		mi = new CardboardCity.Input();
		this.addMouseListener(mi);
		this.addMouseMotionListener(mi);
		
		scrollMenu_CAM1[2] = 430;
		scrollMenu_CAM1[3] = 225;
		menu_TMC[2] = 45;
		menu_TMC[3] = 150;
	}
	
	public int getAmountOfCitizens()
	{
		return citizens.size();
	}
	
	public void changeCurrentTileX(int cTX) 
	{ 
		if(currentTileX + cTX >= 0 && currentTileX + cTX <= 54)
		{
			currentTileX = currentTileX + cTX; 
			this.repaint(); 
			this.revalidate(); 			
		}
	}
	public void changeCurrentTileY(int cTY) 
	{ 
		if(currentTileY + cTY >= 0 && currentTileY + cTY <= 54)
		{
			currentTileY = currentTileY + cTY; 
			this.repaint(); 
			this.revalidate();
		}
	}
	
	public void newSizedColoredText(int index, String textString, int xCoor, int yCoor, int width, int height, float size, Color color)
	{
		text.add(new JLabel());
		text.get(index).setBounds(xCoor, yCoor, width, height);
		text.get(index).setText(textString);
		text.get(index).setForeground(color);
		text.get(index).setFont(text.get(text.size() - 1).getFont().deriveFont(size));
		text.get(index).setOpaque(false);
		this.add(text.get(index));

	}
	
	public void displayTileMenu()
	{
		isTileMenuOpen = true;
		this.repaint();
		this.revalidate();
	}
	public void closeTileMenu()
	{
		isTileMenuOpen = false;
		this.repaint();
		this.revalidate();
	}

	public void displaySceneryRemovalMenu(int i)
	{
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		if(!buildingsOnTile.buildIsSet())
		{
			isASceneryRemovalMenuOpen = i;
			this.repaint();
			this.revalidate();
		}
	}
	public void closeSceneryRemovalMenu()
	{
		isASceneryRemovalMenuOpen = -1;
		this.repaint();
		this.revalidate();
	}

	public void displayCharacterMenu()
	{
		isCharacterMenuOpen = true;
		this.repaint();
		this.revalidate();
	}
	
	public void closeCharacterMenu()
	{
		isCharacterMenuOpen = false;
		this.repaint();
		this.revalidate();
	}
	
	public void displaySpecificCharacterMenu(int i)
	{
		isSpecificCharacterMenuOpen = i;
		this.repaint();
		this.revalidate();
	}
	
	public void closeSpecificCharacterMenu()
	{
		isSpecificCharacterMenuOpen = -1;
		this.repaint();
		this.revalidate();
	}

	public void openACharacterAttributeMenu(int i)
	{
		isACharacterAttributeMenuOpen = i;
		this.repaint();
		this.revalidate();
	}

	public void closeACharacterAttributeMenu()
	{
		isACharacterAttributeMenuOpen = -1;
		this.repaint();
		this.revalidate();
	}
	
	public void updateClock(int[] c, boolean iIANH)
	{
		clock = Arrays.copyOf(c, c.length);
		isItANewHour = iIANH;
	}
	
	public void addCitizen()
	{
		citizens.add(new Citizen());
		citizens.get(citizens.size() - 1).setID((citizens.size() - 1));
	}
	
	public void displayXForActiveClearer(int x, int y, int id)
	{
		displayX[0] = x;
		displayX[1] = y;
		displayX[2] = id;
		
	}

	public void setBuild()
	{
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		buildingsOnTile.setBuildIsSet(true);
		this.repaint();
		this.revalidate();
	}
	
	public boolean isBuildSet()
	{
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		return (buildingsOnTile.buildIsSet() || !(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getType() == -1));
	}
	
	public void drawScenery(int i, int j, int cTXMinMod, int cTXPosMod, int cTYMinMod, int cTYPosMod, int xTileOffset, Graphics g, ImageObserver paintingChild) 
	{
		Scenery sceneryOnTile = locations.get(0).getGrid().getGridOfScenery()[i][j];
		
		switch(locations.get(0).getGround()[i][j])
		{
		
		//Draw Scenery on Grass Tile
		case 0:
			for(int k = 0; k < 3; k++)
			{
				int kXCor = 50;
				int kYCor = 50;
				if(k == 0) { kXCor = 50 - 50; kYCor = 75 - 150; }
				else if(k == 1) { kXCor = 150 - 50; kYCor = 75 - 150; }
				else if(k == 2) { kXCor = 100 - 50; kYCor = 150 - 150; }
				
				switch(sceneryOnTile.getObject(k))
				{
				case 0:
					g.drawImage(scenerySet, ((i - cTXMinMod) * 200 - xTileOffset) + kXCor, 
							((j - cTYMinMod) * 200 - 75) + kYCor, 
							(((i - cTXMinMod) * 200 - xTileOffset) + kXCor) + 100, 
							(((j - cTYMinMod) * 200 - 75) + kYCor) + 150, 
							1200, 0, 1400, 400, paintingChild);
					for(int q = 0; q < sceneryOnTile.getActiveClearers()[k].length; q++)
					{
						if(sceneryOnTile.getActiveClearers()[q][k] != null 
								&& sceneryOnTile.getActiveClearers()[q][k].getSchedule()[clock[2]].getTaskType() == 9)
						{
							if(sceneryOnTile.getActiveClearers()[q][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[q][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * q) + kXCor - 90, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((q % 2) * 5) + kYCor + 60,
										50, 50, paintingChild);
							}
							if(sceneryOnTile.getActiveClearers()[1][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[1][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * 1) + kXCor - 90, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((1 % 2) * 5) + kYCor + 60,
										50, 50, paintingChild);
							}
						}
					}
					break;
				case 1:
					g.drawImage(scenerySet, ((i - cTXMinMod) * 200 - xTileOffset) + kXCor, 
							((j - cTYMinMod) * 200 - 75) + kYCor, 
							(((i - cTXMinMod) * 200 - xTileOffset) + kXCor) + 100, 
							(((j - cTYMinMod) * 200 - 75) + kYCor) + 150, 
							1400, 0, 1600, 400, paintingChild);
					for(int q = 0; q < sceneryOnTile.getActiveClearers()[k].length; q++)
					{
						if(sceneryOnTile.getActiveClearers()[q][k] != null 
								&& sceneryOnTile.getActiveClearers()[q][k].getSchedule()[clock[2]].getTaskType() == 9)
						{
							if(sceneryOnTile.getActiveClearers()[q][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[q][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * q) + kXCor - 90, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((q % 2) * 5) + kYCor + 60,
										50, 50, paintingChild);
							}
							if(sceneryOnTile.getActiveClearers()[1][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[1][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * 1) + kXCor - 90, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((1 % 2) * 5) + kYCor + 60,
										50, 50, paintingChild);
							}
						}
					}
					break;
				case 2:
					g.drawImage(scenerySet, ((i - cTXMinMod) * 200 - xTileOffset) + kXCor, 
							((j - cTYMinMod) * 200 - 75) + kYCor, 
							(((i - cTXMinMod) * 200 - xTileOffset) + kXCor) + 100, 
							(((j - cTYMinMod) * 200 - 75) + kYCor) + 150, 
							1600, 0, 1800, 400, paintingChild);
					for(int q = 0; q < sceneryOnTile.getActiveClearers()[k].length; q++)
					{
						if(sceneryOnTile.getActiveClearers()[q][k] != null 
								&& sceneryOnTile.getActiveClearers()[q][k].getSchedule()[clock[2]].getTaskType() == 9)
						{
							if(sceneryOnTile.getActiveClearers()[q][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[q][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * q) + kXCor - 90, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((q % 2) * 5) + kYCor + 60,
										50, 50, paintingChild);
							}
							if(sceneryOnTile.getActiveClearers()[1][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[1][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * 1) + kXCor - 90, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((1 % 2) * 5) + kYCor + 60,
										50, 50, paintingChild);
							}
						}
					}
					break;
				}
			}
		
			break;
			
		//Draw Scenery on Stone Tile
		case 1:
			for(int k = 0; k < 3; k++)
			{
				int kXCor = 50;
				int kYCor = 50;
				if(k == 0) { kXCor = 50 - 63; kYCor = 75 - 75; }
				else if(k == 1) { kXCor = 150 - 63; kYCor = 75 - 75; }
				else if(k == 2) { kXCor = 100 - 63; kYCor = 150 - 75; }
				
				switch(sceneryOnTile.getObject(k))
				{
				case 0:
					g.drawImage(scenerySet, ((i - cTXMinMod) * 200 - xTileOffset) + kXCor, 
							((j - cTYMinMod) * 200 - 75) + kYCor, 
							(((i - cTXMinMod) * 200 - xTileOffset) + kXCor) + 125, 
							(((j - cTYMinMod) * 200 - 75) + kYCor) + 75, 
							0, 0, 400, 200, paintingChild);
					
					for(int q = 0; q < sceneryOnTile.getActiveClearers()[k].length; q++)
					{
						if(sceneryOnTile.getActiveClearers()[q][k] != null 
								&& sceneryOnTile.getActiveClearers()[q][k].getSchedule()[clock[2]].getTaskType() == 9)
						{
							if(sceneryOnTile.getActiveClearers()[q][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[q][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * q) + kXCor - 75, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((q % 2) * 5) + kYCor,
										50, 50, paintingChild);
							}
							if(sceneryOnTile.getActiveClearers()[1][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[1][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * 1) + kXCor - 75, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((1 % 2) * 5) + kYCor,
										50, 50, paintingChild);
							}
						}
					}
					
					break;
				case 1:
					g.drawImage(scenerySet, ((i - cTXMinMod) * 200 - xTileOffset) + kXCor, 
							((j - cTYMinMod) * 200 - 75) + kYCor, 
							(((i - cTXMinMod) * 200 - xTileOffset) + kXCor) + 125, 
							(((j - cTYMinMod) * 200 - 75) + kYCor) + 75, 
							400, 0, 800, 200, paintingChild);
					
					for(int q = 0; q < sceneryOnTile.getActiveClearers()[k].length; q++)
					{
						if(sceneryOnTile.getActiveClearers()[q][k] != null 
								&& sceneryOnTile.getActiveClearers()[q][k].getSchedule()[clock[2]].getTaskType() == 9)
						{
							if(sceneryOnTile.getActiveClearers()[q][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[q][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * q) + kXCor - 75, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((q % 2) * 5) + kYCor,
										50, 50, paintingChild);
							}
							if(sceneryOnTile.getActiveClearers()[1][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[1][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * 1) + kXCor - 75, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((1 % 2) * 5) + kYCor,
										50, 50, paintingChild);
							}
						}
					}
					
					break;
				case 2:
					g.drawImage(scenerySet, ((i - cTXMinMod) * 200 - xTileOffset) + kXCor, 
							((j - cTYMinMod) * 200 - 75) + kYCor, 
							(((i - cTXMinMod) * 200 - xTileOffset) + kXCor) + 125, 
							(((j - cTYMinMod) * 200 - 75) + kYCor) + 75, 
							800, 0, 1200, 200, paintingChild);
					
					for(int q = 0; q < sceneryOnTile.getActiveClearers()[k].length; q++)
					{
						if(sceneryOnTile.getActiveClearers()[q][k] != null 
								&& sceneryOnTile.getActiveClearers()[q][k].getSchedule()[clock[2]].getTaskType() == 9)
						{
							if(sceneryOnTile.getActiveClearers()[q][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[q][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * q) + kXCor - 75, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((q % 2) * 5) + kYCor,
										50, 50, paintingChild);
							}
							if(sceneryOnTile.getActiveClearers()[1][k] != null)
							{
								g.drawImage(sceneryOnTile.getActiveClearers()[1][k].getAppearance(), 
										((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (20 * 1) + kXCor - 75, 
										((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((1 % 2) * 5) + kYCor,
										50, 50, paintingChild);
							}
						}
					}
					
					break;
				}
			}
			break;
			
		case 2:
//			if(citizens.size() != 0)
//			{
//				for(int k = 0; k < citizens.size(); k++)
//				{
////				g.drawImage(citizens.get(k).getAppearance(), 
////						((i - cTXMinMod) * 200 - xTileOffset) + ((int)(Math.random() * 100 + 50)) - 25, 
////						((j - cTYMinMod) * 200 - 75) + ((int)(Math.random() * 100 + 50)) - 25,
////						50, 50, paintingChild);
//
//					g.drawImage(citizens.get(k).getAppearance(), 
//							((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25, 
//							((j - cTYMinMod) * 200 - 75) + (100) - 25,
//							50, 50, paintingChild);
//				}
//			}
			Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[i][j];
			if(buildingsOnTile.getProgress() != -1)
			{
				if(buildingsOnTile.getProgress() != -1)
				{
					if(buildingsOnTile.getType() == 0) g.drawImage(buildingsSet, ((i - cTXMinMod) * 200 - xTileOffset), ((j - cTYMinMod) * 200 - 75), ((i - cTXMinMod) * 200 - xTileOffset) + 200, ((j - cTYMinMod) * 200 - 75) + 200, 200, 0, 400, 200, paintingChild);
				}
				if(buildingsOnTile.getResidents()[0] != null)
				{
					if(buildingsOnTile.getResidents()[0].getSchedule()[clock[2]].getTaskType() == -1
							|| buildingsOnTile.getResidents()[0].getSchedule()[clock[2]].getTaskType() == 0
							|| buildingsOnTile.getResidents()[0].getSchedule()[clock[2]].getTaskType() == 1
							|| buildingsOnTile.getResidents()[0].getSchedule()[clock[2]].getTaskType() == 2)
					{
						g.drawImage(buildingsOnTile.getResidents()[0].getAppearance(), 
								((i - cTXMinMod) * 200 - xTileOffset) - 25 + 100 - 2, 
								((j - cTYMinMod) * 200 - 75) - 25 + 120 - 5,
								50, 50, paintingChild);
	
					}
				}
			}
			else
			{
				if(buildingsOnTile.getType() != -1)
				{
					g.drawImage(buildingsSet, ((i - cTXMinMod) * 200 - xTileOffset), ((j - cTYMinMod) * 200 - 75) - 10, ((i - cTXMinMod) * 200 - xTileOffset) + 200, ((j - cTYMinMod) * 200 - 75) + 200 - 10, 0, 0, 200, 200, paintingChild);
				}
				for(int r = 0; r < buildingsOnTile.getBuilders().length; r++)
				{
					if(buildingsOnTile.getBuilders()[r] != null)
					{
						if(buildingsOnTile.getBuilders()[r].getSchedule()[clock[2]].getTaskType() == 3)
						{
							g.drawImage(buildingsOnTile.getBuilders()[r].getAppearance(), 
									((i - cTXMinMod) * 200 - xTileOffset) + (100) - 25 + 20 + (40 * r) - 65, 
									((j - cTYMinMod) * 200 - 75) + (100) - 45 + ((r % 2) * 40),
									50, 50, paintingChild);
						}
					}
				}
				
			}
		}
	}
	
	public void drawTile(int i, int j, int cTXMinMod, int cTXPosMod, int cTYMinMod, int cTYPosMod, int xTileOffset, Graphics g, ImageObserver paintingChild)
	{
		switch(locations.get(0).getGround()[i][j])
		{
		//Draw Stone Tile
		case 2:
			g.drawImage(tileSet, (i - cTXMinMod) * 200 - xTileOffset, (j - cTYMinMod) * 200 - 75, ((i - cTXMinMod) * 200) + 200 - xTileOffset, ((j - cTYMinMod) * 200) + 200 - 75, 400, 0, 600, 200, paintingChild);
			break;
			
		//Draw Stone Tile
		case 1:
			g.drawImage(tileSet, (i - cTXMinMod) * 200 - xTileOffset, (j - cTYMinMod) * 200 - 75, ((i - cTXMinMod) * 200) + 200 - xTileOffset, ((j - cTYMinMod) * 200) + 200 - 75, 200, 0, 400, 200, paintingChild);
			break;
			
		//Draw Grass Tile
		case 0:
			g.drawImage(tileSet, (i - cTXMinMod) * 200 - xTileOffset, (j - cTYMinMod) * 200 - 75, ((i - cTXMinMod) * 200) + 200 - xTileOffset, ((j - cTYMinMod) * 200) + 200 - 75, 0, 0, 200, 200, paintingChild);							
			break;						
		}
	}

	public void scrollForCAM1(ScrollBar sB)
	{
		scrollBar_CAM1.setTopLeft(sB.getTopLeft());;
		scrollBar_CAM1.setUserClick(sB.getUserClick());;
		scrollBar_CAM1.setDeltaY(sB.getDeltaY());;
		scrollBar_CAM1.setIsActive(sB.isActive());;
		scrollMenu_CAM1[6] = (scrollBar_CAM1.getTopOfBar() - 375) / 18;
		this.repaint();
		this.revalidate();
	}
	public void scrollForCAM2(ScrollBar sB)
	{
		scrollBar_CAM2.setTopLeft(sB.getTopLeft());;
		scrollBar_CAM2.setUserClick(sB.getUserClick());;
		scrollBar_CAM2.setDeltaY(sB.getDeltaY());;
		scrollBar_CAM2.setIsActive(sB.isActive());;
		this.repaint();
		this.revalidate();
	}

	public void dragTaskForCAM(int[] sM)
	{
		scrollMenu_CAM1 = Arrays.copyOf(sM, sM.length);
		this.repaint();
		this.revalidate();
	}
	
	public void dragTaskForTMC(int[] tM)
	{
		if(locations.get(0).getGround()[currentTileX][currentTileY] != 2)
		{
			if(tM[6] + (tM[7] * 3) < clearers.size() && !clearers.get(tM[6] + (tM[7] * 3)).isAssigned())
			{
				menu_TMC = Arrays.copyOf(tM, tM.length);
			}
			else
			{
				menu_TMC[0] = 0;
				menu_TMC[1] = 0;
				menu_TMC[2] = 45;
				menu_TMC[3] = 150;
				menu_TMC[4] = 0;
				menu_TMC[5] = 0;
				menu_TMC[6] = 0;
				menu_TMC[7] = 0;
				menu_TMC[8] = 0;
			}
		}
		else 
		{
			if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getProgress() != -1)
			{
				if(tM[6] + (tM[7] * 3) < citizens.size())
				{
					menu_TMC = Arrays.copyOf(tM, tM.length);
				}
				else
				{
					menu_TMC[0] = 0;
					menu_TMC[1] = 0;
					menu_TMC[2] = 45;
					menu_TMC[3] = 150;
					menu_TMC[4] = 0;
					menu_TMC[5] = 0;
					menu_TMC[6] = 0;
					menu_TMC[7] = 0;
					menu_TMC[8] = 0;
				}
			}
			else
			{
				if(tM[6] + (tM[7] * 3) < builders.size() && !builders.get(tM[6] + (tM[7] * 3)).isAssignedBuilder())
				{
					menu_TMC = Arrays.copyOf(tM, tM.length);
				}
				else
				{
					menu_TMC[0] = 0;
					menu_TMC[1] = 0;
					menu_TMC[2] = 45;
					menu_TMC[3] = 150;
					menu_TMC[4] = 0;
					menu_TMC[5] = 0;
					menu_TMC[6] = 0;
					menu_TMC[7] = 0;
					menu_TMC[8] = 0;
				}
			}
		}
			
		this.repaint();
		this.revalidate();
	}

	public void setTask(int intialHour, int taskType)
	{
		citizens.get(isSpecificCharacterMenuOpen).setTask(intialHour, taskType);
		this.repaint();
		this.revalidate();
	}
	
	public void setCurrentBuild(int bC)
	{
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		buildingsOnTile.setType(bC);
		this.repaint();
		this.revalidate();
	}
	
	public void setActiveClearer(int slotDrTOX, int slotDrTOY, int clearer)
	{
		if(locations.get(0).getGround()[currentTileX][currentTileY] != 2)
		{
			if(clearer < clearers.size() && !clearers.get(clearer).isAssigned())
			{
				Scenery sceneryOnTile = locations.get(0).getGrid().getGridOfScenery()[currentTileX][currentTileY];
				int clearerID = clearers.get(clearer).getID();
				for(int i = 0; i < citizens.size(); i++) 
				{
					if(citizens.get(i).getID() == clearerID) citizens.get(i).setAssigned(true);
				}
				sceneryOnTile.getActiveClearers()[slotDrTOX][slotDrTOY] = clearers.get(clearer);
			}	
		}
		else 
		{
			if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getProgress() != -1)
			{
				Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
//				int citizenID = buildingsOnTile.getResidents()[0].getID();
//				for(int i = 0; i < citizens.size(); i++) 
//				{
//					if(citizens.get(i).getID() == citizenID) citizens.get(i).setAssignedResident(true);
//				}
				buildingsOnTile.getResidents()[slotDrTOX] = citizens.get(clearer);
			}
			else
			{
				Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
				
				if(buildingsOnTile.getType() != -1)
				{
					int builderID = builders.get(clearer).getID();
					for(int i = 0; i < citizens.size(); i++) 
					{
						if(citizens.get(i).getID() == builderID) citizens.get(i).setAssignedBuilder(true);
					}
					buildingsOnTile.getBuilders()[slotDrTOX] = builders.get(clearer);
				}
			}
		}
		
		this.repaint();
		this.revalidate();
	}

	public void removeTask(int intialHour)
	{
		citizens.get(isSpecificCharacterMenuOpen).removeTask(intialHour);
		this.repaint();
		this.revalidate();
	}
	
	public void removeActiveClearer(int clearer)
	{
		Scenery sceneryOnTile = locations.get(0).getGrid().getGridOfScenery()[currentTileX][currentTileY];
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		
		if(locations.get(0).getGround()[currentTileX][currentTileY] != 2)
		{
			if(sceneryOnTile.getActiveClearers()[clearer % 3][clearer / 3] != null)
			{
				int clearerID = sceneryOnTile.getActiveClearers()[clearer % 3][clearer / 3].getID();
				for(int i = 0; i < citizens.size(); i++) 
				{
					if(citizens.get(i).getID() == clearerID) citizens.get(i).setAssigned(false);
				}
				sceneryOnTile.getActiveClearers()[clearer % 3][clearer / 3] = null;
				
			}
		}
		else 
		{
			if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getProgress() != -1)
			{
				int citizenID = -1;
				if(clearer % 3 < buildingsOnTile.getResidents().length) citizenID = buildingsOnTile.getResidents()[clearer % 3].getID();
				for(int i = 0; i < citizens.size(); i++) 
				{
					if(citizens.get(i).getID() == citizenID) citizens.get(i).setAssignedResident(false);
				}
				buildingsOnTile.getResidents()[clearer % 3] = null;
				
			}
			else
			{
				int citizenID = -1;
				if(clearer % 3 < buildingsOnTile.getBuilders().length) citizenID = buildingsOnTile.getBuilders()[clearer % 3].getID();
				for(int i = 0; i < citizens.size(); i++) 
				{
					if(citizens.get(i).getID() == citizenID) citizens.get(i).setAssignedBuilder(false);
				}
				buildingsOnTile.getBuilders()[clearer % 3] = null;
			}
		}
		
		this.repaint();
		this.revalidate();
	}
	
	public String[] getTaskNameTime(int i)
	{
		String name = "";
		String hours = "";
		switch(i)
		{
		case 0: name = "Sleep"; hours = "1"; break;
		case 1: name = "Eat"; hours = "1"; break;
		case 2: name = "Free Time"; hours = "1"; break;
		case 3: name = "Building"; hours = "3"; break;
		case 4: name = "Hunting"; hours = "5"; break;
		case 5: name = "Fishing"; hours = "6"; break;
		case 6: name = "Gathering"; hours = "3"; break;
		case 7: name = "Farming"; hours = "4"; break;
		case 8: name = "Recruiter"; hours = "9"; break;
		case 9: name = "Clearing"; hours = "2"; break;
		}
		
		String[] nameTime = new String[2];
		nameTime[0] = name;
		nameTime[1] = hours;
		
		return nameTime;
	}
	
	public int getHour()
	{
		int index = -1;
		switch(clock[2])
		{
		case 12:
		case 0: index = 6; break;
		case 13:
		case 1: index = 7; break;
		case 14:
		case 2: index = 8; break;
		case 15:
		case 3: index = 9; break;
		case 16:
		case 4: index = 10; break;
		case 17:
		case 5: index = 11; break;
		case 18:
		case 6: index = 12; break;
		case 19:
		case 7: index = 1; break;
		case 20:
		case 8: index = 2; break;
		case 21:
		case 9: index = 3; break;
		case 22:
		case 10: index = 4; break;
		case 23:
		case 11: index = 5; break;
		}
		
		return index;
	}

	public void drawCitizensClearing(Graphics g, ImageObserver paintingChild)
	{
		int count = 0;
		while(clearers.size() > 0) clearers.remove(0);
		for(int i = 0; i < citizens.size(); i++)
		{
			boolean isClearing = false;
			for(int k = 0; k < 24; k++) if(citizens.get(i).getSchedule()[k].getTaskType() == 9) isClearing = true;
			if(isClearing)
			{
				if(count < 15)
				{
					g.drawImage(boxSet, (((count % 3) * 100) + (40 * (count % 3))) + 45, (((count / 3) * 100) + ((count / 3) * 40) + 100) + 50, (((count % 3) * 100) + (40 * (count % 3))) + 145, (((count / 3) * 100) + ((count / 3) * 40) + 100) + 150, 201, 0, 400, 200, paintingChild); 
					g.drawImage(citizens.get(i).getAppearance(), (((count % 3) * 100) + (40 * (count % 3))) + 55, (((count / 3) * 100) + ((count / 3) * 40) + 100) + 60, 80, 80, paintingChild);
					clearers.add(citizens.get(i));
					count++;
				}
			}
		}
//		count = 30;
//		for(int i = 0; i < count; i++)
//		{
//		g.drawImage(boxSet, (((i % 3) * 100) + (40 * (i % 3))) + 45, (((i / 3) * 100) + ((i / 3) * 40) + 100) + 50, (((i % 3) * 100) + (40 * (i % 3))) + 145, (((i / 3) * 100) + ((i / 3) * 40) + 100) + 150, 201, 0, 400, 200, paintingChild); 
//		}
		
//		for(int i = 0; count > i; i++)
//		{
//			g.drawImage(boxSet, ((i * 100) + (40 * i)) + 45, (((i / 4) * 100) + ((i / 4) * 40) + 100) + 50, ((i * 100) + (40 * i)) + 145, (((i / 4) * 100) + ((i / 4) * 40) + 100) + 150, 201, 0, 400, 200, paintingChild); 
//		}
	}

	public void drawResidents(Graphics g, ImageObserver paintingChild)
	{
		for(int i = 0; i < citizens.size(); i++)
		{
			if(i < 15)
			{
				g.drawImage(boxSet, (((i % 3) * 100) + (40 * (i % 3))) + 45, (((i / 3) * 100) + ((i / 3) * 40) + 100) + 50, (((i % 3) * 100) + (40 * (i % 3))) + 145, (((i / 3) * 100) + ((i / 3) * 40) + 100) + 150, 201, 0, 400, 200, paintingChild); 
				g.drawImage(citizens.get(i).getAppearance(), (((i % 3) * 100) + (40 * (i % 3))) + 55, (((i / 3) * 100) + ((i / 3) * 40) + 100) + 60, 80, 80, paintingChild);
			}
		}
	}
	
	public void drawBuilders(Graphics g, ImageObserver paintingChild)
	{
		int count = 0;
		while(builders.size() > 0) builders.remove(0);
		for(int i = 0; i < citizens.size(); i++)
		{
			boolean isBuilding = false;
			for(int k = 0; k < 24; k++) if(citizens.get(i).getSchedule()[k].getTaskType() == 3) isBuilding = true;
			if(isBuilding)
			{
				if(count < 15)
				{
					g.drawImage(boxSet, (((count % 3) * 100) + (40 * (count % 3))) + 45, (((count / 3) * 100) + ((count / 3) * 40) + 100) + 50, (((count % 3) * 100) + (40 * (count % 3))) + 145, (((count / 3) * 100) + ((count / 3) * 40) + 100) + 150, 201, 0, 400, 200, paintingChild); 
					g.drawImage(citizens.get(i).getAppearance(), (((count % 3) * 100) + (40 * (count % 3))) + 55, (((count / 3) * 100) + ((count / 3) * 40) + 100) + 60, 80, 80, paintingChild);
					builders.add(citizens.get(i));
					count++;
				}
			}
		}
	}
	
	public void checkTasksDone(int[] c)
	{
		clock = Arrays.copyOf(c, c.length);
		for(int i = 0; i < citizens.size(); i++)
		{
			if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 0)
			{
				citizens.get(i).setComfort(citizens.get(i).getComfort() + 3);
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 1)
			{
				if(inventorY[1] >= 2) 
				{
					citizens.get(i).setHunger(citizens.get(i).getHunger() + 3);
					inventorY[1] -= 2;
				}
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 2)
			{
				citizens.get(i).setComfort(citizens.get(i).getComfort() + 2);
				citizens.get(i).setHunger(citizens.get(i).getHunger() + 1);
			}
			
			
			if(citizens.get(i).getHunger() <= 0 || citizens.get(i).getComfort() <= 0) continue;
			
			if(citizens.get(i).isAssigned())
			{
				if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 9)
				{
					Scenery sceneryOnTile = locations.get(0).getGrid().getGridOfScenery()[citizens.get(i).getAssignedLocation()[0]][citizens.get(i).getAssignedLocation()[1]];
					Citizen currentClear = sceneryOnTile.getActiveClearers()[citizens.get(i).getAssignedLocation()[2]][citizens.get(i).getAssignedLocation()[3]];
					int progress = 0;
					if(currentClear != null)
					{
						if((int)(Math.random() * 100) < 75 + ((currentClear.getEndurance() / 2) * (currentClear.getSpeed() / 2))) 
						{
							progress = 1 + ((currentClear.getEndurance() / 2) * (currentClear.getSpeed() / 2)) / 2;
							int progress1 = progress - (int)(Math.random() * progress);
							int progress2 = progress - progress1;
							inventorY[2] += progress1;
							inventorY[2] += progress1;
						}
						citizens.get(i).setComfort(citizens.get(i).getComfort() - 1);
						citizens.get(i).setHunger(citizens.get(i).getHunger() - 1);
						sceneryOnTile.progress(citizens.get(i).getAssignedLocation()[3], progress);
						if(sceneryOnTile.isTileCleared()) locations.get(0).setGround(citizens.get(i).getAssignedLocation()[0], citizens.get(i).getAssignedLocation()[1], 2);
					}
	//				
	//				if(citizens.get(i).getSchedule()[clock[2]].getTaskType() != 9)
	//				{
	//					this.removeActiveClearer(progress)
	//					sceneryOnTile.getActiveClearers()[citizens.get(i).getAssignedLocation()[2]][citizens.get(i).getAssignedLocation()[3]] = null;
	//				}
				}
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 3)
			{
				if(citizens.get(i).isAssignedBuilder())
				{
					Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[citizens.get(i).getAssignedBuilderLocation()[0]][citizens.get(i).getAssignedBuilderLocation()[1]];
					
					if(buildingsOnTile.getProgress() == -1)
					{
						Citizen currentBuild = buildingsOnTile.getBuilders()[citizens.get(i).getAssignedBuilderLocation()[2]];
						int progress = 0;
						if(currentBuild != null)
						{
							if((int)(Math.random() * 100) < 75 + ((currentBuild.getEndurance() / 2) * (currentBuild.getStrength() / 2))) 
							{
								progress = 1 + ((currentBuild.getEndurance() / 2) * (currentBuild.getStrength() / 2)) / 2;
							}
							buildingsOnTile.progress(citizens.get(i).getAssignedBuilderLocation()[2], progress);
							if(buildingsOnTile.isBuilt()) 
							{
								if(currentBuild != null)
								{
									if((int)(Math.random() * 100) < 75 + ((currentBuild.getEndurance() / 2) * (currentBuild.getStrength() / 2))) 
									{
										progress = 1 + ((currentBuild.getEndurance() / 2) * (currentBuild.getStrength() / 2)) / 2;
										citizens.get(i).setComfort(citizens.get(i).getComfort() - 1);
										citizens.get(i).setHunger(citizens.get(i).getHunger() - 1);
									}
									buildingsOnTile.progress(citizens.get(i).getAssignedBuilderLocation()[2], progress);
									if(buildingsOnTile.isBuilt()) 
									{
										for(int k = 0; k < buildingsOnTile.getBuilders().length; k++)
										{
											int citizenID = -1;
											if(k < buildingsOnTile.getBuilders().length) 
											{
												if(buildingsOnTile.getBuilders()[k] != null)
												citizenID = buildingsOnTile.getBuilders()[k].getID();
											}
											for(int j = 0; j < citizens.size(); j++) 
											{
												if(citizens.get(j).getID() == citizenID) 
												{
													citizens.get(j).setAssignedBuilder(false);
													citizens.get(j).setAssignedBuilderLocation(-1, -1, -1);
												}
											}
											buildingsOnTile.getBuilders()[k] = null;
										}
									}
								}
								buildingsOnTile = new Building(buildingsOnTile.getType(), 0);
								
							}
							//if(buildingsOnTile.isBuilt()) locations.get(0).;
						}
					}
				}
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 4)
			{
				if((int)(Math.random() * 100) < 75 + ((citizens.get(i).getSpeed() / 2) * (citizens.get(i).getStrength() / 2))) 
				{
					int progress = 5 + ((citizens.get(i).getSpeed() / 2) * (citizens.get(i).getStrength() / 2)) / 2;
					inventorY[1] += progress;
				}
				citizens.get(i).setComfort(citizens.get(i).getComfort() - 1);
				citizens.get(i).setHunger(citizens.get(i).getHunger() - 2);
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 5)
			{
				if((int)(Math.random() * 100) < 75 + ((citizens.get(i).getIntelligence() / 2) * (citizens.get(i).getStrength() / 2))) 
				{
					int progress = 5 + ((citizens.get(i).getIntelligence() / 2) * (citizens.get(i).getStrength() / 2)) / 2;
					inventorY[1] += progress;
				}
					citizens.get(i).setComfort(citizens.get(i).getComfort() - 1);
				citizens.get(i).setHunger(citizens.get(i).getHunger() - 3);
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 6)
			{
				if((int)(Math.random() * 100) < 75 + ((citizens.get(i).getIntelligence() / 2) * (citizens.get(i).getSpeed() / 2))) 
				{
					int progress = 5 + ((citizens.get(i).getIntelligence() / 2) * (citizens.get(i).getSpeed() / 2)) / 2;
					inventorY[1] += progress;
				}
				citizens.get(i).setComfort(citizens.get(i).getComfort() - 1);
				citizens.get(i).setHunger(citizens.get(i).getHunger() - 2);
			}
			else if(citizens.get(i).getSchedule()[clock[5]].getTaskType() == 8)
			{
				if((int)(Math.random() * 100) < 20 + ((citizens.get(i).getIntelligence() / 2) * (citizens.get(i).getCharisma() / 2))) 
				{
					addCitizen();
				}
				
			}
		}
		
		this.repaint();
		this.revalidate();
	}

	public void drawActiveClearers(Graphics g, ImageObserver paintingChild)
	{
		Scenery sceneryOnTile = locations.get(0).getGrid().getGridOfScenery()[currentTileX][currentTileY];
		int count = 0;
		for(int i = 0; i < sceneryOnTile.getActiveClearers().length; i++)
		{
			for(int j = 0; j < sceneryOnTile.getActiveClearers()[i].length; j++)
			{
				if(sceneryOnTile.getActiveClearers()[i][j] != null)
				{
					g.drawImage(boxSet, ((i * 100) + (40 * i)) + 895, ((j * 220) + 100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, ((j * 220) + 100) + 200 + 15 + 40, 201, 0, 400, 200, paintingChild); 
					g.drawImage(sceneryOnTile.getActiveClearers()[i][j].getAppearance(), ((i * 100) + (40 * i)) + 895 + 10, ((j * 220) + 100) + 100 + 15 + 40 + 10, 80, 80, paintingChild);
					citizens.get(sceneryOnTile.getActiveClearers()[i][j].getID()).setAssignedLocation(currentTileX, currentTileY, i, j);
					// ((i * 100) + (40 * i)) + 895, ((j * 220) + 100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, ((j * 220) + 100) + 200 + 15 + 40
				}
			}
		}
	}
	
	public void drawAssignedResidents(Graphics g, ImageObserver paintingChild)
	{
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		for(int i = 0; i < buildingsOnTile.getResidents().length; i++)
		{
			if(buildingsOnTile.getResidents()[i] != null)
			{
				g.drawImage(boxSet, ((i * 100) + (40 * i)) + 895, (100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, (100) + 200 + 15 + 40, 201, 0, 400, 200, paintingChild); 
				g.drawImage(buildingsOnTile.getResidents()[i].getAppearance(), ((i * 100) + (40 * i)) + 895 + 10, (100) + 100 + 15 + 40 + 10, 80, 80, paintingChild);
				citizens.get(buildingsOnTile.getResidents()[i].getID()).setAssignedResidentLocation(currentTileX, currentTileY, i);
				citizens.get(buildingsOnTile.getResidents()[i].getID()).setAssignedResident(true);
				// ((i * 100) + (40 * i)) + 895, ((j * 220) + 100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, ((j * 220) + 100) + 200 + 15 + 40
			}
		}
	}
	

	public void drawActiveBuilders(Graphics g, ImageObserver paintingChild)
	{
		Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
		int count = 0;
		for(int i = 0; i < buildingsOnTile.getBuilders().length; i++)
		{
			if(buildingsOnTile.getBuilders()[i] != null)
			{
				g.drawImage(boxSet, ((i * 100) + (40 * i)) + 895, (100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, (100) + 200 + 15 + 40, 201, 0, 400, 200, paintingChild); 
				g.drawImage(buildingsOnTile.getBuilders()[i].getAppearance(), ((i * 100) + (40 * i)) + 895 + 10, (100) + 100 + 15 + 40 + 10, 80, 80, paintingChild);
				System.out.println("This was run");
				citizens.get(buildingsOnTile.getBuilders()[i].getID()).setAssignedBuilderLocation(currentTileX, currentTileY, i);
				citizens.get(buildingsOnTile.getBuilders()[i].getID()).setAssignedBuilder(true);
					// ((i * 100) + (40 * i)) + 895, ((j * 220) + 100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, ((j * 220) + 100) + 200 + 15 + 40
			}
		}
	}
	
	// ALL THE GRAPHICS
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		ImageObserver paintingChild = null;
		
		switch(room) 
		{
		
		//TITLE SCREEN
		case 0:
			
			//Background
			for(int i = 0; i < 16; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					int k = (int)(Math.random() * 3);
					switch(k)
					{
					case 2: 
						int p = (int)Math.random() * 2;
						if(p == 0) g.drawImage(tileSet, i * 200, j * 200, (i * 200) + 200, (j * 200) + 200, 0, 0, 200, 200, paintingChild);
						else if(p == 1) g.drawImage(tileSet, i * 200, j * 200, (i * 200) + 200, (j * 200) + 200, 200, 0, 400, 200, paintingChild);
						g.drawImage(tileSet, i * 200, j * 200, (i * 200) + 200, (j * 200) + 200, 400, 0, 600, 200, paintingChild);
						break;
					case 1:
						g.drawImage(tileSet, i * 200, j * 200, (i * 200) + 200, (j * 200) + 200, 200, 0, 400, 200, paintingChild);
						break;
					case 0:
						g.drawImage(tileSet, i * 200, j * 200, (i * 200) + 200, (j * 200) + 200, 0, 0, 200, 200, paintingChild);
						break;
					}
				}
			}
			
			//Person Placement
			for(int i = (int)(Math.random() * 8); i > -1; i--)
			{
				int[] personPlace = {(int)(Math.random() * 1600), (int)(Math.random() * 900)};
				int j = (int)(Math.random() * 3);
				switch(j)
				{
				case 0: g.drawImage(personSet, personPlace[0], personPlace[1], personPlace[0] + 75, personPlace[1] + 75, 0, 0, 200, 200, paintingChild); break;
				case 1: g.drawImage(personSet, personPlace[0], personPlace[1], personPlace[0] + 75, personPlace[1] + 75, 200, 0, 400, 200, paintingChild); break;
				case 2: g.drawImage(personSet, personPlace[0], personPlace[1], personPlace[0] + 75, personPlace[1] + 75, 400, 0, 600, 200, paintingChild); break;
				}
			}
			
			// Set Tile Offset
			int xTileOffset1;
			int amntXTilesRight1;
			if(isTileMenuOpen) { xTileOffset1 = 235; amntXTilesRight1 = 6; }
			else { xTileOffset1 = 125; amntXTilesRight1 = 4; }
			int cTXMinMod1 = currentTileX - 4, cTXPosMod1 = currentTileX + amntXTilesRight1;
			int cTYMinMod1 = currentTileY - 2, cTYPosMod1 = currentTileY + 2;
						
			for(int i = cTXMinMod1; i <= cTXPosMod1; i++)
			{
				for(int j = cTYMinMod1; j <= cTYPosMod1; j++)
				{
					if(!(i < 0) && !(i > 54) && !(j < 0) && !(j > 54)) 
					{
						drawTile(i, j, cTXMinMod1, cTXPosMod1, cTYMinMod1, cTYPosMod1, xTileOffset1, g, paintingChild);
					}
				}
			}

			// Display Current Tile
			g.drawImage(tileSet, (4) * 200 - xTileOffset1, (2) * 200 - 75, ((4) * 200) + 200 - xTileOffset1, ((2) * 200) + 200 - 75, 600, 0, 800, 200, paintingChild);
						
			// Draw Scenery
			for(int  i = cTXMinMod1; i <= cTXPosMod1; i++)
			{
				for(int j = cTYMinMod1; j <= cTYPosMod1; j++)
				{
					if(!(i < 0) && !(i > 54) && !(j < 0) && !(j > 54)) 
					{
									
						//Draw Scenery
						drawScenery(i, j, cTXMinMod1, cTXPosMod1, cTYMinMod1, cTYPosMod1, xTileOffset1, g, paintingChild);
					}
				}
			}
			
			//Blue Overlay
			g.drawImage(overlaySet, 0, 0, 1600, 900, 0, 0, 20, 40, paintingChild);
			
			// Title
			g.drawImage(titleScreenSet, 350, 25, 1150, 425, 0, 0, 822, 336, paintingChild);
			// New Game Button
			g.drawImage(titleScreenSet, 550, 450, 950, 550, 1575, 0, 2323, 181, paintingChild);
			// Continue Button
			g.drawImage(titleScreenSet, 550, 575, 950, 675, 827, 0, 1575, 181, paintingChild);
			
			break;
		
		//GAME
		case 1:
			if(text.size() != 0) 
			{
				while(text.size() > 0) 
				{
					this.remove(text.get(0));					
					text.remove(0);					
				}				
			}
			
			g.drawImage(backgroundSet, 0, 0, 1600, 900, 0, 0, 1600, 900, paintingChild);
			
			// Display Map
			
			// Set Tile Offset
			int xTileOffset;
			int amntXTilesRight;
			if(isTileMenuOpen) { xTileOffset = 235; amntXTilesRight = 6; }
			else { xTileOffset = 125; amntXTilesRight = 4; }
			int cTXMinMod = currentTileX - 4, cTXPosMod = currentTileX + amntXTilesRight;
			int cTYMinMod = currentTileY - 2, cTYPosMod = currentTileY + 2;
			
			for(int i = cTXMinMod; i <= cTXPosMod; i++)
			{
				for(int j = cTYMinMod; j <= cTYPosMod; j++)
				{
					if(!(i < 0) && !(i > 54) && !(j < 0) && !(j > 54)) 
					{
						drawTile(i, j, cTXMinMod, cTXPosMod, cTYMinMod, cTYPosMod, xTileOffset, g, paintingChild);
					}
				}
			}

			// Display Current Tile
			g.drawImage(tileSet, (4) * 200 - xTileOffset, (2) * 200 - 75, ((4) * 200) + 200 - xTileOffset, ((2) * 200) + 200 - 75, 600, 0, 800, 200, paintingChild);
			
			// Draw Scenery
			for(int  i = cTXMinMod; i <= cTXPosMod; i++)
			{
				for(int j = cTYMinMod; j <= cTYPosMod; j++)
				{
					if(!(i < 0) && !(i > 54) && !(j < 0) && !(j > 54)) 
					{
						
						//Draw Scenery
						drawScenery(i, j, cTXMinMod, cTXPosMod, cTYMinMod, cTYPosMod, xTileOffset, g, paintingChild);
					}
				}
			}
			
			if(!isTileMenuOpen && !isItANewHour)
			{
				if(clock[1] == 0)
				{
					g.drawImage(boxSet, 675, 700, 875, 800, 0, 0, 200, 200, paintingChild);
					newSizedColoredText(text.size(), "Start Day", 675, 700, 200, 100, 40f, Color.YELLOW);
					text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
					
				}
			}
			
			// Display Character Menu
			if(isCharacterMenuOpen && !isItANewHour)
			{
				
				g.drawImage(overlaySet, 0, 0, 1600, 900, 800, 0, 960, 90, paintingChild);				
				g.drawImage(boxSet, 200, 100, 1400, 750, 0, 0, 200, 200, paintingChild);
				g.drawImage(boxSet, 300, 200, 1300, 675, 201, 0, 400, 200, paintingChild);
	
				
				g.drawImage(boxSet, 1200, 140, 1250, 190, 200, 0, 400, 200, paintingChild);
				newSizedColoredText(text.size(), "X", 1207, 140, 50, 50, 50f, Color.YELLOW);
			
				if(isSpecificCharacterMenuOpen == -1)
				{
					newSizedColoredText(text.size(), "Citizens", 685, 100, 240, 100, 60f, Color.YELLOW);
									
					for(int i = 0; i < citizens.size(); i++)
					{
						if(i / 6 == 0)
						{
							g.drawImage(boxSet, (i * 75) + (i * 50) + 450, 250, (i * 75) + (i * 50) + 550, 350, 0, 0, 200, 200, paintingChild);
							g.drawImage(citizens.get(i).getAppearance(), (i * 75) + (i * 50) + 463, 263, 75, 75, paintingChild);
						}
						else if(i / 6 == 1)
						{
							g.drawImage(boxSet, (i / 7 * 75) + (i / 7 * 50) + 450, (i / 6 * 50) + (i / 6 * 75) + 250, (i / 7 * 75) + (i / 7 * 50) + 550, (i / 6 * 50) + (i / 6 * 75) + 350, 0, 0, 200, 200, paintingChild);
							g.drawImage(citizens.get(i).getAppearance(), (i / 7 * 75) + (i / 7 * 50) + 463, (i / 6 * 50) + (i / 6 * 75) + 263, 75, 75, paintingChild);
						}
					}
				}
				else
				{
					g.drawImage(boxSet, 350, 140, 400, 190, 200, 0, 400, 200, paintingChild);
					newSizedColoredText(text.size(), "<-", 350, 140, 50, 50, 50f, Color.YELLOW);
					
					if(isACharacterAttributeMenuOpen == -1)
					{
						Citizen citCurrent = citizens.get(isSpecificCharacterMenuOpen);
						g.drawImage(citCurrent.getAppearance(), 400, 250, 350, 350, paintingChild);
						newSizedColoredText(text.size(), citCurrent.getName(), 410, 110, 500, 100, 60f, Color.YELLOW);
						
						g.drawImage(boxSet, 800, 325, 1175, 383, 0, 0, 200, 200, paintingChild);
						newSizedColoredText(text.size(), "Stats", 937, 305, 100, 100, 40f, Color.YELLOW);
	
						g.drawImage(boxSet, 800, 409, 1175, 467, 0, 0, 200, 200, paintingChild);
						newSizedColoredText(text.size(), "Apparel", 915, 389, 150, 100, 40f, Color.YELLOW);
	
						g.drawImage(boxSet, 800, 493, 1175, 551, 0, 0, 200, 200, paintingChild);
						newSizedColoredText(text.size(), "Thoughts", 897, 473, 190, 100, 40f, Color.YELLOW);
					}
					else if(isACharacterAttributeMenuOpen == 0)
					{
						Citizen citCurrent = citizens.get(isSpecificCharacterMenuOpen);
						
						newSizedColoredText(text.size(), "Stats", 200, 110, 1200, 100, 60f, Color.YELLOW);
						text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
						
						g.drawImage(boxSet, 475, 560, 525, 610, 400, 0, 600, 200, paintingChild);
						g.drawImage(aestheticsSet, 475, 560, 525, 610, 0, 0, 200, 200, paintingChild);						
						g.drawImage(aestheticsSet, 475, 245, 525, 545, 265, 80, 270, 85, paintingChild);						
						g.drawImage(aestheticsSet, 485, 535 - (citCurrent.getHunger() * 14), 515, 535, 140, 100, 145, 105, paintingChild);						
										
						g.drawImage(boxSet, 625, 560, 675, 610, 400, 0, 600, 200, paintingChild);
						g.drawImage(aestheticsSet, 625, 560, 675, 610, 200, 0, 400, 200, paintingChild);						
						g.drawImage(aestheticsSet, 625, 245, 675, 545, 265, 80, 270, 85, paintingChild);						
						g.drawImage(aestheticsSet, 635, 535 - (citCurrent.getComfort() * 14), 665, 535, 230, 80, 235, 85, paintingChild);						
						
						g.drawImage(boxSet, 1050, 250, 1100, 300, 400, 0, 600, 200, paintingChild);
						newSizedColoredText(text.size(), "" + citCurrent.getStrength(), 1063, 260, 100, 30, 40f, Color.RED);
						newSizedColoredText(text.size(), "Strength:", 800, 250, 300, 50, 40f, Color.RED);
						
						g.drawImage(boxSet, 1050, 312, 1100, 362, 400, 0, 600, 200, paintingChild);
						newSizedColoredText(text.size(), "" + citCurrent.getSpeed(), 1063, 322, 100, 30, 40f, Color.GREEN);
						newSizedColoredText(text.size(), "Speed:", 800, 312, 300, 50, 40f, Color.GREEN);
						
						g.drawImage(boxSet, 1050, 374, 1100, 424, 400, 0, 600, 200, paintingChild);
						newSizedColoredText(text.size(), "" + citCurrent.getEndurance(), 1063, 384, 100, 30, 40f, Color.ORANGE);
						newSizedColoredText(text.size(), "Endurance:", 800, 374, 300, 50, 40f, Color.ORANGE);
			
						g.drawImage(boxSet, 1050, 436, 1100, 486, 400, 0, 600, 200, paintingChild);
						newSizedColoredText(text.size(), "" + citCurrent.getLuck(), 1063, 446, 100, 30, 40f, Color.MAGENTA);
						newSizedColoredText(text.size(), "Luck:", 800, 436, 300, 50, 40f, Color.MAGENTA);

						g.drawImage(boxSet, 1050, 498, 1100, 548, 400, 0, 600, 200, paintingChild);
						newSizedColoredText(text.size(), "" + citCurrent.getIntelligence(), 1063, 508, 100, 30, 40f, Color.YELLOW);
						newSizedColoredText(text.size(), "Intelligence:", 800, 498, 300, 50, 40f, Color.YELLOW);

						g.drawImage(boxSet, 1050, 560, 1100, 610, 400, 0, 600, 200, paintingChild);
						newSizedColoredText(text.size(), "" + citCurrent.getCharisma(), 1063, 570, 100, 30, 40f, Color.BLUE);
						newSizedColoredText(text.size(), "Charisma:", 800, 560, 300, 50, 40f, Color.BLUE);
						
					}
					else if(isACharacterAttributeMenuOpen == 2)
					{
						Citizen citCurrent = citizens.get(isSpecificCharacterMenuOpen);
						Task[] currentSchedule = Arrays.copyOf(citCurrent.getSchedule(), 24);
						
						newSizedColoredText(text.size(), "Thoughts", 200, 110, 1200, 100, 60f, Color.YELLOW);
						text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
						
						g.drawImage(boxSet, 430, 225, 755, 650, 800, 0, 1000, 200, paintingChild);
						g.drawImage(boxSet, 805, 225, 1130, 650, 800, 0, 1000, 200, paintingChild);
						for(int i = 1; i <= 3; i++) 
						{
							g.drawImage(aestheticsSet, 430, 235 + (i * 100), 755, 235 + (i * 100) + 4, 265, 80, 270, 85, paintingChild);						
							g.drawImage(aestheticsSet, 805, 228 + (i * 105), 1130, 228 + (i * 105) + 5, 265, 80, 270, 85, paintingChild);						
						}
						
						for(int i = 0; i < 4; i++)
						{
							g.drawImage(professionBoxSet, 430, 225 + (i * 105), 755, 330 + (i * 105), 0 + ((i+scrollMenu_CAM1[6]) * 200), 0, 200 + ((i+scrollMenu_CAM1[6]) * 200), 200, paintingChild);
							
							
							newSizedColoredText(text.size(), getTaskNameTime(i+scrollMenu_CAM1[6])[0], 445, 250 + (i * 105), 300, 50, 30f, Color.BLACK);
							newSizedColoredText(text.size(), getTaskNameTime(i+scrollMenu_CAM1[6])[1] + "hr", 670, 250 + (i * 105), 300, 50, 30f, Color.BLACK);
							
							String[] times = {"6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM", "12AM", "1AM", "2AM", "3AM", "4AM", "5AM"};
							int j = (scrollBar_CAM2.getTopOfBar() - 375) / 6;
							newSizedColoredText(text.size(), times[i+j], 820, 240 + (i * 105), 300, 20, 20f, Color.BLACK);
						}
						int taskLengthDisplayed = 0;
						for(int i = 0, j = (scrollBar_CAM2.getTopOfBar() - 375) / 6; i < 4; i++)
						{
							if(i + j + 1 < currentSchedule.length)
							{
								if((currentSchedule[i+j].getTaskType() == currentSchedule[i+j+1].getTaskType()) && 
								(currentSchedule[i+j].getTaskID() == currentSchedule[i+j+1].getTaskID()) &&
								(i != 3) && (currentSchedule[i+j].getTaskType() != -1))
								{
									taskLengthDisplayed++;
									continue;
								}
							}
							if(currentSchedule[i + j].getTaskType() != -1)
							{
								g.drawImage(professionBoxSet, 805, 228 + ((i - taskLengthDisplayed) * 105), 1130, 333 + (i * 105), 0 + ((currentSchedule[i+j].getTaskType()) * 200), 0, 200 + ((currentSchedule[i+j].getTaskType()) * 200), 200, paintingChild);
								
								g.drawImage(boxSet, 1095, 240 + ((i - taskLengthDisplayed) * 105), 1115, 260 + ((i - taskLengthDisplayed) * 105), 0, 0, 200, 200, paintingChild);
								newSizedColoredText(text.size(), "X", 1095, 240 + ((i - taskLengthDisplayed) * 105), 20, 20, 20f, Color.YELLOW);
								text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
								
								newSizedColoredText(text.size(), getTaskNameTime(currentSchedule[i+j].getTaskType())[0], 805 + 15, 228 + 25 + ((i - taskLengthDisplayed) * 105), 300, 50, 30f, Color.BLACK);
								newSizedColoredText(text.size(), getTaskNameTime(currentSchedule[i+j].getTaskType())[1] + "hr", 805 + 240, 228 + 25 + ((i - taskLengthDisplayed) * 105), 300, 50, 30f, Color.BLACK);
								
								taskLengthDisplayed = 0;	
							}
						}
						g.drawImage(aestheticsSet, 765, 375, 785, 525, 265, 80, 270, 85, paintingChild);	
						g.drawImage(boxSet, 765, scrollBar_CAM1.getTopOfBar(), 785, scrollBar_CAM1.getBottomOfBar(), 0, 0, 200, 200, paintingChild);
						g.drawImage(aestheticsSet, 1140, 375, 1160, 525, 265, 80, 270, 85, paintingChild);	
						g.drawImage(boxSet, 1140, scrollBar_CAM2.getTopOfBar(), 1160, scrollBar_CAM2.getBottomOfBar(), 0, 0, 200, 200, paintingChild);
						
						newSizedColoredText(text.size(), getTaskNameTime(scrollMenu_CAM1[7]+scrollMenu_CAM1[6])[0], 15 + (scrollMenu_CAM1[2] + scrollMenu_CAM1[4] - scrollMenu_CAM1[0]), 25 + (scrollMenu_CAM1[3] + scrollMenu_CAM1[5] - scrollMenu_CAM1[1]), 300, 50, 30f, Color.BLACK);
						newSizedColoredText(text.size(), getTaskNameTime(scrollMenu_CAM1[7]+scrollMenu_CAM1[6])[1] + "hr", 240 + (scrollMenu_CAM1[2] + scrollMenu_CAM1[4] - scrollMenu_CAM1[0]), 25 + (scrollMenu_CAM1[3] + scrollMenu_CAM1[5] - scrollMenu_CAM1[1]), 300, 50, 30f, Color.BLACK);
						g.drawImage(professionBoxSet, 
								(scrollMenu_CAM1[2] + scrollMenu_CAM1[4] - scrollMenu_CAM1[0]), (scrollMenu_CAM1[3] + scrollMenu_CAM1[5] - scrollMenu_CAM1[1]), 
								(scrollMenu_CAM1[2] + scrollMenu_CAM1[4] - scrollMenu_CAM1[0]) + 325, (scrollMenu_CAM1[3] + scrollMenu_CAM1[5] - scrollMenu_CAM1[1]) + 105, 
								0 + ((scrollMenu_CAM1[6] + scrollMenu_CAM1[7]) * 200), 0, 
								200 + ((scrollMenu_CAM1[6] + scrollMenu_CAM1[7]) * 200), 200, paintingChild);
					}
				}
			}

			
			// Display Tile Menu
			if(isTileMenuOpen && !isItANewHour)
			{
				g.drawImage(overlaySet, 0, 0, 1600, 900, 800, 0, 960, 90, paintingChild);				
				g.drawImage(boxSet, 0, 0, 475, 850, 0, 0, 200, 200, paintingChild);
				g.drawImage(boxSet, 845, 0, 1320, 850, 0, 0, 200, 200, paintingChild);
				
				//g.drawImage(boxSet, 835, 100, 1575, 200, 745, 0, 1488, 168, paintingChild);

				Scenery sceneryOnTile = locations.get(0).getGrid().getGridOfScenery()[currentTileX][currentTileY];
				String sceneryName = "";
				int tileType = locations.get(0).getGround()[currentTileX][currentTileY];
				if(isASceneryRemovalMenuOpen == -1)
				{
					switch(tileType)
					{
					case 0:
						for(int k = 0; k < sceneryOnTile.getAmountOfScenery(); k++)
						{
							int deltaY = 40;
							// Show the different scenery that can be removed on a tile
							g.drawImage(boxSet, 1150 - 255, (k * 220) + 110 + deltaY, 1270, (k * 220) + 210 + deltaY, 201, 0, 400, 200, paintingChild);
							g.drawImage(sceneryOnTile.getProgressImage(k), 1150 - 255, (k * 220) + 110 + deltaY, 1150 - 255 + sceneryOnTile.getProgessPercent(k, 375), (k * 220) + 210 + deltaY, 0, 0, sceneryOnTile.getProgessPercent(k, 200), 200, paintingChild);
							g.drawImage(boxSet, 1210 - 255, (k * 220) + 120 + deltaY, 1030, (k * 220) + 200 + deltaY, 0, 0, 200, 200, paintingChild);
							
							//.getSubimage(0, 0, (int)(200 * ((maxProgress[i] - progress[i]) / maxProgress[i])), 200);
							
							text.add(new JLabel());
							switch(sceneryOnTile.getObject(k))
							{
							case 0:
								sceneryName = "Sproutling";
								g.drawImage(scenerySet, 920, ((k * 220) + 100) - 120 + deltaY, 1065, ((k * 220) + 200) - 20 + deltaY,
										1200, 0, 1400, 400, paintingChild);
								break;
							case 1:
								sceneryName = "Small Tree";
								g.drawImage(scenerySet, 945, ((k * 220) + 100) - 50 + deltaY, 1040, ((k * 220) + 200) - 10 + deltaY,
										1400, 0, 1600, 400, paintingChild);
								break;
							case 2:
								sceneryName = "Large Tree";
								g.drawImage(scenerySet, 965, ((k * 220) + 100) + 10 + deltaY, 1020, ((k * 220) + 200) - 10 + deltaY,
										1600, 0, 1800, 400, paintingChild);
								break;
							}
							
							newSizedColoredText(k, sceneryName, 1045, (k * 220) + 132 + deltaY, 150, 50, 20f, Color.YELLOW);
						
						}
						
						break;
						
					case 1:
						for(int k = 0; k < sceneryOnTile.getAmountOfScenery(); k++)
						{
							int deltaY = 40;
							// Show the different scenery that can be removed on a tile
							text.add(new JLabel());
							
							g.drawImage(boxSet, 1150 - 255, (k * 220) + 100 + 10 + deltaY, 1525 - 255, (k * 220) + 10 + 200 + deltaY, 201, 0, 400, 200, paintingChild);
							g.drawImage(sceneryOnTile.getProgressImage(k), 1150 - 255, (k * 220) + 110 + deltaY, 1150 - 255 + sceneryOnTile.getProgessPercent(k, 375), (k * 220) + 210 + deltaY, 0, 0, sceneryOnTile.getProgessPercent(k, 200), 200, paintingChild);
							g.drawImage(boxSet, 1210 - 255, (k * 220) + 100 + 10 + 10 + deltaY, 1285 - 255, (k * 220) + 100 + 10 + 90 + deltaY, 0, 0, 200, 200, paintingChild);
							
							switch(sceneryOnTile.getObject(k))
							{
							case 0:
								sceneryName = "Small Rock";
								g.drawImage(scenerySet, 1210 - 100 - 255, ((k * 220) + 100) - 135 + 15 + deltaY, 
										1285 + 100 - 255, ((k * 220) + 200) - 35 + 15 + deltaY, 
										0, 0, 400, 200, paintingChild);							
								break;
							case 1:
								sceneryName = "Large Rock";
								g.drawImage(scenerySet, 1210 - 20 - 255, ((k * 220) + 100) + 15 + deltaY, 
										1285 + 20 - 255, ((k * 220) + 200) - 35 + 15 + deltaY, 
										400, 0, 800, 200, paintingChild);
								break;
							case 2:
								sceneryName = "Boulder";
								g.drawImage(scenerySet, 1210 - 5 - 255, ((k * 220) + 100) + 10 + 15 + deltaY, 
										1285 + 5 - 255, ((k * 220) + 200) - 35 + 15 + deltaY, 
										800, 0, 1200, 200, paintingChild);
								break;
							}
							
						newSizedColoredText(k, sceneryName, 1300 - 255, (k * 220) + 100 + 10 + 22 + deltaY, 150, 50, 20f, Color.YELLOW);
						
						}
						
						break;
						
					case 2:
						Building buildingsOnTile = locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY];
						
						g.drawImage(boxSet, 895, 150, 1270, 250, 201, 0, 400, 200, paintingChild);
						g.drawImage(boxSet, 1210 - 255, 100 + 10 + 10 + 40, 1285 - 255, 100 + 10 + 90 + 40, 0, 0, 200, 200, paintingChild);
						if(buildingsOnTile.getProgress() != -1)
						{
							g.drawImage(buildingsSet, 920 + 27, (100) - 120 + 40 + 140, 920 + 27 + 95, (100) - 120 + 40 + 140 + 95, 200, 0, 400, 200, paintingChild);
							newSizedColoredText(text.size(), "Leaf Tent", 1300 - 255, 100 + 10 + 22 + 40, 150, 50, 20f, Color.YELLOW);
						}
						else
						{
							switch(buildingsOnTile.getType())
							{
							case 0: 
								g.drawImage(boxSet, 1150 - 255, 110 + 40, 1150 - 255 + buildingsOnTile.getProgessPercent(0, 375), 210 + 40, 1200, 0, buildingsOnTile.getProgessPercent(0, 200) + 1200, 200, paintingChild);
								g.drawImage(boxSet, 1210 - 255, 100 + 10 + 10 + 40, 1285 - 255, 100 + 10 + 90 + 40, 0, 0, 200, 200, paintingChild);
								g.drawImage(buildingsSet, 920 + 27, (100) - 120 + 40 + 140, 920 + 27 + 95, (100) - 120 + 40 + 140 + 95, 200, 0, 400, 200, paintingChild);
								newSizedColoredText(text.size(), "Leaf Tent", 1300 - 255, 100 + 10 + 22 + 40, 150, 50, 20f, Color.YELLOW);
								break;
							case -1:
								g.drawImage(boxSet, 1150 - 255, 110 + 40, 1150 - 255 + buildingsOnTile.getProgessPercent(0, 375), 210 + 40, 1200, 0, buildingsOnTile.getProgessPercent(0, 200) + 1200, 200, paintingChild);
								g.drawImage(boxSet, 1210 - 255, 100 + 10 + 10 + 40, 1285 - 255, 100 + 10 + 90 + 40, 0, 0, 200, 200, paintingChild);
								newSizedColoredText(text.size(), "Project hasn't been selected.", 1300 - 255, 100 + 10 + 22 + 40, 300, 50, 14f, Color.YELLOW);
								break;
							}
						}
						
					}
					int iCount = 3;
					int jCount = 3;
					if(locations.get(0).getGround()[currentTileX][currentTileY] == 2) { iCount = 1; jCount = 1; }
					if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getProgress() == -1) iCount = 3;
					for(int i = 0; i < iCount; i++)
					{
						for(int j = 0; j < jCount; j++) 
						{ 
							g.drawImage(boxSet, ((i * 100) + (40 * i)) + 895, ((j * 220) + 100) + 100 + 15 + 40, ((i * 100) + (40 * i)) + 995, ((j * 220) + 100) + 200 + 15 + 40, 1000, 0, 1200, 200, paintingChild); 
							
						}
					}
					
//					// Add the text to the Screen
//					if(text.size() != 0) 
//					{
//						for(int i = 0; i < text.size(); i++) { this.add(text.get(i)); }
//					}

					
					
				}
				else if(isASceneryRemovalMenuOpen == 0)
				{
					g.drawImage(boxSet, 830, 30, 880, 80, 0, 0, 200, 200, paintingChild);
					newSizedColoredText(text.size(), "<-", 830, 30, 50, 50, 50f, Color.YELLOW);
					text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
					
					for(int i = 0; i < 6; i++)
					{
						g.drawImage(boxSet, 895, 150 + (100 * i), 1270, 250 + (100 * i), 201, 0, 400, 200, paintingChild);
						g.drawImage(boxSet, 1210 - 255, 100 + 10 + 10 + 40 + (100 * i), 1285 - 255, 100 + 10 + 90 + 40 + (100 * i), 0, 0, 200, 200, paintingChild);
						if(i == 0)
						{
							g.drawImage(buildingsSet, 920 + 27, (100) - 120 + 40 + 140 + (100 * i), 920 + 27 + 95, (100) - 120 + 40 + 140 + 95 + (100 * i), 0 + ((1 + i) * 200), 0, 200 + ((1 + i) * 200), 200, paintingChild);
							newSizedColoredText(text.size(), "Leaf Tent", 1300 - 255, 100 + 10 + 22 + 40, 150, 50, 20f, Color.YELLOW);
						}
					}
				}
				
				// Display Current Tile To Be Above Overlays
				g.drawImage(tileSet, (4) * 200 - xTileOffset, (2) * 200 - 75, ((4) * 200) + 200 - xTileOffset, ((2) * 200) + 200 - 75, 600, 0, 800, 200, paintingChild);
				drawTile(currentTileX, currentTileY, cTXMinMod, cTXPosMod, cTYMinMod, cTYPosMod, xTileOffset, g, paintingChild);
				g.drawImage(tileSet, (4) * 200 - xTileOffset, (2) * 200 - 75, ((4) * 200) + 200 - xTileOffset, ((2) * 200) + 200 - 75, 600, 0, 800, 200, paintingChild);
				drawScenery(currentTileX, currentTileY, cTXMinMod, cTXPosMod, cTYMinMod, cTYPosMod, xTileOffset, g, paintingChild);
				
				if(locations.get(0).getGround()[currentTileX][currentTileY] == 0 
						|| locations.get(0).getGround()[currentTileX][currentTileY] == 1)
				{
					g.drawImage(headersSet, 915, 35, 1250, 140, 0, 0, 400, 114, paintingChild);				
					g.drawImage(headersSet, 70, 35, 405, 140, 400, 0, 800, 114, paintingChild);				
					
					drawCitizensClearing(g, paintingChild);
					
					drawActiveClearers(g, paintingChild);
					
					//Check if there are citizens set to clear
					if(clearers.size() > menu_TMC[6] + (menu_TMC[7] * 3))
					{
						//Draw the clearer being dragged
						g.drawImage(boxSet, (menu_TMC[2] + menu_TMC[4] - menu_TMC[0]), (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]), 
							(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 100, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 100, 
							201, 0, 400, 200, paintingChild); 
						g.drawImage(clearers.get(menu_TMC[6] + (menu_TMC[7] * 3)).getAppearance(), 
								(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 10, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 10, 
								(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 90, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 90, 
								0, 0, 200, 200, paintingChild);
					}
					
					if(displayX[0] != 0)
					{
						if(locations.get(0).getGrid().getGridOfScenery()[currentTileX][currentTileY].getActiveClearers()[displayX[2] % 3][displayX[2] / 3] != null
								&& locations.get(0).getGrid().getGridOfScenery()[currentTileX][currentTileY].getActiveClearers()[displayX[2] % 3][displayX[2] / 3].isAssigned())
						{
							newSizedColoredText(text.size(), "X", displayX[0], displayX[1], 100, 100, 100f, Color.YELLOW);
							text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
						}
					}
				}
				else if(locations.get(0).getGround()[currentTileX][currentTileY] == 2)
				{
					if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getProgress() != -1)
					{
						g.drawImage(headersSet, 915, 35, 1250, 140, 800, 0, 1200, 114, paintingChild);				
						g.drawImage(headersSet, 70, 35, 405, 140, 1200, 0, 1600, 114, paintingChild);				
						
						drawResidents(g, paintingChild);
						
						drawAssignedResidents(g, paintingChild);
						
						
						if(citizens.size() > menu_TMC[6] + (menu_TMC[7] * 3))
						{
							//Draw the clearer being dragged
							g.drawImage(boxSet, (menu_TMC[2] + menu_TMC[4] - menu_TMC[0]), (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]), 
								(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 100, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 100, 
								201, 0, 400, 200, paintingChild); 
							g.drawImage(citizens.get(menu_TMC[6] + (menu_TMC[7] * 3)).getAppearance(), 
									(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 10, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 10, 
									(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 90, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 90, 
									0, 0, 200, 200, paintingChild);
						}
						if(displayX[0] != 0)
						{
							
							if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getResidents()[0] != null
									&& locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getResidents()[0].isAssignedResident())
							{
								if(displayX[0] == 895 && displayX[1] == 255)
								{
									newSizedColoredText(text.size(), "X", displayX[0], displayX[1], 100, 100, 100f, Color.YELLOW);
									text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
								}
							}
						}
					}
					else
					{
						
						g.drawImage(headersSet, 915, 35, 1250, 140, 1600, 0, 2000, 114, paintingChild);				
						g.drawImage(headersSet, 70, 35, 405, 140, 2000, 0, 2400, 114, paintingChild);				
							
						drawBuilders(g, paintingChild);
						
						if(isASceneryRemovalMenuOpen == -1)
						{	
							drawActiveBuilders(g, paintingChild);
							
							g.drawImage(boxSet, 930, 490, 1235, 595, 201, 0, 400, 200, paintingChild);
							if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].buildIsSet()) newSizedColoredText(text.size(), "Project is set.", 930, 490, 305, 105, 30f, Color.YELLOW);
							else newSizedColoredText(text.size(), "Set this project?", 930, 490, 305, 105, 30f, Color.YELLOW);
							text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
						
							if(builders.size() > menu_TMC[6] + (menu_TMC[7] * 3))
							{
								//Draw the clearer being dragged
								g.drawImage(boxSet, (menu_TMC[2] + menu_TMC[4] - menu_TMC[0]), (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]), 
									(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 100, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 100, 
									201, 0, 400, 200, paintingChild); 
								g.drawImage(builders.get(menu_TMC[6] + (menu_TMC[7] * 3)).getAppearance(), 
										(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 10, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 10, 
										(menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + 90, (menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + 90, 
										0, 0, 200, 200, paintingChild);
							}
							if(displayX[0] != 0)
							{
								
								if(locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getBuilders()[displayX[2] % 3] != null
										&& locations.get(0).getGrid().getGridOfBuildings()[currentTileX][currentTileY].getBuilders()[displayX[2] % 3].isAssignedBuilder())
								{
									if((displayX[0] == 895 || displayX[0] == 1035 || displayX[0] == 1175) && displayX[1] == 255)
									{
										newSizedColoredText(text.size(), "X", displayX[0], displayX[1], 100, 100, 100f, Color.YELLOW);
										text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
									}
								}
							}
						}
					}
				}
			}
		
			if(!isItANewHour)
			{
					
				// Draw Calendar, Menu, Buttons
				g.drawImage(boxSet, 1350, 10, 1575, 210, 0, 0, 200, 200, paintingChild);
				g.drawImage(boxSet, 1375, 215, 1550, 265, 0, 0, 200, 200, paintingChild);
				newSizedColoredText(text.size(), "Citizens", 1375, 215, 175, 50, 30, Color.YELLOW);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
				g.drawImage(boxSet, 1375, 270, 1550, 320, 0, 0, 200, 200, paintingChild);
				newSizedColoredText(text.size(), "Tile Menu", 1375, 270, 175, 50, 30, Color.YELLOW);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
				g.drawImage(boxSet, 1375, 330, 1550, 380, 0, 0, 200, 200, paintingChild);
				g.drawImage(aestheticsSet, 1385, 330, 1435, 380, 0, 0, 200, 200, paintingChild);						
				newSizedColoredText(text.size(), inventorY[1] + "", 1435, 330, 125, 50, 30, Color.YELLOW);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
				
				g.drawImage(aestheticsSet, 1390, 25, 1535, 125, 400, 0, 555, 180, paintingChild);
				
				newSizedColoredText(text.size(), "Day", 1390, 25, 140, 30, 25f, Color.BLACK);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
				newSizedColoredText(text.size(), "" + (day + clock[3]), 1390, 55, 140, 70, 40f, Color.BLACK);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
			
				g.drawImage(aestheticsSet, 1375, 130, 1550, 200, 557, 54, 747, 150, paintingChild);
				String oInMinutes = "";
				if(clock[1] % 60 < 10) oInMinutes = "0";
				String oInHours = "";
				if(getHour() < 10) oInHours = "0";
				newSizedColoredText(text.size(), oInHours + "" + getHour() + ":" + oInMinutes + "" + (clock[1] % 60), 1410, 147, 105, 45, 40f, Color.RED);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
				if(clock[2] > 5 && clock[2] < 18) newSizedColoredText(text.size(), "PM", 1414, 143, 20, 10, 10f, Color.RED);
				else newSizedColoredText(text.size(), "AM", 1414, 143, 20, 10, 10f, Color.RED);
				
			}
			
			if(isItANewHour) 
			{
				g.drawImage(backgroundSet, 0, 0, 1600, 900, 1600, 0, 3200, 900, paintingChild);			
				if(clock[6] / 5 % 2 == 0)
				{
					String oInMinutes = "";
					if(clock[1] % 60 < 10) oInMinutes = "0";
					String oInHours = "";
					if(clock[2] < 10) oInHours = "0";
					newSizedColoredText(text.size(), oInHours + "" + getHour() + ":" + oInMinutes + "" + (clock[1] % 60), 510, 375, 600, 200, 200f, Color.RED);
					text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
					newSizedColoredText(text.size(), "AM", 570, 325, 100, 60, 60f, Color.RED);
				}
				newSizedColoredText(text.size(), "  " + ":" + "  ", 510, 375, 600, 200, 200f, Color.RED);
				text.get(text.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
				newSizedColoredText(text.size(), "AM", 570, 325, 100, 60, 60f, Color.RED);
			}
			
			break;		
		}
	}
}




