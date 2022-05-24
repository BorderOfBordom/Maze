import java.awt.*;

public class Wall
{
	int[] x;
	int[] y;
	int howFar;
	int side;  // 0 for left, 1 for right, 2 for front, 3 for up, 4 for down

	public Wall(int[] x, int[] y, int f, int s)
	{
		this.x = x;
		this.y = y;
		howFar = f;
		side = s;
	}

	public Polygon getPolygon()
	{
		return new Polygon(x, y, 4);
	}

	public String toString()
	{
		String output = "";
		output += "The coordinates for this wall are:\n";
		for(int i=0; i<x.length; i++)
		{
			output += "\t" + (i+1) + ": ( " + x[i] + ", " + y[i] + " )\n";
		}
		return output;
	}

	public GradientPaint getColor()
	{
		GradientPaint c = new GradientPaint(0, 0, Color.BLUE, 100, 100, Color.WHITE);
		Color startColor = new Color(250 - 50*howFar, 250 - 50*howFar, 250 - 50*howFar);
		Color endColor = new Color(250 - 50*(howFar), 250 - 50*(howFar), 250 - 50*(howFar));
		try{
			endColor = new Color(200 - 50*(howFar), 200 - 50*(howFar), 200 - 50*(howFar));
		}
		catch(Exception e)
		{
			endColor = new Color(250 - 50*(howFar), 250 - 50*(howFar), 250 - 50*(howFar));
		}

		switch(side)
		{
			// 0 for left, 1 for right, 2 for front, 3 for down, 4 for up
			case 0:
				c = new GradientPaint(x[0], y[0], startColor, x[0]+50, y[0], endColor);
				break;
			case 1:
				c = new GradientPaint(x[0], y[0], startColor, x[0]-50, y[0], endColor);
				break;
			case 2:
				c = new GradientPaint(x[0], y[0], endColor, x[0], y[0], endColor);
				break;
			case 3:
				c = new GradientPaint(x[0], y[0], startColor, x[0], y[0]-50, endColor);
				break;
			case 4:
				c = new GradientPaint(x[0], y[0], startColor, x[0], y[0]+50, endColor);
				break;
		}

		return c;
	}
}