package rl.linetracer;

public class Control
{
	public int LMotorSpeed;
	public int RMotorSpeed;
	public Control()
	{
		
	}
	public Control(int l,int r)
	{
		LMotorSpeed = l;
		RMotorSpeed = r;
	}
	public Control(Control c)
	{
		LMotorSpeed = c.LMotorSpeed;
		RMotorSpeed = c.RMotorSpeed;
	}
}
