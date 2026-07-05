package CardboardCity;

public class Task 
{
	private int taskType;
	private int time;
	private int taskNum;
	
	/*
	 * -1: Empty			
	 * 0: Sleep			1hr
	 * 1: Eat			1hr
	 * 2: Free time		1hr
	 * 3: Building		3hr		Strength * Endurance
	 * 4: Hunting		5hr		Speed * Strength
	 * 5: Fishing		6hr		Strength * Intelligence
	 * 6: Gathering		3hr		Speed * Intelligence
	 * 7: Farming		4hr		Endurance * Intelligence
	 * 8: Missionary	9hr		Charisma * Intelligence
	 * 9: Clearing		2hr 	Endurance * Speed
	 * 
	 */

	public Task()
	{
		taskType = -1;
		time = -1;
		taskNum = -1;
	}
	public Task(int tT, int tN)
	{
		taskType = tT;
		
		switch(tT)
		{
		case 0: time = 1; break;
		case 1: time = 1; break;
		case 2: time = 1; break;
		case 3: time = 3; break;
		case 4: time = 5; break;
		case 5: time = 6; break;
		case 6: time = 3; break;
		case 7: time = 4; break;
		case 8: time = 9; break;
		case 9: time = 2; break;
		}
		
		taskNum = tN;
	}
	
	public int getTaskType() { return taskType; }
	public int getAmountOfTime() { return time; }
	public int getTaskID() { return taskNum; }
	public void setTaskType(int tT) { taskType = tT; }
	public void setAmountOfTime(int t) { time = t; }
	public void setTaskID(int tN) { taskNum = tN; }
}




