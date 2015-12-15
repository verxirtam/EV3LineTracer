package rl.linetracer;

public class ControlNormal extends Control
{

	public int LMotorSpeed;
	public int RMotorSpeed;
	public ControlNormal()
	{
		
	}
	public ControlNormal(int l,int r)
	{
		LMotorSpeed = l;
		RMotorSpeed = r;
	}
	public ControlNormal(ControlNormal c)
	{
		LMotorSpeed = c.LMotorSpeed;
		RMotorSpeed = c.RMotorSpeed;
	}

}
