package net.kerfuffle.Jame;

import static net.kerfuffle.Jame.Util.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.opengl.Display;

public class Main {

	static final int width = 1000, height = 700;
	
	
	
	public static void main(String args[])
	{
		String map = 
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
		
		
		
		setDisplay(width, height, "Jame");
		
		
		
		Game game = new Game(new Point(0, 0), new Quad(0, 0, width, height, new RGB(0, 0, 0, 0)));
		
		Player p1 = new Player(width/2, height/2, 50, 50, new RGB(1, 0, 0, 1));
		p1.setKeys(KEY_W, KEY_S, KEY_A, KEY_D);
		p1.setSpeed(5);
		
		Quad block = new Quad(100, 400, 100, 100, new RGB(0, 0, 1, 1));
		block.setCollide(false);
		
		Quad block2 = new Quad(100, 100, 700, 15, new RGB(0, 1, 0, 1));
		Quad block3 = new Quad(500, 300, 40, 200, new RGB(0, 1, 0, 1));
		
		//game.customMap(makeMap(map));
		
		game.addPlayer(p1);
		game.addQuad(block);
		game.addQuad(block2);
		game.addQuad(block3);
		
		
		
		while (!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			
			
			game.checkPlayerMovement();
			game.checkLegalPos();
			game.drawPlayers();
			game.drawQuads();
			
			updateAndSync(60);
		}
	}
	
}
