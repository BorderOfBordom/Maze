public class Hero
{
	int r,c;
	String[][] maze;
	int direction;
	boolean hasKey = false;
	int steps;

	public Hero(int r, int c, String[][] maze)
	{
		this.r = r;
		this.c = c;
		this.maze = maze;
		direction = 0;
		steps = 0;
	}

	public int getDirection()
	{
		return direction%4;
	}

	public int getSteps()
	{
		return steps;
	}

	public void move(int key)
	{
		int oldX = c;
		int oldY = r;
		direction = direction%4;
		if(direction == 0)
		{
			if(key == 37)
			{
				//if(!(maze[r][r-1].equals("@")))
				//	r--;
				direction += 3;
			}
			if(key == 38)
			{
				if(!(maze[r][c+1].equals("@")) && !(maze[r][c+1].equals("D")))
					c++;
				else if(maze[r][c+1].equals("D") && hasKey)
					c++;
			}
			if(key == 39)
			{
				//if(!(maze[r+1][c].equals("@")))
				//	r++;
				direction++;
			}
		}
		else if(direction == 1)
		{
			if(key == 37)
			{
				//if(!(maze[r][c+1].equals("@")))
				//	c++;
				direction += 3;
			}
			if(key == 38)
			{
				if(!(maze[r+1][c].equals("@")) && !(maze[r+1][c].equals("D")))
					r++;
				else if(maze[r+1][c].equals("D") && hasKey)
					r++;
			}
			if(key == 39)
			{
				//if(!(maze[r][c-1].equals("@")))
				//	c--;
				direction++;
			}
		}
		else if(direction == 2)
		{
			if(key == 37)
			{
				//if(!(maze[r][r+1].equals("@")))
				//	r++;
				direction += 3;
			}
			if(key == 38)
			{
				if(!(maze[r][c-1].equals("@")) && !(maze[r][c-1].equals("D")))
					c--;
				else if(maze[r][c-1].equals("D") && hasKey)
					c--;
			}
			if(key == 39)
			{
				//if(!(maze[r-1][c].equals("@")))
				//	r--;
				direction++;
			}
		}
		else if(direction == 3)
		{
			if(key == 37)
			{
				//if(!(maze[r][c-1].equals("@")))
				//	c--;
				direction += 3;
			}
			if(key == 38)
			{
				if(!(maze[r-1][c].equals("@")) && !(maze[r-1][c].equals("D")))
					r--;
				else if(hasKey && maze[r-1][c].equals("D"))
					r--;
			}
			if(key == 39)
			{
				//if(!(maze[r][c+1].equals("@")))
				//	c++;
				direction++;
			}
		}
		if(maze[r][c].equals("K"))
			hasKey = true;

		if(oldX != c || oldY != r)
			steps++;
	}

	public int getRow()
	{
		return r;
	}

	public int getCol()
	{
		return c;
	}
}