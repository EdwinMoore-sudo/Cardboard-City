package CardboardCity;

public class Map 
{
	private Grid objects;
	private int[][] ground;
	
	public Map()
	{
		objects = new Grid();
		ground = new int[objects.getGridLength()][objects.getGridWidth()];
		
		for(int i = 0; i < ground.length; i++)
		{
			for(int j = 0; j < ground[i].length; j++)
			{
				ground[i][j] = (int)(Math.random() * 2);
			}
		}
		ground[28][28] = 2;
		objects.getGridOfScenery()[28][28].destroyObject(0);
		objects.getGridOfScenery()[28][28].destroyObject(1);
		objects.getGridOfScenery()[28][28].destroyObject(2);
		objects.getGridOfBuildings()[28][28] = new Building(0, 0);
	}
	
	public int[][] getGround() { return ground; }
	public void setGround(int i, int j, int k) { ground[i][j] = k; }
	public Grid getGrid() { return objects; }
}