package net.kerfuffle.Jame;

public class RGB {

	private float r, g, b, a;
	
	public RGB(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void setColor(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public float[] getParts()
	{
		return new float[]{r, g, b, a};
	}
	
}
