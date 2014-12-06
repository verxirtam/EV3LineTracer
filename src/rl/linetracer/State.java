package rl.linetracer;

public class State
{
	public double RefMax;
	public int ControlCount;
	public State()
	{
		
	}
	public State(double r,int cc)
	{
		RefMax = r;
		ControlCount = cc;
	}
	public State(State state)
	{
		this.RefMax = state.RefMax;
		this.ControlCount = state.ControlCount;
	}
}
