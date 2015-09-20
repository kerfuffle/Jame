package net.kerfuffle.Jame;

import java.util.ArrayList;

import static net.kerfuffle.Jame.Util.*;

public class Game {

	private Point origin;
	private Quad bounds;
	
	private String map = 
			"********************\n" +
			"*                  *\n" +
			"*                  *\n" +
			"***                *\n" +
			"*                  *\n" +
			"*                  *\n" +
			"*   **             *\n" +
			"*    *             *\n" +
			"*  * *             *\n" +
			"*  * *             *\n" +
			"*    ***************\n" +
			"*                  *\n" +
			"*                  *\n" +
			"********************";
	
	private ArrayList <Player> players = new ArrayList <Player>();
	private ArrayList <Quad> quads = makeMap(map);//new ArrayList <Quad>();
	
	private ArrayList <PlayerMP> mpPlayers = new ArrayList <PlayerMP>();
	
	public Game(Point origin, Quad bounds)
	{
		this.origin = origin;
		
		bounds.setCollide(true);
		this.bounds = bounds;
	}
	
	public void checkLegalPos()
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).outDown(bounds))
			{
				players.get(i).setY(players.get(i).y() + players.get(i).getSpeed());
			}
			if (players.get(i).outUp(bounds))
			{
				players.get(i).setY(players.get(i).y() - players.get(i).getSpeed());
			}
			if (players.get(i).outLeft(bounds))
			{
				players.get(i).setX(players.get(i).x() + players.get(i).getSpeed());
			}
			if (players.get(i).outRight(bounds))
			{
				players.get(i).setX(players.get(i).x() - players.get(i).getSpeed());
			}
			
			for (int j = 0; j < quads.size(); j++)
			{
				if (!quads.get(j).canCollide())
				{
					if (players.get(i).hitLeft(quads.get(j)))
					{
						players.get(i).setX(players.get(i).x() + players.get(i).getSpeed());
					}
					if (players.get(i).hitRight(quads.get(j)))
					{
						players.get(i).setX(players.get(i).x() - players.get(i).getSpeed());
					}
					if (players.get(i).hitUp(quads.get(j)))
					{
						players.get(i).setY(players.get(i).y() + players.get(i).getSpeed());
					}
					if (players.get(i).hitDown(quads.get(j)))
					{
						players.get(i).setY(players.get(i).y() - players.get(i).getSpeed());
					}
				}
				
			}
		}
	}
	
	
	public void setBounds(float x, float y, float w, float h)
	{
		bounds.setPos(x, y, w, h);
	}
	
	public void checkPlayerMovement()
	{
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).checkMovement();
		}
	}
	
	public void drawMPPlayers()
	{
		for (int i = 0; i < mpPlayers.size(); i++)
		{
			mpPlayers.get(i).draw();
		}
	}
	
	public void drawPlayers()
	{
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).draw();
		}
	}
	
	public void drawQuads()
	{
		for (int i = 0; i < quads.size(); i++)
		{
			quads.get(i).draw();
		}
	}
	
	public void addPlayer(Player p)
	{
		players.add(p);
	}
	
	public void addQuad(Quad q)
	{
		quads.add(q);
	}
}
