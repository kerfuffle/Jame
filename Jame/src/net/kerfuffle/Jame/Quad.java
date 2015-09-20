package net.kerfuffle.Jame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static net.kerfuffle.Jame.Util.*;

public class Quad {

	private float x, y, w, h;
	
	private RGB color;
	private Texture texture;
	
	private boolean collide = false;
	
	public Quad(float x, float y, float w, float h, RGB color)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.color = color;
	}
	
	public boolean canCollide()
	{
		return collide;
	}
	
	public void setCollide(boolean collide)
	{
		this.collide = collide;
	}
	
	public void draw()
	{
		color(color);
		quad(x, y, w, h);
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
	
	public float x()
	{
		return this.x;
	}
	public float y()
	{
		return this.y;
	}
	public float w()
	{
		return this.w;
	}
	public float h()
	{
		return this.h;
	}
	
	
	
}
