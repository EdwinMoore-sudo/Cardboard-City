package CardboardCity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class CardboardCity 
{
		public static JFrame f = new JFrame("CardboardCity.exe");
	
	//public static GUI gui = new GUI();
	
	public static JLabel lbl = new JLabel();

	public static Game game1;
	
	public static boolean isTileMenuOpen = false;
	public static int isASceneryRemovalMenuOpen = -1;
	public static boolean isCharacterMenuOpen = false;
	public static int isSpecificCharacterMenuOpen = -1;
	public static int isACharacterAttributeMenuOpen = -1;
	public static ScrollBar scrollBar_CAM1 = new ScrollBar(375);
	public static int[] scrollMenu_CAM1 = new int[9];
	public static ScrollBar scrollBar_CAM2 = new ScrollBar(375);
	public static int[] menu_TMC = new int[9];
	
	public static int[] clock = new int[7];
	public static boolean isItANewHour = false;
	
	public static Timer t = new Timer(50, new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			clock[0]++;
			clock[5] = clock[2];
			clock[1] = clock[0] * 16;
			
			clock[2] = clock[1] / 60;
			if(clock[2] > clock[5]) game1.checkTasksDone(clock);
			if(clock[2] >= 24) { tNewHour.start(); isItANewHour = true; clock[2] = 0; game1.updateClock(clock, isItANewHour); t.stop();  }
			
			game1.updateClock(clock, isItANewHour);
			game1.repaint();
			game1.revalidate();
		}
	});
	public static Timer tNewHour = new Timer(50, new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			clock[6]++;
			if(clock[6] >= 50)
			{
				isItANewHour = false;
				clock[6] = 0;
				clock[5] = 0;
				clock[2] = 0;
				clock[1] = 0;
				clock[0] = 0;
				clock[3]++;
				game1.updateClock(clock, isItANewHour);
				game1.repaint();
				game1.revalidate();
				tNewHour.stop();
			}
			game1.updateClock(clock, isItANewHour);
			game1.repaint();
			game1.revalidate();

		}
	});
	
	public static void main(String[] args) 
	{
		//System.gc();
		game1 = new Game();
		// MOVING
		// Move UP
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "moveUP");
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "moveUP");
		game1.getActionMap().put("moveUP", new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) { if(!isCharacterMenuOpen) game1.changeCurrentTileY(-1); }			
		});
		
		// Move DOWN
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "moveDown");
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "moveDown");
		game1.getActionMap().put("moveDown", new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) { if(!isCharacterMenuOpen) game1.changeCurrentTileY(1); }			
		});
		// Move LEFT
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "moveLeft");
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "moveLeft");
		game1.getActionMap().put("moveLeft", new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) { if(!isCharacterMenuOpen) game1.changeCurrentTileX(-1); }			
		});
		// Move RIGHT
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "moveRight");
		game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "moveRight");
		game1.getActionMap().put("moveRight", new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) { if(!isCharacterMenuOpen) game1.changeCurrentTileX(1); }			
		});

		// Open Tile Menu
		if(isTileMenuOpen == false)
		{
			game1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "openTileMenu");
			game1.getActionMap().put("openTileMenu", new AbstractAction() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{ 
					if(!isTileMenuOpen && !isCharacterMenuOpen) { isTileMenuOpen = true; game1.displayTileMenu(); }
					else { isTileMenuOpen = false; game1.closeTileMenu(); }
				}			
			});			
		}

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(1600, 900));
		f.add(game1, BorderLayout.CENTER);

		f.add(lbl, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);
		f.setResizable(false);
		
		scrollMenu_CAM1[2] = 430;
		scrollMenu_CAM1[3] = 225;
		menu_TMC[2] = 45;
		menu_TMC[3] = 150;
	}
	
	public static class Input implements MouseListener, MouseMotionListener {
	
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			// This changes the click-able range for Opening and closing the Tile Menu
			int xOffset;
			if(isTileMenuOpen) xOffset = 175;
			else xOffset = 0;
			
			if(!isTileMenuOpen && !isCharacterMenuOpen)
			{
				if(checkBounds(e.getX(), e.getY(), 675 - xOffset, 825 - xOffset, 325, 525)) 
				{ 
					if(!isTileMenuOpen) { t.stop(); isTileMenuOpen = true; game1.displayTileMenu(); }
				}

				if(checkBounds(e.getX(), e.getY(), 1375, 1550, 215, 265)) 
				{
					if(!isCharacterMenuOpen) { t.stop(); isCharacterMenuOpen = true; game1.displayCharacterMenu(); }
					//else { isCharacterMenuOpen = false; game1.closeTileMenu(); }
				}
				
				if(checkBounds(e.getX(), e.getY(), 1375, 1550, 270, 320)) 
				{
					if(!isTileMenuOpen && !isCharacterMenuOpen) { isTileMenuOpen = true; game1.displayTileMenu(); }
					else { isTileMenuOpen = false; game1.closeTileMenu(); }
				}
				
				if(!t.isRunning())
				{
					if(checkBounds(e.getX(), e.getY(), 700, 900, 700, 800))
					{
						t.start();
					}
				}
			}
			else if(isTileMenuOpen && !isCharacterMenuOpen)
			{
				
				if(isASceneryRemovalMenuOpen == 0)
				{
					if(checkBounds(e.getX(), e.getY(), 830, 880, 30, 80))
					{
						isASceneryRemovalMenuOpen = -1;
						game1.closeSceneryRemovalMenu();
					}
					if(checkBounds(e.getX(), e.getY(), 895, 1270, 150, 750)) 
					{
						int buildingClick = (e.getY() - 150) / 100;
						game1.setCurrentBuild(buildingClick);
						isASceneryRemovalMenuOpen = -1;
						game1.closeSceneryRemovalMenu();
					}
					//975, 490
					
				}
				else if(checkBounds(e.getX(), e.getY(), 895, 1270, 150, 250) && !game1.isBuildSet())
				{
					isASceneryRemovalMenuOpen = 0;
					game1.displaySceneryRemovalMenu(0);		
				}
				else if(isASceneryRemovalMenuOpen == -1)
				{
					
					if(checkBounds(e.getX(), e.getY(), 975, 1190, 490, 595))
					{
						game1.setBuild();
						System.out.println("Did this!!1");
					}
					
					if(checkBounds(e.getX(), e.getY(), 45, 425, 150, 810) && clock[2] == 0) 				
					{
						// 0 Top leftX, 1 Top left Y, 2 User clickX, 3 User clickY, 4 deltaX, 
						// 5 deltaY, 6 XClicked Task Type, 7 YClicked Task Type, 8 Is Dragging
						menu_TMC[6] = (e.getX() - 45) / 140;
						menu_TMC[7] = (e.getY() - 150) / 140;
						menu_TMC[0] = e.getX() - ((menu_TMC[2] + menu_TMC[4] - menu_TMC[0]) + (menu_TMC[6] * 140));
						menu_TMC[1] = e.getY() - ((menu_TMC[3] + menu_TMC[5] - menu_TMC[1]) + (menu_TMC[7] * 140));
						menu_TMC[2] = e.getX();
						menu_TMC[3] = e.getY();
						menu_TMC[4] = 0;
						menu_TMC[5] = 0;
						menu_TMC[8] = 1;
						
						game1.dragTaskForTMC(menu_TMC);
					}
					
					if(clock[2] == 0)
					{
						if(checkBounds(e.getX(), e.getY(), 895, 995, 255, 355)) { game1.removeActiveClearer(0); }
						else if(checkBounds(e.getX(), e.getY(), 1035, 1135, 255, 355)) { game1.removeActiveClearer(1); }
						else if(checkBounds(e.getX(), e.getY(), 1175, 1275, 255, 355)) { game1.removeActiveClearer(2); }
						else if(checkBounds(e.getX(), e.getY(), 895, 995, 475, 575)) { game1.removeActiveClearer(3); }
						else if(checkBounds(e.getX(), e.getY(), 1035, 1135, 475, 575)) { game1.removeActiveClearer(4); }
						else if(checkBounds(e.getX(), e.getY(), 1175, 1275, 475, 575)) { game1.removeActiveClearer(5); }
						else if(checkBounds(e.getX(), e.getY(), 895, 995, 695, 795)) { game1.removeActiveClearer(6); }
						else if(checkBounds(e.getX(), e.getY(), 1035, 1135, 695, 795)) { game1.removeActiveClearer(7); }
						else if(checkBounds(e.getX(), e.getY(), 1175, 1275, 695, 795)) { game1.removeActiveClearer(8); }
					}
					
					if(checkBounds(e.getX(), e.getY(), 565, 765, 325, 525)) 
					{  
						isTileMenuOpen = false; game1.closeTileMenu(); if(!(clock[1] <= 0)) t.start();
					}
				}
				
				if(checkBounds(e.getX(), e.getY(), 1375, 1550, 270, 320)) 
				{
					if(!isTileMenuOpen && !isCharacterMenuOpen) { isTileMenuOpen = true; game1.displayTileMenu(); }
					else { isTileMenuOpen = false; game1.closeTileMenu(); }
				}
				
			}
			else if(isCharacterMenuOpen && !isTileMenuOpen)
			{
				if(isSpecificCharacterMenuOpen == -1)
				{
					int xMin = (e.getX() - 450) / 125;
					int yMin = (e.getY() - 250) / 125;
					if((xMin >= 0 && xMin <= 5) && (yMin >= 0 && yMin <= 2))
					{
						if(game1.getAmountOfCitizens() > (xMin + (yMin * 6)))
						{
							isSpecificCharacterMenuOpen = xMin + (yMin * 6);
							game1.displaySpecificCharacterMenu(isSpecificCharacterMenuOpen);
						}
					}
				}
				else
				{
					if(isACharacterAttributeMenuOpen == -1)
					{
						if(checkBounds(e.getX(), e.getY(), 800, 1175, 325, 383))
						{
							isACharacterAttributeMenuOpen = 0;
							game1.openACharacterAttributeMenu(isACharacterAttributeMenuOpen);
						}
						if(checkBounds(e.getX(), e.getY(), 800, 1175, 493, 551))
						{
							isACharacterAttributeMenuOpen = 2;
							game1.openACharacterAttributeMenu(isACharacterAttributeMenuOpen);						
						}
						if(checkBounds(e.getX(), e.getY(), 350, 400, 140, 190))
						{
							isSpecificCharacterMenuOpen = -1;
							game1.closeSpecificCharacterMenu();
						}
					}
					else 
					{
						if(isACharacterAttributeMenuOpen == 2)
						{
							if((e.getX() > 1140 && e.getX() < 1160) && 
									(e.getY() > scrollBar_CAM2.getTopOfBar() && 
											e.getY() < 25 + scrollBar_CAM2.getBottomOfBar())) 				
							{
								// Set Top Left Corner of Scroll Bar
								scrollBar_CAM2.setTopLeft(e.getY() - (scrollBar_CAM2.getTopOfBar()));
								// Set Where User Clicked
								scrollBar_CAM2.setUserClick(e.getY());
								// Set Change in Y
								scrollBar_CAM2.setDeltaY(0);
								// Make the Scroll Bar Being Active True
								scrollBar_CAM2.setIsActive(1);
								
								game1.scrollForCAM2(scrollBar_CAM2);
							}
							if((e.getX() > 765 && e.getX() < 785) && 
									(e.getY() > scrollBar_CAM1.getTopOfBar() && 
											e.getY() < 25 + scrollBar_CAM1.getBottomOfBar())) 				
							{
								// Set Top Left Corner of Scroll Bar
								scrollBar_CAM1.setTopLeft(e.getY() - (scrollBar_CAM1.getTopOfBar()));
								// Set Where User Clicked
								scrollBar_CAM1.setUserClick(e.getY());
								// Set Change in Y
								scrollBar_CAM1.setDeltaY(0);
								// Make the Scroll Bar Being Active True
								scrollBar_CAM1.setIsActive(1);
								
								game1.scrollForCAM1(scrollBar_CAM1);
							}
							if(checkBounds(e.getX(), e.getY(), 430, 755, 225, 645)) 				
							{
								// 0 Top leftX, 1 Top left Y, 2 User clickX, 3 User clickY, 4 deltaX, 
								// 5 deltaY, 6 First Task Type, 7 Clicked Task Type, 8 Is Dragging
								scrollMenu_CAM1[7] = (e.getY() - 225) / 105;
								scrollMenu_CAM1[0] = e.getX() - (scrollMenu_CAM1[2] + scrollMenu_CAM1[4] - scrollMenu_CAM1[0]);
								scrollMenu_CAM1[1] = e.getY() - ((scrollMenu_CAM1[3] + scrollMenu_CAM1[5] - scrollMenu_CAM1[1]) + (scrollMenu_CAM1[7] * 105));
								scrollMenu_CAM1[2] = e.getX();
								scrollMenu_CAM1[3] = e.getY();
								scrollMenu_CAM1[4] = 0;
								scrollMenu_CAM1[5] = 0;
								scrollMenu_CAM1[8] = 1;
								
								game1.dragTaskForCAM(scrollMenu_CAM1);
							}
						}
						
						int yMin = (e.getY() - 240) / 105;
						if((e.getX() > 1095 && e.getX() < 1115) && (yMin >= 0 && yMin <= 3))
						{
							int hourDisplayedFirst = (scrollBar_CAM2.getTopOfBar() - 375) / 6;
							int hourDraggedTo = (e.getY() - 225) / 100;
							game1.removeTask(hourDisplayedFirst + hourDraggedTo);
						}
						
						if(checkBounds(e.getX(), e.getY(), 350, 400, 140, 190))
						{
							isACharacterAttributeMenuOpen = -1;
							game1.closeACharacterAttributeMenu();
						}
						
						
					}	
				}
				
				if(checkBounds(e.getX(), e.getY(), 1200, 1250, 140, 190)) 				
				{					
					isCharacterMenuOpen = false; game1.closeCharacterMenu(); if(!(clock[1] <= 0)) t.start();
				}
			}
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(scrollBar_CAM2.isActive() == 1) 
			{
				scrollBar_CAM2.setIsActive(0);
				
			}
			if(scrollBar_CAM1.isActive() == 1) 
			{
				scrollBar_CAM1.setIsActive(0);
				
			}
			if(scrollMenu_CAM1[8] == 1) 
			{
				if(checkBounds(e.getX(), e.getY(), 805, 1130, 225, 650))
				{
					int hourDisplayedFirst = (scrollBar_CAM2.getTopOfBar() - 375) / 6;
					int hourDraggedTo = (e.getY() - 225) / 100;
					game1.setTask(hourDisplayedFirst + hourDraggedTo, scrollMenu_CAM1[6] + scrollMenu_CAM1[7]);
				}
				scrollMenu_CAM1[0] = 0;
				scrollMenu_CAM1[1] = 0;
				scrollMenu_CAM1[2] = 430;
				scrollMenu_CAM1[3] = 225;
				scrollMenu_CAM1[4] = 0;
				scrollMenu_CAM1[5] = 0;
				scrollMenu_CAM1[7] = 0;
				scrollMenu_CAM1[8] = 0;
				game1.dragTaskForCAM(scrollMenu_CAM1);
			}
			if(menu_TMC[8] == 1) 
			{
				if(checkBounds(e.getX(), e.getY(), 895, 1275, 255, 755))
				{
//					int hourDisplayedFirst = (scrollBar_CAM2.getTopOfBar() - 375) / 6;
					int slotDraggedToX = (e.getX() - 895) / 140;
					int slotDraggedToY = (e.getY() - 255) / 220;
					game1.setActiveClearer(slotDraggedToX, slotDraggedToY, menu_TMC[6] + (menu_TMC[7] * 3));
				}
				menu_TMC[0] = 0;
				menu_TMC[1] = 0;
				menu_TMC[2] = 45;
				menu_TMC[3] = 150;
				menu_TMC[4] = 0;
				menu_TMC[5] = 0;
				menu_TMC[6] = 0;
				menu_TMC[7] = 0;
				menu_TMC[8] = 0;
				game1.dragTaskForTMC(menu_TMC);
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(isACharacterAttributeMenuOpen == 2)
			{
				if(scrollBar_CAM2.isActive() == 1) 				
				{
					
					scrollBar_CAM2.setIsActive(1);	
					scrollBar_CAM2.setDeltaY(e.getY() - scrollBar_CAM2.getUserClick());
					if(scrollBar_CAM2.getBottomOfBar() > 525)
					{
						scrollBar_CAM2.setDeltaY(525 - scrollBar_CAM2.getUserClick() + scrollBar_CAM2.getTopLeft() - 25);
						game1.scrollForCAM2(scrollBar_CAM2);
					}
					else if(scrollBar_CAM2.getTopOfBar() < 375)
					{
						scrollBar_CAM2.setDeltaY(375 - scrollBar_CAM2.getUserClick() + scrollBar_CAM2.getTopLeft());
						game1.scrollForCAM2(scrollBar_CAM2);
					}
					else game1.scrollForCAM2(scrollBar_CAM2);
				}
				if(scrollBar_CAM1.isActive() == 1) 				
				{
					
					scrollBar_CAM1.setIsActive(1);	
					scrollBar_CAM1.setDeltaY(e.getY() - scrollBar_CAM1.getUserClick());
					if(scrollBar_CAM1.getBottomOfBar() > 525)
					{
						scrollBar_CAM1.setDeltaY(525 - scrollBar_CAM1.getUserClick() + scrollBar_CAM1.getTopLeft() - 25);
					}
					else if(scrollBar_CAM1.getTopOfBar() < 375)
					{
						scrollBar_CAM1.setDeltaY(375 - scrollBar_CAM1.getUserClick() + scrollBar_CAM1.getTopLeft());
					}
					scrollMenu_CAM1[6] = (scrollBar_CAM1.getTopOfBar() - 375) / 18;
					game1.scrollForCAM1(scrollBar_CAM1);
				}
				if(scrollMenu_CAM1[8] == 1) 				
				{
					
					scrollMenu_CAM1[8] = 1;	
					scrollMenu_CAM1[4] = e.getX() - scrollMenu_CAM1[2];
					scrollMenu_CAM1[5] = e.getY() - scrollMenu_CAM1[3];
					game1.dragTaskForCAM(scrollMenu_CAM1);
				}
			}
			if(isTileMenuOpen)
			{
				if(menu_TMC[8] == 1) 				
				{
					
					menu_TMC[8] = 1;	
					menu_TMC[4] = e.getX() - menu_TMC[2];
					menu_TMC[5] = e.getY() - menu_TMC[3];
					game1.dragTaskForTMC(menu_TMC);
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			lbl.setText(e.getX() + " " + e.getY());
			if(isTileMenuOpen)
			{
				if(checkBounds(e.getX(), e.getY(), 895, 995, 255, 355)) { game1.displayXForActiveClearer(895, 255, 0); }
				else if(checkBounds(e.getX(), e.getY(), 1035, 1135, 255, 355)) { game1.displayXForActiveClearer(1035, 255, 1); }
				else if(checkBounds(e.getX(), e.getY(), 1175, 1275, 255, 355)) { game1.displayXForActiveClearer(1175, 255, 2); }
				else if(checkBounds(e.getX(), e.getY(), 895, 995, 475, 575)) { game1.displayXForActiveClearer(895, 475, 3); }
				else if(checkBounds(e.getX(), e.getY(), 1035, 1135, 475, 575)) { game1.displayXForActiveClearer(1035, 475, 4); }
				else if(checkBounds(e.getX(), e.getY(), 1175, 1275, 475, 575)) { game1.displayXForActiveClearer(1175, 475, 5); }
				else if(checkBounds(e.getX(), e.getY(), 895, 995, 695, 795)) { game1.displayXForActiveClearer(895, 695, 6); }
				else if(checkBounds(e.getX(), e.getY(), 1035, 1135, 695, 795)) { game1.displayXForActiveClearer(1035, 695, 7); }
				else if(checkBounds(e.getX(), e.getY(), 1175, 1275, 695, 795)) { game1.displayXForActiveClearer(1175, 695, 8); }
				else { game1.displayXForActiveClearer(0, 0, -1); }
			}
		}
	}
	
	public static boolean checkBounds(int x, int y, int xMin, int xMax, int yMin, int yMax)
	{
		if((x > xMin && x < xMax) && (y > yMin && y < yMax)) return true;
		else return false;
	}
}













