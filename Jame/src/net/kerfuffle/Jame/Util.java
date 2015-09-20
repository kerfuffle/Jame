package net.kerfuffle.Jame;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Util {

	static boolean keyStates[] = new boolean [256];
	static boolean mouseStates[] = new boolean [2];
	
	static void color(RGB color)
	{
		float temp[] = color.getParts();
		glColor3f(temp[0], temp[1], temp[2]);
	}
	
	static ArrayList<Quad> makeMap(String str)
	{
		ArrayList<Quad> ret = new ArrayList<Quad>();
		
		String sp[] = flipArray(str.split("\n"));
		
		for (int i = 0 ; i < Main.height/50; i++)
		{
			for (int j = 0; j < Main.width/50; j++)
			{
				if (sp[i].charAt(j) == '*')
				{
					Quad q = new Quad(j*50, i*50, 50, 50, new RGB(0, 1, 0, 1));
					ret.add(q);
				}
			}
		}
		
		return ret;
	}
	
	static String[] flipArray(String str[])
	{
		String ret[] = new String[str.length];
		
		int count = 0;
		for (int i = str.length-1; i > -1; i--)
		{
			ret[count] = str[i];
			count++;
		}
		
		return ret;
	}
	
	/*static class RGB
	{
		float r = 1, g = 1, b = 1;
	};
	
	static class RGBA
	{
		float r = 1, g = 1, b = 1, a = 1;
	};
	
	static class Quad
	{
		float x, y, w, h;
		boolean canCollide = false;
		boolean visible = true;
		RGB color;
	};
	
	static class Button
	{
		Quad box;
		String str;
		RGB highlight;
	};*/
	
	static class Bitmap
	{
		int w = 16, h = 26;
		Texture tex;
		String charTable;
	}
	
	/*static void drawBitmapString(String str, float x, float y, RGB color, Bitmap map)
	{
		int counterX = 0, counterY = 0;
		
		char cstr[] = str.toCharArray();
		Vector <Quad> background = new Vector <Quad>();
		
		for (int i = background.size(); i < cstr.length; i++)
		{
			Quad q = new Quad(0, 0, 0, 0, color);
			background.add(q);
		}
		
		for (int i = 0; i < background.size(); i++)
		{
			if (cstr[i] == '\n')
			{
				counterY++;
				counterX = -1;
			}
			
			background.get(i).w = map.w;
			background.get(i).h = map.h;
			background.get(i).x = x + (counterX*(map.w/2 + 2));
			background.get(i).y = y - (counterY*map.h);
			
			int coord[] = getCharCoords(cstr[i], map);	//coord[0] = x, coord[1] = y, coord[2] = # of columns, cord[3] = # of rows
			
			float width = map.tex.getImageWidth();
			float height = map.tex.getImageHeight();
			
			float finalX = 1/(width/coord[0]);
			float finalY = 1/(height/coord[1]);
			
			float finalWidth = 1/(width/map.w);
			float finalHeight = 1/(height/map.h);
			
			glColor3f(1, 1, 1);
			glEnable(GL_TEXTURE_2D);
			map.tex.bind();
			setColor(color);
			glBegin(GL_QUADS);
			
			glTexCoord2f(finalX + finalWidth, finalY + finalHeight);
			
			glVertex2f(background.get(i).x, background.get(i).y);
			
			glTexCoord2f(finalX + finalWidth, finalY);
			
			glVertex2f(background.get(i).x, background.get(i).y + background.get(i).h);
			
			glTexCoord2f(finalX, finalY);
			
			glVertex2f(background.get(i).x + background.get(i).w, background.get(i).y + background.get(i).h);
			
			glTexCoord2f(finalX, finalY + finalHeight);
			
			glVertex2f(background.get(i).x + background.get(i).w, background.get(i).y);
			
			glEnd();
			glDisable(GL_TEXTURE_2D);
			
			counterX++;
		}
		
	}*/
	
	static void printAllInVec(Vector vec)
	{
		for (int i = 0; i < vec.size(); i++)
		{
			System.out.println(vec.get(i));
		}
	}
	
	public static Texture loadTex(String name)
	{
		try
		{
			return TextureLoader.getTexture("PNG", new FileInputStream(new File(name + ".png")));
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("File not found!");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	static int getHighestNumber(Vector <Integer> vec)
	{
		int ret = 0;
		
		for (int i = 1; i < vec.size(); i++)
		{
			if (vec.get(i) > vec.get(i-1))
			{
				ret = vec.get(i);
			}
			else
			{
				ret = vec.get(i-1);
			}
		}
		
		return ret;
	}
	
	static int[] getCharCoords(char c, Bitmap map)
	{
		int coord[] = new int[4];
		
		String line[] = map.charTable.split("\n");
		
		Vector <Integer> columns = new Vector<Integer>();
		Vector <Integer> rows = new Vector<Integer>();
		
		for (int i = 0; i < line.length; i++)
		{
			String sp[] = line[i].split(":");
			if (c == ':' && sp[0] == "\\")
			{
				coord[0] = Integer.parseInt(sp[1]);// * map.w;
				coord[1] = Integer.parseInt(sp[2]) * map.h;
			}
			else if (line[i].charAt(0) == c)
			{
				coord[0] = Integer.parseInt(sp[1]);// * map.w;
				coord[1] = Integer.parseInt(sp[2]) * map.h;
			}
			
			columns.add(Integer.parseInt(sp[1]));
			rows.add(Integer.parseInt(sp[2]));
		}
		
		coord[2] = getHighestNumber(columns) + 1;
		coord[3] = getHighestNumber(rows) + 1;
		
		if (isEven(coord[2]))
		{
			int mid = coord[2]/2;
			int mid2 = coord[2]/2 - 1;
			
			int disFromMid = Math.abs(coord[0]-mid2);
			
			if (coord[0] == mid)
			{
				coord[0] = mid2;
				coord[0] *= map.w;
			}
			else if (coord[0] == mid2)
			{
				coord[0] = mid;
				coord[0] *= map.w;
			}
			else if (coord[0] < mid)
			{
				disFromMid = Math.abs(coord[0]-mid2);
				coord[0] += disFromMid*2 + 1;
				coord[0] *= map.w;
			}
			else if (coord[0] > mid)
			{
				disFromMid = Math.abs(coord[0]-mid);
				coord[0] -= disFromMid*2 - 1;
				coord[0] *= map.w;
			}
		}
		return coord;
	}
	
	static boolean isEven(int i)
	{
		if (i % 2 == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*static String typeString(String str)
	{
		if (checkKey(Keyboard.KEY_BACK))
		{
			if (str.length() != 0)
			{
				str = backspace(str);
			}
		}
		else if (Util.checkKey(Keyboard.KEY_SLASH))
		{
			str += "/";
		}
		else if (Util.checkKey(Keyboard.KEY_BACKSLASH))
		{
			str += "\\";
		}
		else if (Util.checkKey(Keyboard.KEY_SEMICOLON))
		{
			str += ":";
		}
		else if (Util.checkKey(Keyboard.KEY_PERIOD))
		{
			str += ".";
		}
		else
		{
			for (char i = 'a'; i < 'z'; i++)
			{
				if (checkCharKey(i))
				{
					str += i;
				}
			}
			
			for (int i = 2; i <= 10; i++)
			{
				if (Util.checkKey(i))
				{
					int toAdd = i - 1;
					str += toAdd;
				}
			}
			if (Util.checkKey(Keyboard.KEY_0))
			{
				str += '0';
			}
			
		}
		
		return str;
	}*/
	
	/*static boolean checkCharKey(char c)
	{
		if (c == 'a')
		{
			if (checkKey(Keyboard.KEY_A))
			{
				return true;
			}
		}
		if (c == 'b')
		{
			if (checkKey(Keyboard.KEY_B))
			{
				return true;
			}
		}
		if (c == 'c')
		{
			if (checkKey(Keyboard.KEY_C))
			{
				return true;
			}
		}
		if (c == 'd')
		{
			if (checkKey(Keyboard.KEY_D))
			{
				return true;
			}
		}
		if (c == 'e')
		{
			if (checkKey(Keyboard.KEY_E))
			{
				return true;
			}
		}
		if (c == 'f')
		{
			if (checkKey(Keyboard.KEY_F))
			{
				return true;
			}
		}
		if (c == 'g')
		{
			if (checkKey(Keyboard.KEY_G))
			{
				return true;
			}
		}
		if (c == 'h')
		{
			if (checkKey(Keyboard.KEY_H))
			{
				return true;
			}
		}
		if (c == 'i')
		{
			if (checkKey(Keyboard.KEY_I))
			{
				return true;
			}
		}
		if (c == 'j')
		{
			if (checkKey(Keyboard.KEY_J))
			{
				return true;
			}
		}
		if (c == 'k')
		{
			if (checkKey(Keyboard.KEY_K))
			{
				return true;
			}
		}
		if (c == 'l')
		{
			if (checkKey(Keyboard.KEY_L))
			{
				return true;
			}
		}
		if (c == 'm')
		{
			if (checkKey(Keyboard.KEY_M))
			{
				return true;
			}
		}
		if (c == 'n')
		{
			if (checkKey(Keyboard.KEY_N))
			{
				return true;
			}
		}
		if (c == 'o')
		{
			if (checkKey(Keyboard.KEY_O))
			{
				return true;
			}
		}
		if (c == 'p')
		{
			if (checkKey(Keyboard.KEY_P))
			{
				return true;
			}
		}
		if (c == 'q')
		{
			if (checkKey(Keyboard.KEY_Q))
			{
				return true;
			}
		}
		if (c == 'r')
		{
			if (checkKey(Keyboard.KEY_R))
			{
				return true;
			}
		}
		if (c == 's')
		{
			if (checkKey(Keyboard.KEY_S))
			{
				return true;
			}
		}
		if (c == 't')
		{
			if (checkKey(Keyboard.KEY_T))
			{
				return true;
			}
		}
		if (c == 'u')
		{
			if (checkKey(Keyboard.KEY_U))
			{
				return true;
			}
		}
		if (c == 'v')
		{
			if (checkKey(Keyboard.KEY_V))
			{
				return true;
			}
		}
		if (c == 'w')
		{
			if (checkKey(Keyboard.KEY_W))
			{
				return true;
			}
		}
		if (c == 'x')
		{
			if (checkKey(Keyboard.KEY_X))
			{
				return true;
			}
		}
		if (c == 'y')
		{
			if (checkKey(Keyboard.KEY_Y))
			{
				return true;
			}
		}
		if (c == 'z')
		{
			if (checkKey(Keyboard.KEY_Z))
			{
				return true;
			}
		}
		
		return false;
	}
	
	static boolean checkCharKeyDown(char c)
	{
		if (c == ' ')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			{
				return true;
			}
		}
		if (c == 'a')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				return true;
			}
		}
		if (c == 'b')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_B))
			{
				return true;
			}
		}
		if (c == 'c')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_C))
			{
				return true;
			}
		}
		if (c == 'd')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				return true;
			}
		}
		if (c == 'e')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_E))
			{
				return true;
			}
		}
		if (c == 'f')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_F))
			{
				return true;
			}
		}
		if (c == 'g')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_G))
			{
				return true;
			}
		}
		if (c == 'h')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_H))
			{
				return true;
			}
		}
		if (c == 'i')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_I))
			{
				return true;
			}
		}
		if (c == 'j')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_J))
			{
				return true;
			}
		}
		if (c == 'k')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_K))
			{
				return true;
			}
		}
		if (c == 'l')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_L))
			{
				return true;
			}
		}
		if (c == 'm')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_M))
			{
				return true;
			}
		}
		if (c == 'n')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_N))
			{
				return true;
			}
		}
		if (c == 'o')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_O))
			{
				return true;
			}
		}
		if (c == 'p')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_P))
			{
				return true;
			}
		}
		if (c == 'q')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_Q))
			{
				return true;
			}
		}
		if (c == 'r')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				return true;
			}
		}
		if (c == 's')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				return true;
			}
		}
		if (c == 't')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_T))
			{
				return true;
			}
		}
		if (c == 'u')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_U))
			{
				return true;
			}
		}
		if (c == 'v')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_V))
			{
				return true;
			}
		}
		if (c == 'w')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				return true;
			}
		}
		if (c == 'x')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_X))
			{
				return true;
			}
		}
		if (c == 'y')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_Y))
			{
				return true;
			}
		}
		if (c == 'z')
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_Z))
			{
				return true;
			}
		}
		
		return false;
	}*/
	
	/*static String typeFloat(String tempR)
	{
		if (Util.checkKey(Keyboard.KEY_BACK))
		{
			if (tempR.length() != 0)
			{
				tempR = Util.backspace(tempR);
			}
		}
		else if (Util.checkKey(Keyboard.KEY_PERIOD))
		{
			tempR += ".";
		}
		else
		{
			if (tempR.length() < 6)
			{
				for (int i = 2; i <= 10; i++)
				{
					if (Util.checkKey(i))
					{
						int toAdd = i - 1;
						tempR += toAdd;
					}
				}
				if (Util.checkKey(Keyboard.KEY_0))
				{
					tempR += '0';
				}
			}
		}
		return tempR;
	}*/
	
	/*static void drawQuad(Quad q)
	{
		setColor(q.color);
		quad(q.x, q.y, q.w, q.h);
	}*/
	
	/*static RGB assignColor(float r, float g, float b)
	{
		RGB rgb = new RGB();
		rgb.r = r;
		rgb.g = g;
		rgb.b = b;
		return rgb;
	}
	
	static RGBA assignColorAndOpacity(float r, float g, float b, float a)
	{
		RGBA rgba = new RGBA();
		rgba.r = r;
		rgba.g = g;
		rgba.b = b;
		rgba.a = a;
		return rgba;
	}*/
	
	/*static void setColor(RGB rgb)
	{
		glColor3f(rgb.r, rgb.g, rgb.b);
	}*/
	
	/*static void setColorAndOpacity(RGBA rgba)
	{
		glColor4f(rgba.r, rgba.g, rgba.b, rgba.a);
	}*/
	
	static Vector<Character> stringToVec(String str)
	{
		Vector <Character> vec = new Vector<Character>();
		for (int i = 0; i < str.length(); i++)
		{
			vec.add(str.charAt(i));
		}
		return vec;
	}
	
	static String backspace(String str)
	{
		if (str.length() != 0)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(str);
			sb.deleteCharAt(str.length() - 1);
			str = sb.toString();
		}
		return str;
	}
	
	
	static int getIntValueFromBool(boolean bool)
	{
		if (bool)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	static boolean getBoolValueFromInt(int i)
	{
		if (i == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	static String vecStringToString(Vector<String> s)
	{
		String str = null;
		for (int i = 0; i < s.size(); i++)
		{
			str += s.get(i);
		}
		return str;
	}
	
	public static void outFile(String str, String name)
	{	
		PrintWriter out = null;
		try
		{
			out = new PrintWriter (new FileWriter(name));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) == '\n')
			{
				out.println();
			}
			else
			{
				out.print(str.charAt(i));
			}
		}
		out.close();
	}
	
	public static String inFile(String name)
	{
		Vector<Character> c = new Vector<Character>();
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(name));
			int r;
			while((r = in.read()) != -1)
			{
				c.add((char) r);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String ret = vecToString(c);
		
		if (ret != null)
		{
			if (ret.contains("null"))
			{
				String fix[] = ret.split("null");
				return fix[fix.length - 1];
			}
			else
			{
				return ret;
			}
		}
		
		return null;
	}
	
	public static String vecToString(Vector vec)
	{
		String out = null;
		for (int i = 0; i < vec.size(); i++)
		{
			out += vec.get(i);
		}
		return out;
	}
	
	
	static void quad(float x, float y, float w, float h)
	{
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x, y + h);
		glVertex2f(x + w, y + h);
		glVertex2f(x + w, y);
		glEnd();
	}
	
	static boolean checkKey(int i)
	{
		if (Keyboard.isKeyDown(i) != keyStates[i])
		{
			return keyStates[i] = !keyStates[i];
		}
		else
		{
			return false;
		}
	}
	
	/*static boolean onHover(float x, float y, float w, float h)
	{
		int mouseX = Mouse.getX() + Game.originX;
		int mouseY = Mouse.getY() + Game.originY;
		
		if ((mouseX >= x && mouseX <= x + w) && (mouseY >= y && mouseY <= y + h))
		{
			return true;
		}
		else
		{
			return false;
		}
	}8/
	
	/*static boolean onHover(Button b)
	{
		if (onHover(b))
		{
			return true;
		}
		else
		{
			return false;
		}
	}*/
	
	/*static boolean onRClick(int x, int y, int w, int h)
	{
		if (onHover(x, y, w, h) && checkMouse(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static boolean onClickandDrag(int x, int y, int w, int h)
	{
		if (onHover(x, y, w, h) && Mouse.isButtonDown(0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static boolean onClick(float x, float y, float w, float h)
	{
		if (onHover(x, y, w, h) && checkMouse(0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*static boolean onClick(Button b)
	{
		if (onClick(b.box.x, b.box.y, b.box.w, b.box.h))
		{
			return true;
		}
		else
		{
			return false;
		}
	}*/

	/**
	 * Checks the state of a button (Being clicked, Mouse hovering, or nothing) and returns the respective numbers (0, 1, 2)
	 */
	/*static int checkButton(Button b)
	{
		if (onClick(b))
		{
			return 0;
		}
		else if (onHover(b))
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}*/
	
	public static void initGL()
	{
		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();		
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		//glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glEnable(GL_ALPHA);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glClearColor(0, 0, 0, 1);

		glDisable(GL_DEPTH_TEST);
	}
	
	public static void setDisplay(int width, int height, String name)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode (width, height));
			Display.setTitle(name);
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			
		}
		initGL();
	}
	
	public static void updateAndSync(int fps)
	{
		Display.update();
		Display.sync(fps);
	}
	
	static boolean checkMouse(int i)
	{
		if (Mouse.isButtonDown(i) != mouseStates[i])
		{
			return mouseStates[i] = !mouseStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static void drawString(String s, float x, float y) {
		float startX = x;
		GL11.glBegin(GL11.GL_POINTS);
		//glColor3f(0f, 0f, 0f);
		if (s.equals("!^"))
		{
			for (int i = 0; i<=10; i++)
			{
				glVertex2f(x - i, y + i);
				glVertex2f(x + i, y + i);
			}
		}
		else
		{
			for (char c : s.toLowerCase().toCharArray()) {

				if (c=='/')
				{
					for (int i = 0; i <= 8; i ++)
					{
						glVertex2f(x + i, y + i);
					}
					x += 8;
				}
				else if (c== '\\')
				{
					for (int i = 0; i <= 8; i++)
					{
						glVertex2f(8 + (x - i), y + i);
					}
					x += 8;
				}
				else if (c == '<')
				{
					for (int i =0; i <= 10; i++)
					{
						glVertex2f(x + i, y + i);
					}
					for (int i=0; i <=10; i++)
					{
						glVertex2f(x + i, y - i);
					}
					x += 8;
				}

				else if (c == '>')
				{
					for (int i=0; i <= 10; i++)
					{
						glVertex2f(x - i, y + i);
					}
					for (int i=0; i <= 10; i++)
					{
						glVertex2f(x - i, y - i);
					}
					x += 8;
				}

				else if (c== '^')
				{
					for (int i = 0; i <= 10; i++)
					{
						glVertex2f(x - i, y - i);
					}
					for (int i = 0; i <=10; i++)
					{
						glVertex2f(x + i, y - i);
					}
					x += 8;
				}

				else if (c == '-')
				{
					for (int i = 0; i <= 6; i ++)
					{
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				}

				else if (c == '=')
				{
					for (int i = 0; i <= 6; i++)
					{
						GL11.glVertex2f(x + i, y + 2);
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				}
				else if (c == ':')
				{
					GL11.glVertex2f(x + 1.5f, y);
					GL11.glVertex2f(x + 2.5f, y);
					GL11.glVertex2f(x + 1.5f, y + 1);
					GL11.glVertex2f(x + 2.5f, y + 1);

					GL11.glVertex2f(x + 1.5f, y + 7);
					GL11.glVertex2f(x + 2.5f, y + 7);
					GL11.glVertex2f(x + 2.5f, y + 6);
					GL11.glVertex2f(x + 1.5f, y + 6);
					
					x += 8;
				}
				else if (c == 'a') {
					for (int i = 0; i < 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'b') {
					for (int i = 0; i < 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 4);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 7, y + 5);
					GL11.glVertex2f(x + 7, y + 7);
					GL11.glVertex2f(x + 7, y + 6);
					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 3);
					x += 8;
				} else if (c == 'c') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 6, y + 2);

					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 7);

					x += 8;
				} else if (c == 'd') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 6, y + 2);
					GL11.glVertex2f(x + 6, y + 3);
					GL11.glVertex2f(x + 6, y + 4);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 7);

					x += 8;
				} else if (c == 'e') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 0);
						GL11.glVertex2f(x + i, y + 8);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'f') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'g') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 6, y + 2);
					GL11.glVertex2f(x + 6, y + 3);
					GL11.glVertex2f(x + 5, y + 3);
					GL11.glVertex2f(x + 7, y + 3);

					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 7);

					x += 8;
				} else if (c == 'h') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'i') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 3, y + i);
					}
					for (int i = 1; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 0);
						GL11.glVertex2f(x + i, y + 8);
					}
					x += 7;
				} else if (c == 'j') {
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 6, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 0);
					}
					GL11.glVertex2f(x + 1, y + 3);
					GL11.glVertex2f(x + 1, y + 2);
					GL11.glVertex2f(x + 1, y + 1);
					x += 8;
				} else if (c == 'k') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					GL11.glVertex2f(x + 6, y + 8);
					GL11.glVertex2f(x + 5, y + 7);
					GL11.glVertex2f(x + 4, y + 6);
					GL11.glVertex2f(x + 3, y + 5);
					GL11.glVertex2f(x + 2, y + 4);
					GL11.glVertex2f(x + 2, y + 3);
					GL11.glVertex2f(x + 3, y + 4);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 5, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 7, y);
					x += 8;
				} else if (c == 'l') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					x += 7;
				} else if (c == 'm') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					GL11.glVertex2f(x + 3, y + 6);
					GL11.glVertex2f(x + 2, y + 7);
					GL11.glVertex2f(x + 4, y + 5);

					GL11.glVertex2f(x + 5, y + 6);
					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 4, y + 5);
					x += 8;
				} else if (c == 'n') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					GL11.glVertex2f(x + 2, y + 7);
					GL11.glVertex2f(x + 2, y + 6);
					GL11.glVertex2f(x + 3, y + 5);
					GL11.glVertex2f(x + 4, y + 4);
					GL11.glVertex2f(x + 5, y + 3);
					GL11.glVertex2f(x + 6, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					x += 8;
				} else if (c == 'o' || c == '0') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 0);
					}
					x += 8;
				} else if (c == 'p') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 4);
					}
					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 6, y + 6);
					x += 8;
				} else if (c == 'q') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
						if (i != 1)
							GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						if (i != 6)
							GL11.glVertex2f(x + i, y + 0);
					}
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 5, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 7, y);
					x += 8;
				} else if (c == 'r') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 4);
					}
					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 6, y + 6);

					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 5, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 7, y);
					x += 8;
				} else if (c == 's') {
					for (int i = 2; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 1, y + 7);
					GL11.glVertex2f(x + 1, y + 6);
					GL11.glVertex2f(x + 1, y + 5);
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
						GL11.glVertex2f(x + i, y);
					}
					GL11.glVertex2f(x + 7, y + 3);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 1, y + 1);
					GL11.glVertex2f(x + 1, y + 2);
					x += 8;
				} else if (c == 't') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					x += 7;
				} else if (c == 'u') {
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 0);
					}
					x += 8;
				} else if (c == 'v') {
					for (int i = 2; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 6, y + i);
					}
					GL11.glVertex2f(x + 2, y + 1);
					GL11.glVertex2f(x + 5, y + 1);
					GL11.glVertex2f(x + 3, y);
					GL11.glVertex2f(x + 4, y);
					x += 7;
				} else if (c == 'w') {
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					GL11.glVertex2f(x + 2, y);
					GL11.glVertex2f(x + 3, y);
					GL11.glVertex2f(x + 5, y);
					GL11.glVertex2f(x + 6, y);
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					x += 8;
				} else if (c == 'x') {
					for (int i = 1; i <= 7; i++)
						GL11.glVertex2f(x + i, y + i);
					for (int i = 7; i >= 1; i--)
						GL11.glVertex2f(x + i, y + 8 - i);
					x += 8;
				} else if (c == 'y') {
					GL11.glVertex2f(x + 4, y);
					GL11.glVertex2f(x + 4, y + 1);
					GL11.glVertex2f(x + 4, y + 2);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 4, y + 4);

					GL11.glVertex2f(x + 3, y + 5);
					GL11.glVertex2f(x + 2, y + 6);
					GL11.glVertex2f(x + 1, y + 7);
					GL11.glVertex2f(x + 1, y + 8);

					GL11.glVertex2f(x + 5, y + 5);
					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 7, y + 7);
					GL11.glVertex2f(x + 7, y + 8);
					x += 8;
				} else if (c == 'z') {
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + i);
					}
					GL11.glVertex2f(x + 6, y + 7);
					x += 8;
				} else if (c == '1') {
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					GL11.glVertex2f(x + 3, y + 7);
					x += 8;
				} else if (c == '2') {
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 1, y + 7);
					GL11.glVertex2f(x + 1, y + 6);

					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 5, y + 4);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 3, y + 2);
					GL11.glVertex2f(x + 2, y + 1);
					x += 8;
				} else if (c == '3') {
					for (int i = 1; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 6, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == '4') {
					for (int i = 2; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 1);
					}
					for (int i = 0; i <= 4; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					x += 8;
				} else if (c == '5') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					for (int i = 4; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					GL11.glVertex2f(x + 1, y + 1);
					GL11.glVertex2f(x + 2, y);
					GL11.glVertex2f(x + 3, y);
					GL11.glVertex2f(x + 4, y);
					GL11.glVertex2f(x + 5, y);
					GL11.glVertex2f(x + 6, y);

					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 3);

					GL11.glVertex2f(x + 6, y + 4);
					GL11.glVertex2f(x + 5, y + 4);
					GL11.glVertex2f(x + 4, y + 4);
					GL11.glVertex2f(x + 3, y + 4);
					GL11.glVertex2f(x + 2, y + 4);
					x += 8;
				} else if (c == '6') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 3);
					GL11.glVertex2f(x + 6, y + 4);
					x += 8;
				} else if (c == '7') {
					for (int i = 0; i <= 7; i++)
						GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + 7, y + 7);
					GL11.glVertex2f(x + 7, y + 6);

					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 5, y + 4);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 3, y + 2);
					GL11.glVertex2f(x + 2, y + 1);
					GL11.glVertex2f(x + 1, y);
					x += 8;
				} else if (c == '8') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 0);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == '9') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 5; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 0);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					GL11.glVertex2f(x + 1, y + 0);
					x += 8;
				} else if (c == '.') {
					GL11.glVertex2f(x + 1, y);
					x += 2;
				} else if (c == ',') {
					GL11.glVertex2f(x + 1, y);
					GL11.glVertex2f(x + 1, y + 1);
					x += 2;
				} else if (c == '\n') {
					y -= 10;
					x = startX;
				} else if (c == ' ') {
					x += 8;
				}
			}
		}
		GL11.glEnd();
	}
	
	/*static void save() throws IOException
	{
		 PrintWriter out = null;
		 try
		 {
			  out = new PrintWriter(new FileWriter("coords.txt"));
			  
			  String coords = Main.x + ", " + Main.y;
			 
			  out.println(coords);
		 } 
		 finally 
		 {
			 if (out != null) 
			 {
				 out.close();
	         }
		 }
	}
	
	static void open() throws IOException
	{
		 BufferedReader in = null;
		 try
		 {
			 in = new BufferedReader(new FileReader("coords.txt"));
			 
			 Main.coords = in.readLine();
		 }
		 finally 
		 {
			 if (in != null) 
			 {
				 in.close();
	         }
		 }
	}
	
	static void refresh()
	{
		String sp[] = Main.coords.split(", ");
		Main.x = Integer.parseInt(sp[0]);
		Main.y = Integer.parseInt(sp[1]);
	}*/
	
}

