package CardboardCity;

public class Grid 
{
	private int gridLength;
	private int gridWidth;
	private Scenery[][] grid;
	private Building[][] buildings = new Building[55][55];
	
	public Grid()
	{
		gridLength = 55;
		gridWidth = 55;
		grid = new Scenery[55][55];
		
		for(int i = 0; i < 55; i++)
		{
			for(int j = 0; j < 55; j++)
			{
				grid[i][j] = new Scenery();
				buildings[i][j] = new Building();
			}
		}
	}
	
	public int getGridLength() { return gridLength; }
	public int getGridWidth() { return gridWidth; }
	public Scenery[][] getGridOfScenery() { return grid; }
	public Building[][] getGridOfBuildings() { return buildings; }
}