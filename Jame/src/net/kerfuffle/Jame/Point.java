package net.kerfuffle.Jame;

public class Point {

	private float x, y;
	
	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setPos(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float x()
	{
		return x;
	}
	public float y()
	{
		return y;
	}
	
}
