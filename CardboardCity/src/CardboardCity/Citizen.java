package CardboardCity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Citizen 
{
	private int[] stats;
	/*
	 * Strength: stat[0]
	 * Speed: stat[1]
	 * Endurance: stat[2]
	 * Luck: return[3]
	 * Intelligence: stat[4]
	 * Charisma: stat[5]
	 * Wisdom: stat[6]
	 * Comfort: stat[7]
	 * Hunger: stat[8]
	 */
	
	private Task[] schedule;
	
	private BufferedImage personSet;
	private BufferedImage appearance;
	private boolean gender;
	private int race;
	private String name;
	private int id;
	private boolean assigned;
	private boolean assignedResident;
	private boolean assignedBuilder;
	private int[] assignedLocation = {-1, -1, -1, -1};
	private int[] assignedResidentLocation = {-1, -1, -1, -1};
	private int[] assignedBuilderLocation = {-1, -1, -1, -1};
	
	public Citizen()
	{
		//Set Race & Gender
		race = (int)(Math.random() * 6);
//		if((int)(Math.random() * 2) == 0) gender = true;
//		else gender = false;
		if((int)(Math.random() * 2) == 0) gender = true;
		else gender = false;
		
		//Set Stats
		stats = new int[9];
		for(int i = 0; i < stats.length; i++) stats[i] = 0;
		
		name = "Hanse";
		
		assigned = false;
		
		int pickStat = (int)(Math.random() * 6);
		stats[pickStat]++;
		
		stats[6] = 5;
		stats[7] = 5;
		stats[8] = 20;
		
		//Set Appearance
		try {
			personSet = ImageIO.read(new File("PersonSet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switch(race)
		{
		case 0:
			if(gender == true) appearance = personSet.getSubimage(0, 0, 200, 200);
			else appearance = personSet.getSubimage(200, 0, 200, 200);
			stats[race] += 2;
			break;
		case 1:
			if(gender == true) appearance = personSet.getSubimage(400, 0, 200, 200);
			else appearance = personSet.getSubimage(600, 0, 200, 200);
			stats[race] += 2;
			break;
		case 2:
			if(gender == true) appearance = personSet.getSubimage(800, 0, 200, 200);
			else appearance = personSet.getSubimage(1000, 0, 200, 200);
			stats[race] += 2;
			break;
		case 3:
			if(gender == true) appearance = personSet.getSubimage(1200, 0, 200, 200);
			else appearance = personSet.getSubimage(1400, 0, 200, 200);
			stats[race] += 2;
			break;
		case 4:
			if(gender == true) appearance = personSet.getSubimage(1600, 0, 200, 200);
			else appearance = personSet.getSubimage(1800, 0, 200, 200);
			stats[race] += 2;
			break;
		case 5:
			if(gender == true) appearance = personSet.getSubimage(2000, 0, 200, 200);
			else appearance = personSet.getSubimage(2200, 0, 200, 200);
			stats[race] += 2;
			break;
			
		}
		
		schedule = new Task[24];
		for(int i = 0; i < schedule.length; i++)
		{
			schedule[i] = new Task();
		}
	}
	
	public int getStrength() { return stats[0]; }
	public int getSpeed() { return stats[1]; }
	public int getEndurance() { return stats[2]; }
	public int getLuck() { return stats[3]; }
	public int getIntelligence() { return stats[4]; }
	public int getCharisma() { return stats[5]; }
	public int getAge() { return stats[6]; }
	public int getComfort() { return stats[7]; }
	public int getHunger() { return stats[8]; }
	public void setComfort(int i) { if(i >= 20) { stats[7] = 20; } else if(i <= 0) { stats[7] = 0; } else { stats[7] = i; } }
	public void setHunger(int i) { if(i >= 20) { stats[8] = 20; } else if(i <= 0) { stats[8] = 0; } else { stats[8] = i;  } }
	public BufferedImage getAppearance() { return appearance; }
	public String getName() { return name; }
	
	public void upgradeStrength() { stats[0]++; }
	public void upgradeSpeed() { stats[1]++; }
	public void upgradeEndurance() { stats[2]++; }
	public void upgradeLuck() { stats[3]++; }
	public void upgradeIntelligence() { stats[4]++; }
	public void upgradeCharisma() { stats[5]++; }
	
	public void setTask(int initialHour, int taskType)
	{
		int i = -1;
		System.out.println(initialHour);
		System.out.println(taskType);
		// Make sure this is a unique task being put in
		boolean taskOfSameID = true;
		int maxTaskID = -1;
			for(int j = 0; j < schedule.length; j++)
			{
				if(schedule[j].getTaskType() == taskType)
				{
					if(schedule[j].getTaskID() > maxTaskID) maxTaskID = schedule[j].getTaskID();
				}
			}
		do 
		{
			i++;
			if(i > maxTaskID)
			{
				taskOfSameID = false;
			}
		} while(taskOfSameID);
		
		// Make sure the task is allowed to be placed here
		Task currentTask = new Task(taskType, i);
		boolean setTask = true;
		for(int j = 0; j < currentTask.getAmountOfTime(); j++)
		{
			if(!(initialHour + j < schedule.length))
			{
				setTask = false;
			}
			else
			{
				if(!(schedule[initialHour + j].getTaskType() == -1))
				{
					setTask = false;
				}
			}
		}
		
		// If yes, place the task here
		if(setTask)
		{
			for(int j = 0; j < currentTask.getAmountOfTime(); j++)
			{
				schedule[initialHour + j].setAmountOfTime(currentTask.getAmountOfTime());
				schedule[initialHour + j].setTaskType(currentTask.getTaskType());
				schedule[initialHour + j].setTaskID(currentTask.getTaskID());
			}
		}
		System.out.println("\n");
		for(int r = 0; i < schedule.length; i++) 
		{
			System.out.println(schedule[i].getTaskType() + " " + schedule[i].getTaskID() + " " + schedule[i].getAmountOfTime());
		}
	}
	
	public void removeTask(int intialHour)
	{
		for(int i = 0; intialHour - i > 0; i++)
		{
			if(intialHour - i - 1 >= 0)
			{
				if(schedule[intialHour].getTaskType() == schedule[intialHour-i-1].getTaskType())
				{
					if(schedule[intialHour].getTaskID() == schedule[intialHour-i-1].getTaskID())
					{
						schedule[intialHour-i-1].setAmountOfTime(-1);
						schedule[intialHour-i-1].setTaskType(-1);
						schedule[intialHour-i-1].setTaskID(-1);
					}
				}
			}
		}
		for(int i = 0; intialHour + i < schedule.length; i++)
		{
			if(intialHour + i + 1 < schedule.length)
			{
				if(schedule[intialHour].getTaskType() == schedule[intialHour+i+1].getTaskType())
				{
					if(schedule[intialHour].getTaskID() == schedule[intialHour+i+1].getTaskID())
					{
						schedule[intialHour+i+1].setAmountOfTime(-1);
						schedule[intialHour+i+1].setTaskType(-1);
						schedule[intialHour+i+1].setTaskID(-1);
					}
				}
			}
		}
		
		schedule[intialHour].setAmountOfTime(-1);
		schedule[intialHour].setTaskType(-1);
		schedule[intialHour].setTaskID(-1);
	
	}
	
	public Task getTask(int i) { return schedule[i]; }
	public Task[] getSchedule() { return schedule; }
	
	public int getID() { return id; }
	public void setID(int id) { this.id = id; }
	public boolean isAssigned() { return assigned; }
	public void setAssigned(boolean as) { assigned = as; }
	public int[] getAssignedLocation()
	{
		return assignedLocation;
	}
	public void setAssignedLocation(int i, int j, int k, int l)
	{
		assignedLocation[0] = i;
		assignedLocation[1] = j;
		assignedLocation[2] = k;
		assignedLocation[3] = l;
		
	}

	public boolean isAssignedResident() { return assignedResident; }
	public void setAssignedResident(boolean as) { assignedResident = as; }
	public int[] getAssignedResidentLocation()
	{
		return assignedResidentLocation;
	}
	public void setAssignedResidentLocation(int i, int j, int k)
	{
		assignedResidentLocation[0] = i;
		assignedResidentLocation[1] = j;
		assignedResidentLocation[2] = k;
	}
	
	public boolean isAssignedBuilder() { return assignedBuilder; }
	public void setAssignedBuilder(boolean as) { assignedBuilder = as; }
	public int[] getAssignedBuilderLocation()
	{
		return assignedBuilderLocation;
	}
	public void setAssignedBuilderLocation(int i, int j, int k)
	{
		assignedBuilderLocation[0] = i;
		assignedBuilderLocation[1] = j;
		assignedBuilderLocation[2] = k;
	}
}







