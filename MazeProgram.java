import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//TODO: FINISH FRONT WALL FUNC (FIX SIZE)

public class MazeProgram extends JPanel implements KeyListener
{
	JFrame frame;
	String[][] maze;
	Hero hero;
	boolean Mode = true;

	public MazeProgram()
	{
		setMaze();

		frame = new JFrame("Maze Program");
		frame.add(this);
		frame.setSize(1200,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(223,158,27));
		g.fillRect(0,0,frame.getWidth(),frame.getHeight());
		Graphics2D a = (Graphics2D) g;

		g.setColor(Color.GRAY);
		g.fillOval(1000,650,50,50);//N
		g.fillOval(1000,550,50,50);//S
		g.fillOval(1050,600,50,50);//W
		g.fillOval(950,600,50,50);//E
		g.setColor(Color.RED);

		switch(hero.getDirection())
		{
			case 0:
				g.fillOval(1050,600,50,50);
				break;

			case 1:
				g.fillOval(1000,650,50,50);
				break;
			case 2:
				g.fillOval(950,600,50,50);
				break;
			case 3:
				g.fillOval(1000,550,50,50);
				break;
		}

		g.setColor(Color.BLACK);
		g.drawString("N",1022,577);
		g.drawString("S",1022,677);
		g.drawString("W",972,627);
		g.drawString("E",1072,627);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		if(hero.getCol() == 49 && hero.getRow() == 8)
			g.drawString("You have reached the end",0,20);
		g.drawString("You have gone "+hero.getSteps()+" steps.",0,40);
		if(Mode)
		{
			for(int i=0; i<maze.length; i++)
			{
				for(int j=0; j<maze[i].length; j++)
				{
					if(maze[i][j].equals("@"))
					{
						g.setColor(Color.BLACK);
						g.fillRect(j*20+50, i*20+150, 20, 20);
					}
					else if(maze[i][j].equals(" "))
					{
						g.setColor(Color.WHITE);
						g.drawRect(j*20+50, i*20+150, 20, 20);
					}
					else if(maze[i][j].equals("K"))
					{
						g.setColor(Color.BLUE);
						g.fillRect(j*20+50, i*20+150, 20, 20);
					}
					else if(maze[i][j].equals("D"))
					{
						g.setColor(Color.WHITE);
						g.fillRect(j*20+50, i*20+150, 20, 20);
					}
				}
			}
			g.setColor(Color.CYAN);
			g.fillOval(hero.getCol()*20+50,hero.getRow()*20+150,20,20);
		}
		else
		{
			ArrayList<Wall> ceiling = getCeiling();
			for(Wall w : ceiling)
			{
				a.setPaint(w.getColor());
				a.fillPolygon(w.getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(w.getPolygon());
			}

			ArrayList<Wall> floor = getFloor();
			for(Wall w : floor)
			{
				a.setPaint(w.getColor());
				a.fillPolygon(w.getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(w.getPolygon());
			}

			ArrayList<Wall> leftWalls = getLeft();
			for(Wall w : leftWalls)
			{
				a.setPaint(w.getColor());
				a.fillPolygon(w.getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(w.getPolygon());
			}

			ArrayList<Wall> rightWalls = getRight();
			for(Wall w : rightWalls)
			{
				a.setPaint(w.getColor());
				a.fillPolygon(w.getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(w.getPolygon());
			}

			ArrayList<Wall> leftSide = getLeftWalls(hero.getDirection());
			for(Wall w : leftSide)
			{
				a.setPaint(w.getColor());
				a.fillPolygon(w.getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(w.getPolygon());
			}

			ArrayList<Wall> rightSide = getRightWalls(hero.getDirection());
			for(Wall w : rightSide)
			{
				a.setPaint(w.getColor());
				a.fillPolygon(w.getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(w.getPolygon());
			}

			Wall frontWall = getFrontWall(hero.getDirection());
			a.setPaint(frontWall.getColor());
			a.fillPolygon(frontWall.getPolygon());
			g.setColor(Color.BLACK);
			g.drawPolygon(frontWall.getPolygon());
			System.out.println(frontWall);
		}

	}

	public void keyPressed(KeyEvent e)
	{

	}

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == 32 && Mode)
			Mode = false;
		else if(e.getKeyCode() == 32 && !Mode)
			Mode = true;


		hero.move(e.getKeyCode());
		repaint();
	}

	public void keyTyped(KeyEvent e)
	{

	}

	public Wall getFrontWall(int direction)
	{
		int r = hero.getRow();
		int c = hero.getCol();
		int distance = 0;

		//Facing: 0 = right 1 = down 2 = left 3 = up
		try{
			switch(direction)
			{
				case 0:
					for(int i=0; i<6; i++)
					{
						distance = i-1;
						if(maze[r][c+i].equals("@"))
						{
							int[] x = {150+50*(i-1), 800-50*(i-1), 800-50*(i-1), 150+50*(i-1)};
							int[] y = {150+50*(i-1), 150+50*(i-1), 750-50*(i-1), 750-50*(i-1)};
							Wall wall = new Wall(x,y,distance,2);
							return wall;
						}
					}
					break;

				case 1:
					for(int i=0; i<6; i++)
					{
						distance = i-1;
						if(maze[r+i][c].equals("@"))
						{
							int[] x = {150+50*(i-1), 800-50*(i-1), 800-50*(i-1), 150+50*(i-1)};
							int[] y = {150+50*(i-1), 150+50*(i-1), 750-50*(i-1), 750-50*(i-1)};
							Wall wall = new Wall(x,y,distance,2);
							return wall;
						}
					}
					break;

				case 2:
					for(int i=0; i<6; i++)
					{
						distance = i-1;
						if(maze[r][c-i].equals("@"))
						{
							int[] x = {150+50*(i-1), 800-50*(i-1), 800-50*(i-1), 150+50*(i-1)};
							int[] y = {150+50*(i-1), 150+50*(i-1), 750-50*(i-1), 750-50*(i-1)};
							distance = i-1;
							Wall wall = new Wall(x,y,distance,2);
							return wall;
						}
					}
					break;

				case 3:
					for(int i=0; i<6; i++)
					{
						distance = i-1;
						if(maze[r-i][c].equals("@"))
						{
							int[] x = {150+50*(i-1), 800-50*(i-1), 800-50*(i-1), 150+50*(i-1)};
							int[] y = {150+50*(i-1), 150+50*(i-1), 750-50*(i-1), 750-50*(i-1)};
							distance = i-1;
							Wall wall = new Wall(x,y,distance,2);
							return wall;
						}
					}
					break;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{}

		int[] x = {150+50*distance, 800-50*distance, 800-50*distance, 150+50*distance};
		int[] y = {150+50*distance, 150+50*distance, 750-50*distance, 750-50*distance};
		Wall wall = new Wall(x,y,distance,2);
		return wall;
		}

	public ArrayList<Wall> getRight()
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();

		for(int i=0; i<6; i++)
		{
			int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
			int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
			walls.add(new Wall(x,y,i,1));
		}

		return walls;
	}

	public ArrayList<Wall> getLeft()
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();

		for(int i=0; i<6; i++)
		{
			int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
			int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
			walls.add(new Wall(x,y,i,0));
		}

		return walls;
	}

	public ArrayList<Wall> getCeiling()
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();

		for(int i=0; i<6; i++)
		{
			int[] x = {100+50*i, 100+50*(i+1), 800-50*(i), 800-50*(i-1)};
			int[] y = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};

			walls.add(new Wall(x,y,i,4));
		}

		return walls;
	}

	public ArrayList<Wall> getFloor()
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();

		for(int i=0; i<6; i++)
		{
			int[] x = {100+50*i, 100+50*(i+1), 800-50*(i), 800-50*(i-1)};
			int[] y = {800-50*i, 800-50*(i+1), 800-50*(i+1), 800-50*i};

			walls.add(new Wall(x,y,i,3));
		}

		return walls;
	}

	public ArrayList<Wall> getLeftWalls(int direction)
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();
		int r = hero.getRow();
		int c = hero.getCol();

		//Facing: 0 = right 1 = down 2 = left 3 = up
		try{
			switch(direction)
			{
				case 0:
					for(int i=0; i<6; i++)
					{
						if(!(maze[r-1][c+i].equals("@")))
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,0));
						}
						else
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,0));
						}
					}
					break;

				case 1:
					for(int i=0; i<6; i++)
					{
						if(!(maze[r+i][c+1].equals("@")))
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,0));
						}
						else
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,0));
						}
					}
					break;

				case 2:
					for(int i=0; i<6; i++)
					{
						if(!maze[r+1][c-i].equals("@"))
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,0));
						}
						else
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,0));
						}
					}
					break;

				case 3:
					for(int i=0; i<6; i++)
					{
						if(!maze[r-i][c-1].equals("@"))
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,0));
						}
						else
						{
							int[] x = {100+50*i, 100+50*(i+1), 100+50*(i+1), 100+50*i};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,0));
						}
					}
					break;

			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		return walls;
	}

	public ArrayList<Wall> getRightWalls(int direction)
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();
		int r = hero.getRow();
		int c = hero.getCol();

		//Facing: 0 = right 1 = down 2 = left 3 = up
		try{
			switch(direction)
			{
				case 0:
					for(int i=0; i<6; i++)
					{
						if(!maze[r+1][c+i].equals("@"))
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,1));
						}
						else
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,1));
						}
					}
					break;

				case 1:
					for(int i=0; i<6; i++)
					{
						if(!maze[r+i][c-1].equals("@"))
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,1));
						}
						else
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,1));
						}
					}
					break;

				case 2:
					for(int i=0; i<6; i++)
					{
						if(!maze[r-1][c-i].equals("@"))
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,1));
						}
						else
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,1));
						}
					}
					break;

				case 3:
					for(int i=0; i<6; i++)
					{
						if(!maze[r-i][c+1].equals("@"))
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {150+50*i, 150+50*(i), 750-50*(i), 750-50*i};
							walls.add(new Wall(x,y,i,1));
						}
						else
						{
							int[] x = {800-50*(i-1), 800-50*(i), 800-50*(i), 800-50*(i-1)};
							int[] y = {100+50*i, 100+50*(i+1), 800-50*(i+1), 800-50*i};
							walls.add(new Wall(x,y,i,1));
						}
					}
					break;

			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		return walls;
	}

	public void setMaze()
	{
		maze = new String[10][50];

		File file = new File("MazeInput.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(file));
			String text;
			int row = 0;
			while((text = input.readLine()) != null)
			{
				maze[row] = text.split("");
				row++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		hero = new Hero(1,0,maze);
	}

	public static void main(String[] args)
	{
		MazeProgram app = new MazeProgram();
	}
}