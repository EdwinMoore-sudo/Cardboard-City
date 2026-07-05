package CardboardCity;

public class ScrollBar 
{
	private int topLeft;
	private int userClick;
	private int deltaY;
	private int isActive;
	
	public ScrollBar(int uC)
	{
		userClick = uC;
	}

	public int getTopLeft() { return topLeft; }
	public int getUserClick() { return userClick; }
	public int getDeltaY() { return deltaY; }
	public int isActive() { return isActive; }
	public int getTopOfBar() { return userClick + deltaY - topLeft; }
	public int getBottomOfBar() { return userClick + deltaY - topLeft + 25; }
	
	public void setTopLeft(int tL) { topLeft = tL; }
	public void setUserClick(int uC) { userClick = uC; }
	public void setDeltaY(int dY) { deltaY = dY; }
	public void setIsActive(int iA) { isActive = iA; }
	
}


