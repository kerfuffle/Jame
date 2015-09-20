package net.kerfuffle.Jame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static net.kerfuffle.Jame.Util.*;

public class Player {

	private float x, y, w, h;
	private float speed;
	
	private RGB color;
	private Texture texture;
	
	private int up, down, left, right;
	private String name;
	
	public Player(float x, float y, float w, float h, RGB color)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.color = color;
	}
	
	public boolean outLeft(Quad q)
	{
		if (x < q.x())
		{
			return true;
		}
		return false;
	}
	
	public boolean outRight(Quad q)
	{
		if (x+w > q.x() + q.w())
		{
			return true;
		}
		return false;
	}
	
	public boolean outUp(Quad q)
	{
		if (y+h > q.y()+q.h())
		{
			return true;
		}
		return false;
	}
	
	public boolean outDown(Quad q)
	{
		if (y < q.y())
		{
			return true;
		}
		return false;
	}
	
	public boolean hitLeft(Quad q)
	{
		if (x < q.x()+q.w() && x > q.x()+(q.w()/2) && ((y < q.y()+q.h() && y > q.y()) || (y+h < q.y()+q.h() && y+h > q.y()) || (q.y() >= y && q.y()+q.h() <= y+h)) && x+(w/8) > q.x()+q.w())
		{
			return true;
		}
		return false;
	}
	
	public boolean hitRight(Quad q)
	{
		if (x+w > q.x() && x+w < q.x()+(q.w()/2) && ((y < q.y()+q.h() && y > q.y()) || (y+h < q.y()+q.h() && y+h > q.y()) || (q.y() >= y && q.y()+q.h() <= y+h)) && x+w-(w/8) < q.x())
		{
			return true;
		}
		return false;
	}
	
	public boolean hitUp(Quad q)
	{
		if (y < q.y()+q.h() && y > q.y()+(q.h()/2) && ((x < q.x()+q.w() && x > q.x()) || (x+w < q.x()+q.w() && x+w > q.x()) || (q.x() >= x && q.x()+q.w() <= x+w)) && y+(h/8) > q.y()+q.h())
		{
			return true;
		}
		return false;
	}

	public boolean hitDown(Quad q)
	{
		if (y+h < q.y()+(q.h()/2) && y+h > q.y() && ((x < q.x()+q.w() && x > q.x()) || (x+w < q.x()+q.w() && x+w > q.x()) || (q.x() >= x && q.x()+q.w() <= x+w)) && y+h-(w/8) < q.y())
		{
			return true;
		}
		return false;
	}
	
	
	public void draw()
	{
		color(color);
		quad(x, y, w, h);
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void checkMovement()
	{
		if (Keyboard.isKeyDown(up))
		{
			y += speed;
		}
		if (Keyboard.isKeyDown(down))
		{
			y -= speed;
		}
		if (Keyboard.isKeyDown(left))
		{
			x -= speed;
		}
		if (Keyboard.isKeyDown(right))
		{
			x += speed;
		}
	}
	
	public void setKeys(int up, int down, int left, int right)
	{
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public void setColor(RGB color)
	{
		this.color = color;
	}
	
	public void setPos(float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean checkClick()
	{
		if (Mouse.getX() > x && Mouse.getX() < x + w && Mouse.getY() > y && Mouse.getY() < y + h)
		{
			return true;
		}
		return false;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public void setW(float w)
	{
		this.w = w;
	}
	public void setH(float h)
	{
		this.h = h;
	}
	
	public float x()
	{
		return x;
	}
	public float y()
	{
		return y;
	}
	public float w()
	{
		return w;
	}
	public float h()
	{
		return h;
	}
	
}
