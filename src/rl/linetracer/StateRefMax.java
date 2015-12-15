package rl.linetracer;

public class StateRefMax extends State
{
	public double RefMax;

	public StateRefMax(double r,int cc)
	{
		RefMax = r;
		ControlCount = cc;
	}
}
